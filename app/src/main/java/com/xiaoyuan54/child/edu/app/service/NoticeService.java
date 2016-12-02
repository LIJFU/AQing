package com.xiaoyuan54.child.edu.app.service;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;

import com.xiaoyuan54.child.edu.app.AppConfig;
import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.Constants;
import com.xiaoyuan54.child.edu.app.bean.Notice;
import com.xiaoyuan54.child.edu.app.bean.NoticeDetail;
import com.xiaoyuan54.child.edu.app.bean.Result;
import com.xiaoyuan54.child.edu.app.bean.ResultBean;
import com.xiaoyuan54.child.edu.app.broadcast.AlarmReceiver;

import net.oschina.app.service.INoticeService;

import com.xiaoyuan54.child.edu.app.ui.main.MainActivity;
import com.xiaoyuan54.child.edu.app.util.UIHelper;
import com.xiaoyuan54.child.edu.app.util.XmlUtils;

import cz.msebera.android.httpclient.Header;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class NoticeService extends Service {
    public static final String INTENT_ACTION_GET = "net.oschina.app.service.GET_NOTICE";
    public static final String INTENT_ACTION_CLEAR = "net.oschina.app.service.CLEAR_NOTICE";
    public static final String INTENT_ACTION_BROADCAST = "net.oschina.app.service.BROADCAST";
    public static final String INTENT_ACTION_SHUTDOWN = "net.oschina.app.service.SHUTDOWN";
    public static final String INTENT_ACTION_REQUEST = "net.oschina.app.service.REQUEST";
    public static final String BUNDLE_KEY_TPYE = "bundle_key_type";

    private static final long INTERVAL = 1000 * 120;
    private AlarmManager mAlarmMgr;

    private Notice mNotice;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Constants.INTENT_ACTION_NOTICE.equals(action)) {
        	Notice notice = (Notice) intent.getSerializableExtra("notice_bean");
                int atmeCount = notice.getAtmeCount();// @我
                int msgCount = notice.getMsgCount();// 私信
                int reviewCount = notice.getReviewCount();// 评论
                int newFansCount = notice.getNewFansCount();// 新粉丝
                int newLikeCount = notice.getNewLikeCount();// 点赞数
                int activeCount = atmeCount + reviewCount + msgCount
                        + newFansCount + newLikeCount;
                if (activeCount == 0) {
                    NotificationManagerCompat.from(NoticeService.this).cancel(
                            R.string.you_have_news_messages);
                }
            } else if (INTENT_ACTION_BROADCAST.equals(action)) {
                if (mNotice != null) {
                    UIHelper.sendBroadCast(NoticeService.this, mNotice);
                }
            } else if (INTENT_ACTION_SHUTDOWN.equals(action)) {
                stopSelf();
            } else if (INTENT_ACTION_REQUEST.equals(action)) {
                requestNotice();
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAlarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        startRequestAlarm();
        requestNotice();

        IntentFilter filter = new IntentFilter(INTENT_ACTION_BROADCAST);
        filter.addAction(Constants.INTENT_ACTION_NOTICE);
        filter.addAction(INTENT_ACTION_SHUTDOWN);
        filter.addAction(INTENT_ACTION_REQUEST);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        cancelRequestAlarm();
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void startRequestAlarm() {
        cancelRequestAlarm();
        // 从1秒后开始，每隔2分钟执行getOperationIntent()
        mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 1000, INTERVAL,
                getOperationIntent());
    }

    /**
     * <!-- kymjs --> 即使启动PendingIntent的原进程结束了的话,PendingIntent本身仍然还存在，可在其他进程（
     * PendingIntent被递交到的其他程序）中继续使用.
     * 如果我在从系统中提取一个PendingIntent的，而系统中有一个和你描述的PendingIntent对等的PendingInent,
     * 那么系统会直接返回和该PendingIntent其实是同一token的PendingIntent，
     * 而不是一个新的token和PendingIntent。然而你在从提取PendingIntent时，通过FLAG_CANCEL_CURRENT参数，
     * 让这个老PendingIntent的先cancel()掉，这样得到的pendingInten和其token的就是新的了。
     */
    private void cancelRequestAlarm() {
        mAlarmMgr.cancel(getOperationIntent());
    }

    /**
     * OSC采用轮询方式实现消息推送<br>
     *
     * @return
     */
    private PendingIntent getOperationIntent() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return operation;
    }

    private void clearNotice(int uid, int type) {
        HttpClientApi.clearNotice(uid, type, mClearNoticeHandler);
    }

    private int lastNotifiyCount;

    private void notification(Notice notice) {
    }

    private final AsyncHttpResponseHandler mGetNoticeHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            try {
                Notice notice = XmlUtils.toBean(NoticeDetail.class,
                	arg2).getNotice();
                if (notice != null) {
                    UIHelper.sendBroadCast(NoticeService.this, notice);
                    if (AppContext.get(AppConfig.KEY_NOTIFICATION_ACCEPT, true)) {
                        notification(notice);
                    }
                    mNotice = notice;
                } else {
//                    ResultBean resultBean = XmlUtils.toBean(ResultBean.class, arg2);
//                    if (resultBean != null && resultBean.getResult() != null) {
//                	AppContext appContext = AppContext.getInstance();
//                	if (appContext != null) {
//                	    appContext.Logout();
//                	}
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(arg0, arg1, arg2, e);
            }
        };

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                Throwable arg3) {
            arg3.printStackTrace();
        }
    };

    private final AsyncHttpResponseHandler mClearNoticeHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            try {
                ResultBean rsb = XmlUtils.toBean(ResultBean.class,
                        new ByteArrayInputStream(arg2));
                Result res = rsb.getResult();
                if (res.OK() && rsb.getNotice() != null) {
                    mNotice = rsb.getNotice();
                    UIHelper.sendBroadCast(NoticeService.this, rsb.getNotice());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                Throwable arg3) {}
    };

    /**
     * 请求是否有新通知
     */
    private void requestNotice() {
        HttpClientApi.getNotices(mGetNoticeHandler);
    }

    private static class ServiceStub extends INoticeService.Stub {
        WeakReference<NoticeService> mService;

        ServiceStub(NoticeService service) {
            mService = new WeakReference<NoticeService>(service);
        }

        @Override
        public void clearNotice(int uid, int type) throws RemoteException {
            mService.get().clearNotice(uid, type);
        }

        @Override
        public void scheduleNotice() throws RemoteException {
            mService.get().startRequestAlarm();
        }

        @Override
        public void requestNotice() throws RemoteException {
            mService.get().requestNotice();
        }
    }

    private final IBinder mBinder = new ServiceStub(this);
}

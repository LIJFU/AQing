package com.xiaoyuan54.child.edu.app.ui.update.service;

import android.content.Context;
import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.AppDirManager;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.base.service.BaseService;
import com.xiaoyuan54.child.edu.app.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.bean.version.Version;
import com.xiaoyuan54.child.edu.app.dao.impl.VersionDao;
import com.xiaoyuan54.child.edu.app.util.TDevice;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

/**
 * Created by L.QING on 2016-11-27.
 */

public class UpdateServiceAccessRandom extends BaseService {

    public final static String update_app_action = "com.54xiaoyuan.update.install.app.action";

    public Version version;

    public Thread downThread;

    private VersionDao versionDao;

    private int is_finish = 1;

    public static void start(Context context)
    {
       Intent intent = new Intent(context,UpdateServiceAccessRandom.class);
       context.startService(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        initService();
        checkVersion();
        return super.onStartCommand(intent, flags, startId);
    }


    private void initService()
    {
        versionDao = VersionDao.instance();
    }


    private void checkVersion()
    {
        HttpClientApi.checkUpdate(checkUpdateHandler);
    }


    /**
     * check this version app was downloaded
     */
    public void checkDownload(Version version)
    {
        Version old  = versionDao.getOne();
        if(null==old)
        {
            old = version;
            old.setId(1);
            old.setRangeSize(0l);
            versionDao.insert(old);
        }
        else if(old.getVersionCode()==version.getVersionCode())
        {
            /**
             * 已经下载完成，可能用户未安装
             */
            if(is_finish==old.getFinish())
            {
                File apk = new File(AppDirManager.APK
                        + File.separator+old.getVersionCode()
                        +"_"+old.getVersionName()+".apk");
                if(apk.exists())
                {
                    notifyInstall();
                    return;
                }

                //已删除，重新下载
                old.setFinish(0);
                old.setRangeSize(0l);

                Version update = new Version(old.getId());
                update.setFinish(0);
                update.setRangeSize(0l);
                versionDao.update(update);
            }
        }
        else
        {
            //两版本不一样
            version.setId(old.getId());
            versionDao.delete(old);
            old = version;
            old.setRangeSize(0l);
            versionDao.insert(old);
        }

        downThread = new Thread(new DownloadRunnable(old));
        downThread.start();
    }


    private void notifyInstall()
    {
       sendBroadcast(new Intent(update_app_action));
       stopItSelf();
    }


    public boolean haveNew(Version version)
    {
        boolean haveNew = false;
        int curVersionCode = TDevice.getVersionCode(AppContext
                .getInstance().getPackageName());
        if (curVersionCode < version.getVersionCode())
        {
            haveNew = true;
        }
        return haveNew;
    }

    private void stopItSelf()
    {
        stopSelf();
    }

    private TextHttpResponseHandler  checkUpdateHandler = new TextHttpResponseHandler() {

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
        {

        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString)
        {
            ResultBean<Version> result = AppContext.createGson().fromJson(responseString,new TypeToken<ResultBean<Version>>(){}.getType());
            if(result.isSuccess())
            {
                Version version = result.getItem();
                if(null==version||null==version.getVersionCode())
                {
                    //错误处理
                    return;
                }
                if(haveNew(version))
                {
                   checkDownload(version);
                }
                else
                {
                    stopItSelf();
                }
            }

        }
    };


    public class DownloadRunnable implements   Runnable
    {
        private Version version;
        private long downLength = 0;

        DownloadRunnable(Version old)
        {
            this.version = old;
        }

        @Override
        public void run()
        {
            try
            {
                RandomAccessFile fio = new RandomAccessFile(AppDirManager.APK
                        + File.separator+version.getVersionCode()
                        +"_"+version.getVersionName()+".apk","rwd");

                URL url = new URL(version.getDownload());
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setConnectTimeout(10 * 1000);
                http.setRequestMethod("GET");
                http.setRequestProperty("Accept", "application/x-ms-application");
                http.setRequestProperty("Accept-Language", "zh-CN");
                http.setRequestProperty("Charset", "UTF-8");
                http.setRequestProperty("Connection", "Keep-Alive");

              /*  if(version.getRangeSize()>0)
                {
                    downLength = version.getRangeSize();
                    fio.seek(downLength);
                   // http.setRequestProperty("Range", "bytes=" + downLength + "-");
                    http.setRequestProperty("RANGE", "bytes="
                            + downLength + "-");
                }
*/
                InputStream io = http.getInputStream();
                int bufferSize = 1024*10;
                byte[] buffer = new byte[bufferSize];
                int length =0;
                int updateDBOff = 52; //0.5M更新一次数据库
                while (-1!=(length = io.read(buffer,0,bufferSize)))
                {
                    fio.write(buffer,0,length);
                    downLength = downLength + length;
                   /*
                    updateDBOff = updateDBOff - 1;
                   if(0==updateDBOff)
                    {
                        Version update = new Version(version.getId());
                        update.setRangeSize(downLength);
                        versionDao.update(update);
                        updateDBOff = 256;
                    }*/
                }

                io.close();
                fio.close();


                Version update = new Version(version.getId());
                update.setFinish(is_finish);
                versionDao.update(update);
                notifyInstall();
            }
            catch (Exception e)
            {
                versionDao.delete(version);
            }
        }
    };


}

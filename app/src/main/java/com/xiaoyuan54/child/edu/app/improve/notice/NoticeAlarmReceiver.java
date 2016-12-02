package com.xiaoyuan54.child.edu.app.improve.notice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xiaoyuan54.child.edu.app.service.NoticeUtils;

/**
 * Created by JuQiu
 * on 16/8/19.
 */
public class NoticeAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("NoticeAlarmReceiver", "onReceived");
        NoticeUtils.requestNotice(context);
    }
}

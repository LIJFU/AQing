package com.xiaoyuan54.child.edu.app.broadcast;

import com.xiaoyuan54.child.edu.app.service.NoticeUtils;
import com.xiaoyuan54.child.edu.app.util.TLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		TLog.log("onReceive ->net.oschina.app收到定时获取消息");
		NoticeUtils.requestNotice(context);
	}
}

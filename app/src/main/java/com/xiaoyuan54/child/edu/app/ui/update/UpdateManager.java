package com.xiaoyuan54.child.edu.app.ui.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.Update;
import com.xiaoyuan54.child.edu.app.bean.version.Version;
import com.xiaoyuan54.child.edu.app.util.DialogHelp;
import com.xiaoyuan54.child.edu.app.util.TDevice;
import com.xiaoyuan54.child.edu.app.util.UIHelper;
import com.xiaoyuan54.child.edu.app.util.XmlUtils;

import java.io.ByteArrayInputStream;

import cz.msebera.android.httpclient.Header;

/**
 * 更新管理类
 */

public class UpdateManager {

    private Version version;

    private Context mContext;

    private boolean isShow = false;

    private ProgressDialog _waitDialog;

    private AsyncHttpResponseHandler mCheckUpdateHandle = new AsyncHttpResponseHandler() {

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            hideCheckDialog();
            if (isShow) {
                showFaileDialog();
            }
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
        {
            hideCheckDialog();

            version = new Version();
            version.setVersionCode(2);
            version.setVersionNote("更新了个人头像");
            onFinishCheck();
        }
    };

    public UpdateManager(Context context, boolean isShow) {
        this.mContext = context;
        this.isShow = isShow;
    }

    public boolean haveNew()
    {
        if (null==version) {
            return false;
        }
        boolean haveNew = false;
        int curVersionCode = TDevice.getVersionCode(AppContext
                .getInstance().getPackageName());
        if (curVersionCode < version.getVersionCode())
        {
            haveNew = true;
        }
        return haveNew;
    }

    public void checkUpdate()
    {
        if (isShow) {
            showCheckDialog();
        }
        //HttpClientApi.checkUpdate(mCheckUpdateHandle);
        //mCheckUpdateHandle.onSuccess(12,null,null);
    }

    private void onFinishCheck() {
        if (haveNew()) {
            showUpdateInfo();
        } else {
            if (isShow) {
                showLatestDialog();
            }
        }
    }

    private void showCheckDialog()
    {
        if (_waitDialog == null) {
            _waitDialog = DialogHelp.getWaitDialog((Activity) mContext, "正在获取新版本信息...");
        }
        _waitDialog.show();
    }

    private void hideCheckDialog() {
        if (_waitDialog != null) {
            _waitDialog.dismiss();
        }
    }

    private void showUpdateInfo() {
        if (version == null)
        {
            return;
        }
        AlertDialog.Builder dialog = DialogHelp.getConfirmDialog(mContext,version.getVersionNote(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
               // UIHelper.openDownLoadService(mContext,"", "");
            }
        });
        dialog.setTitle("发现新版本");
        dialog.show();
    }

    private void showLatestDialog() {
        DialogHelp.getMessageDialog(mContext, "已经是新版本了").show();
    }

    private void showFaileDialog() {
        DialogHelp.getMessageDialog(mContext, "网络异常，无法获取新版本信息").show();
    }
}

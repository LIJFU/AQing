package com.xiaoyuan54.child.edu.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.bean.user.User;
import com.xiaoyuan54.child.edu.app.dao.impl.UserDao;
import com.xiaoyuan54.child.edu.app.ui.FeedBackActivity;
import com.xiaoyuan54.child.edu.app.ui.login.LoginActivity;
import com.xiaoyuan54.child.edu.app.ui.main.MainActivity;
import com.xiaoyuan54.child.edu.app.util.PreferenceUtils;
import com.xiaoyuan54.child.edu.app.util.TDevice;
import com.xiaoyuan54.child.edu.app.util.UIHelper;

import org.kymjs.kjframe.utils.PreferenceHelper;

import cz.msebera.android.httpclient.Header;

/**
 * 应用启动界面
 */
public class AppStart extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }

        setContentView(R.layout.app_start);
        findViewById(R.id.app_start_view).postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
            }
        }, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int cacheVersion = PreferenceHelper.readInt(this, "first_install",
                "first_install", -1);
        int currentVersion = TDevice.getVersionCode();
        if (cacheVersion < currentVersion) {
            PreferenceHelper.write(this, "first_install", "first_install",
                    currentVersion);
        }
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        String mobile = PreferenceUtils.getString(LoginActivity.LOGIN_MOBILE,null);
        checkLoginState(mobile);
    }

    private void checkLoginState(String mobile) {
        if(mobile == null) {
            UIHelper.showLoginActivity(this);
        }
        User dbUser = UserDao.instance().findByMobile(mobile);
        if(dbUser != null){
            HttpClientApi.autoLogin(mobile, dbUser.getToken(), new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    throwable.printStackTrace();
                    UIHelper.showLoginActivity(AppStart.this);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    ResultBean<User> resultBean = AppContext.createGson().fromJson(responseString,new TypeToken<ResultBean<User>>(){}.getType());
                    if(resultBean.isSuccess()){
                        User item = resultBean.getItem();
                        UserDao.instance().modifyUserByMobile(item);
                        PreferenceUtils.put(LoginActivity.LOGIN_MOBILE,item.getMobile());
                        AppContext.appUser = item;
                        UIHelper.showMain(AppStart.this);
                    }else {
                        UIHelper.showLoginActivity(AppStart.this);
                    }
                }
            });
        }
    }
}

package com.xiaoyuan54.child.edu.app.ui.login;

import android.app.ProgressDialog;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xiaoyuan54.child.edu.app.AppConfig;
import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.base.BaseActivity;
import com.xiaoyuan54.child.edu.app.bean.user.User;
import com.xiaoyuan54.child.edu.app.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.dao.impl.UserDao;
import com.xiaoyuan54.child.edu.app.ui.login.widget.Countdown;
import com.xiaoyuan54.child.edu.app.util.DialogHelp;
import com.xiaoyuan54.child.edu.app.util.PreferenceUtils;
import com.xiaoyuan54.child.edu.app.util.StringUtils;
import com.xiaoyuan54.child.edu.app.util.UIHelper;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by m on 2016-11-27.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    public static final String LOGIN_MOBILE = "login_mobile";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    // layout View start
    @Bind(R.id.et_mobile)
    EditText mMobile;
    @Bind(R.id.et_login_code)
    EditText mCode;
    @Bind(R.id.bt_get_login_code)
    Button mGetCode;
    //layout View end

    //resource start
    public static final int millisInFuture = 50000;
    private int countDownInterval = 1000;
    private final int mGetCodeTouchedBg = R.drawable.login_button_login_code_touched;
    private final int mGetCodeNorBg = R.drawable.login_button_login_code;
    private final int mGetCodeHint = R.string.get_login_code;
    //resource end


    //widget start
    private Countdown<Button> codeCountDown;
    //widget end

    @Override
    @OnClick({R.id.bt_get_login_code,R.id.bt_login_commit})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login_commit:
                checkLogin();
                break;
            case R.id.bt_get_login_code:
                if(mGetCode.isClickable()) {
                    sendCodeRequest(mMobile.getText().toString());
                    mGetCode.setBackgroundDrawable(getResources().getDrawable(mGetCodeTouchedBg));
                    if(codeCountDown == null){
                        initCodeCountDown();
                    }
                    codeCountDown.start();
                    mGetCode.setClickable(false);
                }
                break;
        }
    }

    private void sendCodeRequest(String mobile) {
        if(mobile != null && StringUtils.isMobile(mobile)) {
            HttpClientApi.getMobileCode(mobile, new TextHttpResponseHandler() {
                private final String TAG = this.getClass().getSimpleName();
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    throwable.printStackTrace();
                    Toast.makeText(LoginActivity.this,"获取验证码失败",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.w(TAG, responseString);
                }
            });
        }
    }

    private void checkLogin() {
        String mobile = mMobile.getText().toString();
        String code = mCode.getText().toString();
        if(null == code || code.trim().equalsIgnoreCase("")){
            Toast.makeText(this,
                    getResources().getString(R.string.login_code_hint_error),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(!StringUtils.isMobile(mobile)){
            Toast.makeText(this,
                    getResources().getString(R.string.login_mobile_hint_error),
                    Toast.LENGTH_LONG).show();
            return;
        }
        final ProgressDialog dialog =DialogHelp.getWaitDialog(this,getResources().getString(R.string.progress_login));
        dialog.setCancelable(false);
        dialog.show();

        HttpClientApi.userLogin(mobile, code, new TextHttpResponseHandler() {
            private final String TAG = this.getClass().getSimpleName();

            @Override
            public void onFinish() {
                dialog.dismiss();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                Log.w(TAG,responseBody);
                try {
                    //Gson gson = new Gson();
                    //ResultBean<User> resultBean = gson.fromJson(responseBody,new TypeToken<ResultBean<User>>(){}.getType());
                    ResultBean<User> resultBean = AppContext.createGson().fromJson(responseBody,new TypeToken<ResultBean<User>>(){}.getType());
                    if(resultBean.isSuccess()){
                        User item = resultBean.getItem();
                        UserDao.instance().modifyUserByMobile(item);
                        PreferenceUtils.put(LOGIN_MOBILE,item.getMobile());
                        AppContext.appUser = item;
                        UIHelper.showMain(LoginActivity.this);
                    }else{
                        Toast.makeText(LoginActivity.this,"验证码错误",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    Log.e(TAG,"返回值解析错误"+responseBody);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initCodeCountDown(){
        codeCountDown = new Countdown<>(millisInFuture, countDownInterval, new Countdown.OnExecuteListener<Button>() {
            @Override
            public void onTick(Button button, long millisUntilFinished) {
                long seconds = millisUntilFinished/1000;
                button.setText(String.valueOf(seconds)+"s");
            }
            @Override
            public void onFinish(Button button) {
                button.setBackgroundDrawable(getResources().getDrawable(mGetCodeNorBg));
                button.setText(getResources().getString(mGetCodeHint));
                button.setClickable(true);
            }
        },mGetCode);
    }

    @Override
    public void initView() {
        mActionBar.hide();
    }

    @Override
    public void initData() {
    }
}

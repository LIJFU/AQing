package com.xiaoyuan54.child.edu.app.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by m on 2016-11-30.
 */

public class FeedBackActivity extends BaseActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    @Bind(R.id.tool_hint)
    TextView mToolHint;

    @Bind(R.id.action_button)
    Button mActive;

    @Bind(R.id.rb_feedback_error)
    RadioButton mFeedType;

    @Bind(R.id.et_feedback)
    EditText mFeedBackContent;

    private Context mContext;

    private Activity mActivity;

    private final String TITLE = "意见反馈";
    private final String ACTIVE_HINT = "提交";

    private void initToolBar(){
        mToolHint.setText(TITLE);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        mActive.setVisibility(View.VISIBLE);
        mActive.setText(ACTIVE_HINT);
        mActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"完成");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView() {
        mActionBar.hide();
        mContext = this;
        mActivity = this;
        initToolBar();
    }

    @Override
    public void initData() {

    }
}

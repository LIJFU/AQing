package com.xiaoyuan54.child.edu.app.ui.mime;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.base.BaseActivity;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/11/19.
 */

public class NameModifyActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    @Bind(R.id.tool_hint)
    TextView mToolHint;

    @Bind(R.id.action_button)
    Button mCommitButton;

    private Context mContext;

    private Activity mActivity;



    private final String TITLE = "修改昵称";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_name_modify;
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

    private void initToolBar(){
        mToolHint.setText(TITLE);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });

        mCommitButton.setVisibility(View.VISIBLE);
        mCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageDataCommit();
                mActivity.finish();
                //mActivity.onBackPressed();
            }
        });
    }

    private void pageDataCommit(){

    }

    @Override
    public void initData() {

    }
}

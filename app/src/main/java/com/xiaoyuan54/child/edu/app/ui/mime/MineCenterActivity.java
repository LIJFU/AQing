package com.xiaoyuan54.child.edu.app.ui.mime;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.base.BaseActivity;
import com.xiaoyuan54.child.edu.app.util.UIHelper;

import butterknife.Bind;

/**
 * Created by m on 2016-11-13.
 */

public class MineCenterActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    @Bind(R.id.tool_hint)
    TextView mToolHint;

    @Bind(R.id.lv_mine_info)
    LinearLayout mInfo;

    private Context mContext;

    private Activity mActivity;

    private final String TITLE = "个人中心";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_center;
    }

    private void initToolBar(){
        mToolHint.setText(TITLE);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lv_mine_info:
                UIHelper.showMineInfo(mContext);
                break;
        }

    }

    @Override
    public void initView() {
        mActionBar.hide();
        mContext = this;
        mActivity = MineCenterActivity.this;
        initToolBar();

        mInfo.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }
}

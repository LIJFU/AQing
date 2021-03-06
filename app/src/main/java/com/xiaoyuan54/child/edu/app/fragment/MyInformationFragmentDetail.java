package com.xiaoyuan54.child.edu.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.base.BaseFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.UserV2;
import com.xiaoyuan54.child.edu.app.ui.empty.EmptyLayout;
import com.xiaoyuan54.child.edu.app.util.StringUtils;

import org.kymjs.kjframe.Core;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录用户信息详情
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2015年1月6日 上午10:33:18
 */

public class MyInformationFragmentDetail extends BaseFragment {

    @Bind(R.id.iv_avatar)
    ImageView mUserFace;

    @Bind(R.id.tv_name)
    TextView mName;

    @Bind(R.id.tv_join_time)
    TextView mJoinTime;

    @Bind(R.id.tv_location)
    TextView mFrom;

    @Bind(R.id.tv_development_platform)
    TextView mPlatFrom;

    @Bind(R.id.tv_academic_focus)
    TextView mFocus;

    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;

    private UserV2 userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        userInfo = (UserV2) arguments.getSerializable("user_info");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.fragment_my_information_detail, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        //  sendRequiredData();
        fillUI();
    }

    @SuppressWarnings("deprecation")
    public void fillUI() {
        Core.getKJBitmap().displayWithLoadBitmap(mUserFace, userInfo.getPortrait(),
                R.mipmap.widget_dface);
        mName.setText(userInfo.getName());
        mJoinTime.setText(StringUtils.formatYearMonthDayNew(userInfo.getMore().getJoinDate()));
        mFrom.setText(userInfo.getMore().getCity());
        mPlatFrom.setText(userInfo.getMore().getPlatform());
        mFocus.setText(userInfo.getMore().getExpertise());
    }

}

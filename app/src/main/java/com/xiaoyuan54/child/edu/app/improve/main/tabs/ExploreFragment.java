package com.xiaoyuan54.child.edu.app.improve.main.tabs;

import android.content.Intent;
import android.view.View;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.SimpleBackPage;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseTitleFragment;
import com.xiaoyuan54.child.edu.app.improve.search.SearchActivity;
import com.xiaoyuan54.child.edu.app.ui.FindUserActivity;
import com.xiaoyuan54.child.edu.app.ui.ShakeActivity;
import com.xiaoyuan54.child.edu.app.util.UIHelper;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by fei on 2016/9/6.
 * desc:
 */

public class ExploreFragment extends BaseTitleFragment implements View.OnClickListener {

    @Bind(R.id.rl_soft)
    View mRlActive;

    @Bind(R.id.rl_find_osc)
    View mFindOSCer;

    // @Bind(R.id.rl_city)
    // View mCity;

    @Bind(R.id.rl_scan)
    View mScan;

    @Override
    protected int getIconRes() {
        return R.mipmap.btn_search_normal;
    }

    @Override
    protected View.OnClickListener getIconClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.SEARCH);
                SearchActivity.show(getContext());
            }
        };
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_explore;
    }

    @OnClick({R.id.rl_soft, R.id.rl_find_osc, R.id.rl_scan, R.id.rl_shake})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl_find_osc:
                showFindUser();
                break;
            //  case R.id.rl_city:
            //   UIUtil.showSimpleBack(getActivity(), SimpleBackPage.SAME_CITY);
            //   break;
            case R.id.rl_scan:
                UIHelper.showScanActivity(getActivity());
                break;
            case R.id.rl_shake:
                showShake();
                break;
            case R.id.iv_explore_discover:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.SEARCH);
                break;
            default:
                break;
        }
    }

    private void showShake() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShakeActivity.class);
        getActivity().startActivity(intent);
    }

    private void showFindUser() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), FindUserActivity.class);
        getActivity().startActivity(intent);
    }
}

package com.xiaoyuan54.child.edu.app.improve.base.fragments;

import com.xiaoyuan54.child.edu.app.interf.OnTabReselectListener;

/**
 * Created by huanghaibin_dev
 * on 2016/8/30.
 */
public abstract class BaseGeneralRecyclerFragment<T> extends BaseRecyclerViewFragment<T> implements OnTabReselectListener{
    @Override
    public void onTabReselect() {
        if(mRecyclerView != null){
            mRecyclerView.scrollToPosition(0);
            mRefreshLayout.setRefreshing(true);
            onRefreshing();
        }
    }
}

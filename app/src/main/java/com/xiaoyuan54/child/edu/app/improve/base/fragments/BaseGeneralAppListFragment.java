package com.xiaoyuan54.child.edu.app.improve.base.fragments;

import com.xiaoyuan54.child.edu.app.interf.OnTabReselectListener;

/**
 * Created by JuQiu
 * on 16/6/6.
 */

public abstract class BaseGeneralAppListFragment<T> extends BaseAppListFragment<T> implements OnTabReselectListener {
    @Override
    public void onTabReselect() {
        if(mListView != null){
            mListView.setSelection(0);
            mRefreshLayout.setRefreshing(true);
            onRefreshing();
        }
    }
}

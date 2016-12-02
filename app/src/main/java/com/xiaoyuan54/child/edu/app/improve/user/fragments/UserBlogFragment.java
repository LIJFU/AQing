package com.xiaoyuan54.child.edu.app.improve.user.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.reflect.TypeToken;

import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.bean.Blog;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.BlogDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.user.adapter.UserBlogAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;

import java.lang.reflect.Type;

/**
 * created by thanatosx  on 2016/8/16.
 */
public class UserBlogFragment extends BaseRecyclerViewFragment<Blog> {

    public static final String HISTORY_BLOG = "history_my_blog";
    public static final String BUNDLE_KEY_USER_ID = "BUNDLE_KEY_USER_ID";
    private long userId;

    public static Fragment instantiate(long uid) {
        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_KEY_USER_ID, uid);
        Fragment fragment = new UserBlogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        userId = bundle.getLong(BUNDLE_KEY_USER_ID);
    }

    @Override
    protected void requestData() {
        HttpClientApi.getUserBlogList(null, userId, mHandler);

    }

    @Override
    protected BaseRecyclerAdapter<Blog> getRecyclerAdapter() {
        return new UserBlogAdapter(getContext(), BaseRecyclerAdapter.ONLY_FOOTER);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<Blog>>>() {
        }.getType();
    }

    @Override
    protected boolean isNeedCache() {
        return false;
    }

    @Override
    protected boolean isNeedEmptyView() {
        return false;
    }

    @Override
    public void onItemClick(int position, long itemId) {
        Blog blog = mAdapter.getItem(position);
        BlogDetailActivity.show(getActivity(), blog.getId());
    }

    @Override
    public void onLoadMore() {
        HttpClientApi.getUserBlogList(null, userId, mHandler);
    }
}

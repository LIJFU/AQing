package com.xiaoyuan54.child.edu.app.improve.user.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.bean.simple.Origin;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.EventDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.tweet.activities.TweetDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.Active;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.BlogDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.NewsDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.QuestionDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.SoftwareDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.user.adapter.UserActiveAdapter;
import com.xiaoyuan54.child.edu.app.util.UIHelper;

import java.lang.reflect.Type;


/**
 * 某用户的动态(讨论)列表
 * Created by thanatos on 16/8/16.
 */
public class UserActiveFragment extends BaseRecyclerViewFragment<Active> {

    public static final String BUNDLE_KEY_USER_ID = "BUNDLE_KEY_USER_ID";

    private long uid;

    public static Fragment instantiate(Long uid) {
        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_KEY_USER_ID, uid);
        Fragment fragment = new UserActiveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        uid = bundle.getLong(BUNDLE_KEY_USER_ID);
    }

    @Override
    protected BaseRecyclerAdapter<Active> getRecyclerAdapter() {
        return new UserActiveAdapter(getContext(), Glide.with(this));
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<Active>>>() {
        }.getType();
    }

    @Override
    public void onLoadMore() {
        HttpClientApi.getUserActives(uid, null, mHandler);
    }

    @Override
    protected void requestData() {
        HttpClientApi.getUserActives(uid, null, mHandler);
    }

    @Override
    public void onItemClick(int position, long itemId) {
        Origin origin = mAdapter.getItem(position).getOrigin();
        switch (origin.getType()) {
            case Origin.ORIGIN_TYPE_LINK:
                UIHelper.showUrlRedirect(getContext(), origin.getHref());
                break;
            case Origin.ORIGIN_TYPE_SOFTWARE:
                SoftwareDetailActivity.show(getContext(), origin.getId());
                break;
            case Origin.ORIGIN_TYPE_DISCUSS:
                QuestionDetailActivity.show(getContext(), origin.getId());
                break;
            case Origin.ORIGIN_TYPE_BLOG:
                BlogDetailActivity.show(getContext(), origin.getId());
                break;
            case Origin.ORIGIN_TYPE_TRANSLATION:
                NewsDetailActivity.show(getContext(), origin.getId());
                break;
            case Origin.ORIGIN_TYPE_ACTIVE:
                EventDetailActivity.show(getContext(), origin.getId());
                break;
            case Origin.ORIGIN_TYPE_NEWS:
                NewsDetailActivity.show(getContext(), origin.getId());
                break;
            case Origin.ORIGIN_TYPE_TWEETS:
                TweetDetailActivity.show(getContext(), origin.getId());
                break;
            default:
                // pass
        }
    }

    @Override
    protected boolean isNeedCache() {
        return false;
    }

    @Override
    protected boolean isNeedEmptyView() {
        return false;
    }
}

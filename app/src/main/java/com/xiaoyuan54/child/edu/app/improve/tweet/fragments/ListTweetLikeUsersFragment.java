package com.xiaoyuan54.child.edu.app.improve.tweet.fragments;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.User;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.tweet.adapter.TweetLikeUsersAdapter;
import com.xiaoyuan54.child.edu.app.improve.tweet.contract.TweetDetailContract;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;
import com.xiaoyuan54.child.edu.app.improve.bean.simple.TweetLike;

import com.xiaoyuan54.child.edu.app.util.UIHelper;

import java.lang.reflect.Type;

/**
 * 动弹详情, 点赞列表
 * Created by thanatos
 * on 16/6/13.
 */
public class ListTweetLikeUsersFragment extends BaseRecyclerViewFragment<TweetLike> implements TweetDetailContract.IThumbupView {

    private TweetDetailContract.Operator mOperator;
    private TweetDetailContract.IAgencyView mAgencyView;

    public static ListTweetLikeUsersFragment instantiate(TweetDetailContract.Operator operator, TweetDetailContract.IAgencyView mAgencyView) {
        ListTweetLikeUsersFragment fragment = new ListTweetLikeUsersFragment();
        fragment.mOperator = operator;
        fragment.mAgencyView = mAgencyView;
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOperator = (TweetDetailContract.Operator) activity;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mOperator.onScroll();
                }
            }
        });
    }

    @Override
    protected BaseRecyclerAdapter<TweetLike> getRecyclerAdapter() {
        return new TweetLikeUsersAdapter(getContext());
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<TweetLike>>>(){}.getType();
    }

    @Override
    public void onLoadMore() {
        HttpClientApi.getTweetLikeList(
                mOperator.getTweetDetail().getId(),null,mHandler
        );
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
    protected void requestData() {
        HttpClientApi.getTweetLikeList(mOperator.getTweetDetail().getId(), null, mHandler);
    }

    @Override
    protected void onRequestSuccess(int code) {
        super.onRequestSuccess(code);
        if (mAdapter.getCount() < 20 && mAgencyView != null)
            mAgencyView.resetLikeCount(mAdapter.getCount());
    }

    @Override
    public void onItemClick(int position, long itemId) {
        super.onItemClick(position, itemId);
        TweetLike liker = mAdapter.getItem(position);
        UIHelper.showUserCenter(getContext(), liker.getAuthor().getId(), liker.getAuthor().getName());
    }

    @Override
    public void onLikeSuccess(boolean isUp, User user) {
        onRefreshing();
    }
}

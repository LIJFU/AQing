package com.xiaoyuan54.child.edu.app.improve.user.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.reflect.TypeToken;

import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.Question;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.QuestionDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.user.adapter.UserQuestionAdapter;

import java.lang.reflect.Type;

/**
 * @author thanatosx
 */
public class UserQuestionFragment extends BaseRecyclerViewFragment<Question> {

    public static final String HISTORY_MY_QUESTION = "history_my_question";
    public static final String BUNDLE_KEY_AUTHOR_ID = "author_id";
    private long userId;

    public static Fragment instantiate(int authorId) {
        UserQuestionFragment fragment = new UserQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_KEY_AUTHOR_ID, authorId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        userId = bundle.getLong(BUNDLE_KEY_AUTHOR_ID, 0);
    }

    @Override
    protected void requestData() {
        HttpClientApi.getUserQuestionList(null, userId, mHandler);
    }

    @Override
    protected BaseRecyclerAdapter<Question> getRecyclerAdapter() {
        return new UserQuestionAdapter(getContext(), BaseRecyclerAdapter.ONLY_FOOTER);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<Question>>>() {}.getType();
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
        Question question = mAdapter.getItem(position);
        QuestionDetailActivity.show(getActivity(), question.getId());
    }

    @Override
    public void onLoadMore() {
        HttpClientApi.getUserQuestionList(null, userId, mHandler);
    }
}

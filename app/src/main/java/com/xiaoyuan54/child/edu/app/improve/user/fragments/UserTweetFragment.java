package com.xiaoyuan54.child.edu.app.improve.user.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.user.adapter.UserTweetAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.Tweet;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.tweet.activities.TweetDetailActivity;

import com.xiaoyuan54.child.edu.app.util.DialogHelp;
import com.xiaoyuan54.child.edu.app.util.HTMLUtil;
import com.xiaoyuan54.child.edu.app.util.TDevice;

import org.json.JSONObject;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * created by fei  on 2016/8/16.
 * desc: question list module
 */
public class UserTweetFragment extends BaseRecyclerViewFragment<Tweet> implements
        BaseRecyclerAdapter.OnItemLongClickListener {

    public static final String BUNDLE_KEY_USER_ID = "BUNDLE_KEY_USER_ID";
    private long userId;

    public static Fragment instantiate(long uid) {
        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_KEY_USER_ID, uid);
        Fragment fragment = new UserTweetFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        userId = bundle.getLong(BUNDLE_KEY_USER_ID, 0);
    }

    @Override
    protected void requestData() {
        HttpClientApi.getUserTweetList(userId, null, mHandler);
    }

    @Override
    protected BaseRecyclerAdapter<Tweet> getRecyclerAdapter() {
        UserTweetAdapter userTweetAdapter = new UserTweetAdapter(getContext(), BaseRecyclerAdapter.ONLY_FOOTER);
        userTweetAdapter.setOnItemLongClickListener(this);
        return userTweetAdapter;
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<Tweet>>>() {
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
        Tweet tweet = mAdapter.getItem(position);
        TweetDetailActivity.show(getActivity(), tweet.getId());
    }


    @Override
    public void onLoadMore() {
        HttpClientApi.getUserTweetList(userId, null, mHandler);
    }

    @Override
    public void onLongClick(int position, long itemId) {
        handleLongClick(mAdapter.getItem(position), position);
    }

    private void handleLongClick(final Tweet tweet, final int position) {
        String[] items;
        if (AppContext.getInstance().getLoginId() == tweet.getAuthor().getId()) {
            items = new String[]{getString(R.string.copy),
                    getString(R.string.delete)};
        } else {
            items = new String[]{getString(R.string.copy)};
        }

        DialogHelp.getSelectDialog(getActivity(), items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case 0:
                        TDevice.copyTextToBoard(HTMLUtil.delHTMLTag(tweet.getContent()));
                        break;
                    case 1:
                        // TODO: 2016/7/21 删除动弹
                        DialogHelp.getConfirmDialog(getActivity(), "是否删除该动弹?", new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HttpClientApi.deleteTweet(tweet.getId(), new DeleteHandler(position));
                            }
                        }).show();
                        break;
                }
            }
        }).show();
    }


    private class DeleteHandler extends TextHttpResponseHandler {
        private int position;

        public DeleteHandler(int position) {
            this.position = position;
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            try {
                JSONObject jsonObject = new JSONObject(responseString);
                if (jsonObject.optInt("code") == 1) {
                    mAdapter.removeItem(position);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                onFailure(statusCode, headers, responseString, e);
            }
        }
    }

}

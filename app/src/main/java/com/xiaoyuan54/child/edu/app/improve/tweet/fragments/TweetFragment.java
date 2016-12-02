package com.xiaoyuan54.child.edu.app.improve.tweet.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.Constants;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseListAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseGeneralListFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.Tweet;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.tweet.activities.TweetDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.tweet.adapter.TweetAdapter;
import com.xiaoyuan54.child.edu.app.ui.empty.EmptyLayout;
import com.xiaoyuan54.child.edu.app.util.DialogHelp;
import com.xiaoyuan54.child.edu.app.util.HTMLUtil;
import com.xiaoyuan54.child.edu.app.util.TDevice;
import com.xiaoyuan54.child.edu.app.util.UIHelper;

import org.json.JSONObject;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * 动弹列表
 * Created by huanghaibin_dev
 * on 2016/7/18.
 */
public class TweetFragment extends BaseGeneralListFragment<Tweet> {
    public static final int CATEGORY_TYPE = 1; //请求最新或者最热
    public static final int CATEGORY_USER = 2; //请求用户

    public static final int TWEET_TYPE_NEW = 1;
    public static final int TWEET_TYPE_HOT = 2;

    public static final String CACHE_NEW_TWEET = "cache_new_tweet";
    public static final String CACHE_HOT_TWEET = "cache_hot_tweet";
    public static final String CACHE_USER_TWEET = "cache_user_tweet";

    public int requestCategory;//请求类型
    public int tweetType;
    public long authorId;

    public static Fragment instantiate(long aid) {
        Bundle bundle = new Bundle();
        bundle.putLong("authorId", aid);
        bundle.putInt("requestCategory", CATEGORY_USER);
        Fragment fragment = new TweetFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        requestCategory = bundle.getInt("requestCategory", CATEGORY_TYPE);
        tweetType = bundle.getInt("tweetType", TWEET_TYPE_NEW);
        authorId = bundle.getLong("authorId", AppContext.getInstance().getLoginId());
    }

    /**
     * fragment被销毁的时候重新调用，初始化保存的数据
     *
     * @param bundle onSaveInstanceState
     */
    @Override
    protected void onRestartInstance(Bundle bundle) {
        super.onRestartInstance(bundle);
        requestCategory = bundle.getInt("requestCategory", 1);
        tweetType = bundle.getInt("tweetType", 1);
        authorId = bundle.getLong("authorId", AppContext.getInstance().getLoginId());
    }

    @Override
    protected void initData() {
        super.initData();
        switch (requestCategory) {
            case CATEGORY_TYPE:
                CACHE_NAME = tweetType == TWEET_TYPE_NEW ? CACHE_NEW_TWEET : CACHE_HOT_TWEET;
                break;
            case CATEGORY_USER:
                CACHE_NAME = CACHE_USER_TWEET;
                IntentFilter filter = new IntentFilter(
                        Constants.INTENT_ACTION_USER_CHANGE);
                filter.addAction(Constants.INTENT_ACTION_LOGOUT);
                if (mReceiver == null) {
                    mReceiver = new LoginReceiver();
                    getActivity().registerReceiver(mReceiver, filter);
                }
                break;
        }

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Tweet tweet = mAdapter.getItem(position);
                if (tweet != null) {
                    handleLongClick(tweet, position);
                }
                return true;
            }
        });

        if (authorId == 0 && requestCategory == CATEGORY_USER) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            mErrorLayout.setErrorMessage(getString(R.string.unlogin_tip));

        }
    }

    private class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setupContent();
        }
    }

    private LoginReceiver mReceiver;

    private void setupContent() {
        if (AppContext.getInstance().isLogin()) {
            authorId = Long.parseLong(String.valueOf(AppContext.getInstance().getLoginUid()));
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            onRefreshing();
        } else {
            authorId = 0;
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            mErrorLayout.setErrorMessage(getString(R.string.unlogin_tip));
        }
    }


    @Override
    protected void requestData() {
        super.requestData();
        switch (requestCategory) {
            case CATEGORY_TYPE:
                HttpClientApi.getTweetList(tweetType, null, mHandler);
                break;
            case CATEGORY_USER:
                if (authorId != 0) {
                    HttpClientApi.getUserTweetList(authorId, null, mHandler);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TweetDetailActivity.show(getContext(), mAdapter.getItem(position));
    }

    @Override
    public void onClick(View v) {
        if (requestCategory == CATEGORY_USER && !AppContext.getInstance().isLogin()) {
            UIHelper.showLoginActivity(getActivity());
        } else {
            super.onClick(v);
        }
    }

    private void handleLongClick(final Tweet tweet, final int position) {
        String[] items;
        if (AppContext.getInstance().getLoginUid() == (int) tweet.getAuthor().getId()) {
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


    @Override
    protected BaseListAdapter getListAdapter() {
        return new TweetAdapter(this);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<Tweet>>>() {
        }.getType();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("requestCategory", requestCategory);
        outState.putInt("tweetType", tweetType);
        outState.putLong("authorId", authorId);
        super.onSaveInstanceState(outState);
    }

    /**
     * 注销广播
     */
    @Override
    public void onDestroy() {
        if (requestCategory == CATEGORY_USER && mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }
        super.onDestroy();
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

            }
        }
    }
}

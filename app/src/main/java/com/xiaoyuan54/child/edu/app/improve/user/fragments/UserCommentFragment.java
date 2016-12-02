package com.xiaoyuan54.child.edu.app.improve.user.fragments;

import com.google.gson.reflect.TypeToken;

import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.bean.simple.Origin;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.EventDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.tweet.activities.TweetDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.Mention;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.BlogDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.NewsDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.QuestionDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.SoftwareDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.user.adapter.UserMentionAdapter;
import com.xiaoyuan54.child.edu.app.util.UIHelper;

import java.lang.reflect.Type;

/**
 * Created by huanghaibin_dev
 * on 2016/8/16.
 */

public class UserCommentFragment extends BaseRecyclerViewFragment<Mention> {

    @Override
    protected void requestData() {
        super.requestData();
        HttpClientApi.getMsgCommentList(null, mHandler);
    }

    @Override
    public void onItemClick(int position, long itemId) {
        Mention mention = mAdapter.getItem(position);
        Origin origin = mention.getOrigin();
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
    protected BaseRecyclerAdapter<Mention> getRecyclerAdapter() {
        return new UserMentionAdapter(this);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<Mention>>>() {
        }.getType();
    }
}

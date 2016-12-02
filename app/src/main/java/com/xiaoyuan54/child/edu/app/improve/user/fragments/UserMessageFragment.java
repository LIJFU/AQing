package com.xiaoyuan54.child.edu.app.improve.user.fragments;

import com.google.gson.reflect.TypeToken;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.bean.simple.Author;
import com.xiaoyuan54.child.edu.app.improve.user.activities.UserSendMessageActivity;
import com.xiaoyuan54.child.edu.app.improve.user.adapter.UserMessageAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.Message;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;

import java.lang.reflect.Type;

/**
 * Created by huanghaibin_dev
 * on 2016/8/16.
 */

public class UserMessageFragment extends BaseRecyclerViewFragment<Message> {
    public long authorId;

    @Override
    public void initData() {
        super.initData();
        authorId = Long.parseLong(String.valueOf(AppContext.getInstance().getLoginUid()));
    }

    @Override
    protected void requestData() {
        super.requestData();
        HttpClientApi.getUserMessageList(null, mHandler);
    }

    @Override
    public void onItemClick(int position, long itemId) {
        Message message = mAdapter.getItem(position);
        Author sender = message.getSender();
        if (sender != null)
            UserSendMessageActivity.show(getContext(), message.getSender());
    }

    @Override
    protected BaseRecyclerAdapter<Message> getRecyclerAdapter() {
        return new UserMessageAdapter(this);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<Message>>>() {
        }.getType();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

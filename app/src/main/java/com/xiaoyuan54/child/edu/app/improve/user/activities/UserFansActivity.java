package com.xiaoyuan54.child.edu.app.improve.user.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.reflect.TypeToken;

import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.notice.NoticeBean;
import com.xiaoyuan54.child.edu.app.improve.user.adapter.UserFansOrFollowAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.activities.BaseRecyclerViewActivity;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.notice.NoticeManager;
import com.xiaoyuan54.child.edu.app.improve.user.bean.UserFansOrFollows;

import java.lang.reflect.Type;

/**
 * Created by fei on 2016/8/24.
 * desc: user fans
 */

public class UserFansActivity extends BaseRecyclerViewActivity<UserFansOrFollows> {

    public static final String BUNDLE_KEY_ID = "bundle_key_id";
    private long userId;


    private int getRequestType() {
        return HttpClientApi.TYPE_USER_FANS;
    }

    /**
     * show activity
     *
     * @param context context
     * @param userId  uid
     */
    public static void show(Context context, long userId) {
        Intent intent = new Intent(context, UserFansActivity.class);
        intent.putExtra(BUNDLE_KEY_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        userId = bundle.getLong(BUNDLE_KEY_ID, 0);
        return super.initBundle(bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();

        NoticeBean FansNotice = NoticeManager.getNotice();
        if (FansNotice == null) return;
        int fans = FansNotice.getFans();
        if (fans > 0) {
            NoticeManager.clear(this, NoticeManager.FLAG_CLEAR_FANS);
        }

    }


    @Override
    protected void requestData() {
        super.requestData();
        HttpClientApi.getUserFansOrFlows(getRequestType(), userId, null, mHandler);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<UserFansOrFollows>>>() {
        }.getType();
    }

    @Override
    protected BaseRecyclerAdapter<UserFansOrFollows> getRecyclerAdapter() {
        return new UserFansOrFollowAdapter(this, BaseRecyclerAdapter.ONLY_FOOTER);
    }

    @Override
    protected void onItemClick(UserFansOrFollows item, int position) {
        super.onItemClick(item, position);
        if (item.getId() <= 0) return;
        OtherUserHomeActivity.show(this, item.getId());
    }

}

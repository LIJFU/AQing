package com.xiaoyuan54.child.edu.app.improve.detail.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.EventApplyData;
import com.xiaoyuan54.child.edu.app.bean.Result;
import com.xiaoyuan54.child.edu.app.improve.detail.contract.EventDetailContract;
import com.xiaoyuan54.child.edu.app.improve.detail.fragments.DetailFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.EventDetail;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.detail.fragments.EventDetailFragment;
import com.xiaoyuan54.child.edu.app.util.UIHelper;
import com.xiaoyuan54.child.edu.app.util.XmlUtils;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * Created by huanghaibin
 * on 16-6-13.
 */
public class EventDetailActivity extends DetailActivity<EventDetail, EventDetailContract.View> implements EventDetailContract.Operator {

    public static void show(Context context, long id) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    void requestData() {
        HttpClientApi.getEventDetail(mDataId, getRequestHandler());
    }

    @Override
    Class<? extends DetailFragment> getDataViewFragment() {
        return EventDetailFragment.class;
    }

    @Override
    Type getDataType() {
        return new TypeToken<ResultBean<EventDetail>>() {
        }.getType();
    }

    @Override
    int getOptionsMenuId() {
        return R.menu.menu_detail_share;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            final EventDetail detail = getData();
            if (detail != null) {
                toShare(detail.getTitle(), detail.getBody(), detail.getHref());
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void toFav() {
        if (!isLogin())
            return;
        final EventDetail mDetail = getData();
        HttpClientApi.getFavReverse(mDataId, 5, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideWaitDialog();
                if (mDetail.isFavorite())
                    AppContext.showToastShort(R.string.del_favorite_faile);
                else
                    AppContext.showToastShort(R.string.add_favorite_faile);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Type type = new TypeToken<ResultBean<EventDetail>>() {
                    }.getType();

                    ResultBean<EventDetail> resultBean = AppContext.createGson().fromJson(responseString, type);
                    if (resultBean != null && resultBean.isSuccess()) {
                        mDetail.setFavorite(!mDetail.isFavorite());
                        mView.toFavOk(mDetail);
                        if (mDetail.isFavorite())
                            AppContext.showToastShort(R.string.add_favorite_success);
                        else
                            AppContext.showToastShort(R.string.del_favorite_success);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailure(statusCode, headers, responseString, e);
                }
                hideWaitDialog();
            }
        });
    }

    @Override
    public void toSignUp(EventApplyData data) {
        if (!isLogin())
            return;
        final EventDetail mDetail = getData();
        showWaitDialog(R.string.progress_submit);
        HttpClientApi.eventApply(data, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Result rs = XmlUtils.toBean(com.xiaoyuan54.child.edu.app.bean.ResultBean.class,
                        new ByteArrayInputStream(responseBody)).getResult();
                if (rs.OK()) {
                    AppContext.showToast("报名成功");
                    mDetail.setApplyStatus(0);
                    mView.toSignUpOk(mDetail);
                } else {
                    AppContext.showToast(rs.getErrorMessage());
                }
                hideWaitDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                hideWaitDialog();
            }
        });
    }

    private boolean isLogin() {
        if (!AppContext.getInstance().isLogin()) {
            UIHelper.showLoginActivity(this);
            return false;
        }
        return true;
    }
}

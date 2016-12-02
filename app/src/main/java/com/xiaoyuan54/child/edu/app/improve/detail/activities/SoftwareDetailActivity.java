package com.xiaoyuan54.child.edu.app.improve.detail.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.improve.bean.SoftwareDetail;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.detail.contract.SoftDetailContract;
import com.xiaoyuan54.child.edu.app.improve.detail.fragments.DetailFragment;
import com.xiaoyuan54.child.edu.app.improve.detail.fragments.SoftWareDetailFragment;
import com.xiaoyuan54.child.edu.app.util.HTMLUtil;
import com.xiaoyuan54.child.edu.app.util.StringUtils;
import com.xiaoyuan54.child.edu.app.util.URLsUtils;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * Created by fei on 2016/6/13.
 * desc:   news detail  module
 */
public class SoftwareDetailActivity extends DetailActivity<SoftwareDetail, SoftDetailContract.View>
        implements SoftDetailContract.Operator {

    private static final String TAG = "SoftwareDetailActivity";
    private String ident;

    @Override
    int getOptionsMenuId() {
        return 0;
    }

    /**
     * show news detail
     *
     * @param context context
     * @param id      id
     */
    public static void show(Context context, long id) {
        Intent intent = new Intent(context, SoftwareDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        intent.putExtras(bundle);
        //intent.putExtra("id", id);
        context.startActivity(intent);
    }

    /**
     * show news detail
     *
     * @param context context
     * @param ident   ident--> software Name
     */
    public static void show(Context context, String ident) {
        Intent intent = new Intent(context, SoftwareDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ident", ident);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        ident = bundle.getString("ident", null);
        if (!TextUtils.isEmpty(ident)) {
            return true;
        }
        return super.initBundle(bundle);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_blog_detail;
    }

    @Override
    void requestData() {
        if (TextUtils.isEmpty(ident)) {
            HttpClientApi.getNewsDetail(getDataId(), HttpClientApi.CATALOG_SOFTWARE_DETAIL, getRequestHandler());
        } else {
            HttpClientApi.getSoftwareDetail(ident, HttpClientApi.CATALOG_SOFTWARE_DETAIL, getRequestHandler());
        }

    }

    @Override
    Class<? extends DetailFragment> getDataViewFragment() {
        return SoftWareDetailFragment.class;
    }

    @Override
    Type getDataType() {
        return new TypeToken<ResultBean<SoftwareDetail>>() {
        }.getType();
    }

    @Override
    public void toFavorite() {
        int uid = requestCheck();
        if (uid == 0)
            return;
        showWaitDialog(R.string.progress_submit);
        final SoftwareDetail softwareDetail = getData();
        HttpClientApi.getFavReverse(getDataId(), 1, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideWaitDialog();
                if (softwareDetail.isFavorite())
                    AppContext.showToastShort(R.string.del_favorite_faile);
                else
                    AppContext.showToastShort(R.string.add_favorite_faile);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Type type = new TypeToken<ResultBean<SoftwareDetail>>() {
                    }.getType();

                    ResultBean<SoftwareDetail> resultBean = AppContext.createGson().fromJson(responseString, type);
                    if (resultBean != null && resultBean.isSuccess()) {
                        softwareDetail.setFavorite(!softwareDetail.isFavorite());
                        mView.toFavoriteOk(softwareDetail);
                        if (softwareDetail.isFavorite())
                            AppContext.showToastShort(R.string.add_favorite_success);
                        else
                            AppContext.showToastShort(R.string.del_favorite_success);
                    }
                    hideWaitDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailure(statusCode, headers, responseString, e);
                }
            }
        });
    }

    @Override
    public void toShare() {
        if (getDataId() != 0 && getData() != null) {
            String content;

            String url = String.format(URLsUtils.URL_MOBILE + "software/%s", getDataId());
            final SoftwareDetail softwareDetail = getData();
            if (softwareDetail.getBody().length() > 55) {
                content = HTMLUtil.delHTMLTag(softwareDetail.getBody().trim());
                if (content.length() > 55)
                    content = StringUtils.getSubString(0, 55, content);
            } else {
                content = HTMLUtil.delHTMLTag(softwareDetail.getBody().trim());
            }
            String title = softwareDetail.getName();

            if (TextUtils.isEmpty(url) || TextUtils.isEmpty(content) || TextUtils.isEmpty(title)) {
                AppContext.showToast("内容加载失败...");
                return;
            }
            toShare(title, content, url);
        } else {
            AppContext.showToast("内容加载失败...");
        }
    }
}

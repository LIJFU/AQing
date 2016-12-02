package com.xiaoyuan54.child.edu.app.improve.general.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.Banner;
import com.xiaoyuan54.child.edu.app.cache.CacheManager;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseGeneralListFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.general.adapter.NewsAdapter;
import com.xiaoyuan54.child.edu.app.improve.widget.ViewNewsHeader;
import com.xiaoyuan54.child.edu.app.improve.app.AppOperator;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseListAdapter;
import com.xiaoyuan54.child.edu.app.improve.bean.News;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;

import com.xiaoyuan54.child.edu.app.util.UIHelper;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;


/**
 * 资讯界面
 */
public class NewsFragment extends BaseGeneralListFragment<News> {

    public static final String HISTORY_NEWS = "history_news";

    public static final String NEWS_SYSTEM_TIME = "news_system_time";

    private boolean isFirst = true;

    private static final String NEWS_BANNER = "news_banner";

    private ViewNewsHeader mHeaderView;
    private Handler handler = new Handler();
    private String mSystemTime;

    @Override
    protected void onRestartInstance(Bundle bundle) {
        super.onRestartInstance(bundle);
        mIsRefresh = false;
        mSystemTime = bundle.getString("system_time", "");
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mHeaderView = new ViewNewsHeader(getActivity());
        AppOperator.runOnThread(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                final ProPageBean<Banner> pageBean = (ProPageBean<Banner>) CacheManager.readObject(getActivity(), NEWS_BANNER);
                if (pageBean != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ((NewsAdapter) mAdapter).setSystemTime(AppContext.get(NEWS_SYSTEM_TIME, null));
                            mHeaderView.initData(getImgLoader(), pageBean.getItems());
                        }
                    });
                }
            }
        });

        mHeaderView.setRefreshLayout(mRefreshLayout);
        mListView.addHeaderView(mHeaderView);
        getBannerList();
    }

    @Override
    protected void initData() {
        super.initData();
        if (!TextUtils.isEmpty(mSystemTime)) {
            ((NewsAdapter) mAdapter).setSystemTime(mSystemTime);
        }
    }

    @Override
    public void onRefreshing() {
        super.onRefreshing();
        if (!isFirst)
            getBannerList();
    }

    @Override
    protected void requestData() {
        super.requestData();
        HttpClientApi.getMusicList(1, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News news = mAdapter.getItem(position - 1);
        if (news != null) {
            int type = news.getType();
            long newsId = news.getId();
            UIHelper.showDetail(getActivity(), type, newsId, news.getHref());
            TextView title = (TextView) view.findViewById(R.id.tv_title);
            TextView content = (TextView) view.findViewById(R.id.tv_description);
            updateTextColor(title, content);
            saveToReadedList(HISTORY_NEWS, String.valueOf(news.getId()));
        }
    }

    @Override
    protected BaseListAdapter<News> getListAdapter() {
        return new NewsAdapter(this);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<News>>>() {
        }.getType();
    }

    @Override
    protected void onRequestFinish() {
        super.onRequestFinish();
        isFirst = false;
    }

    @Override
    protected void setListData(ResultBean<ProPageBean<News>> resultBean) {
        mSystemTime = resultBean.getTime();
        ((NewsAdapter) mAdapter).setSystemTime(mSystemTime);
        AppContext.set(NEWS_SYSTEM_TIME, mSystemTime);
        super.setListData(resultBean);
    }

    private void getBannerList() {
        HttpClientApi.getBannerList(HttpClientApi.CATALOG_BANNER_NEWS, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    final ResultBean<ProPageBean<Banner>> resultBean = AppContext.createGson().fromJson(responseString, new TypeToken<ResultBean<ProPageBean<Banner>>>() {
                    }.getType());
                    if (resultBean != null && resultBean.isSuccess()) {
                        AppOperator.runOnThread(new Runnable() {
                            @Override
                            public void run() {
                                CacheManager.saveObject(getActivity(), resultBean.getResult(), NEWS_BANNER);
                            }
                        });
                        mHeaderView.initData(getImgLoader(), resultBean.getResult().getItems());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("system_time", mSystemTime);
        super.onSaveInstanceState(outState);
    }
}

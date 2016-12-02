package com.xiaoyuan54.child.edu.app.improve.general.fragments;

import android.os.Handler;
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
import com.xiaoyuan54.child.edu.app.improve.bean.Event;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.EventDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.general.adapter.EventAdapter;
import com.xiaoyuan54.child.edu.app.improve.app.AppOperator;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseListAdapter;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;
import com.xiaoyuan54.child.edu.app.improve.widget.ViewEventHeader;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * 活动界面
 */
public class EventFragment extends BaseGeneralListFragment<Event> {

    private boolean isFirst = true;
    private static final String EVENT_BANNER = "event_banner";
    private ViewEventHeader mHeaderView;
    public static final String HISTORY_EVENT = "history_event";
    private Handler handler = new Handler();

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mHeaderView = new ViewEventHeader(getActivity());

        AppOperator.runOnThread(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                final ProPageBean<Banner> pageBean = (ProPageBean<Banner>) CacheManager.readObject(getActivity(), EVENT_BANNER);
                if (pageBean != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
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
    public void onRefreshing() {
        super.onRefreshing();
        if (!isFirst)
            getBannerList();
    }

    @Override
    protected void requestData() {
        super.requestData();
        HttpClientApi.getEventList(null, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event event = mAdapter.getItem(position - 1);
        if (event != null) {
            EventDetailActivity.show(getActivity(), event.getId());
            TextView title = (TextView) view.findViewById(R.id.tv_event_title);
            updateTextColor(title, null);
            saveToReadedList(HISTORY_EVENT, String.valueOf(event.getId()));
        }
    }

    @Override
    protected BaseListAdapter<Event> getListAdapter() {
        return new EventAdapter(this);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<ProPageBean<Event>>>() {
        }.getType();
    }

    @Override
    protected void onRequestFinish() {
        super.onRequestFinish();
        isFirst = false;
    }

    private void getBannerList() {
        HttpClientApi.getBannerList(HttpClientApi.CATALOG_BANNER_EVENT, new TextHttpResponseHandler() {
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
                                CacheManager.saveObject(getActivity(), resultBean.getResult(), EVENT_BANNER);
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

}

package com.xiaoyuan54.child.edu.app.improve.base.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.cache.CacheManager;
import com.xiaoyuan54.child.edu.app.improve.app.AppOperator;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseListAdapter;
import com.xiaoyuan54.child.edu.app.bean.base.PageBean;
import com.xiaoyuan54.child.edu.app.ui.base.fragment.BaseFragment;
import com.xiaoyuan54.child.edu.app.ui.empty.EmptyLayout;
import com.xiaoyuan54.child.edu.app.widget.SuperRefreshLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


public abstract class BaseAppListFragment<T> extends BaseFragment implements
        SuperRefreshLayout.SuperRefreshLayoutListener,
        AdapterView.OnItemClickListener, BaseListAdapter.Callback,
        View.OnClickListener {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LOADING = 1;
    public static final int TYPE_NO_MORE = 2;
    public static final int TYPE_ERROR = 3;
    public static final int TYPE_NET_ERROR = 4;
    protected String CACHE_NAME = getClass().getName();
    protected ListView mListView;
    protected SuperRefreshLayout mRefreshLayout;
    protected EmptyLayout mErrorLayout;
    protected BaseListAdapter<T> mAdapter;
    protected boolean mIsRefresh;
    protected TextHttpResponseHandler mHandler;
    protected PageBean<T> mBean;
    private String mTime;
    private View mFooterView;

    private View mHeadView;

    private ProgressBar mFooterProgressBar;
    private TextView mFooterText;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mListView = (ListView) root.findViewById(R.id.listView);

        mRefreshLayout = (SuperRefreshLayout) root.findViewById(R.id.superRefreshLayout);
        mRefreshLayout.setColorSchemeResources(
                R.color.main_green, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mErrorLayout = (EmptyLayout) root.findViewById(R.id.error_layout);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.list_cell_hint, null);
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_view_footer, null);
        mFooterText = (TextView) mFooterView.findViewById(R.id.tv_footer);
        mFooterProgressBar = (ProgressBar) mFooterView.findViewById(R.id.pb_footer);
        mListView.setOnItemClickListener(this);

        mErrorLayout.setOnLayoutClickListener(this);
        if (isNeedFooter())
            mListView.addFooterView(mFooterView);
        if(isNeedHeader()){
            mListView.addFooterView(mHeadView);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        //when open this fragment,read the obj

        mAdapter = getListAdapter();
        mListView.setAdapter(mAdapter);

        mHandler = new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onRequestError(statusCode);
                onRequestFinish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Type type = getType();
                    PageBean<T> resultBean = AppContext.createGson().fromJson(responseString, type);
                    if (resultBean != null && resultBean.isSuccess() && resultBean.getItem() != null) {
                        onRequestSuccess(resultBean.getStacode());
                        setListData(resultBean);
                    } else {
                        setFooterType(TYPE_NO_MORE);
                        //mRefreshLayout.setNoMoreData();
                    }
                    onRequestFinish();
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailure(statusCode, headers, responseString, e);
                }
            }
        };

        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                mBean = (PageBean<T>) CacheManager.readObject(getActivity(), CACHE_NAME);
                //if is the first loading
                if (mBean == null) {
                    mBean = new PageBean<>();
                    mBean.setItem(new ArrayList<T>());
                    onRefreshing();
                } else {
                    mRoot.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(mBean.getItem());
                            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                            mRefreshLayout.setVisibility(View.VISIBLE);
                            onRefreshing();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        onRefreshing();
    }

    @Override
    public void onRefreshing() {
        mIsRefresh = true;
        requestData();
    }

    @Override
    public void onLoadMore()
    {
        if(mBean.hasNext())
        {
          requestData();
        }
    }

    /**
     * request network data
     */
    protected void requestData() {
        onRequestStart();
        setFooterType(TYPE_LOADING);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    protected void onRequestStart() {

    }

    protected void onRequestSuccess(int code) {

    }

    /**
     * save readed list
     *
     * @param fileName fileName
     * @param key      key
     */
    protected void saveToReadedList(String fileName, String key) {

        // 放入已读列表
        AppContext.putReadedPostList(fileName, key, "true");
    }

    /**
     * update textColor
     *
     * @param title   title
     * @param content content
     */
    protected void updateTextColor(TextView title, TextView content) {
        if (title != null) {
            title.setTextColor(getResources().getColor(R.color.count_text_color_light));
        }
        if (content != null) {
            content.setTextColor(getResources().getColor(R.color.count_text_color_light));
        }
    }

    protected void onRequestError(int code) {
        setFooterType(TYPE_NET_ERROR);
        if (mAdapter.getDatas().size() == 0)
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
    }

    protected void onRequestFinish() {
        onComplete();
    }

    protected void onComplete() {
        mRefreshLayout.onLoadComplete();
        mIsRefresh = false;
    }

    protected void setListData(PageBean<T> resultBean) {
        //is refresh
        //mBean.setNextPageToken(resultBean.getResult().getNextPageToken());
        if (mIsRefresh) {
            //cache the time
            mTime = getSystemTime().toString();
            mBean.setItem(resultBean.getItem());
            mAdapter.clear();
            mAdapter.addItem(mBean.getItem());
           // mBean.setPrevPageToken(resultBean.getResult().getPrevPageToken());
            mRefreshLayout.setCanLoadMore();
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    CacheManager.saveObject(getActivity(), mBean, CACHE_NAME);
                }
            });
        } else {
            mAdapter.addItem(resultBean.getItem());
        }
        if (resultBean.getItem().size() < 20) {
            setFooterType(TYPE_NO_MORE);
            //mRefreshLayout.setNoMoreData();
        }
        if (mAdapter.getDatas().size() > 0) {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
        } else {
            mErrorLayout.setErrorType(EmptyLayout.NODATA);
        }
    }

    @Override
    public Date getSystemTime() {
        return new Date();
    }

    protected abstract BaseListAdapter<T> getListAdapter();

    protected abstract Type getType();


    protected boolean isNeedHeader() {
        return false;
    }

    protected boolean isNeedFooter() {
        return true;
    }


    protected void setFooterType(int type) {
        try {
            switch (type) {
                case TYPE_NORMAL:
                case TYPE_LOADING:
                    mFooterText.setText(getResources().getString(R.string.footer_type_loading));
                    mFooterProgressBar.setVisibility(View.VISIBLE);
                    break;
                case TYPE_NET_ERROR:
                    mFooterText.setText(getResources().getString(R.string.footer_type_net_error));
                    mFooterProgressBar.setVisibility(View.GONE);
                    break;
                case TYPE_ERROR:
                    mFooterText.setText(getResources().getString(R.string.footer_type_error));
                    mFooterProgressBar.setVisibility(View.GONE);
                    break;
                case TYPE_NO_MORE:
                    mFooterText.setText(getResources().getString(R.string.footer_type_not_more));
                    mFooterProgressBar.setVisibility(View.GONE);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

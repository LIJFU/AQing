package com.xiaoyuan54.child.edu.app.ui.music.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;

import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.Banner;
import com.xiaoyuan54.child.edu.app.bean.music.Music;

import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;

import com.xiaoyuan54.child.edu.app.bean.base.PageBean;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.improve.widget.ViewEventHeader;

import com.xiaoyuan54.child.edu.app.ui.music.adapter.MusicCommonActionAdapter;

import com.xiaoyuan54.child.edu.app.ui.music.adapter.MusicCommonAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;





/**
 * 儿歌通用Fragment界面
 */
public class MusicCommonFragment extends BaseRecyclerViewFragment<Music> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        return manager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {

        headerBannerView = new ViewEventHeader(getActivity());

        List<Banner> banners = new ArrayList<>();

        for(int i= 0;i<4;i++)
        {
            Banner banner = new Banner();

            //http://static.oschina.net/uploads/cooperation/75323/ubuntu-forum-black-sql_89cc6798-d602-4e9c-a89e-aebc10a71962.jpg
            banner.setImg("http://static.oschina.net/uploads/cooperation/75410/google-beta-natural-language-api_bb0d92ff-728d-48ab-aed8-a06c9cd0e4eb.jpg");
            banner.setName("测试的banner图");
            banner.setHref("http://www.baidu.com");
            banner.setId(78129);
            banner.setType(6);
            banners.add(banner);
        }

        headerBannerView.setRefreshLayout(mRefreshLayout);
        headerBannerView.initData(getImgLoader(),banners);


        BaseRecyclerAdapter.HeaderViewHolder headerViewHolder = new BaseRecyclerAdapter.HeaderViewHolder(headerBannerView);
        return headerViewHolder;
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public static final String QUES_ASK = "ques_ask";
    public static final String QUES_SHARE = "ques_share";
    public static final String QUES_COMPOSITE = "ques_composite";
    public static final String QUES_PROFESSION = "ques_profession";
    public static final String QUES_WEBSITE = "ques_website";

    private int catalog = 1;
    private MusicCommonActionAdapter quesActionAdapter;
    private int[] positions = {1, 0, 0, 0, 0};
    private ConnectivityManager connectivityManager;


    private ViewEventHeader headerBannerView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);





    }

    /**
     * According to the distribution network is events
     */
    private void requestEventDispatcher() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            boolean connected = networkInfo.isConnected();
            NetworkInfo.State state = networkInfo.getState();
            if (connected && state == NetworkInfo.State.CONNECTED) {
                mRefreshLayout.setRefreshing(true);
                onRefreshing();
                requestData();
            } else {
                requestLocalCache();
            }
        } else {
            requestLocalCache();
        }
    }


    /**
     * notify action data
     *
     * @param position position
     */
    private void updateAction(int position) {
        int len = positions.length;
        for (int i = 0; i < len; i++) {
            if (i != position) {
                positions[i] = 0;
            } else {
                positions[i] = 1;
            }
        }
        quesActionAdapter.notifyDataSetChanged();
    }

    /**
     * request local cache
     */
    @SuppressWarnings("unchecked")
    private void requestLocalCache() {
       /* verifyCacheType();
        mBean = (PageBean<Question>) CacheManager.readObject(getActivity(), CACHE_NAME);
        if (mBean != null) {
            mAdapter.clear();
            mAdapter.addItem(mBean.getItems());
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRefreshLayout.setCanLoadMore();
        } else {
            mBean = new PageBean<>();
            mBean.setItems(new ArrayList<Question>());
            onRefreshing();
        }*/
    }



    @Override
    protected void onRequestError(int code) {
        super.onRequestError(code);
        requestLocalCache();
    }

    @Override
    protected BaseRecyclerAdapter<Music> getRecyclerAdapter() {
        MusicCommonAdapter adapter = new MusicCommonAdapter(getContext());
        adapter.setOnLoadingHeaderCallBack(this);
        return adapter;
    }

    @Override
    protected void requestData()
    {
        super.requestData();
        verifyCacheType();

        HttpClientApi.getMusicList(mBean.getNextPage(),mHandler);
    }

    @Override
    protected Type getType() {
        return new TypeToken<PageBean<Music>>() {
        }.getType();
    }

    @Override
    protected void setListData(PageBean<Music> resultBean) {
        verifyCacheType();
        super.setListData(resultBean);
    }

    /**
     * verify cache type
     */
    private void verifyCacheType() {

        switch (catalog) {
            case 1:
                CACHE_NAME = QUES_ASK;
                break;
            case 2:
                CACHE_NAME = QUES_SHARE;
                break;
            case 3:
                CACHE_NAME = QUES_COMPOSITE;
                break;
            case 4:
                CACHE_NAME = QUES_PROFESSION;
                break;
            case 5:
                CACHE_NAME = QUES_WEBSITE;
                break;
            default:
                break;
        }

    }



}

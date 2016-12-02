package com.xiaoyuan54.child.edu.app.ui.fable.fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.Banner;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseListAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseGeneralListFragment;
import com.xiaoyuan54.child.edu.app.improve.bean.Blog;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ProPageBean;
import com.xiaoyuan54.child.edu.app.improve.bean.base.ResultBean;
import com.xiaoyuan54.child.edu.app.improve.widget.ViewNewsHeader;
import com.xiaoyuan54.child.edu.app.ui.fable.adapter.FableCommonAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m on 2016-10-30.
 */

public class FableCommonFragment extends BaseGeneralListFragment<Blog> {

    private FableCommonAdapter fableAdapter;

    private ViewNewsHeader headerBannerView;

    private ConnectivityManager connectivityManager;

    private Context mContent;

    @Override
    protected BaseListAdapter<Blog> getListAdapter() {
        fableAdapter = new FableCommonAdapter(this,mContent);
        return fableAdapter;

    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mContent = getContext();
        headerBannerView = new ViewNewsHeader(getActivity());

        List<Banner> banners = new ArrayList<Banner>();
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


        mListView.addHeaderView(headerBannerView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected Type getType() {
        return  new TypeToken<ResultBean<ProPageBean<Blog>>>(){}.getType();
    }

    @Override
    protected void setListData(ResultBean<ProPageBean<Blog>> resultBean) {
        super.setListData(resultBean);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onRequestError(int code) {
        super.onRequestError(code);
        requestData();
    }



    @Override
    protected void requestData() {
        super.requestData();
        int catalog = HttpClientApi.CATALOG_BLOG_NORMAL;
        HttpClientApi.getBlogList(catalog,null, mHandler);
    }
}

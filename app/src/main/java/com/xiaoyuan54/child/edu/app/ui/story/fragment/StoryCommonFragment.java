package com.xiaoyuan54.child.edu.app.ui.story.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.google.gson.reflect.TypeToken;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.base.BaseListFragment;
import com.xiaoyuan54.child.edu.app.bean.base.PageBean;
import com.xiaoyuan54.child.edu.app.bean.story.Story;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.ui.story.adapter.StoryCommonAdapter;

import java.lang.reflect.Type;

/**
 * Created by L.QING on 2016-11-10.
 */

public  class StoryCommonFragment extends BaseRecyclerViewFragment<Story>
{

    private StoryCommonAdapter adapter;

    private int currentPageIndex = 1;

    private int catalog = 0;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        adapter =  new StoryCommonAdapter(getContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBundle(Bundle bundle)
    {
        super.initBundle(bundle);
        if(null!=bundle)
        {
            catalog = bundle.getInt(BaseListFragment.BUNDLE_KEY_CATALOG,0);
        }
    }

    @Override
    protected BaseRecyclerAdapter<Story> getRecyclerAdapter()
    {
        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager()
    {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        manager.offsetChildrenHorizontal(50);
        return manager;
    }

    @Override
    protected Type getType()
    {
        return new TypeToken<PageBean<Story>>() {}.getType();
    }

    @Override
    protected void requestData()
    {
        HttpClientApi.getStoryList(catalog,currentPageIndex,mHandler);
    }

    @Override
    public void onRefreshing()
    {
        currentPageIndex = 1;
        super.onRefreshing();
    }

    @Override
    protected void setListData(PageBean<Story> resultBean) {
        super.setListData(resultBean);
        ++currentPageIndex;
    }

    @Override
    public void onItemClick(int position, long itemId)
    {
        /*Album album = adapter.getItem(position);
        if(null==album) return;



        MainPresenter presenter =(MainPresenter) getContext();
        AlbumDetailMainFragment albumSongListFragment = new AlbumDetailMainFragment();
        albumSongListFragment.setAlbumData(album);
        presenter.attachFrag(albumSongListFragment);*/
    }
}
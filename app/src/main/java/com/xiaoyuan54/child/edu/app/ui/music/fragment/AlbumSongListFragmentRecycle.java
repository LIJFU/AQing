package com.xiaoyuan54.child.edu.app.ui.music.fragment;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.base.PageBean;
import com.xiaoyuan54.child.edu.app.bean.music.Album;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.modules.music.PlayList;
import com.xiaoyuan54.child.edu.app.ui.music.adapter.AlbumSongListAdapterRecycle;
import java.lang.reflect.Type;


public class AlbumSongListFragmentRecycle extends BaseRecyclerViewFragment<Song>
{

    private Album album;

    private boolean isLoad = false;

    AlbumSongListAdapterRecycle  adaptor;

    PlayList playList;


    public void setBundle(Bundle bundle)
    {
        if(null!=bundle)
        {
            album =(Album) bundle.getSerializable("album");
        }
    }


    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        playList = PlayList.getPlayList();

    }

    @Override
    protected BaseRecyclerAdapter<Song> getRecyclerAdapter()
    {
        adaptor= new AlbumSongListAdapterRecycle(getContext(), BaseRecyclerAdapter.ONLY_FOOTER);
        return adaptor;
    }

    @Override
    protected Type getType() {
        return new TypeToken<PageBean<Song>>() {
        }.getType();
    }


    @Override
    protected void requestData()
    {
        super.requestData();
        if (null==album)
        {
            mErrorLayout.setErrorMessage("加载数据失败");
        }
        HttpClientApi.getAlbumSongList(album.getId(),mHandler);
    }

    @Override
    public void onRefreshing()
    {
        super.onRefreshing();
        isLoad = false;
    }

    @Override
    protected void setListData(PageBean<Song> resultBean) {
        super.setListData(resultBean);
        isLoad = true;
        mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
    }

    @Override
    protected boolean isNeedRefresh() {
        return false;
    }

    @Override
    protected boolean isNeedCache() {
        return false;
    }


    @Override
    public void onItemClick(int position, long itemId)
    {
        super.onItemClick(position, itemId);
        playList.updateSongList(adaptor.getItems(),position);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("fragment"," album song list fragment destroy--------------------->>");
    }
}

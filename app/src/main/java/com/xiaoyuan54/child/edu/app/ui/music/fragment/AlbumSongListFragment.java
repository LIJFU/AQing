package com.xiaoyuan54.child.edu.app.ui.music.fragment;
import com.google.gson.reflect.TypeToken;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.bean.base.PageBean;
import com.xiaoyuan54.child.edu.app.bean.music.Album;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseNoRefreshListAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseNoRefreshListFragment;
import com.xiaoyuan54.child.edu.app.ui.music.adapter.AlbumSongListAdapter;
import java.lang.reflect.Type;
import butterknife.Bind;


public class AlbumSongListFragment extends BaseNoRefreshListFragment<Song>
{

    private Album album;

    @Override
    protected Type getType() {
        return new TypeToken<PageBean<Song>>() {
        }.getType();
    }

    public void setAlbumData(Album album)
    {
        this.album = album;
    }


    @Override
    protected void requestData()
    {
        super.requestData();
        HttpClientApi.getAlbumSongList(1,mHandler);
    }

    @Override
    protected BaseNoRefreshListAdapter<Song> getListAdapter() {
        return new AlbumSongListAdapter(getContext());
    }


}

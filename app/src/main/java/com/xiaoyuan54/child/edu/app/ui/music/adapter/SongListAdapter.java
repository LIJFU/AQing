package com.xiaoyuan54.child.edu.app.ui.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.ViewHolder;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseListAdapter;
import com.xiaoyuan54.child.edu.app.util.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * create by L.QING
 */
public class SongListAdapter extends BaseListAdapter<Song> {

    private RequestManager reqManager;
    private Context context;

    public SongListAdapter(Callback callback) {
        super(callback);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected void convert(ViewHolder vh, Song song, int position)
    {
        TViewHolder holder = (TViewHolder) vh;
        holder.songTitle.setText(song.getTitle());
        holder.songSinger.setText(song.getSinger());
        ImageLoader.loadImage(reqManager, holder.songCoverImg, song.getCoverImg(),R.drawable.midden_fable);
    }

    @Override
    protected int getLayoutId(int position, Song item)
    {
        return R.layout.item_album_song;
    }

    public static class TViewHolder extends ViewHolder
    {
        @Bind(R.id.item_song_title)
        TextView songTitle;

        @Bind(R.id.item_song_cover)
        ImageView songCoverImg;

        @Bind(R.id.item_song_singer)
        TextView songSinger;

        public TViewHolder(Callback caller, ViewGroup parent, int position)
        {
            super(caller,parent,R.layout.item_album_song,position);
            ButterKnife.bind(this, parent);
        }
    }

}

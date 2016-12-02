package com.xiaoyuan54.child.edu.app.ui.music.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.ViewHolder;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseNoRefreshListAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.util.ImageLoader;

import org.kymjs.kjframe.Core;
import org.kymjs.kjframe.bitmap.BitmapCallBack;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlbumSongListAdapter extends BaseNoRefreshListAdapter<Song>
{

    public AlbumSongListAdapter(Context context) {
        super(context);
    }

    @Override
    protected  ViewHolder onCreateHolder(ViewGroup parent, int position)
    {
        return new TViewHolder(parent);
    }

    @Override
    protected void convert(ViewHolder holder, Song song, int position)
    {
        TViewHolder vh =(TViewHolder)holder;
        final ImageView cover = vh.songCoverImg;
        TextView title =vh.songTitle;
        TextView singer =vh.songSinger;
        title.setText(song.getTitle());
        singer.setText(song.getSinger());

        new Core.Builder().url(song.getCoverImg()).view(cover).loadBitmapRes(R.mipmap.load_img_default)
            .bitmapCallBack(new BitmapCallBack() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    super.onSuccess(bitmap);
                    if (bitmap != null)
                    {
                        cover.setImageBitmap(bitmap);
                    }
                }
            }).doTask();
       // ImageLoader.loadImage(Glide.with(mContext),cover,song.getCoverImg(),R.drawable.midden_fable);

    }


    public  class TViewHolder extends BaseNoRefreshListAdapter.ViewHolder
    {
        @Bind(R.id.item_song_title)
        TextView songTitle;

        @Bind(R.id.item_song_cover)
        ImageView songCoverImg;

        @Bind(R.id.item_song_singer)
        TextView songSinger;


        public TViewHolder(ViewGroup parent) {
            super(R.layout.item_album_song,parent);
        }
    }
}

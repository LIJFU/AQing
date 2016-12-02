package com.xiaoyuan54.child.edu.app.ui.mime.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.ViewHolder;
import com.xiaoyuan54.child.edu.app.base.adapter.BaseSimpleListAdapter;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseListAdapter;

import org.kymjs.kjframe.Core;
import org.kymjs.kjframe.bitmap.BitmapCallBack;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by m on 2016-11-10.
 */

public class MineMusicListViewAdapter extends BaseSimpleListAdapter<Song>
{
    public MineMusicListViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId(int position, Song item)
    {
        return R.layout.item_mine_music;
    }


    @Override
    protected BaseViewHolder getViewHolder(View parent,int position, Song item) {
        return new ViewHolder(parent);
    }


    @Override
    protected void convert(BaseViewHolder vh, Song song, int position)
    {
        final ViewHolder holder = (ViewHolder) vh;
        holder.songTitle.setText(song.getTitle());
        holder.songSinger.setText(song.getSinger());
        new Core.Builder().url(song.getCoverImg()).view(holder.songCoverImg).loadBitmapRes(R.mipmap.load_img_default)
                .bitmapCallBack(new BitmapCallBack() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        super.onSuccess(bitmap);
                        if (bitmap != null)
                        {
                            holder.songCoverImg.setImageBitmap(bitmap);
                        }
                    }
                }).doTask();
    }


    public  class ViewHolder extends BaseViewHolder
    {
        @Bind(R.id.item_song_title)
        TextView songTitle;

        @Bind(R.id.item_song_cover)
        ImageView songCoverImg;

        @Bind(R.id.item_song_singer)
        TextView songSinger;

        @Bind(R.id.item_song_menu)
        View songMenu;


        public ViewHolder(View itemView)
        {
            ButterKnife.bind(this, itemView);
        }

    }
}

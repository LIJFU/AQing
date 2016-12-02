package com.xiaoyuan54.child.edu.app.ui.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.ui.music.widget.AlbumSongMenuDialog;
import com.xiaoyuan54.child.edu.app.util.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlbumSongListAdapterRecycle extends BaseRecyclerAdapter<Song>
{
    private Context context;

    public AlbumSongListAdapterRecycle(Context context, int mode) {
        super(context, mode);
        this.context = context;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_album_song, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder,final Song song, int position)
    {
        ViewHolder vh =(ViewHolder)holder;
        ImageView cover = vh.songCoverImg;
        TextView title =vh.songTitle;
        TextView singer =vh.songSinger;
        title.setText(song.getTitle());
        singer.setText(song.getSinger());
        ImageLoader.loadImage(Glide.with(mContext),cover,song.getCoverImg(),R.drawable.midden_fable);

        vh.songMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlbumSongMenuDialog dialog  = new AlbumSongMenuDialog(context,song);
                dialog.show();
            }
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.item_song_title)
        TextView songTitle;

        @Bind(R.id.item_song_cover)
        ImageView songCoverImg;

        @Bind(R.id.item_song_singer)
        TextView songSinger;

        @Bind(R.id.item_song_menu)
        View songMenu;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

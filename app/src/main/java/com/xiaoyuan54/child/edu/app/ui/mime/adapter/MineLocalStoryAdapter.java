package com.xiaoyuan54.child.edu.app.ui.mime.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.base.adapter.BaseSimpleListAdapter;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.bean.story.Story;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;

import org.kymjs.kjframe.Core;
import org.kymjs.kjframe.bitmap.BitmapCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by m on 2016-11-10.
 */

public class MineLocalStoryAdapter extends BaseRecyclerAdapter<Story>
{
    private Context context;

    public MineLocalStoryAdapter(Context context)
    {
       super(context,BOTH_HEADER_FOOTER);
        this.context = context;
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_local_story,parent,false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder vh, Story item, int position)
    {
        final ViewHolder holder = (ViewHolder) vh;
      //  holder.songTitle.setText(item.getTitle());
       // holder.songSinger.setText(item.getAuthor());
        new Core.Builder().url(item.getCoverImg()).view(holder.songCoverImg).loadBitmapRes(R.mipmap.load_img_default)
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


    public  class ViewHolder extends RecyclerView.ViewHolder
    {

        @Bind(R.id.iv_music_cover)
        ImageView songCoverImg;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}

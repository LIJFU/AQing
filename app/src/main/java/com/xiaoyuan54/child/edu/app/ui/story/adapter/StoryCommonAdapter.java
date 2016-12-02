package com.xiaoyuan54.child.edu.app.ui.story.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.story.Story;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.util.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * create by L.QING
 */

public class StoryCommonAdapter extends BaseRecyclerAdapter<Story>{

    private RequestManager reqManager;
    private Context context;

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



    public StoryCommonAdapter(Context context)
    {
        super(context, BOTH_HEADER_FOOTER);
        this.context = context;
        reqManager = Glide.with(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_music_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Story item, int position)
    {
        ViewHolder holder = (ViewHolder) h;
        holder.mViewTitle.setText(item.getTitle());
        holder.listenTv.setText(""+item.getPlayTimes());
        ImageLoader.loadImage(reqManager, holder.mImageCover, item.getCoverImg(),R.drawable.midden_fable);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.tv_title)
        TextView mViewTitle;

        @Bind(R.id.iv_music_cover)
        ImageView mImageCover;

        @Bind(R.id.song_listen_count)
        TextView listenTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

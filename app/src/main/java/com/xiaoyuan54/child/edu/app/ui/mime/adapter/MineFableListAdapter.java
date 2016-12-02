package com.xiaoyuan54.child.edu.app.ui.mime.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.util.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by m on 2016-11-27.
 */

public class MineFableListAdapter extends BaseRecyclerAdapter<Song> {



    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private RequestManager reqManager;
    private View.OnClickListener onPortraitClickListener;


    public MineFableListAdapter(Context context) {
        super(context, BOTH_HEADER_FOOTER);
        reqManager = Glide.with(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        RecyclerView.ViewHolder v = new MineFableListAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_music_item, parent, false));
        return v;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Song item, int position) {
        MineFableListAdapter.ViewHolder holder = (MineFableListAdapter.ViewHolder) h;
        if(position%2 != 0){
            Log.e("fable_list_adapter L 57",String.valueOf(position));
            holder.root.setGravity(Gravity.RIGHT);
        }else{
            holder.root.setGravity(Gravity.LEFT);
        }
        holder.mViewTitle.setText(item.getTitle()+position);
        ImageLoader.loadImage(reqManager, holder.mImageCover, item.getCoverImg(),R.drawable.midden_fable);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder h) {
        super.onViewRecycled(h);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout root;
        @Bind(R.id.tv_title)
        TextView mViewTitle;
        @Bind(R.id.iv_music_cover)
        ImageView mImageCover;

        int position = -1;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            root = (LinearLayout) itemView;
        }
    }
}

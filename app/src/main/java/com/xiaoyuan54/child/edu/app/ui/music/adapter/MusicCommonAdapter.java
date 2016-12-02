package com.xiaoyuan54.child.edu.app.ui.music.adapter;

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
import com.xiaoyuan54.child.edu.app.bean.Banner;
import com.xiaoyuan54.child.edu.app.bean.music.Music;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.widget.ViewNewsHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by m on 2016-11-08.
 */

public class MusicCommonAdapter extends BaseRecyclerAdapter<Music> {

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private RequestManager reqManager;
    private View.OnClickListener onPortraitClickListener;


    public MusicCommonAdapter(Context context) {
        super(context, BOTH_HEADER_FOOTER);
        reqManager = Glide.with(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_music_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Music item, int position) {
        ViewHolder holder = (ViewHolder) h;
        holder.mViewTitle.setText("吃了会变媛");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView mViewTitle;
        @Bind(R.id.iv_music_cover)
        ImageView mImageCover;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

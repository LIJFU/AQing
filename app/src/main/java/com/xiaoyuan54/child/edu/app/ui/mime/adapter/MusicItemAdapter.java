package com.xiaoyuan54.child.edu.app.ui.mime.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.music.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m on 2016-11-07.
 */

public class MusicItemAdapter implements ListAdapter {

    private List<Music> data;

    private Context mContent;

    private List<View> viewList = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public MusicItemAdapter(Context context,List<Music> data) {
        this.mContent =context;
        this.data = data;

        layoutInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        for(int i=0;i<data.size();i++) {
            View v = layoutInflater.inflate(R.layout.fragment_mine_recommend_music_item, null);
            viewList.add(v);
        }
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return viewList.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return viewList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return viewList.get(position).getLayerType();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

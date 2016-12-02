package com.xiaoyuan54.child.edu.app.ui.fable.adapter;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.my.Fable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m on 2016-10-30.
 */

public class FableItemAdapter implements ListAdapter {

    private List<Fable> fableList;

    private List<View> viewList = new ArrayList<>();

    private Context mContent;


    private LayoutInflater layoutInflater;

    public FableItemAdapter(Context context, List<Fable> data) {
        this.mContent = context;
        this.fableList = data;

        layoutInflater = LayoutInflater.from(context);

        initViewGroup();
    }

    private void initViewGroup() {
        for(int i=0;i<fableList.size();i++) {
            View v = layoutInflater.inflate(R.layout.fragment_item_fable, null);
            viewList.add(v);
        }
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return fableList.size();
    }

    @Override
    public Object getItem(int position) {
        return fableList.get(position);
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
        if(position>=viewList.size()){
            return viewList.get(0);
        }
        return viewList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return  0;
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

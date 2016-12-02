package com.xiaoyuan54.child.edu.app.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.my.Fable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m on 2016-11-01.
 */

public class TempAdapter implements ListAdapter {
    private List<String> sourceList;

    private List<View> viewList = new ArrayList<>();

    private Context mContent;

    private LayoutInflater layoutInflater;
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    public TempAdapter(Context context, List<String> data) {
        this.mContent = context;
        this.sourceList = data;

        layoutInflater = LayoutInflater.from(context);

        initViewGroup();
    }

    private void initViewGroup() {
        for(int i=0;i<sourceList.size();i++) {
            TextView v = new TextView(mContent);
            v.setText(sourceList.get(i));
            viewList.add(v);
        }
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
        return sourceList.size();
    }

    @Override
    public Object getItem(int position) {
        return sourceList.get(position);
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
        return 1;
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

package com.xiaoyuan54.child.edu.app.improve.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseNoRefreshListAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<T> mDatas;
    private List<T> mPreData;

    public Context mContext;

    public BaseNoRefreshListAdapter(Context context)
    {
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = new ArrayList<T>();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= 0 && position < mDatas.size())
            return mDatas.get(position);
        return null;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        T item = getItem(position);
        final ViewHolder vh = onCreateHolder(parent,position);
        convert(vh, item, position);
        return vh.getRootView();
    }

    public List<T> getDatas() {
        return this.mDatas;
    }

    protected abstract ViewHolder onCreateHolder(ViewGroup parent, int position);

    protected abstract void convert(ViewHolder vh, T item, int position);

    public void updateItem(int location, T item) {
        if (mDatas.isEmpty()) return;
        mDatas.set(location, item);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        checkListNull();
        mDatas.add(item);
        notifyDataSetChanged();
    }

    public void addItem(int location, T item) {
        checkListNull();
        mDatas.add(location, item);
        notifyDataSetChanged();
    }

    public void addItem(List<T> items) {
        checkListNull();
        if (items != null) {
            List<T> date = new ArrayList<>();
            if (mPreData != null) {
                for (T d : items) {
                    if (!mPreData.contains(d)) {
                        date.add(d);
                    }
                }
            } else {
                date = items;
            }
            mPreData = items;
            mDatas.addAll(date);
        }
        notifyDataSetChanged();
    }

    public void addItem(int position, List<T> items) {
        checkListNull();
        mDatas.addAll(position, items);
        notifyDataSetChanged();
    }

    public void removeItem(int location) {
        if (mDatas == null || mDatas.isEmpty()) {
            return;
        }
        mDatas.remove(location);
        notifyDataSetChanged();
    }

    public void clear() {
        if (mDatas == null || mDatas.isEmpty()) {
            return;
        }
        mPreData = null;
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void checkListNull() {
        if (mDatas == null) {
            mDatas = new ArrayList<T>();
        }
    }

    public LayoutInflater getInflate() {
        return mInflater;
    }

    public  class ViewHolder
    {
        public View root;

        public ViewHolder(int layout,ViewGroup parent)
        {
            root = mInflater.inflate(layout,parent,false);
            ButterKnife.bind(this, root);
        }

        public View getRootView()
        {
            return root;
        }
    }

}

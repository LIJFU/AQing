package com.xiaoyuan54.child.edu.app.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaoyuan54.child.edu.app.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * create by L.QING
 */
public abstract class BaseSimpleListAdapter<T> extends BaseAdapter
{

    private List<T> items;

    private Context context;

    public BaseSimpleListAdapter(Context context)
    {
        super();
        this.context = context;
        this.items = new ArrayList<T>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position)
    {
        if (position >= 0 && position < items.size())
            return items.get(position);
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
        View root = LayoutInflater.from(context).inflate(getLayoutId(position,item),parent,false);
        BaseViewHolder holder = getViewHolder(root,position,item);
        convert(holder, item, position);
        return root;
    }

    public List<T> getDatas() {
        return this.items;
    }

    protected abstract int getLayoutId(int position, T item);

    protected abstract BaseViewHolder getViewHolder(View parent,int position, T item);

    protected abstract void convert(BaseViewHolder holder, T item, int position);

    public void addItems(List<T> items)
    {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(T item)
    {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addItem(int position, T item) {
        items.add(position, item);
        notifyDataSetChanged();
    }


    public void addItem(int position, List<T> items) {
        this.items.addAll(position, items);
        notifyDataSetChanged();
    }

    public void removeItem(int location) {
        if (items == null || items.isEmpty()) {
            return;
        }
        items.remove(location);
        notifyDataSetChanged();
    }

    public void clear() {
        if (items == null || items.isEmpty()) {
            return;
        }
        items.clear();
        notifyDataSetChanged();
    }


    public abstract class BaseViewHolder
    {

    }

}

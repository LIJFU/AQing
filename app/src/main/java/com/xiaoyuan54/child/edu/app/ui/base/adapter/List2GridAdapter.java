package com.xiaoyuan54.child.edu.app.ui.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L.QING on 2016-11-28.
 *
 * 2 column list view
 */

public class List2GridAdapter<T> extends BaseAdapter
{
    private List<T> items;
    private Context context;

    public List2GridAdapter(Context context)
    {
        items = new ArrayList();
        this.context = context;
    }

    @Override
    public int getCount()
    {
        int c = items.size()%2;
        if(0==c)
        {
            return items.size()/2;
        }
        else
        {
            return items.size()/2+1;
        }
    }

    @Override
    public T[] getItem(int i)
    {
        //if(i=items.size())

        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public void setItems(List<T> items)
    {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<T> getItems()
    {
        return items;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        return null;
    }


    public abstract class BaseHolder
    {
        abstract public View getRoot();
    }
}

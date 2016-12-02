package com.xiaoyuan54.child.edu.app.dao;

import android.content.Context;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.db.utils.DBHelper;
import com.xiaoyuan54.child.edu.app.db.utils.SQLUtil;


/**
 * Created by L.QING on 2016-09-19.
 */
public abstract class  AbstractDao
{
    private Context context;

    public AbstractDao()
    {
       this.context = AppContext.getInstance();
    }

    public void insert(Object o)
    {
        SQLUtil.instance().insert(o);
    }

    public void update(Object o)
    {
        SQLUtil.instance().update(o);
    }

    public int delete(Object o)
    {
       return SQLUtil.instance().delete(o);
    }

    public Context getContext()
    {
        return context;
    }
}

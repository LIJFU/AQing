package com.xiaoyuan54.child.edu.app.db.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.bean.user.User;
import com.xiaoyuan54.child.edu.app.bean.version.Version;
import com.xiaoyuan54.child.edu.app.config.LogConfig;

/**
 * Created by L.QING on 2016-09-18.
 */
public class DBHelper extends SQLiteOpenHelper
{

    private static final String dbName="school_children_clz";

    private static final int dbVersion  = 1;

    private DBHelper(Context context)
    {
        super(context, dbName,null, dbVersion);
    }

    public static DBHelper instance()
    {
        return new DBHelper(AppContext.getInstance());
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Class<?>[] cls = tables();
        for(Class<?> cl: cls)
        {
            String tableSql = SQLUtil.instance().createTableSql(cl);
            db.execSQL(tableSql);
            Log.d(LogConfig.sql_log,"onCreate ****************************"+tableSql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion)
    {

    }

    private Class<?>[] tables()
    {
       return new Class[]
       {
         Version.class, User.class

       };
    }
}

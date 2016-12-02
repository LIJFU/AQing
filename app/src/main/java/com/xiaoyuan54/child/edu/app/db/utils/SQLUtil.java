package com.xiaoyuan54.child.edu.app.db.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xiaoyuan54.child.edu.app.config.LogConfig;
import com.xiaoyuan54.child.edu.app.db.annotation.Column;
import com.xiaoyuan54.child.edu.app.db.annotation.JType;
import com.xiaoyuan54.child.edu.app.db.annotation.PK;
import com.xiaoyuan54.child.edu.app.db.annotation.Table;
import com.xiaoyuan54.child.edu.app.util.TLog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by L.QING on 2016-09-18.
 */
public class SQLUtil {

    private  static enum Type
    {
        UPDATE,DELETE,INSERT,SELECT
    }

    public static SQLUtil instance()
    {
        return new SQLUtil();
    }

    public String createInsertSql(Object o)
    {
        return createSql(Type.INSERT,o);
    }

    public String createQuerySql(Object... keyAndValues)
    {
        return createSql(Type.SELECT,keyAndValues);
    }

    public String createUpdateSql(Object o)
    {
        return createSql(Type.UPDATE,o);
    }

    protected String createTableSql(Class<?> cls)
    {
        String tableName = getTableName(cls);
        String primaryKey= getPrimaryKey(cls);
        Field[] fs = AnnotationUtil.getAnnotationField(cls);

        StringBuffer sb = new StringBuffer();
        sb.append(" create table "+tableName);
        sb.append(" ( ");
        sb.append(primaryKey+" INTEGER PRIMARY KEY AUTOINCREMENT ,");
        for(Field fd : fs)
        {
            Column clm  = fd.getAnnotation(Column.class);
            sb.append(clm.name()+" "+clm.dbType().type+" ,");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" ) ");

        return sb.toString();
    }


    private  String createSql(Type type ,Object o)
    {
        String tableName = getTableName(o.getClass());
        Field[] fs = AnnotationUtil.getAnnotationField(o.getClass());

        StringBuffer sb  = new StringBuffer();
        Map<String,Object> fv = getFieldValue(fs,o);
        switch (type)
        {
            case INSERT:

                if(!fv.isEmpty())
                {
                  sb.append(" insert into "+ tableName);
                    sb.append(" ( ");
                  for(String key : fv.keySet())
                  {
                     sb.append(key+",");
                  }
                    sb.deleteCharAt(sb.length()-1);
                    sb.append(" ) ");

                    sb.append(" values ( ");
                  for(String key : fv.keySet())
                  {
                    sb.append("'"+fv.get(key)+"',");
                  }
                    sb.deleteCharAt(sb.length()-1);
                    sb.append(" ) ");
                }
             break;

            case UPDATE:
                String pk = AnnotationUtil.getPrimaryKey(o.getClass());
                sb.append("update "+tableName);
                sb.append(" set ");
                for(String key : fv.keySet())
                {
                    sb.append(key+"=" + " '"+fv.get(key)+"' ,");
                }
                sb.deleteCharAt(sb.length()-1);
                String getPKMethod = "get"+pk.substring(0,1).toUpperCase()+pk.substring(1);
                sb.append("where "+pk+"="+invokeGet(getPKMethod,o));
             break;

        }
        return sb.toString() ;
    }




    public Map<String,Object> getFieldValue(Field[] fields, Object o)
    {
        Map<String,Object> result = new HashMap<String,Object>();
        try
        {
            for(Field field : fields)
            {
                String name  = field.getName();
                String getMethodName = "get"+name.substring(0,1).toUpperCase()+name.substring(1);

                Object v = invokeGet(getMethodName,o);
                if(null==v)
                {
                    continue;
                }
                if(!field.isAnnotationPresent(Column.class))
                {
                    continue;
                }
                Column column = field.getAnnotation(Column.class);
                result.put(column.name(),v);
            }

        }
        catch (Exception e)
        {
            Log.e("yunmei_log",e.getMessage());
        }
        return result;
    }

    public Object invokeGet(String methodName,Object o)
    {
        try
        {
            Method getMethod = o.getClass().getMethod(methodName,new Class<?>[]{});
            return getMethod.invoke(o,new Object[]{});
        }
        catch (Exception e)
        {
            Log.e(LogConfig.sql_log,e.getMessage());
        }
        return "";

    }


    public void insert(Object o)
    {
        SQLiteDatabase db = DBHelper.instance().getWritableDatabase();
        String sql =  SQLUtil.instance().createInsertSql(o);
        Log.d(LogConfig.sql_log,"insert sql =========="+sql);
        db.execSQL(sql);
        db.close();
    }

    public void update(Object o)
    {
        SQLiteDatabase db = DBHelper.instance().getWritableDatabase();
        String sql =  SQLUtil.instance().createUpdateSql(o);
        Log.d(LogConfig.sql_log,"update sql =========="+sql);
        db.execSQL(sql);
        db.close();
    }

    public int delete(Object o)
    {
        try
        {
            SQLiteDatabase db = DBHelper.instance().getWritableDatabase();
            SQLUtil util = SQLUtil.instance();
            String tableName  = util.getTableName(o.getClass());
            String primary = util.getPrimaryKey(o.getClass());
            String id =util.invokeGet("getId",o).toString();
            int i  = db.delete(tableName,primary+"=?",new String[]{id});
            db.close();
            return i;
        }
        catch (Exception e)
        {
            TLog.error("sql error ------->>>"+e.getMessage());
        }
        return -1;
    }


    public <T> T queryOne(Class<T> t,String sql)
    {
        SQLiteDatabase db = DBHelper.instance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,new String[]{});
        Log.d("yunmei_log","query sql =========="+sql);
        if(!cursor.moveToFirst())
        {
            return null;
        }
        T result = injectValue(t,cursor);
        db.close();
        return result;
    }


    public <T> List<T> query(Class<T> t, String sql)
    {
        List<T> result = new ArrayList<>();
        SQLiteDatabase db = DBHelper.instance().getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,new String[]{});

        while(cursor.moveToNext()){
            T row = injectValue(t, cursor);
            result.add(row);
        }

        db.close();
        return result;
    }


    public void execute(String sql)
    {
        SQLiteDatabase db = DBHelper.instance().getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }



    public <T> T injectValue(Class<T> t,Cursor cursor)
    {
        T o = null;
        try
        {
            //CureModelData.UserPlan.class.newInstance();

            o = t.newInstance();
            Field[] fields  = AnnotationUtil.getAnnotationField(t);
            for(Field field :fields)
            {
                if(field.isAnnotationPresent(Column.class))
                {
                    Column column = field.getAnnotation(Column.class);
                    int index = cursor.getColumnIndex(column.name());
                    String name = field.getName();
                    String setMethodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1);
                    JType jType = column.javaType();
                    Method method  = t.getMethod(setMethodName,jType.clz);
                    switch (jType)
                    {
                        case INTEGER:
                            Integer iv = cursor.getInt(index);
                            method.invoke(o,iv);
                            break;

                        case LONG:
                            Long lv = cursor.getLong(index);
                            method.invoke(o,lv);
                            break;

                        case STRING:
                            String sv = cursor.getString(index);
                            method.invoke(o,sv);
                            break;
                    }
                }
            }

            // inject primary key
            String pk = AnnotationUtil.getPrimaryKey(t);
            if(null!=pk)
            {
                int index = cursor.getColumnIndex(pk);
                int sv  = cursor.getInt(index);
                String setMethodName = "set"+pk.substring(0,1).toUpperCase()+pk.substring(1);
                Method method  = t.getMethod(setMethodName,Integer.class);
                method.invoke(o,sv);
            }

        }
        catch (Exception e)
        {
            Log.e(LogConfig.error_log,e.getMessage());
            return  null;
        }

        return o;
    }






    public String getTableName(Class<?> cls)
    {
        if(cls.isAnnotationPresent(Table.class))
        {
            return cls.getAnnotation(Table.class).name();
        }
        return null;
    }


    public  String getPrimaryKey(Class<?> cls)
    {
        Field[] fields =  cls.getDeclaredFields();
        for(Field fd : fields)
        {
            if(fd.isAnnotationPresent(PK.class))
            {
                return fd.getAnnotation(PK.class).name();
            }
        }
        return null;
    }

}

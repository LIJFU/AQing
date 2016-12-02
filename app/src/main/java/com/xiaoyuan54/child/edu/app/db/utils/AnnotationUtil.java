package com.xiaoyuan54.child.edu.app.db.utils;

import com.xiaoyuan54.child.edu.app.db.annotation.Column;
import com.xiaoyuan54.child.edu.app.db.annotation.PK;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by L.QING on 2016-09-19.
 */
public class AnnotationUtil {


    public static synchronized Field[] getAnnotationField(Class<?> cls)
    {
        List<Field> fs = new ArrayList<Field>();
        Field[] fields =  cls.getDeclaredFields();
        for(Field fd : fields)
        {
            if(fd.isAnnotationPresent(Column.class))
            {
                fs.add(fd);
            }
        }

        return fs.toArray(new Field[fs.size()]);
    }


    public static synchronized String getPrimaryKey(Class<?> cls)
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

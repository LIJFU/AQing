package com.xiaoyuan54.child.edu.app.db.annotation;

import java.util.Date;

/**
 * Created by L.QING on 2016-11-17.
 */

public enum JType {

    INTEGER(Integer.class),STRING(String.class),LONG(Long.class);

    JType(Class clz)
    {
       this.clz = clz;
    }

    public Class clz;
}

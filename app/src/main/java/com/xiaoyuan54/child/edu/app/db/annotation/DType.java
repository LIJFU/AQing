package com.xiaoyuan54.child.edu.app.db.annotation;

/**
 * Created by L.QING on 2016-11-17.
 */

public enum DType {

    INTEGER("INTEGER"),VARCHAR("VARCHAR");

    DType(String type)
    {
       this.type= type;
    }

    public String type;
}

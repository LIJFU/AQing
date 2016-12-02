package com.xiaoyuan54.child.edu.app.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by L.QING on 2016-09-18.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    String name();

    DType dbType() default DType.VARCHAR;

    JType javaType() default JType.STRING;

}

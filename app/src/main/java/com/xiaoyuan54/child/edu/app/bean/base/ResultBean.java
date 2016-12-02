package com.xiaoyuan54.child.edu.app.bean.base;

/**
 * Created by L.QING on 2016-11-28.
 */

public class ResultBean<T> {

    T item;

    private String stamsg;

    private int stacode;

    public boolean isSuccess()
    {
        return 200==stacode;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public String getStamsg() {
        return stamsg;
    }

    public void setStamsg(String stamsg) {
        this.stamsg = stamsg;
    }

    public int getStacode() {
        return stacode;
    }

    public void setStacode(int stacode) {
        this.stacode = stacode;
    }
}

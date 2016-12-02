package com.xiaoyuan54.child.edu.app.bean.base;

import java.io.Serializable;
import java.util.List;

/**
 * create by L.QING
 */
public class PageBean<T> implements Serializable
{
    private String stamsg;

    private int stacode;

    private List<T> item;

    private int pageSize =20;

    private int pageIndex = 0;

    public boolean hasNext()
    {
        if(0==pageIndex||(null!=item&&item.size()==pageSize))
        {
            return true;
        }
        return false;
    }

    public int getNextPage()
    {
        if(!hasNext())
        {
            return -1;
        }
        return pageIndex+1;
    }

    public boolean isSuccess()
    {
        return 200==stacode;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<T> getItem() {
        return item;
    }

    public void setItem(List<T> item) {
        this.item = item;
    }
}
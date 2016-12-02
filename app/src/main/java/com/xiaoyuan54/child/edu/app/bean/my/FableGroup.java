package com.xiaoyuan54.child.edu.app.bean.my;

import java.util.List;

/**
 * Created by m on 2016-10-30.
 */

public class FableGroup {

    /**
     * 标题
     * */
    private String title;

    private List<Fable> fableList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Fable> getFableList() {
        return fableList;
    }

    public void setFableList(List<Fable> fableList) {
        this.fableList = fableList;
    }
}

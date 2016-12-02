package com.xiaoyuan54.child.edu.app.bean.my;

import com.xiaoyuan54.child.edu.app.bean.Entity;

/**
 * Created by m on 2016-10-30.
 */

public class Fable  extends Entity{

    /**
     * b标题
     * */
    private String title;
    /**
     * 阅读计数
     * */
    private String count;
    /**
     * key
     * */
    private int id;

    /**
     * 图片
     * */
    private String image;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}

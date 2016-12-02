package com.xiaoyuan54.child.edu.app.bean.music;

import com.xiaoyuan54.child.edu.app.db.annotation.Column;
import com.xiaoyuan54.child.edu.app.db.annotation.DType;
import com.xiaoyuan54.child.edu.app.db.annotation.JType;
import com.xiaoyuan54.child.edu.app.db.annotation.PK;
import com.xiaoyuan54.child.edu.app.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by L.QING on 2016-11-19.
 * Album 和 Song 的中间表
 */

@Table(name="music_menu_song")
public class MusicMenu_Song implements Serializable
{
    @PK
    private Integer id;

    @Column(name = "menu_id",dbType = DType.INTEGER,javaType = JType.INTEGER)
    private Integer albumId;

    @Column(name = "song_id",dbType = DType.INTEGER,javaType = JType.INTEGER)
    private String songId;

    @Column(name = "sort",dbType = DType.INTEGER,javaType = JType.INTEGER)
    private Integer sort;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}

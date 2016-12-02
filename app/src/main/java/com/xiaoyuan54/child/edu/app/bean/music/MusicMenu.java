package com.xiaoyuan54.child.edu.app.bean.music;

import com.xiaoyuan54.child.edu.app.db.annotation.PK;
import com.xiaoyuan54.child.edu.app.db.annotation.Table;

/**
 * Created by L.QING on 2016-11-19.
 *
 * 用户歌单
 * The song menu created by user
 */
@Table(name = "music_menu")
public class MusicMenu
{

    @PK
    private Integer id;
}

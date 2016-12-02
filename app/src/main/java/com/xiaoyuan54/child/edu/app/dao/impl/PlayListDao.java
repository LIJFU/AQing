package com.xiaoyuan54.child.edu.app.dao.impl;

import com.xiaoyuan54.child.edu.app.dao.AbstractDao;
import com.xiaoyuan54.child.edu.app.modules.music.PlayList;

import java.util.List;

/**
 * Created by L.QING on 2016-11-19.
 */

public class PlayListDao extends AbstractDao {

    public static PlayListDao instance()
    {
        return new PlayListDao();
    }

}

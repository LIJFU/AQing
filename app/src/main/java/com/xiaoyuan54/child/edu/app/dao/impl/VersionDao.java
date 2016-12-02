package com.xiaoyuan54.child.edu.app.dao.impl;

import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.bean.version.Version;
import com.xiaoyuan54.child.edu.app.dao.AbstractDao;
import com.xiaoyuan54.child.edu.app.db.utils.DBHelper;
import com.xiaoyuan54.child.edu.app.db.utils.SQLUtil;

import java.util.List;

/**
 * Created by L.QING on 2016-11-19.
 */

public class VersionDao extends AbstractDao {

    public static VersionDao instance()
    {
        return new VersionDao();
    }


    public Version queryByVersionCode(int vCode)
    {
        String sql ="select * from version where version_code = "+vCode;
        return SQLUtil.instance().queryOne(Version.class,sql);
    }

    public Version getOne()
    {
        String sql ="select * from version limit 1";
        return SQLUtil.instance().queryOne(Version.class,sql);
    }



}

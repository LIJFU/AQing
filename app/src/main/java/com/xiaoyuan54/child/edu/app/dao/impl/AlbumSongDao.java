package com.xiaoyuan54.child.edu.app.dao.impl;

import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.dao.AbstractDao;
import com.xiaoyuan54.child.edu.app.db.utils.DBHelper;
import com.xiaoyuan54.child.edu.app.db.utils.SQLUtil;

import java.util.List;

/**
 * Created by L.QING on 2016-11-19.
 */

public class AlbumSongDao extends AbstractDao {

    public static AlbumSongDao instance()
    {
        return new AlbumSongDao();
    }


    public List<Song> querySongs(int albumId)
    {
        String sql = " select s.* from song s inner join (select song_id from album_song where id = "+albumId+") al on s.id = al.song_id ";
        return SQLUtil.instance().query(Song.class,sql);
    }



}

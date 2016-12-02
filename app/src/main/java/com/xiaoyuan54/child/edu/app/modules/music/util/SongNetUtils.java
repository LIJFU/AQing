package com.xiaoyuan54.child.edu.app.modules.music.util;

import com.xiaoyuan54.child.edu.app.api.ApiHttpClient;
import com.xiaoyuan54.child.edu.app.bean.music.Song;


public class SongNetUtils {


    public static String getSongNetUrl(String path)
    {
        if(null==path)
        {
            return "";
        }
        if(path.startsWith("http"))
        {
            return path;
        }
        return String.format(ApiHttpClient.API_URL, path);
    }

}

package com.xiaoyuan54.child.edu.app;

import android.os.Environment;

import com.xiaoyuan54.child.edu.app.util.FileUtil;

import java.io.File;

/**
 * Created by L.QING on 2016-11-25.
 */

public class AppDirManager
{
   public final static String ROOT = Environment
           .getExternalStorageDirectory()
           + File.separator+"com.54xiaoyuan";

   public final static String CACHE = ROOT+File.separator+"cache";

   public final static String CACHE_IMG = CACHE+File.separator+"image";

   public final static String CACHE_MUSIC = CACHE+File.separator+"music";

   public final static String CACHE_LOG = CACHE+File.separator+"log";

   public final static String MUSIC = ROOT+File.separator+"music";

   public final static String IMAGE = ROOT+File.separator+"image";

   public final static String SETTING = ROOT+File.separator+"setting";

   public final static String DOWNLOAD = ROOT+File.separator+"download";

   public final static String APK = DOWNLOAD+File.separator+"apk";

   private final static String[] Dirs
      = new String[]{CACHE_IMG,CACHE_MUSIC,CACHE_LOG,MUSIC,IMAGE,SETTING,APK};

   public static void initDir()
   {
       for(String dir :Dirs)
       {
           FileUtil.createPath(dir);
       }
   }
}

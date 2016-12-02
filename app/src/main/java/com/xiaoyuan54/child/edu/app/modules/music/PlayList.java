package com.xiaoyuan54.child.edu.app.modules.music;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.xiaoyuan54.child.edu.app.AppConfig;
import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.AppDirManager;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.config.LogConfig;
import com.xiaoyuan54.child.edu.app.modules.music.interf.PlayListActionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by L.QING on 2016-11-08.
 */

public class PlayList implements Serializable
{

    private  static PlayList playList;

    private String persistence_path = AppDirManager.SETTING+File.separator+"playlist";

    private List<Song> songs = null;

    private int playIndex = 10;

    private List<PlayListActionListener> listeners;

    private PlayMode playMode = PlayMode.LOOP;

    public enum Action
    {
        READY,CHANGE,EMPTY,SONG_CHANGE
    }

    public synchronized static PlayList getPlayList()
    {
        if(null==playList)
        {
            playList = new PlayList();
        }
        return playList;
    }


    private void  onCreate()
    {
        songs = new ArrayList();
        listeners = new ArrayList();
        init();
    }


    public void destroy()
    {
        persistence();
    }

    public void persistence()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                ObjectOutputStream objOut = null;
                FileOutputStream fio = null;
                try
                {
                    File playListFile = new File(persistence_path);
                    if(playListFile.exists())
                    {
                        boolean fg = playListFile.delete();
                    }
                    fio= new FileOutputStream(playListFile,false);
                    objOut = new ObjectOutputStream(fio);
                    objOut.writeObject(songs);
                    objOut.flush();
                }
                catch (Exception e) {
                    Log.e(LogConfig.error_log,"---------->>>持久化PlayList出错");
                }
                finally
                {
                    try
                    {
                        if(null!=objOut)
                            objOut.close();
                        if (null!=fio)
                            fio.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void init()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                FileInputStream fio = null;
                ObjectInputStream objIn = null;
                try
                {
                    fio = new FileInputStream(persistence_path);
                    objIn = new ObjectInputStream(fio);
                    songs= (List<Song>) objIn.readObject();
                    playIndex = songs.size()-1;
                }
                catch (Exception e)
                {
                    Log.e(LogConfig.error_log,"---------->>>反序列化PlayList出错---"+e.getMessage());
                    // 反序列化失败 - 删除缓存文件
                    if (e instanceof InvalidClassException)
                    {
                        File data = AppContext.getInstance().getFileStreamPath(persistence_path);
                        data.delete();
                    }
                }
                finally
                {
                    try
                    {
                        if(null!=objIn)
                            objIn.close();
                        if (null!=fio)
                            fio.close();
                    }
                    catch (Exception e){}

                }

                notifyActionChange(Action.READY);
            }
        }).start();

    }

    public void setPlayIndex(int index)
    {
        playIndex = index;
    }

    public int getPlayIndex()
    {
        return playIndex;
    }

    public void updateSongList(List<Song> songs,int index)
    {
        this.songs.clear();
        this.songs.addAll(songs);
        playIndex = index;
        notifyActionChange(Action.SONG_CHANGE);

        persistence();
    }

    public void addSong(Song song,int index)
    {
        songs.add(index,song);
        notifyActionChange(Action.CHANGE);
    }

    public void resort(int position)
    {
       int newIndex = position-1;
       Song song = songs.get(position);
       songs.remove(position);
       songs.add(newIndex,song);
       if(position==playIndex)
       {
           playIndex = newIndex;
       }
       else if (newIndex == playIndex)
       {
           playIndex++;
       }
       notifyActionChange(Action.CHANGE);
    }


    public boolean isPrepare()
    {
        return !songs.isEmpty();
    }

    public int size()
    {
        return songs.size();
    }

    public List<Song> getSongs()
    {
        return songs;
    }

    public Song get(int index)
    {
        return songs.get(index);
    }

    public void remove(int index)
    {
        songs.remove(index);
        int oldIndex = playIndex;
        if(playIndex >=index)
        {
            playIndex= playIndex-1<0?0:playIndex-1;
        }
        if(songs.isEmpty())
        {
            playIndex = -1;
            notifyActionChange(Action.EMPTY);
        }
        else if (oldIndex==index)
        {
            notifyActionChange(Action.SONG_CHANGE);
        }
        else
        {
            notifyActionChange(Action.CHANGE);
        }
    }

    public void clear()
    {
      songs.clear();
      playIndex = -1;
      notifyActionChange(Action.EMPTY);
    }


    public Song getPlayingSong()
    {
        if(playIndex <0|| playIndex >=songs.size())
        {
            return null;
        }
        return songs.get(playIndex);
    }

    public void setPlayingSong(int index)
    {
        if(index <0|| index >=songs.size())
        {
            index = 0;
        }
        playIndex = index;

        notifyActionChange(Action.SONG_CHANGE);
    }

    public void moveToNextSong()
    {
        if(songs.isEmpty())
        {
            return;
        }
        playIndex = getNextPlayIndex();
        notifyActionChange(Action.SONG_CHANGE);
    }



    public Song getNextSong()
    {
        if(songs.isEmpty())
        {
            return null;
        }
        playIndex = getNextPlayIndex();
        notifyActionChange(Action.SONG_CHANGE);
        return songs.get(playIndex);
    }


    public void registerActionListener(PlayListActionListener listener)
    {
        listeners.add(listener);
        if(isPrepare())
        {
            listener.listActionChange(Action.READY);
        }
    }

    public void unregisterActionListener(PlayListActionListener listener)
    {
        listeners.remove(listener);
    }

    private int getNextPlayIndex()
    {
        int index = playIndex;
        switch (playMode)
        {
            case SINGLE:
                int sgIndex = playIndex+1;
                index = sgIndex>songs.size()-1?0:sgIndex;
            break;

            case LOOP:
                int newIndex = playIndex+1;
                index = newIndex>songs.size()-1?0:newIndex;
            break;

            case SHUFFLE:
                index = randomPlayIndex();
            break;
        }
        return index;
    }

    private int randomPlayIndex()
    {
        int randomIndex = new Random().nextInt(songs.size());
        if (songs.size() > 1 && randomIndex == playIndex)
        {
            randomPlayIndex();
        }
        return randomIndex;
    }

    public PlayMode getPlayMode() {
        return playMode;
    }

    public void setPlayMode(PlayMode playMode)
    {
        this.playMode = playMode;
        notifyActionChange(Action.CHANGE);
    }

    private void notifyActionChange(Action action)
    {
      for(PlayListActionListener listener :listeners)
      {
          listener.listActionChange(action);
      }
    }

    private PlayList()
    {
        onCreate();
    }

}

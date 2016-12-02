package com.xiaoyuan54.child.edu.app.modules.music.service;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.xiaoyuan54.child.edu.app.base.service.BaseService;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.config.LogConfig;
import com.xiaoyuan54.child.edu.app.modules.music.IPlayback;
import com.xiaoyuan54.child.edu.app.modules.music.PlayList;
import com.xiaoyuan54.child.edu.app.modules.music.PlayMode;
import com.xiaoyuan54.child.edu.app.modules.music.action.PlayerActionKey;
import com.xiaoyuan54.child.edu.app.modules.music.action.PlayerAction;
import com.xiaoyuan54.child.edu.app.modules.music.interf.PlayListActionListener;
import com.xiaoyuan54.child.edu.app.modules.music.util.SongNetUtils;

/**
 * Created by L.QING on 2016-11-04.
 * This service run in background which manage the music player
 */

public class MusicPlayerService extends BaseService
    implements IPlayback, MediaPlayer.OnCompletionListener,PlayListActionListener,
    MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnPreparedListener
{

    private MediaPlayer mediaPlayer;

    private Song currentSong;

    private PlayList playList;

    private int buffer_percent;
    private boolean is_pause = false;

    @Override
    public IBinder onBind(Intent intent)
    {
        initService();
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        initService();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initService()
    {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(this);
        playList = PlayList.getPlayList();
        playList.registerActionListener(this);
    }


    @Override
    public boolean play()
    {
        try
        {
            if(is_pause)
            {
                mediaPlayer.start();
                sendBroadcast(PlayerActionKey.Val.PLAY);
                return true;
            }
            Song  song = playList.getPlayingSong();
            if(null==song) return false;
            if(null==currentSong||(!currentSong.equals(song)))
            {
                currentSong = song;
            }
            buffer_percent = 0;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(SongNetUtils.getSongNetUrl(currentSong.getSongUrl()));
            mediaPlayer.prepareAsync();
            return true;
        }
        catch (Exception e)
        {
            Log.e(LogConfig.error_log,"-------------->>play music error--"+e.getMessage());
        }
        return false;
    }


    @Override
    public boolean play(Song song)
    {
        try
        {
            mediaPlayer.setOnCompletionListener(null);
            currentSong = song;
            buffer_percent = 0;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(SongNetUtils.getSongNetUrl(currentSong.getSongUrl()));
            mediaPlayer.prepareAsync();
            return true;
        }
        catch (Exception e)
        {
            Log.e(LogConfig.error_log,"-------------->>play music error--"+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean playLast()
    {
        return false;
    }

    @Override
    public boolean playNext()
    {
       Song song = playList.getNextSong();
       return play(song);
    }

    @Override
    public boolean pause()
    {
        if(isPlaying())
        {
           mediaPlayer.pause();
           is_pause = true;
           sendBroadcast(PlayerActionKey.Val.PAUSE);
           return true;
        }
        return false;
    }

    public boolean isPlaying()
    {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getProgress()
    {
        return mediaPlayer.getCurrentPosition();
    }


   @Override
    public int getBufProgress()
    {
        return buffer_percent;
    }


    @Override
    public int getDuration()
    {
        return mediaPlayer.getDuration();
    }

    @Override
    public Song getPlayingSong()
    {
        return currentSong;
    }

    @Override
    public boolean seekTo(int progress)
    {
        mediaPlayer.seekTo(progress);

        return true;
    }

    @Override
    public void setPlayMode(PlayMode playMode)
    {
       playList.setPlayMode(playMode);
    }

    @Override
    public PlayMode getPlayMode()
    {
       return  playList.getPlayMode();
    }

    @Override
    public void registerCallback(Callback callback) {

    }

    @Override
    public void unregisterCallback(Callback callback) {

    }

    @Override
    public void removeCallbacks() {

    }

    @Override
    public void releasePlayer()
    {

    }


    public void stop()
    {
        mediaPlayer.stop();
        currentSong = null;
        sendBroadcast(PlayerActionKey.Val.STOP);
    }


    private void sendBroadcast(PlayerActionKey.Val val)
    {
        Intent intent = new Intent(PlayerAction.POUT.ACT);
        intent.putExtra(PlayerActionKey.Key,val.val);
        sendBroadcast(intent);
    }


    @Override
    public void onDestroy()
    {
        playList.unregisterActionListener(this);
        super.onDestroy();
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent)
    {
        buffer_percent = percent;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer)
    {
        this.mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(this);
        sendBroadcast(PlayerActionKey.Val.PLAY);
    }

    @Override
    public void onCompletion(MediaPlayer player)
    {
        playList.moveToNextSong();
    }


    @Override
    public void listActionChange(PlayList.Action action)
    {
        switch (action)
        {
            case READY:
                break;

            case SONG_CHANGE:
                Song song = playList.getPlayingSong();
                play(song);
            break;

            case EMPTY:
                stop();
                break;
        }
    }

    private IBinder binder = new LocalBind();
    public class  LocalBind extends Binder
    {
         public MusicPlayerService getService()
         {
             return MusicPlayerService.this;
         }
    }

}

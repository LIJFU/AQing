package com.xiaoyuan54.child.edu.app.modules.music;

import android.support.annotation.Nullable;

import com.xiaoyuan54.child.edu.app.bean.music.Song;

public interface IPlayback {


    boolean play();

    boolean play(Song song);

    boolean playLast();

    boolean playNext();

    boolean pause();

    boolean isPlaying();

    int getProgress();

    int getBufProgress();

    int getDuration();

    Song getPlayingSong();

    boolean seekTo(int progress);

    void setPlayMode(PlayMode playMode);

    PlayMode getPlayMode();

    void registerCallback(Callback callback);

    void unregisterCallback(Callback callback);

    void removeCallbacks();

    void releasePlayer();

    interface Callback {

        void onSwitchLast(@Nullable Song last);

        void onSwitchNext(@Nullable Song next);

        void onComplete(@Nullable Song next);

        void onPlayStatusChanged(boolean isPlaying);
    }
}

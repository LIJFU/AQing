package com.xiaoyuan54.child.edu.app.modules.music;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xiaoyuan54.child.edu.app.base.BasePresenter;
import com.xiaoyuan54.child.edu.app.base.BaseView;
import com.xiaoyuan54.child.edu.app.bean.music.Song;


interface MusicPlayerContract {

    interface View extends BaseView<Presenter> {

        void handleError(Throwable error);

       // void onPlaybackServiceBound(PlaybackService service);

        void onPlaybackServiceUnbound();

        void onSongSetAsFavorite(@NonNull Song song);

        void onSongUpdated(@Nullable Song song);

        void updatePlayMode(PlayMode playMode);

        void updatePlayToggle(boolean play);

        void updateFavoriteToggle(boolean favorite);
    }

    interface Presenter extends BasePresenter {

        void retrieveLastPlayMode();

        void setSongAsFavorite(Song song, boolean favorite);

        void bindPlaybackService();

        void unbindPlaybackService();
    }
}

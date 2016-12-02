package com.xiaoyuan54.child.edu.app.modules.music.interf;
import com.xiaoyuan54.child.edu.app.ui.base.fragment.BaseFragment;
import com.xiaoyuan54.child.edu.app.modules.music.service.MusicPlayerService;
import com.xiaoyuan54.child.edu.app.ui.music.widget.PlayerBar;

/**
 * Created by L.QING on 2016-11-10.
 */

public interface MainPresenter
{
   void attachFrag(BaseFragment fragment);

   void detachFrag();

   PlayerBar getPlayerBar();

   MusicPlayerService getMusicPlayer();
}

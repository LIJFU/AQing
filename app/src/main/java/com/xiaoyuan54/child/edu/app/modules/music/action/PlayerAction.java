package com.xiaoyuan54.child.edu.app.modules.music.action;

/**
 * Created by L.QING on 2016-11-23.
 */

public enum PlayerAction
{
    /**
     * musicPlayerService send broadcast
     */
    POUT("com.54xiaoyuan.player.out.action");


    PlayerAction(String action)
    {
      this.ACT = action;
    }

    public String ACT;
}

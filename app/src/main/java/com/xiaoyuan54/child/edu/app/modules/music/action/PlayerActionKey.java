package com.xiaoyuan54.child.edu.app.modules.music.action;

import com.xiaoyuan54.child.edu.app.widget.IndexView;

/**
 * Created by L.QING on 2016-11-23.
 */

public class PlayerActionKey {

    public final static String Key ="_player_action_key";

    public static enum  Val
    {
        STOP(1),PAUSE(2),PLAY(3);

        Val(int val)
        {
            this.val = val;
        }

        public int val;

        public static Val fromVal(int iVal)
        {
            for (Val val : values())
            {
                if(val.val== iVal)
                {
                    return val;
                }
            }
            return null;
        }
    }
}

package com.xiaoyuan54.child.edu.app.modules.music;

public enum PlayMode
{
    SINGLE, LOOP,SHUFFLE;

    public static PlayMode getDefault() {
        return LOOP;
    }

    public static PlayMode switchNextMode(PlayMode current) {
        if (current == null) return getDefault();

        switch (current) {
            case LOOP:
                return SINGLE;
            case SINGLE:
                return SHUFFLE;
            case SHUFFLE:
                return LOOP;
        }
        return getDefault();
    }
}

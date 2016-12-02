package com.xiaoyuan54.child.edu.app.ui.music.activity;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.improve.base.activities.NormalBaseActivity;
import com.xiaoyuan54.child.edu.app.modules.music.PlayList;
import com.xiaoyuan54.child.edu.app.modules.music.PlayMode;
import com.xiaoyuan54.child.edu.app.modules.music.interf.PlayListActionListener;
import com.xiaoyuan54.child.edu.app.ui.ShareDialog;
import com.xiaoyuan54.child.edu.app.ui.music.adapter.PlayerLyricListAdapter;
import com.xiaoyuan54.child.edu.app.ui.music.widget.PlayListDialog;

import butterknife.Bind;
import butterknife.OnClick;


public class MusicDetailActivity extends NormalBaseActivity
        implements PlayListActionListener
{

    @Bind(R.id.music_d_title)
    public TextView titleView;

    @Bind(R.id.player_favor)
    public ImageView favorImg;

    @Bind(R.id.player_play)
    public ImageView playImg;

    @Bind(R.id.player_lyric_lv)
    public ListView lyricLv;

    @Bind(R.id.player_play_mode) ImageView playMode;

    PlayList playList;

    public static void lunch(Context context)
    {
        Intent intent = new Intent(context, MusicDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView()
    {
        return R.layout.activity_music_detail;
    }

    @Override
    protected void initData()
    {
        //presenter = MusicPlayerPresenter.getInstance();
       // presenter.registerStatusListener(this);

        playList =PlayList.getPlayList();
        playList.registerActionListener(this);

       /* if(presenter.isIs_playing())
        {
            playImg.setImageDrawable(getResources().getDrawable(R.mipmap.player_ing_white));
        }
      */
        lyricLv.setAdapter(new PlayerLyricListAdapter(this));
    }



    @OnClick(value={R.id.music_d_title,R.id.player_favor,
                    R.id.player_share,R.id.player_play,
                    R.id.player_play_list,R.id.back,
                    R.id.player_play_mode})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
            break;

            case R.id.player_share:
                ShareDialog dialog = new ShareDialog(this);
                dialog.getTitleTextView().setText("标题就是这个");
                dialog.setShareInfo("放一些菜单","","");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            break;

            case R.id.player_favor:
                favorImg.setImageDrawable(getResources().getDrawable(R.mipmap.player_favor_sel));
            break;

            case R.id.player_play:

                /*if(presenter.isIs_playing())
                {
                   presenter.pause();
                }
                else
                {
                   presenter.play();
                }*/



            break;

            case R.id.player_play_list:
                PlayListDialog listDialog = new PlayListDialog(this);
                listDialog.setCancelable(true);
                listDialog.setCanceledOnTouchOutside(true);
                listDialog.show();
            break;

            case  R.id.player_play_mode:
                playList.setPlayMode(PlayMode.switchNextMode(playList.getPlayMode()));
            break;

        }

    }


    @Override
    protected void onDestroy()
    {
        //presenter.unregisterStatusListener(this);
        playList.unregisterActionListener(this);
        super.onDestroy();
    }

/*    @Override
    public void statusChange(Status status)
    {
        switch (status)
        {
            case READY:
                break;

            case PAUSE:
                playImg.setImageDrawable(getResources().getDrawable(R.mipmap.player_pause_white));
            break;

            case PLAY:
                playImg.setImageDrawable(getResources().getDrawable(R.mipmap.player_ing_white));
            break;
        }
    }*/


    @TargetApi(23)
    private void updateViewUI()
    {
        PlayMode mode = playList.getPlayMode();

        int    modeRes =R.mipmap.play_mode_single_gray;
        switch (mode)
        {
            case LOOP:
                modeRes =R.mipmap.play_mode_loop_white;
                break;

            case SHUFFLE:
                modeRes =R.mipmap.play_mode_shuffle_white;
                break;
        }

        if(Build.VERSION.SDK_INT<23){
            playMode.setImageDrawable(getResources().getDrawable(modeRes));
        }
        else{
            playMode.setImageDrawable(getDrawable(modeRes));
        }
    }



    @Override
    public void listActionChange(PlayList.Action action)
    {
        switch (action)
        {
            case READY:
                break;

            case CHANGE:
                updateViewUI();
            break;

            case EMPTY:
                finish();
            break;
        }

    }
}

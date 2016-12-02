package com.xiaoyuan54.child.edu.app.ui.music.fragment;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.ui.base.fragment.BaseFragment;
import com.xiaoyuan54.child.edu.app.modules.music.PlayMode;
import com.xiaoyuan54.child.edu.app.modules.music.action.PlayerAction;
import com.xiaoyuan54.child.edu.app.modules.music.action.PlayerActionKey;
import com.xiaoyuan54.child.edu.app.modules.music.service.MusicPlayerService;
import com.xiaoyuan54.child.edu.app.ui.ShareDialog;
import com.xiaoyuan54.child.edu.app.ui.main.MainActivity;
import com.xiaoyuan54.child.edu.app.ui.music.adapter.PlayerLyricListAdapter;
import com.xiaoyuan54.child.edu.app.ui.music.widget.PlayListDialog;
import com.xiaoyuan54.child.edu.app.util.StringUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;


public class MusicDetailFragment extends BaseFragment
{
    @Bind(R.id.music_d_title)    TextView titleView;

    @Bind(R.id.player_positon_tx)    TextView positionTx;

    @Bind(R.id.player_duration_tx)    TextView durationTx;

    @Bind(R.id.player_favor)     ImageView favorImg;

    @Bind(R.id.player_play)      ImageView playImg;

    @Bind(R.id.player_lyric_lv)  ListView lyricLv;

    @Bind(R.id.player_play_mode) ImageView playModeImg;

    @Bind(R.id.player_seekBar) SeekBar playSeekBar;

    MainActivity context;
    MusicPlayerService playerService;
    Timer  updateProgressTimer;

    Handler handler;

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_music_detail;
    }

    @Override
    protected void initWidget(View root)
    {
        super.initWidget(root);
        context = (MainActivity) getContext();
        context.registerReceiver(receiver,new IntentFilter(PlayerAction.POUT.ACT));
        handler = new Handler();
        updateProgressTimer = new Timer();
        updateProgressTimer.schedule(updateProgressTask,0,1000l);
        playSeekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        playerService = context.getMusicPlayer();
        context.getPlayerBar().setBarNeed(false);
        if(playerService.isPlaying())
        {
            initPlayUI();
        }
        updatePlayModel();
    }

    @Override
    protected void initData()
    {
        super.initData();
        lyricLv.setAdapter(new PlayerLyricListAdapter(context));
    }


    @Override
    public void onDestroy()
    {
        context.unregisterReceiver(receiver);
        updateProgressTimer.cancel();
        updateProgressTimer = null;
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {

        context.getPlayerBar().setBarNeed(true);
        super.onDetach();
    }


    @OnClick(value =
           {R.id.back,R.id.player_play_list,
            R.id.player_play,R.id.player_play_mode,
            R.id.player_play_next,R.id.player_play_last,
                   R.id.player_share})
    public void clickAction(View view)
    {
      switch (view.getId())
      {
          case R.id.back:
              context.onBackPressed();
          break;

          case R.id.player_play:

              if(playerService.isPlaying())
              {
                  playerService.pause();
              }
              else
              {
                  playerService.play();
              }
          break;

          case R.id.player_play_next:
              playerService.playNext();
          break;

          case R.id.player_play_last:
              playerService.playNext();
          break;

          case R.id.player_play_list:
              PlayListDialog listDialog = new PlayListDialog(context);
              listDialog.show();
          break;

          case R.id.player_share:
              new ShareDialog(context).show();
          break;

          case R.id.player_play_mode:
              playerService.setPlayMode(PlayMode.switchNextMode(playerService.getPlayMode()));
              updatePlayModel();
          break;
      }
    }



    private void initPlayUI()
    {
        playImg.setImageDrawable(getResources().getDrawable(R.mipmap.player_ing_white));
    }


    @TargetApi(23)
    private void updatePlayModel()
    {

        PlayMode playMode = playerService.getPlayMode();
        int    modeRes =R.mipmap.play_mode_single_white;
        switch (playMode)
        {
            case LOOP:
                modeRes =R.mipmap.play_mode_loop_white;
                break;

            case SHUFFLE:
                modeRes =R.mipmap.play_mode_shuffle_white;
                break;
        }
        if(Build.VERSION.SDK_INT<23)
        {
            playModeImg.setImageDrawable(context.getResources().getDrawable(modeRes));
        }
        else
        {
            playModeImg.setImageDrawable(context.getDrawable(modeRes));
        }
    }


    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if(PlayerAction.POUT.ACT.equals(action))
            {
                int iVal = intent.getIntExtra(PlayerActionKey.Key,-1);
                PlayerActionKey.Val val = PlayerActionKey.Val.fromVal(iVal);
                switch(val)
                {
                    case PLAY:
                        playImg.setImageDrawable(getResources().getDrawable(R.mipmap.player_ing_white));
                        initPlayUI();
                    break;

                    case PAUSE:
                        playImg.setImageDrawable(getResources().getDrawable(R.mipmap.player_pause_white));
                    break;
                }
            }
        }
    };


    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener
    {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser)
        {
            this.progress = progress * playerService.getDuration()/ seekBar.getMax();
            positionTx.setText(StringUtils.mSecond2Min(this.progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
            positionTx.setTextSize(15);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            playerService.seekTo(progress);
            positionTx.setTextSize(14);
        }

    }



    TimerTask  updateProgressTask = new TimerTask() {

        @Override
        public void run()
        {
            handler.post(new Runnable() {
                @Override
                public void run()
                {
                    int buf = playerService.getBufProgress();
                    int progress = playerService.getProgress();
                    int duration = playerService.getDuration();
                    if (duration > 0)
                    {
                        long pos = playSeekBar.getMax() * progress / duration;
                        playSeekBar.setProgress((int) pos);
                        positionTx.setText(StringUtils.mSecond2Min(progress));
                        playSeekBar.setSecondaryProgress(buf);
                        durationTx.setText(StringUtils.mSecond2Min(duration));
                    }

                    if(progress>duration)
                    {
                        playerService.playNext();
                    }
                }
            });
        }
    };

}

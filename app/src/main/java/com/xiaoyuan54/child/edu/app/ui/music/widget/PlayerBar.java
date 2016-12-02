package com.xiaoyuan54.child.edu.app.ui.music.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.modules.music.PlayList;
import com.xiaoyuan54.child.edu.app.modules.music.action.PlayerAction;
import com.xiaoyuan54.child.edu.app.modules.music.action.PlayerActionKey;
import com.xiaoyuan54.child.edu.app.modules.music.interf.PlayListActionListener;
import com.xiaoyuan54.child.edu.app.modules.music.service.MusicPlayerService;
import com.xiaoyuan54.child.edu.app.ui.main.MainActivity;
import com.xiaoyuan54.child.edu.app.ui.music.fragment.MusicDetailFragment;

import org.kymjs.kjframe.Core;
import org.kymjs.kjframe.bitmap.BitmapCallBack;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by L.QING on 2016-11-07.
 */

public class PlayerBar extends LinearLayout implements PlayListActionListener
{
    @Bind(R.id.play_bar_cover)
    public ImageView coverImg;

    @Bind(R.id.play_bar_play)
    public ImageView playBtn;

    @Bind(R.id.play_bar_title)
    public TextView  titleTv;

    @Bind(R.id.play_bar_subtitle)
    public TextView  subTitleTv;

    private View rootView;

    private MainActivity context;

    private PlayList playList;

    private boolean is_need = true;

    public PlayerBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate(context);
    }

    public PlayerBar(Context context) {
        this(context,null);
    }

    public PlayerBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        onCreate(context);
    }

    private void onCreate(Context context)
    {
        this.context = (MainActivity)context;
        rootView  = LayoutInflater.from(context).inflate(R.layout.widget_player_bar,this,true);
        ButterKnife.bind(rootView);

        context.registerReceiver(receiver,new IntentFilter(PlayerAction.POUT.ACT));
        playList = PlayList.getPlayList();
        playList.registerActionListener(this);

    }


    public void destroy()
    {
        context.unregisterReceiver(receiver);
        playList.unregisterActionListener(this);
        playList.destroy();
    }


    @OnClick(value = {R.id.play_bar_play,R.id.play_bar_content_root,
                      R.id.play_bar_next,R.id.play_bar_list})
    public void playAction(View view)
    {
       MusicPlayerService playerService = context.getMusicPlayer();

       switch (view.getId())
       {
           case R.id.play_bar_play:

               if(playerService.isPlaying())
               {
                   playerService.pause();
               }
               else
               {
                   playerService.play();
               }
           break;

           case R.id.play_bar_next:
               playerService.playNext();
           break;

           case R.id.play_bar_list:
               PlayListDialog listDialog = new PlayListDialog(context);
               listDialog.show();
           break;

           case R.id.play_bar_content_root:
               context.attachFrag( new MusicDetailFragment());
           break;

       }

    }


    @Override
    public void listActionChange(PlayList.Action action)
    {
       switch (action)
       {
           case READY:
              this.setVisibility(VISIBLE);
               updateView();
           break;

           case CHANGE:
               if(playList.isPrepare())
               {
                   if(is_need&&View.GONE==this.getVisibility())
                   {
                     barShow();
                   }
               }
           break;

           case SONG_CHANGE:
               if(playList.isPrepare())
               {
                   if(is_need&&View.GONE==this.getVisibility())
                   {
                       barShow();
                   }
               }
               updateView();
           break;

           case EMPTY:
             this.setVisibility(GONE);
           break;
       }
    }

    public void setBarNeed(boolean isNeed)
    {
        is_need  = isNeed;
        if(is_need)
        {
            if(playList.isPrepare())
            {
              setVisibility(VISIBLE);
            }
        }
        else
        {
            setVisibility(GONE);
        }
    }

    public boolean getBarNeed()
    {
       return is_need ;
    }


    public void updateView()
    {
        Song song = playList.getPlayingSong();
        titleTv.setText(song.getTitle());
        subTitleTv.setText(song.getSinger());

        new Core.Builder().url(song.getCoverImg()).view(coverImg).loadBitmapRes(R.mipmap.load_img_default)
                .bitmapCallBack(new BitmapCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                super.onSuccess(bitmap);
                if (bitmap != null) {
                    coverImg.setImageBitmap(bitmap);
                }
            }
        }).doTask();
    }


    public void barShow()
    {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.in_from_bottom);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        this.startAnimation(animation);
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
                        playBtn.setImageDrawable(getResources().getDrawable(R.mipmap.play_ing));
                    break;

                    case PAUSE:
                        playBtn.setImageDrawable(getResources().getDrawable(R.mipmap.play_stop));
                    break;
                }
            }
        }
    };
}

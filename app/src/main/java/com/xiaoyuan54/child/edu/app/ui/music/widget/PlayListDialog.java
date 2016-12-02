package com.xiaoyuan54.child.edu.app.ui.music.widget;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.modules.music.PlayList;
import com.xiaoyuan54.child.edu.app.modules.music.PlayMode;
import com.xiaoyuan54.child.edu.app.modules.music.interf.PlayListActionListener;
import com.xiaoyuan54.child.edu.app.util.DialogHelp;
import com.xiaoyuan54.child.edu.app.util.StringUtils;

/**
 * 播放列表歌曲展示dialog
 */
public class PlayListDialog extends Dialog implements
        View.OnClickListener ,PlayListActionListener{

    private Context context;
    private String title;
    private String content;

    private double height = 0.6;

    private View root;

    public ListView listView;

    public TextView playModeTv;

    public TextView clearBtnTv;

    public ImageView playModeImg;

    private PlayList playList;

    private PlayListAdapter listAdapter;


    public PlayListDialog(Context context)
    {
        super(context, R.style.dialog_bottom);
        this.context = context;
        root= getLayoutInflater().inflate(R.layout.widget_play_list_dialog, null);
        listView = (ListView) root.findViewById(R.id.play_lt_dialog_lv);
        playModeTv = (TextView) root.findViewById(R.id.play_lt_dialog_mode_tv);
        playModeImg = (ImageView) root.findViewById(R.id.play_lt_dialog_mode_img);
        playModeImg.setOnClickListener(this);
        clearBtnTv = (TextView) root.findViewById(R.id.play_lt_dialog_clear);
        clearBtnTv.setOnClickListener(this);
        super.setContentView(root);
    }

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        getWindow().setGravity(Gravity.BOTTOM);

        WindowManager m = getWindow().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        Display d = m.getDefaultDisplay();
        d.getMetrics(metrics);
        WindowManager.LayoutParams p = getWindow().getAttributes();
       // p.width = d.getWidth();
       // p.height= (int)(d.getHeight()*height);
        p.width = metrics.widthPixels;
        p.height= (int)(metrics.heightPixels*height);
        getWindow().setAttributes(p);

        playList = PlayList.getPlayList();
        playList.registerActionListener(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        playList.unregisterActionListener(this);
    }

    private void initData()
    {
        listAdapter = new PlayListAdapter();
        listView.setAdapter(listAdapter);
        if(playList.getPlayIndex()>2)
        {
            listView.setSelection(playList.getPlayIndex()-2);
        }
    }

    @TargetApi(23)
    private void updateViewUI()
    {
        PlayMode mode = playList.getPlayMode();

        String modeText ="单曲循环";
        int    modeRes =R.mipmap.play_mode_single_gray;
        switch (mode)
        {
            case LOOP:
                modeRes =R.mipmap.play_mode_loop_gray;
                modeText = String.format("循环播放(%s)",playList.size());
            break;

            case SHUFFLE:
                modeRes =R.mipmap.play_mode_shuffle_gray;
                modeText = String.format("随机播放(%s)",playList.size());
            break;
        }

        if(Build.VERSION.SDK_INT<23){
            playModeImg.setImageDrawable(context.getResources().getDrawable(modeRes));
        }
        else{
            playModeImg.setImageDrawable(context.getDrawable(modeRes));
        }
        playModeTv.setText(modeText);
    }


    private void initListener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l)
            {
                //playing this song
                playList.setPlayingSong(index);
            }
        });
    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.play_lt_dialog_mode_img:
                playList.setPlayMode(PlayMode.switchNextMode(playList.getPlayMode()));
            break;

            case R.id.play_lt_dialog_clear:
                DialogHelp.getConfirmDialog(context, "确定清空列表吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        playList.clear();
                    }
                }).show();

            break;
        }
    }

    @Override
    public void listActionChange(PlayList.Action action)
    {
       switch (action)
       {
           case READY:
               initData();
               initListener();
               updateViewUI();
           break;

           case CHANGE:
               updateViewUI();
               listAdapter.notifyDataSetChanged();
           break;

           case SONG_CHANGE:
               updateViewUI();
               listAdapter.notifyDataSetChanged();
           break;

           case EMPTY:
               listAdapter.notifyDataSetChanged();
               root.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       dismiss();
                   }
               },500);

           break;
       }
    }


    public class PlayListAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return playList.size();
        }

        @Override
        public Song getItem(int i) {
            return playList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @TargetApi(23)
        @Override
        public View getView(final int position, View view, ViewGroup viewGroup)
        {
            Song song = playList.get(position);
            ViewHolder holder = new ViewHolder(R.layout.item_play_list,viewGroup);
            View root = holder.getRootView();
            if(0==position)
            {
              holder.upImg.setVisibility(View.GONE);
            }
            holder.titleTv.setText(song.getTitle());
            String singer = null==song.getSinger()?"未知歌手":song.getSinger();
            holder.singerTv.setText(" - "+singer);

            int playIndex = playList.getPlayIndex();
            if(position == playIndex)
            {
                if(Build.VERSION.SDK_INT<23)
                {
                   holder.titleTv.setTextColor(context.getResources().getColor(R.color.user_info_bg_color));
                   holder.singerTv.setTextColor(context.getResources().getColor(R.color.user_info_bg_color));
                }
                else
                {
                    holder.titleTv.setTextColor(context.getColor(R.color.user_info_bg_color));
                    holder.singerTv.setTextColor(context.getColor(R.color.user_info_bg_color));
                }
            }

            holder.delImg.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                   playList.remove(position);
                }
            });

            holder.upImg.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    playList.resort(position);
                }
            });

            return root;
        }
    }


    public  class ViewHolder
    {
        public View root;

        public TextView titleTv;

        public TextView singerTv;

        public ImageView delImg;

        public ImageView upImg;



        public ViewHolder(int layout,ViewGroup parent)
        {
            root = getLayoutInflater().inflate(layout,parent,false);
            titleTv = (TextView) root.findViewById(R.id.item_play_list_title);
            singerTv = (TextView) root.findViewById(R.id.item_play_list_singer);
            upImg = (ImageView) root.findViewById(R.id.item_play_list_up);
            delImg = (ImageView) root.findViewById(R.id.item_play_list_del);
        }

        public View getRootView()
        {
            return root;
        }
    }


}

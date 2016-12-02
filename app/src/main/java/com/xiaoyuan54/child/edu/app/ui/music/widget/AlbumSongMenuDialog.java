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
import android.view.WindowManager;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.util.TDevice;

/**
 * 专辑Item菜单dialog
 */
public class AlbumSongMenuDialog extends Dialog implements
        View.OnClickListener{

    private Context context;
    private View root;
    private Song song;


    public AlbumSongMenuDialog(Context context,Song song)
    {
        super(context, R.style.dialog_bottom);
        this.context = context;
        this.song = song;
        root= getLayoutInflater().inflate(R.layout.widget_album_song_menu_dialog, null);
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
        p.width = metrics.widthPixels;
        p.height= ((int)TDevice.dp2px(280))+1;
        getWindow().setAttributes(p);

        initData();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    private void initData()
    {
        TextView titleTv = (TextView) root.findViewById(R.id.song_menu_dialog_title);
        titleTv.setText(song.getTitle());
    }


    private void initListener()
    {
    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.play_lt_dialog_mode_img:
            break;

            case R.id.play_lt_dialog_clear:

            break;
        }
    }

}

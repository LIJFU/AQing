package com.xiaoyuan54.child.edu.app.ui.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hp.hpl.sparta.Text;
import com.xiaoyuan54.child.edu.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L.QING on 2016-11-10.
 */

public class PlayerLyricListAdapter  extends BaseAdapter
{
    private Context context;

    private List<String> lyric;

    public PlayerLyricListAdapter(Context context)
    {
       this.context = context;

        lyric = new ArrayList();

        for(int i = 0;i<100;i++)
        {
            lyric.add("don't push me so hard...");
        }
    }

    @Override
    public int getCount()
    {
        return lyric.size();
    }

    @Override
    public Object getItem(int i)
    {
        return lyric.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder holder = new ViewHolder();
        View root = LayoutInflater.from(context).inflate(R.layout.fragment_player_lyric_lv,viewGroup,false);
        holder.lyricTv = (TextView) root.findViewById(R.id.player_lyric_tv);
        root.setTag(holder);
        holder.lyricTv.setText(lyric.get(i));
        return root;
    }


    public static class ViewHolder
    {
        TextView lyricTv;
    }

}

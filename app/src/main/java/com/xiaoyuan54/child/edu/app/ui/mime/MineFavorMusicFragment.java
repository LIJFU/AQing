package com.xiaoyuan54.child.edu.app.ui.mime;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.ui.base.fragment.BaseFragment;
import com.xiaoyuan54.child.edu.app.ui.mime.adapter.MineMusicListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by m on 2016-11-09.
 */
public class MineFavorMusicFragment extends BaseFragment
{

   @Bind(R.id.listView) ListView listView;

    private MineMusicListViewAdapter adapter;

    public static MineFavorMusicFragment newInstance()
    {
        Bundle args = new Bundle();
        MineFavorMusicFragment fragment = new MineFavorMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_mine_favor_music;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

    }

    @Override
    protected void initData()
    {
        super.initData();

        List<Song> songs  = new ArrayList();

        for(int i= 0;i< 20;i++)
        {
           Song song = new Song();
            song.setTitle("白龙马");
            song.setSinger("小蓓蕾");
            song.setCoverImg("http://business.cdn.qianqian.com/qianqian/pic/bos_client_f38f43a0a480b772fd6299aa0cd43ac1.jpg");
            songs.add(song);
        }

        adapter = new MineMusicListViewAdapter(getContext());
        listView.setAdapter(adapter);
        adapter.addItems(songs);
    }
}

package com.xiaoyuan54.child.edu.app.ui.mime;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.music.Song;
import com.xiaoyuan54.child.edu.app.ui.base.fragment.BaseFragment;
import com.xiaoyuan54.child.edu.app.modules.music.interf.MainPresenter;
import com.xiaoyuan54.child.edu.app.ui.mime.adapter.FableListAdapter;
import com.xiaoyuan54.child.edu.app.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by m on 2016-11-07.
 */

public class MineMainFragment extends BaseFragment implements View.OnClickListener{


    @Bind(R.id.fable_list)
    GridView fableList;

    @Bind(R.id.lv_mine)
    LinearLayout mMineView;

    @Bind(R.id.mine_root_scroll)
    ScrollView scrollView;

    private List<Song> musicData = new ArrayList<>();
    
    private List<Song> fableData = new ArrayList<>();

    private FableListAdapter fableItemAdapter;

    private MainPresenter sliderPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initWidget(View root)
    {
        super.initWidget(root);

        fableList.setFocusable(false);

        loadFableData();

        mMineView.setOnClickListener(this);

        fableItemAdapter = new FableListAdapter(getContext(),fableData);
        fableList.setNumColumns(2);
        fableList.setAdapter(fableItemAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lv_mine:
                UIHelper.showMineCenter(getContext());
            break;
            default:
                break;
        }
    }


    @Override
    protected void initData()
    {
        super.initData();
        sliderPresenter =(MainPresenter)getContext();
    }

    private void loadFableData() {
        Song music = new Song();
        music.setTitle("我选一");
        musicData.add(music);

        Song music1 = new Song();
        music.setTitle("我选一");
        musicData.add(music);

        Song music2 = new Song();
        music.setTitle("我选一");

        Song music3 = new Song();
        music.setTitle("我选一");

        fableData.add(music3);
        fableData.add(music2);
        fableData.add(music1);
        fableData.add(music);
    }


    @OnClick(value = {R.id.main_my_music_root,R.id.main_my_fable_root,R.id.main_my_collection_root})
    public void clickAction(View view)
    {
        switch (view.getId()) {
            case R.id.main_my_music_root:
                sliderPresenter.attachFrag(new MineLocalMusicFragment());
                break;
            case R.id.main_my_fable_root:
                sliderPresenter.attachFrag(new MineLocalStoryFragment());
                break;
            case R.id.main_my_collection_root:
                sliderPresenter.attachFrag(new MineFavorMainFragment());
                break;
            default:
                break;
        }
    }
    public static MineMainFragment newInstance() {
        Bundle args = new Bundle();
        MineMainFragment fragment = new MineMainFragment();
        fragment.setArguments(args);
        return fragment;
    }



}

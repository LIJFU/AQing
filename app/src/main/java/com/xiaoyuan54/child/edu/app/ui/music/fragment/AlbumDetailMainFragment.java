package com.xiaoyuan54.child.edu.app.ui.music.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.music.Album;
import com.xiaoyuan54.child.edu.app.ui.base.fragment.BaseFragment;

import org.kymjs.kjframe.Core;
import org.kymjs.kjframe.bitmap.BitmapCallBack;

import butterknife.Bind;

public class AlbumDetailMainFragment extends BaseFragment
{

    @Bind(R.id.album_sl_subtitle)
    public TextView subTitleTv;

    @Bind(R.id.album_sl_title)
    public TextView titleTv;

    @Bind(R.id.album_sl_singer)
    public TextView singerTv;

    @Bind(R.id.album_cover_img)
    public ImageView coverImg;

    @Bind(R.id.album_logo_cover)
    public ImageView logCoverImg;

    @Bind(R.id.album_logo_title)
    public TextView logTitleTv;

    @Bind(R.id.album_sl_pt)
    public TextView playTimeTv;

    @Bind(R.id.album_sl_ft)
    public TextView favorTimeTv;

    @Bind(R.id.album_layout_appbar)
    public AppBarLayout appBarLayout;

    @Bind(R.id.album_sl_toolbar)
    public Toolbar toolbar;

    @Bind(R.id.album_sl_menu_root)
    public View menuLayout;

    @Bind(R.id.album_sl_divider)
    public View lineDivider;

    @Bind(R.id.album_sl_viewpage)
    public ViewPager viewPager;

    @Bind(R.id.album_sl_tab)
    public TabLayout tabLayout;

    private TabLayoutOffsetChangeListener mOffsetChangerListener;

    private Album album;

    private FragmentManager fragmentManager;

    private Fragment[] viewPageFragments;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_album_detail;
    }


    public void setAlbumData(Album album)
    {
        this.album = album;
    }


    @Override
    protected void initData()
    {
        appBarLayout.addOnOffsetChangedListener(mOffsetChangerListener = new TabLayoutOffsetChangeListener());
        tabLayout.setupWithViewPager(viewPager);

        fragmentManager = getFragmentManager();
        viewPageFragments = new Fragment[2];

        Bundle bundle  = new Bundle();
        bundle.putSerializable("album",album);

     //   viewPageFragments[0] = Fragment.instantiate(getContext(),AlbumSongListFragmentRecycle.class.getName(),bundle);
        AlbumSongListFragmentRecycle songListFragment = new AlbumSongListFragmentRecycle();
        songListFragment.setBundle(bundle);

        AlbumSongListFragmentRecycle albumFragment = new AlbumSongListFragmentRecycle();
        albumFragment.setBundle(bundle);

        viewPageFragments[0] = songListFragment;
        viewPageFragments[1] = albumFragment;

        SongListViewPageAdapter pagerAdapter = new SongListViewPageAdapter(fragmentManager);
        viewPager.setAdapter(pagerAdapter);


        dataChange();
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onDestroy()
    {
        try
        {
            FragmentTransaction ft  = fragmentManager.beginTransaction();
            ft.remove(viewPageFragments[0]);
            ft.remove(viewPageFragments[1]);
            ft.commit();
        }
        catch (Exception e)
        {
           Log.e("fragment"," album detail fragment destroy error------------>>"+e.getMessage());
        }
        finally
        {
            super.onDestroy();
            Log.e("fragment"," album detail fragment destroy--------------------->>");
        }
    }

    private void dataChange()
    {
        if(null==album)return;
        titleTv.setText(album.getTitle());
        logTitleTv.setText(album.getTitle());
        subTitleTv.setText(album.getSubTitle());
        singerTv.setText(album.getSinger());
        playTimeTv.setText("播放 "+album.getPlayTimes());
        favorTimeTv.setText("收藏 "+album.getFavorTimes());

        new Core.Builder().url(album.getCoverImg()).view(coverImg).loadBitmapRes(R.mipmap.load_img_default)
            .bitmapCallBack(new BitmapCallBack() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    super.onSuccess(bitmap);
                    if (bitmap != null)
                    {
                        coverImg.setImageBitmap(bitmap);
                        logCoverImg.setImageBitmap(bitmap);
                    }
                }
            }).doTask();

        mOffsetChangerListener.resetRange();
    }

    private class TabLayoutOffsetChangeListener implements AppBarLayout.OnOffsetChangedListener {
        boolean isShow = false;
        int mScrollRange = -1;

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (mScrollRange == -1) {
                mScrollRange = appBarLayout.getTotalScrollRange();
            }
            if (mScrollRange + verticalOffset == 0) {
                logTitleTv.setVisibility(View.VISIBLE);
                logCoverImg.setVisibility(View.VISIBLE);
                lineDivider.setVisibility(View.GONE);
                isShow = true;
            } else if (isShow) {
                logTitleTv.setVisibility(View.GONE);
                logCoverImg.setVisibility(View.GONE);
                lineDivider.setVisibility(View.VISIBLE);
                isShow = false;
            }
            menuLayout.getBackground().setAlpha(
                    Math.round(255 - Math.abs(verticalOffset) / (float) mScrollRange * 255));
        }

        public void resetRange(){
            mScrollRange = -1;
        }
    }


    public class SongListViewPageAdapter extends FragmentPagerAdapter
    {

        public SongListViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return viewPageFragments[position];
        }

        @Override
        public int getCount() {
            return viewPageFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            String[] titles = new String[]{"歌曲","详情"};
            return titles[position];
        }
    }


}

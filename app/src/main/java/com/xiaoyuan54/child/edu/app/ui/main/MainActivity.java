package com.xiaoyuan54.child.edu.app.ui.main;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.version.Version;
import com.xiaoyuan54.child.edu.app.improve.base.activities.BaseActivity;
import com.xiaoyuan54.child.edu.app.ui.base.fragment.BaseFragment;
import com.xiaoyuan54.child.edu.app.modules.music.interf.MainPresenter;
import com.xiaoyuan54.child.edu.app.modules.music.service.MusicPlayerService;
import com.xiaoyuan54.child.edu.app.ui.music.widget.PlayerBar;
import com.xiaoyuan54.child.edu.app.ui.mime.MineMainFragment;
import com.xiaoyuan54.child.edu.app.ui.music.MusicMainTabFragment;
import com.xiaoyuan54.child.edu.app.ui.story.StoryMainTabFragment;
import com.xiaoyuan54.child.edu.app.ui.update.fragment.UpdateFragment;
import com.xiaoyuan54.child.edu.app.ui.update.service.UpdateService;

import java.util.Stack;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MainPresenter
{

    @Bind(R.id.main_tab_nav)
    public TabLayout tabLayout;

    @Bind(R.id.main_base_viewPager)
    public ViewPager viewPager;

    @Bind(R.id.main_player_bar)
    public PlayerBar playerBar;

    @Bind(R.id.main_slider_frame)
    public View sliderFragment;

    private Stack<BaseFragment> fragmentStack;
    private MusicPlayerService playerService;

    @Override
    protected int getContentView()
    {
        return R.layout.activity_main_new_ui;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        Intent intent = new Intent(this, MusicPlayerService.class);
        this.bindService(intent,playerConnect, Context.BIND_AUTO_CREATE);
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter();
        filter.addAction(UpdateService.update_app_action);
        registerReceiver(mainReceiver,filter);

        UpdateService.start(this);
    }


    @Override
    protected void initWidget()
    {
        super.initWidget();

        fragmentStack = new Stack<>();

        FragmentManager manager = getSupportFragmentManager();
        String[] titles = getResources().getStringArray(R.array.main_menu_arrays);
        PagerInfo[] infoList = new PagerInfo[3];


		infoList[0] = new PagerInfo(titles[0], MineMainFragment.class, null);
        infoList[1] = new PagerInfo(titles[1], MusicMainTabFragment.class, null);
        infoList[2] = new PagerInfo(titles[2], StoryMainTabFragment.class, null);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new BaseViewPagerAdapter(manager,infoList));

    }


    @Override
    public void onBackPressed()
    {
        if(View.VISIBLE==sliderFragment.getVisibility())
        {
            BaseFragment fragment = fragmentStack.peek();
            if(fragment.getCanBackPress())
            {
              detachFrag();
            }
            return;
        }
        this.moveTaskToBack(true);
    }


    @Override
    protected void onDestroy()
    {
        playerBar.destroy();
        unregisterReceiver(mainReceiver);
        unbindService(playerConnect);
        super.onDestroy();
    }


    @Override
    public void attachFrag(BaseFragment fragment)
    {
        fragmentStack.push(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_slider_frame,fragment,fragment.getTag());
        transaction.attach(fragment);
        transaction.commitAllowingStateLoss();
        sliderFragment.setVisibility(View.VISIBLE);
    }


    @Override
    public void detachFrag()
    {
        BaseFragment fragment = fragmentStack.pop();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.detach(fragment);
        transaction.commitAllowingStateLoss();
        if(fragmentStack.isEmpty())
        {
            sliderFragment.setVisibility(View.GONE);
        }
    }

    @Override
    public PlayerBar getPlayerBar()
    {
        return playerBar;
    }

    @Override
    public MusicPlayerService getMusicPlayer()
    {
        return playerService;
    }


    public BroadcastReceiver mainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if(UpdateService.update_app_action.equals(action))
            {
                final Version version = (Version)intent.getSerializableExtra("version");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        UpdateFragment update = new UpdateFragment();
                        update.setVersion(version);
                        attachFrag(update);
                    }
                });
            }
        }
    };


    public ServiceConnection playerConnect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            playerService = ((MusicPlayerService.LocalBind)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            playerService = null;
        }
    };


    public class BaseViewPagerAdapter extends FragmentPagerAdapter
    {
        private PagerInfo[] mInfoList;
        private Fragment mCurFragment;

        public BaseViewPagerAdapter(FragmentManager fm, PagerInfo[] infoList) {
            super(fm);
            mInfoList = infoList;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (object instanceof Fragment) {
                mCurFragment = (Fragment) object;
            }
        }


        @Override
        public Fragment getItem(int position)
        {
            PagerInfo info = mInfoList[position];
            return Fragment.instantiate(MainActivity.this, info.clx.getName(), info.args);
        }

        @Override
        public int getCount() {
            return mInfoList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mInfoList[position].title;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }


    public static class PagerInfo {
        private String title;
        private Class<?> clx;
        private Bundle args;

        public PagerInfo(String title, Class<?> clx, Bundle args) {
            this.title = title;
            this.clx = clx;
            this.args = args;
        }
    }


}

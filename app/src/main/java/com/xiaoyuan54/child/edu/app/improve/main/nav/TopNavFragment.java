package com.xiaoyuan54.child.edu.app.improve.main.nav;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.ui.base.fragment.BaseFragment;
import com.xiaoyuan54.child.edu.app.improve.notice.NoticeBean;
import com.xiaoyuan54.child.edu.app.improve.notice.NoticeManager;
import com.xiaoyuan54.child.edu.app.improve.user.fragments.NewUserInfoFragment;
import com.xiaoyuan54.child.edu.app.ui.fable.FableViewPagerFragmentOld;
import com.xiaoyuan54.child.edu.app.ui.music.MusicMainTabFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by m on 2016-10-31.
 */

public class TopNavFragment  extends BaseFragment implements View.OnClickListener, NoticeManager.NoticeNotify {

    @Bind(R.id.nav_item_music)
    TopNavigationButton  mNavMusic;

    @Bind(R.id.nav_item_fables)
    TopNavigationButton  mNavFable;

    @Bind(R.id.nav_item_me)
    TopNavigationButton  mNavMe;

    private Context mContext;
    private int mContainerId;
    private FragmentManager mFragmentManager;
    private TopNavigationButton mCurrentNavButton;
    private OnNavigationReselectListener mOnNavigationReselectListener;


    public interface OnNavigationReselectListener {
        void onReselect(TopNavigationButton navigationButton);
    }


    public void setup(Context context, FragmentManager fragmentManager, int contentId, TopNavFragment.OnNavigationReselectListener listener) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mContainerId = contentId;
        mOnNavigationReselectListener = listener;

        // do clear
        clearOldFragment();
        // do select first
        doSelect(mNavMusic);
    }

    private void clearOldFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (transaction == null || fragments == null || fragments.size() == 0)
            return;
        boolean doCommit = false;
        for (Fragment fragment : fragments) {
            if (fragment != this) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit)
            transaction.commitNow();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        mNavMusic.init(R.string.main_tab_name_music,MusicMainTabFragment.class);

        mNavFable.init(R.string.main_tab_name_story,FableViewPagerFragmentOld.class);

        mNavMe.init(R.string.main_tab_name_my,NewUserInfoFragment.class);
    }

    @OnClick({R.id.nav_item_me, R.id.nav_item_fables,
            R.id.nav_item_music})
    @Override
    public void onClick(View v) {
        if (v instanceof TopNavigationButton) {
            TopNavigationButton nav = (TopNavigationButton) v;
            doSelect(nav);
        }
    }

    public void select(int index) {
        if (mNavMe != null)
            doSelect(mNavMe);
    }


    private void doTabChanged(TopNavigationButton oldNavButton, TopNavigationButton newNavButton) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                ft.detach(oldNavButton.getFragment());
            }
        }
        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                Fragment fragment = Fragment.instantiate(mContext,
                        newNavButton.getClx().getName(), null);
                ft.add(mContainerId, fragment, newNavButton.getTag());
                newNavButton.setFragment(fragment);
            } else {
                ft.attach(newNavButton.getFragment());
            }
        }
        ft.commit();
    }

    private void doSelect(TopNavigationButton newNavButton) {
        TopNavigationButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChanged(oldNavButton, newNavButton);
        mCurrentNavButton = newNavButton;

    }

    private void onReselect(TopNavigationButton navigationButton) {
        TopNavFragment.OnNavigationReselectListener listener = mOnNavigationReselectListener;
        if (listener != null) {
            listener.onReselect(navigationButton);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_top_nav;
    }

    @Override
    public void onNoticeArrived(NoticeBean bean) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeManager.unBindNotify(this);
    }

    @Override
    protected void initData() {
        super.initData();
        NoticeManager.bindNotify(this);
    }
}

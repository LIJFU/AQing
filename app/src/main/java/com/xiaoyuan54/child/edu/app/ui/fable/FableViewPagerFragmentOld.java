package com.xiaoyuan54.child.edu.app.ui.fable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseGeneralListFragment;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseViewPagerFragment;
import com.xiaoyuan54.child.edu.app.interf.OnTabReselectListener;
import com.xiaoyuan54.child.edu.app.ui.fable.fragment.FableCommonFragment;
/**
 * Created by fei
 * on 2016/9/5.
 * <p>
 * Changed qiujuer
 * on 2016/9/5.
 */
public class FableViewPagerFragmentOld extends BaseViewPagerFragment implements OnTabReselectListener {

    /**
     * @param requestCategory 请求类型，1为普通动弹，2用户动弹
     * @param tweetType       1最新，2最热
     * @return Bundle
     */
    private Bundle getBundle(int requestCategory, int tweetType) {
        Bundle bundle = new Bundle();
        bundle.putInt("requestCategory", requestCategory);
        bundle.putInt("tweetType", tweetType);
        return bundle;
    }

    @Override
    public void onTabReselect() {
        if (mBaseViewPager != null) {
            BaseViewPagerAdapter pagerAdapter = (BaseViewPagerAdapter) mBaseViewPager.getAdapter();
            Fragment fragment = pagerAdapter.getCurFragment();
            if (fragment != null && fragment instanceof BaseGeneralListFragment) {
                ((BaseGeneralListFragment) fragment).onTabReselect();
            }
        }
    }

    @Override
    protected PagerInfo[] getPagers() {

        String[] titles = getResources().getStringArray(R.array.fable_viewpage_arrays);
        PagerInfo[] infoList = new PagerInfo[3];

        infoList[0] = new PagerInfo(titles[0], FableCommonFragment.class, null);
        infoList[1] = new PagerInfo(titles[1], FableCommonFragment.class, null);
        infoList[2] = new PagerInfo(titles[2], FableCommonFragment.class, null);

        return infoList;
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_story;
    }
}

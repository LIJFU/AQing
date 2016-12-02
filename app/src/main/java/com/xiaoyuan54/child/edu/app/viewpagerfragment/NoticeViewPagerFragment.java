package com.xiaoyuan54.child.edu.app.viewpagerfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.ViewPageFragmentAdapter;
import com.xiaoyuan54.child.edu.app.base.BaseListFragment;
import com.xiaoyuan54.child.edu.app.base.BaseViewPagerFragment;
import com.xiaoyuan54.child.edu.app.bean.ActiveList;
import com.xiaoyuan54.child.edu.app.bean.Constants;
import com.xiaoyuan54.child.edu.app.bean.FriendsList;
import com.xiaoyuan54.child.edu.app.fragment.ActiveFragment;
import com.xiaoyuan54.child.edu.app.fragment.FriendsFragment;
import com.xiaoyuan54.child.edu.app.fragment.MessageFragment;
import com.xiaoyuan54.child.edu.app.fragment.TweetsLikesFragment;
import com.xiaoyuan54.child.edu.app.widget.BadgeView;
import com.xiaoyuan54.child.edu.app.widget.PagerSlidingTabStrip.OnPagerChangeLis;

/**
 * 消息中心页面
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @author kymjs (https://github.com/kymjs)
 * @created 2014年9月25日 下午2:21:52
 * 
 */
public class NoticeViewPagerFragment extends BaseViewPagerFragment {

    public BadgeView mBvAtMe, mBvComment, mBvMsg, mBvFans, mBvLike;
    public static int sCurrentPage = 0;
    public static int[] sShowCount = new int[] { 0, 0, 0, 0, 0}; // 当前界面显示了多少次
    private BroadcastReceiver mNoticeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setNoticeTip();
//            changePagers();
        }
    };

    /**
     * 界面每次显示，去重置tip的显示
     */
    @Override
    public void onResume() {
        super.onResume();
        setNoticeTip();
//        changePagers();
        mViewPager.setOffscreenPageLimit(2);
    }

    /**
     * 设置tip
     */
    private void setNoticeTip()
    {

    }

    /**
     * 判断指定控件是否应该显示tip红点
     * 
     * @author kymjs
     */
    private void changeTip(BadgeView view, int count) {
        if (count > 0) {
            view.setText(count + "");
            view.show();
        } else {
            view.hide();
        }
    }

    /**
     * 当前tip是否在显示
     * 
     * @param which
     *            哪个界面的tip
     * @return
     */
    private boolean tipIsShow(int which) {
        switch (which) {
        case 0:
            return mBvAtMe.isShown();
        case 1:
            return mBvComment.isShown();
        case 2:
            return mBvMsg.isShown();
        case 3:
            return mBvFans.isShown();
        case 4:
            return mBvLike.isShown();
        default:
            return false;
        }
    }

    /**
     * 首次进入，切换到有tip的page
     */
    private void changePagers()
    {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 注册接收者接受tip广播
        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_NOTICE);
        getActivity().registerReceiver(mNoticeReceiver, filter);

        mBvAtMe = new BadgeView(getActivity(), mTabStrip.getBadgeView(0));
        mBvAtMe.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        mBvAtMe.setBadgePosition(BadgeView.POSITION_CENTER);
        mBvAtMe.setGravity(Gravity.CENTER);
        mBvAtMe.setBackgroundResource(R.mipmap.notification_bg);

        mBvComment = new BadgeView(getActivity(), mTabStrip.getBadgeView(1));
        mBvComment.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        mBvComment.setBadgePosition(BadgeView.POSITION_CENTER);
        mBvComment.setGravity(Gravity.CENTER);
        mBvComment.setBackgroundResource(R.mipmap.notification_bg);

        mBvMsg = new BadgeView(getActivity(), mTabStrip.getBadgeView(2));
        mBvMsg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        mBvMsg.setBadgePosition(BadgeView.POSITION_CENTER);
        mBvMsg.setGravity(Gravity.CENTER);
        mBvMsg.setBackgroundResource(R.mipmap.notification_bg);

        mBvFans = new BadgeView(getActivity(), mTabStrip.getBadgeView(3));
        mBvFans.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        mBvFans.setBadgePosition(BadgeView.POSITION_CENTER);
        mBvFans.setGravity(Gravity.CENTER);
        mBvFans.setBackgroundResource(R.mipmap.notification_bg);
        
        mBvLike = new BadgeView(getActivity(), mTabStrip.getBadgeView(4));
        mBvLike.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        mBvLike.setBadgePosition(BadgeView.POSITION_CENTER);
        mBvLike.setGravity(Gravity.CENTER);
        mBvLike.setBackgroundResource(R.mipmap.notification_bg);

        mTabStrip.getBadgeView(0).setVisibility(View.GONE);
        mTabStrip.getBadgeView(1).setVisibility(View.VISIBLE);
        mTabStrip.getBadgeView(2).setVisibility(View.VISIBLE);
        mTabStrip.getBadgeView(3).setVisibility(View.VISIBLE);
        mTabStrip.getBadgeView(4).setVisibility(View.VISIBLE);
        initData();
        initView(view);
    }

    @Override
    protected void setScreenPageLimit() {
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mNoticeReceiver);
        mNoticeReceiver = null;
    }

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = getResources().getStringArray(
                R.array.mymes_viewpage_arrays);
        adapter.addTab(title[0], "active_me", ActiveFragment.class,
                getBundle(ActiveList.CATALOG_ATME));
        adapter.addTab(title[1], "active_comment", ActiveFragment.class,
                getBundle(ActiveList.CATALOG_COMMENT));
        adapter.addTab(title[2], "active_mes", MessageFragment.class, null);
        Bundle bundle = getBundle(FriendsList.TYPE_FANS);
        bundle.putInt(FriendsFragment.BUNDLE_KEY_UID, AppContext.getInstance()
                .getLoginUid());
        adapter.addTab(title[3], "active_fans", FriendsFragment.class, bundle);
        adapter.addTab(title[4], "my_tweet", TweetsLikesFragment.class, null);
    }

    private Bundle getBundle(int catalog) {
        Bundle bundle = new Bundle();
        bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, catalog);
        return bundle;
    }

    @Override
    public void onClick(View v) {}

    @Override
    public void initView(View view) {
        changePagers();
        mViewPager.setOffscreenPageLimit(3);
        mTabStrip.setOnPagerChange(new OnPagerChangeLis() {
            @Override
            public void onChanged(int page) {
                refreshPage(page);
                sShowCount[page]++;
                sCurrentPage = page;
            }
        });
    }

    private void refreshPage(int index) {
        if (tipIsShow(index)) {
            try {
                ((BaseListFragment) getChildFragmentManager().getFragments()
                        .get(index)).onRefresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initData() {}
}

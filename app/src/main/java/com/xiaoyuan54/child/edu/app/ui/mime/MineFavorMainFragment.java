package com.xiaoyuan54.child.edu.app.ui.mime;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.ViewPageFragmentAdapter;
import com.xiaoyuan54.child.edu.app.base.BaseListFragment;
import com.xiaoyuan54.child.edu.app.bean.Post;
import com.xiaoyuan54.child.edu.app.ui.base.fragment.BaseFragment;
import com.xiaoyuan54.child.edu.app.ui.empty.EmptyLayout;
import com.xiaoyuan54.child.edu.app.ui.music.fragment.SongCommonFragment;
import com.xiaoyuan54.child.edu.app.widget.PagerSlidingTabStrip;

import butterknife.Bind;

/**
 *首页儿歌主界面
 */
public class MineFavorMainFragment extends BaseFragment {

    private static final String TAG = "CollectionMainTabFragment";

    @Bind(R.id.pager_tabstrip)
    PagerSlidingTabStrip mTabStrip;

    @Bind(R.id.pager)
    ViewPager mViewPager;

    ViewPageFragmentAdapter mTabsAdapter;

    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;



    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    protected void initData() {
        super.initData();
        mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(), mTabStrip, mViewPager);
        setScreenPageLimit();
        onSetupTabAdapter(mTabsAdapter);
    }

    protected void setScreenPageLimit() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection_tab;
    }

    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = getResources().getStringArray(R.array.mine_collection_arrays);
        adapter.addTab(title[0], "fable", SongCommonFragment.class, getBundle(Post.CATALOG_ASK));
        adapter.addTab(title[1], "songs", MineFavorMusicFragment.class, getBundle(Post.CATALOG_ASK));
    }

    private Bundle getBundle(int catalog) {
        Bundle bundle = new Bundle();
        bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, catalog);
        return bundle;
    }
}

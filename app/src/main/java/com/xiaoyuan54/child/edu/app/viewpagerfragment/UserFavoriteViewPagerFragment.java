package com.xiaoyuan54.child.edu.app.viewpagerfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.ViewPageFragmentAdapter;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.base.BaseViewPagerFragment;
import com.xiaoyuan54.child.edu.app.improve.user.fragments.NewUserFavoriteFragment;

/**
 * 用户收藏页
 */
public class UserFavoriteViewPagerFragment extends BaseViewPagerFragment {

    public static UserFavoriteViewPagerFragment newInstance() {
        return new UserFavoriteViewPagerFragment();
    }

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {

        FrameLayout generalActionBar = (FrameLayout) mRoot.findViewById(R.id.general_actionbar);
        generalActionBar.setVisibility(View.GONE);

        String[] title = getResources().getStringArray(R.array.userfavorite);
        adapter.addTab(title[0], "favorite_all", NewUserFavoriteFragment.class, getBundle(HttpClientApi.CATALOG_ALL));
        adapter.addTab(title[1], "favorite_software", NewUserFavoriteFragment.class, getBundle(HttpClientApi.CATALOG_SOFTWARE));
        adapter.addTab(title[2], "favorite_question", NewUserFavoriteFragment.class, getBundle(HttpClientApi.CATALOG_QUESTION));
        adapter.addTab(title[3], "favorite_blogs", NewUserFavoriteFragment.class, getBundle(HttpClientApi.CATALOG_BLOG));
        adapter.addTab(title[4], "favorite_translation", NewUserFavoriteFragment.class, getBundle(HttpClientApi.CATALOG_TRANSALITON));
        //adapter.addTab(title[5], "favotite_event", NewUserFavoriteFragment.class, getBundle(OSChinaApi.CATALOG_EVENT));
        adapter.addTab(title[6], "favorite_news", NewUserFavoriteFragment.class, getBundle(HttpClientApi.CATALOG_NEWS));

    }

    private Bundle getBundle(int favoriteType) {
        Bundle bundle = new Bundle();
        bundle.putInt(NewUserFavoriteFragment.CATALOG_TYPE, favoriteType);
        return bundle;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

}

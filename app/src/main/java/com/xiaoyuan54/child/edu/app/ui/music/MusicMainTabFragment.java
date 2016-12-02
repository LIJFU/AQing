package com.xiaoyuan54.child.edu.app.ui.music;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.ViewPageFragmentAdapter;
import com.xiaoyuan54.child.edu.app.base.BaseListFragment;
import com.xiaoyuan54.child.edu.app.base.BaseViewPagerFragment;
import com.xiaoyuan54.child.edu.app.bean.Post;
import com.xiaoyuan54.child.edu.app.ui.music.fragment.MusicCommonFragment;
import com.xiaoyuan54.child.edu.app.ui.music.fragment.MusicCommonFragmentOld;
import com.xiaoyuan54.child.edu.app.ui.music.fragment.SongCommonFragment;

/**
 *首页儿歌主界面
 */
public class MusicMainTabFragment extends BaseViewPagerFragment {

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {

        FrameLayout generalActionBar = (FrameLayout) mRoot.findViewById(R.id.general_actionbar);
        generalActionBar.setVisibility(View.GONE);

        String[] title = getResources().getStringArray(R.array.music_viewpage_arrays);
        adapter.addTab(title[0], "hot", SongCommonFragment.class, getBundle(0));
        adapter.addTab(title[1], "english", SongCommonFragment.class, getBundle(1));
        adapter.addTab(title[2], "number", SongCommonFragment.class, getBundle(2));
    }

    private Bundle getBundle(int catalog) {
        Bundle bundle = new Bundle();
        bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, catalog);
        return bundle;
    }
}

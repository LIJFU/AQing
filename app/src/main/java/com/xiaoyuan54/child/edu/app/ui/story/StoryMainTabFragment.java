package com.xiaoyuan54.child.edu.app.ui.story;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.ViewPageFragmentAdapter;
import com.xiaoyuan54.child.edu.app.base.BaseListFragment;
import com.xiaoyuan54.child.edu.app.base.BaseViewPagerFragment;
import com.xiaoyuan54.child.edu.app.ui.story.fragment.StoryCommonFragment;

/**
 *首页故事主界面
 */
public class StoryMainTabFragment extends BaseViewPagerFragment {

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {

        FrameLayout generalActionBar = (FrameLayout) mRoot.findViewById(R.id.general_actionbar);
        generalActionBar.setVisibility(View.GONE);

        String[] title = getResources().getStringArray(R.array.story_viewpage_arrays);
        adapter.addTab(title[0], "story_recommand", StoryCommonFragment.class, getBundle(0));
        adapter.addTab(title[1], "story_fable", StoryCommonFragment.class, getBundle(1));
        adapter.addTab(title[2], "story_english", StoryCommonFragment.class, getBundle(2));
    }

    private Bundle getBundle(int catalog) {
        Bundle bundle = new Bundle();
        bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, catalog);
        return bundle;
    }
}

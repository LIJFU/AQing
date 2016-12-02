package com.xiaoyuan54.child.edu.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.PostAdapter;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.base.BaseActivity;
import com.xiaoyuan54.child.edu.app.base.BaseListFragment;
import com.xiaoyuan54.child.edu.app.bean.Post;
import com.xiaoyuan54.child.edu.app.bean.PostList;
import com.xiaoyuan54.child.edu.app.util.UIHelper;
import com.xiaoyuan54.child.edu.app.util.XmlUtils;

import java.io.InputStream;
import java.io.Serializable;

/**
 * 标签相关帖子
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * 
 */
public class QuestionTagFragment extends BaseListFragment<Post> {

    public static final String BUNDLE_KEY_TAG = "BUNDLE_KEY_TAG";
    protected static final String TAG = QuestionTagFragment.class
            .getSimpleName();
    private static final String CACHE_KEY_PREFIX = "post_tag_";
    private String mTag;

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mTag = args.getString(BUNDLE_KEY_TAG);
            ((BaseActivity) getActivity()).setActionBarTitle(getString(
                    R.string.actionbar_title_question_tag, mTag));
        }
    }

    @Override
    protected PostAdapter getListAdapter() {
        return new PostAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + mTag;
    }

    @Override
    protected PostList parseList(InputStream is) throws Exception {
        return XmlUtils.toBean(PostList.class, is);
    }

    @Override
    protected PostList readList(Serializable seri) {
        return ((PostList) seri);
    }

    @Override
    protected void sendRequestData() {
        HttpClientApi.getPostListByTag(mTag, mCurrentPage, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Post post = mAdapter.getItem(position);
        if (post != null)
            UIHelper.showPostDetail(view.getContext(), post.getId(),
                    post.getAnswerCount());
    }
}

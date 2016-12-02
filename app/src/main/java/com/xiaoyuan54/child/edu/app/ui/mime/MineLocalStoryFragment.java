package com.xiaoyuan54.child.edu.app.ui.mime;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.google.gson.reflect.TypeToken;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.base.PageBean;
import com.xiaoyuan54.child.edu.app.bean.story.Story;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseRecyclerAdapter;
import com.xiaoyuan54.child.edu.app.improve.base.fragments.BaseRecyclerViewFragment;
import com.xiaoyuan54.child.edu.app.ui.mime.adapter.MineLocalStoryAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by m on 2016-11-09.
 */
public class MineLocalStoryFragment extends BaseRecyclerViewFragment<Story>
{

    public static MineLocalStoryFragment newInstance()
    {
        Bundle args = new Bundle();
        MineLocalStoryFragment fragment = new MineLocalStoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_mine_local_story;
    }

    @Override
    protected void requestData()
    {
        super.requestData();

        List<Story> storys  = new ArrayList();
        for(int i= 0;i< 20;i++)
        {
            Story song = new Story();
            song.setTitle("白龙马");
            song.setAuthor("小蓓蕾");
            song.setCoverImg("http://business.cdn.qianqian.com/qianqian/pic/bos_client_f38f43a0a480b772fd6299aa0cd43ac1.jpg");
            storys.add(song);
        }

        mBean.setItem(storys);
        setListData(mBean);
    }

    @Override
    protected Type getType()
    {
        return new TypeToken<PageBean<Story>>(){}.getType();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager()
    {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        manager.offsetChildrenHorizontal(50);
        return manager;
    }

    @Override
    protected BaseRecyclerAdapter<Story> getRecyclerAdapter()
    {
        return new MineLocalStoryAdapter(getContext());
    }

}

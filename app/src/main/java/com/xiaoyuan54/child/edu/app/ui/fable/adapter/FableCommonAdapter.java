package com.xiaoyuan54.child.edu.app.ui.fable.adapter;
import android.content.Context;
import android.widget.GridView;
import android.widget.TextView;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.ViewHolder;
import com.xiaoyuan54.child.edu.app.bean.my.Fable;
import com.xiaoyuan54.child.edu.app.bean.my.FableGroup;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseListAdapter;
import com.xiaoyuan54.child.edu.app.improve.bean.Blog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m on 2016-10-30.
 */
public class FableCommonAdapter extends BaseListAdapter<Blog> {
    private Context mContent;

    public FableCommonAdapter(Callback callback, Context context) {
        super(callback);
        mContent = context;
    }

    @Override
    protected void convert(ViewHolder vh, Blog item, int position) {
        TextView title =  vh.getView(R.id.tv_group_title);

        title.setText("热销图书");
        GridView fables = vh.getView(R.id.gv_fables);



        List<Fable> fableList = new ArrayList<>();
        Fable temp = new Fable();
        temp.setTitle("阿波快跑");

        fableList.add(temp);
        fableList.add(temp);
        fableList.add(temp);
        fableList.add(temp);
        fables.setNumColumns(2);
        fables.setAdapter(new FableItemAdapter(mContent,fableList));



    }

    @Override
    protected int getLayoutId(int position, Blog item) {
        return R.layout.fragment_group_fable;
    }
}

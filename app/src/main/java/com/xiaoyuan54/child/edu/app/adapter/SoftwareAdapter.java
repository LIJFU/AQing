package com.xiaoyuan54.child.edu.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.base.ListBaseAdapter;
import com.xiaoyuan54.child.edu.app.bean.SoftwareDec;
import com.xiaoyuan54.child.edu.app.bean.SoftwareList;
import com.xiaoyuan54.child.edu.app.util.ThemeSwitchUtils;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SoftwareAdapter extends ListBaseAdapter<SoftwareDec> {

    static class ViewHold {
        @Bind(R.id.tv_title)
        TextView name;
        @Bind(R.id.tv_software_des)
        TextView des;

        public ViewHold(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {

        ViewHold vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.list_cell_software, null);
            vh = new ViewHold(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHold) convertView.getTag();
        }

        SoftwareDec softwareDes = (SoftwareDec) mDatas.get(position);
        vh.name.setText(softwareDes.getName());

        if (AppContext.isOnReadedPostList(SoftwareList.PREF_READED_SOFTWARE_LIST,
                softwareDes.getName())) {
            vh.name.setTextColor(parent.getContext().getResources()
                    .getColor(ThemeSwitchUtils.getTitleReadedColor()));
        } else {
            vh.name.setTextColor(parent.getContext().getResources()
                    .getColor(ThemeSwitchUtils.getTitleUnReadedColor()));
        }

        vh.des.setText(softwareDes.getDescription());

        return convertView;
    }
}

package com.xiaoyuan54.child.edu.app.improve.main.nav;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;

/**
 * Created by m on 2016-10-31.
 */

public class TopNavigationButton extends FrameLayout {
    private Fragment mFragment = null;
    private Class<?> mClx;
    private TextView mTitleView;

    private String mTag;

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_top_nav_item, this, true);
        mTitleView = (TextView) findViewById(R.id.nav_tv_title);
    }

    public void init(@StringRes int strId, Class<?> clx) {
        mTitleView.setText(strId);
        mClx = clx;
        mTag = mClx.getName();
    }

    public Class<?> getClx() {
        return mClx;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    public String getTag() {
        return mTag;
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mTitleView.setSelected(selected);
    }

    public TopNavigationButton(Context context) {
        super(context);
        init();
    }

    public TopNavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopNavigationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

}

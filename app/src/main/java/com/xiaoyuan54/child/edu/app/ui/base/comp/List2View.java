package com.xiaoyuan54.child.edu.app.ui.base.comp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by L.QING on 2016-11-28.
 */

public class List2View extends ListView {

    public List2View(Context context) {
        super(context);
    }

    public List2View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public List2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public List2View(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
       // setMeasuredDimension();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

package com.xiaoyuan54.child.edu.app.ui.mime.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.DatePicker;

/**
 * Created by m on 2016-10-14.
 */

public class DatePickerDialog extends AlertDialog {
    private Context mContext;

    private DatePicker mPicker;


    public DatePickerDialog(@NonNull Context context) {
        super(context);

        init(context);
    }

    private void init(Context context){
        mContext = context;
        mPicker = new DatePicker(mContext);

        this.setView(mPicker);
    }

    public DatePicker getmPicker() {
        return mPicker;
    }
}

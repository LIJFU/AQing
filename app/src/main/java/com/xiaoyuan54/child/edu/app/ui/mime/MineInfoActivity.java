package com.xiaoyuan54.child.edu.app.ui.mime;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.base.BaseActivity;
import com.xiaoyuan54.child.edu.app.ui.mime.dialog.DatePickerDialog;
import com.xiaoyuan54.child.edu.app.ui.mime.dialog.GenderDialog;
import com.xiaoyuan54.child.edu.app.util.UIHelper;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;

/**
 * Created by m on 2016-11-13.
 */

public class MineInfoActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    @Bind(R.id.tool_hint)
    TextView mToolHint;

    @Bind(R.id.lv_gender)
    LinearLayout mGender;

    @Bind(R.id.lv_birthday)
    LinearLayout mBirthday;

    @Bind(R.id.tv_birthday_value)
    TextView birthdayValue;

    @Bind(R.id.tv_gender_name)
    TextView mGenderName;

    @Bind(R.id.ageValue)
    TextView mAgeValue;


    @Bind(R.id.lv_name)
    LinearLayout mName;

    private Context mContext;

    private Activity mActivity;

    private final String TITLE = "个人资料";


    private GenderDialog genderDialog;



    private void initToolBar(){
        mToolHint.setText(TITLE);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_info;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lv_gender:
                genderDialog.show();
                break;
            case R.id.lv_birthday:
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                com.fourmob.datetimepicker.date.DatePickerDialog dialog = com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(new com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog, int year, int month, int day) {
                        Date date = new Date(year - 1900, month, day);
                        birthdayValue.setText(DateFormat.format("yyyy-MM-dd", date));
                        long currentYear = calendar.get(Calendar.YEAR);
                        mAgeValue.setText(String.valueOf(currentYear - year));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.setTheme(R.style.dialog_trans);
                dialog.show(manager,"user_info_date_pick");
                break;
            case R.id.lv_name:
                UIHelper.showNameModify(mContext);
                break;
            default:
                break;
        }
    }

    @Override
    public void initView() {
        mActionBar.hide();
        mContext = this;
        mActivity = this;
        initToolBar();
        genderDialog = new GenderDialog(
                this,
                mGenderName.getText().toString(),
                new GenderDialog.OnItemClickListener() {
                    @Override
                    public void onGirlClick() {
                        mGenderName.setText(GenderDialog.GIRL);
                    }
                    @Override
                    public void onBoyClick() {
                        mGenderName.setText(GenderDialog.BOY);
                    }
                }
        );
        mGender.setOnClickListener(this);
        mBirthday.setOnClickListener(this);
        mName.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }
}

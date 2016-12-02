package com.xiaoyuan54.child.edu.app.ui.mime.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.ui.dialog.CommonDialog;
/**
 * Created by m on 2016-11-13.
 */
public class GenderDialog extends CommonDialog {

    private Context mContext;

    private LinearLayout mBoy;

    private LinearLayout mGirl;

    private ImageView mBoyCheckFlag;

    private ImageView mGirlCheckFlag;

    public static final String BOY = "男";
    public static final String GIRL = "女";

    public interface OnItemClickListener {
         void onGirlClick();
         void onBoyClick();
    }

    private OnItemClickListener clickCallBack;

    public GenderDialog(Context context,String genderName,OnItemClickListener onItemClickListener) {
        super(context,R.style.dialog_trans);
        mContext = context;
        View v = getLayoutInflater().inflate(R.layout.dialog_gender,null);
        mBoy = (LinearLayout) v.findViewById(R.id.lv_boy);
        mGirl = (LinearLayout) v.findViewById(R.id.lv_girl);
        mBoyCheckFlag = (ImageView) v.findViewById(R.id.iv_boy_check);
        mGirlCheckFlag = (ImageView) v.findViewById(R.id.iv_girl_check);
        this.clickCallBack = onItemClickListener;
        this.setCancelable(true);
        this.closeOptionsMenu();
        initView(genderName);
        mBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.onBoyClick();
                dismiss();
            }
        });
        mGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.onGirlClick();
                dismiss();
            }
        });

        setContent(v, 0);

    }

    private void initView(String genderName) {
        switch (genderName){
            case BOY:
                mBoyCheckFlag.setVisibility(View.VISIBLE);
                mGirlCheckFlag.setVisibility(View.INVISIBLE);
                break;
            case GIRL:
                mBoyCheckFlag.setVisibility(View.INVISIBLE);
                mGirlCheckFlag.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = getWindow().getAttributes();

        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }
}

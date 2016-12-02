package com.xiaoyuan54.child.edu.app.ui.update.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.AppDirManager;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.bean.version.Version;
import com.xiaoyuan54.child.edu.app.ui.base.fragment.BaseFragment;
import com.xiaoyuan54.child.edu.app.ui.main.MainActivity;
import com.xiaoyuan54.child.edu.app.util.TDevice;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by L.QING on 2016-11-27.
 */

public class UpdateFragment extends BaseFragment {

    private MainActivity context;
    private boolean playBarNeed = false;

    private Version version;

    @Bind(R.id.update_close_btn)  ImageView closeImg;
    @Bind(R.id.update_v_name) TextView  versionNameTv;
    @Bind(R.id.update_note_tv) TextView  versionNoteTv;
    @Bind(R.id.update_intall_btn) TextView  installBtn;

    @Override
    protected int getLayoutId() {
       return R.layout.fragment_update_version;
    }

    @Override
    protected void initWidget(View root)
    {
        super.initWidget(root);
        context = (MainActivity) getContext();
        this.setCanBackPress(false);
        playBarNeed = context.getPlayerBar().getBarNeed();
        context.getPlayerBar().setBarNeed(false);
        updateViewUi();
    }

    public void setVersion(Version version)
    {
        this.version = version;
    }


    private void updateViewUi()
    {
        if(null==version)
        {
            return;
        }
        String cName = TDevice.getVersionName();
        String toVersion = null==cName?version.getVersionName():cName+" -> "+version.getVersionName();
        versionNameTv.setText(toVersion);
        String note = version.getVersionNote();
        if(null!=note)
        {
            note = note.replaceAll("<br>","\n");
        }
        versionNoteTv.setText(note);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        context.getPlayerBar().setBarNeed(playBarNeed);
    }


    @OnClick(value = {R.id.update_close_btn,R.id.update_intall_btn})
    public void  clickAction(View view)
    {
        switch (view.getId())
        {
            case R.id.update_close_btn:
                if(null!=version&&"Y".equals(version.getForceUpdate()))
                {
                  context.moveTaskToBack(true);
                  return;
                }
                else
                {
                  context.detachFrag();
                }
            break;

            case R.id.update_intall_btn:
                File apk = new File(AppDirManager.APK
                        + File.separator+version.getVersionCode()
                        +"_"+version.getVersionName()+".apk");
                TDevice.installAPK(getContext(),apk);
            break;
        }
    }



}

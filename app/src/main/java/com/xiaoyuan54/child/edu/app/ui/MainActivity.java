package com.xiaoyuan54.child.edu.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import com.xiaoyuan54.child.edu.app.AppConfig;
import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.AppManager;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.base.BaseApplication;
import com.xiaoyuan54.child.edu.app.bean.Constants;
import com.xiaoyuan54.child.edu.app.bean.Notice;
import com.xiaoyuan54.child.edu.app.bean.SimpleBackPage;
import com.xiaoyuan54.child.edu.app.cache.DataCleanManager;
import com.xiaoyuan54.child.edu.app.fragment.MyInformationFragment;
import com.xiaoyuan54.child.edu.app.interf.BaseViewInterface;
import com.xiaoyuan54.child.edu.app.interf.OnTabReselectListener;
import com.xiaoyuan54.child.edu.app.service.NoticeUtils;
import com.xiaoyuan54.child.edu.app.util.UIHelper;
import com.xiaoyuan54.child.edu.app.widget.BadgeView;
import com.xiaoyuan54.child.edu.app.widget.MyFragmentTabHost;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        OnTabChangeListener, BaseViewInterface, View.OnClickListener,
        OnTouchListener {

    private Context mContext4Umeng;
    private final String packageName4Umeng = "MainActivity";

    private long mBackPressedTime;

    @Bind(android.R.id.tabhost)
    MyFragmentTabHost mTabHost;

    private BadgeView mBvNotice;

    public static Notice mNotice;

    private String[] mTitles;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.INTENT_ACTION_NOTICE)) {
                mNotice = (Notice) intent.getSerializableExtra("notice_bean");
               // int atmeCount = mNotice.getAtmeCount();// @我
                int atmeCount = 3;// @我
                int msgCount = mNotice.getMsgCount();// 留言
                int reviewCount = mNotice.getReviewCount();// 评论
                int newFansCount = mNotice.getNewFansCount();// 新粉丝
                int newLikeCount = mNotice.getNewLikeCount();// 收到赞
                int activeCount = atmeCount + reviewCount + msgCount + newFansCount + newLikeCount;

                Fragment fragment = getCurrentFragment();
                if (fragment instanceof MyInformationFragment) {
                    ((MyInformationFragment) fragment).setNotice();
                } else {
                    if (activeCount > 0) {
                        mBvNotice.setText(String.format("%s", activeCount + ""));
                        mBvNotice.show();
                    } else {
                        mBvNotice.hide();
                        mNotice = null;
                    }
                }
            } else if (intent.getAction()
                    .equals(Constants.INTENT_ACTION_LOGOUT)) {
               // mBvNotice.hide();
               // mNotice = null;
            }
        }
    };

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Bind(R.id.quick_option_iv)
    View mAddBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.mContext4Umeng = this;

        super.onCreate(savedInstanceState);
        /*
        if (AppContext.getNightModeSwitch()) {
            setTheme(R.style.App.Theme.Night);
        } else {
            setTheme(R.style.App.Theme.Light);
        }
        */
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();


        AppManager.getAppManager().addActivity(this);

        handleIntent(getIntent());

        //umeng analytics
        MobclickAgent.setDebugMode(false);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.packageName4Umeng);
        MobclickAgent.onResume(this.mContext4Umeng);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageStart(this.packageName4Umeng);
        MobclickAgent.onResume(this.mContext4Umeng);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    /**
     * 处理传进来的intent
     */
    private void handleIntent(Intent intent) {
        if (intent == null)
            return;
        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            UIHelper.showUrlRedirect(this, intent.getDataString());
        } else if (intent.getBooleanExtra("NOTICE", false)) {
            notificationBarClick(intent);
        }
    }

    /**
     * 从通知栏点击的时候相应
     */
    private void notificationBarClick(Intent fromWhich) {
        if (fromWhich != null) {
            boolean fromNoticeBar = fromWhich.getBooleanExtra("NOTICE", false);
            if (fromNoticeBar) {
                Intent toMyInfor = new Intent(this, SimpleBackActivity.class);
                toMyInfor.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE,
                        SimpleBackPage.MY_MES.getValue());
                startActivity(toMyInfor);
            }
        }
    }

    @Override
    public void initView() {
        mTitle = getResources().getString(R.string.main_tab_name_music);
        mTitles = getResources().getStringArray(R.array.main_titles_arrays);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }

        initTabs();

        // 中间按键图片触发
        mAddBt.setOnClickListener(this);

        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);

        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_NOTICE);
        filter.addAction(Constants.INTENT_ACTION_LOGOUT);
        registerReceiver(mReceiver, filter);
        NoticeUtils.bindToService(this);

        if (AppContext.isFristStart()) {
            DataCleanManager.cleanInternalCache(AppContext.getInstance());
            AppContext.setFristStart(false);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NoticeUtils.unbindFromService(this);
        unregisterReceiver(mReceiver);
        mReceiver = null;
        NoticeUtils.tryToShutDown(this);
        AppManager.getAppManager().removeActivity(this);
    }

    @Override
    public void initData() {
    }

    @SuppressWarnings("deprecation")
    private void initTabs()
    {

    }

    @SuppressWarnings("deprecation")
    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        restoreActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search:
                UIHelper.showSimpleBack(this, SimpleBackPage.SEARCH);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabChanged(String tabId) {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            // 点击了快速操作按钮
            case R.id.quick_option_iv:
                showQuickOption();
                break;
            default:
                break;
        }
    }

    // 显示快速操作界面
    private void showQuickOption() {
        final QuickOptionDialog dialog = new QuickOptionDialog(
                MainActivity.this);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    @Override
    public void onBackPressed() {

        boolean isDoubleClick = BaseApplication.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true);

        if (isDoubleClick) {
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (3 * 1000)) {
                finish();
            } else {
                mBackPressedTime = curTime;
                Toast.makeText(this, R.string.tip_double_click_exit, Toast.LENGTH_LONG).show();
            }
        } else {
            finish();
        }

    }
}
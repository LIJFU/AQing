package com.xiaoyuan54.child.edu.app.improve.detail.fragments;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.improve.bean.Software;
import com.xiaoyuan54.child.edu.app.improve.comment.CommentsView;
import com.xiaoyuan54.child.edu.app.improve.bean.NewsDetail;
import com.xiaoyuan54.child.edu.app.improve.bean.simple.Comment;
import com.xiaoyuan54.child.edu.app.improve.behavior.FloatingAutoHideDownBehavior;
import com.xiaoyuan54.child.edu.app.improve.comment.OnCommentClickListener;
import com.xiaoyuan54.child.edu.app.improve.detail.activities.SoftwareDetailActivity;
import com.xiaoyuan54.child.edu.app.improve.detail.contract.NewsDetailContract;
import com.xiaoyuan54.child.edu.app.improve.user.activities.OtherUserHomeActivity;
import com.xiaoyuan54.child.edu.app.improve.widget.DetailAboutView;
import com.xiaoyuan54.child.edu.app.util.StringUtils;
import com.xiaoyuan54.child.edu.app.util.TDevice;

/**
 * Created by qiujuer
 * on 16/5/26.
 */

public class NewsDetailFragment extends DetailFragment<NewsDetail, NewsDetailContract.View, NewsDetailContract.Operator>
        implements View.OnClickListener, NewsDetailContract.View, OnCommentClickListener {

    private long mId;
    // private TextView mTVAuthorName;
    private TextView mTVPubDate;
    private TextView mTVTitle;
    // private ImageView mIVAuthorPortrait;
    private ImageView mIVFav;
    private EditText mETInput;
    private long mCommentId;
    private long mCommentAuthorId;
    private boolean mInputDoubleEmpty = false;
    private DetailAboutView mAbouts;
    private CommentsView mComments;
    private CoordinatorLayout mLayCoordinator;
    private NestedScrollView mLayContent;
    private View mLayBottom;
    private TextView mAbhoutSoftwareTitle;
    private LinearLayout mAboutSoftware;
    private TextView mTVName;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_general_news_detail;
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);


        //mTVAuthorName = (TextView) root.findViewById(R.id.tv_name);
        mTVPubDate = (TextView) root.findViewById(R.id.tv_pub_date);
        mTVTitle = (TextView) root.findViewById(R.id.tv_title);
        mTVName = (TextView) root.findViewById(R.id.tv_info_view);
        mTVName.setOnClickListener(this);

        setGone(R.id.iv_info_view);
        //setGone(R.id.tv_info_view);
        setGone(R.id.iv_info_comment);

        //mIVAuthorPortrait = (ImageView) root.findViewById(R.id.iv_avatar);
        //  mIVAuthorPortrait.setOnClickListener(this);
        mIVFav = (ImageView) root.findViewById(R.id.iv_fav);
        mIVFav.setOnClickListener(this);

        mETInput = (EditText) root.findViewById(R.id.et_input);

        mAbouts = (DetailAboutView) root.findViewById(R.id.lay_detail_about);
        mAboutSoftware = (LinearLayout) root.findViewById(R.id.lay_about_software);
        mAbhoutSoftwareTitle = (TextView) root.findViewById(R.id.tv_about_software_title);
        mComments = (CommentsView) root.findViewById(R.id.lay_detail_comment);

        mLayCoordinator = (CoordinatorLayout) root.findViewById(R.id.fragment_blog_detail);
        mLayContent = (NestedScrollView) root.findViewById(R.id.lay_nsv);

        registerScroller(mLayContent, mComments);

        mLayBottom = root.findViewById(R.id.lay_option);

        root.findViewById(R.id.iv_share).setOnClickListener(this);
        mIVFav.setOnClickListener(this);
        mETInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handleSendComment();
                    return true;
                }
                return false;
            }
        });
        mETInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    handleKeyDel();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 相关软件
            case R.id.lay_about_software:
                SoftwareDetailActivity.show(getActivity(), mOperator.getData().getSoftware().getId());
                break;
            // 收藏
            case R.id.iv_fav:
                handleFavorite();
                break;
            // 分享
            case R.id.iv_share:
                handleShare();
                break;
            case R.id.tv_info_view:
                OtherUserHomeActivity.show(getActivity(), mOperator.getData().getAuthorId());
                break;
            default:
                break;
            // 评论列表
            //case R.id.tv_see_comment: {
            // UIUtil.showBlogComment(getActivity(), (int) mId,
            //  (int) mOperator.getNewsDetail().getId());
            //   }
            // break;
        }
    }

    @Override
    protected void initData() {
        final NewsDetail newsDetail = mOperator.getData();
        if (newsDetail == null)
            return;

        mId = mCommentId = newsDetail.getId();

        setCommentCount(newsDetail.getCommentCount());
        setBodyContent(newsDetail.getBody());

        //mTVAuthorName.setText(newsDetail.getAuthor());
        // getImgLoader().load(newsDetail.getAuthorPortrait()).error(R.mipmap.widget_dface).into(mIVAuthorPortrait);
        mTVName.setText(String.format("%s%s%s%s", "@", newsDetail.getAuthor(), "  ", "发布于 "));

        mTVPubDate.setText(StringUtils.formatSomeAgo(newsDetail.getPubDate()));

        mTVTitle.setText(newsDetail.getTitle());

        toFavoriteOk(newsDetail);

        // setText(R.id.tv_info_view, String.valueOf(newsDetail.getViewCount()));
        setText(R.id.tv_info_comment, StringUtils.formatYearMonthDay(newsDetail.getPubDate()));

        Software software = newsDetail.getSoftware();
        if (software != null) {
            mAboutSoftware.setOnClickListener(this);
            mAbhoutSoftwareTitle.setText(software.getName());
        } else {
            mAboutSoftware.setVisibility(View.GONE);
        }

        mAbouts.setAbout(newsDetail.getAbouts(), 6);

        mComments.setTitle(String.format("评论 (%s)", newsDetail.getCommentCount()));
        mComments.init(newsDetail.getId(), HttpClientApi.COMMENT_NEWS, newsDetail.getCommentCount(), getImgLoader(), this);
    }

    private void handleKeyDel() {
        if (mCommentId != mId) {
            if (TextUtils.isEmpty(mETInput.getText())) {
                if (mInputDoubleEmpty) {
                    mCommentId = mId;
                    mCommentAuthorId = 0;
                    mETInput.setHint("发表评论");
                } else {
                    mInputDoubleEmpty = true;
                }
            } else {
                mInputDoubleEmpty = false;
            }
        }
    }


    private void handleFavorite() {
        mOperator.toFavorite();
    }

    private void handleShare() {
        mOperator.toShare();
    }

    private void handleSendComment() {
        mOperator.toSendComment(mId, mCommentId, mCommentAuthorId, mETInput.getText().toString());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void toFavoriteOk(NewsDetail newsDetail) {
        if (newsDetail.isFavorite())
            mIVFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_faved));
        else
            mIVFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav));
    }

    @Override
    public void toSendCommentOk(Comment comment) {
        (Toast.makeText(getContext(), "评论成功", Toast.LENGTH_LONG)).show();
        mETInput.setText("");
        mComments.addComment(comment, getImgLoader(), this);
        TDevice.hideSoftKeyboard(mETInput);
    }

    @Override
    public void onClick(View view, Comment comment) {
        FloatingAutoHideDownBehavior.showBottomLayout(mLayCoordinator, mLayContent, mLayBottom);
        mCommentId = comment.getId();
        mCommentAuthorId = comment.getAuthorId();
        mETInput.setHint(String.format("回复: %s", comment.getAuthor()));
        TDevice.showSoftKeyboard(mETInput);
    }
}

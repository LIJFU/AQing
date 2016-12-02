package com.xiaoyuan54.child.edu.app.bean;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.fragment.ActiveFragment;
import com.xiaoyuan54.child.edu.app.fragment.BrowserFragment;
import com.xiaoyuan54.child.edu.app.fragment.CommentFrament;
import com.xiaoyuan54.child.edu.app.fragment.EventAppliesFragment;
import com.xiaoyuan54.child.edu.app.fragment.EventFragment;
import com.xiaoyuan54.child.edu.app.fragment.FeedBackFragment;
import com.xiaoyuan54.child.edu.app.fragment.MessageDetailFragment;
import com.xiaoyuan54.child.edu.app.fragment.MyInformationFragment;
import com.xiaoyuan54.child.edu.app.fragment.MyInformationFragmentDetail;
import com.xiaoyuan54.child.edu.app.fragment.QuestionTagFragment;
import com.xiaoyuan54.child.edu.app.fragment.SettingsFragment;
import com.xiaoyuan54.child.edu.app.fragment.SettingsNotificationFragment;
import com.xiaoyuan54.child.edu.app.fragment.SoftWareTweetsFrament;
import com.xiaoyuan54.child.edu.app.fragment.TweetLikeUsersFragment;
import com.xiaoyuan54.child.edu.app.fragment.TweetRecordFragment;
import com.xiaoyuan54.child.edu.app.fragment.TweetsFragment;
import com.xiaoyuan54.child.edu.app.fragment.UserCenterFragment;
import com.xiaoyuan54.child.edu.app.improve.user.fragments.UserBlogFragment;
import com.xiaoyuan54.child.edu.app.viewpagerfragment.FriendsViewPagerFragment;
import com.xiaoyuan54.child.edu.app.viewpagerfragment.NoticeViewPagerFragment;
import com.xiaoyuan54.child.edu.app.viewpagerfragment.QuestViewPagerFragment;
import com.xiaoyuan54.child.edu.app.viewpagerfragment.SearchViewPageFragment;
import com.xiaoyuan54.child.edu.app.viewpagerfragment.UserFavoriteViewPagerFragment;

public enum SimpleBackPage {

    COMMENT(1, R.string.actionbar_title_comment, CommentFrament.class),

    QUEST(2, R.string.actionbar_title_questions, QuestViewPagerFragment.class),

//    TWEET_PUB(3, R.string.actionbar_title_tweetpub, TweetPubFragment.class),

    SOFTWARE_TWEETS(4, R.string.actionbar_title_softtweet,
            SoftWareTweetsFrament.class),

    USER_CENTER(5, R.string.actionbar_title_user_center,
            UserCenterFragment.class),

    USER_BLOG(6, R.string.actionbar_title_user_blog, UserBlogFragment.class),

    MY_INFORMATION(7, R.string.actionbar_title_my_information,
            MyInformationFragment.class),

    MY_ACTIVE(8, R.string.actionbar_title_active, ActiveFragment.class),

    MY_MES(9, R.string.actionbar_title_mes, NoticeViewPagerFragment.class),

    MY_FOLLOWING(11, R.string.following,
            FriendsViewPagerFragment.class),

    QUESTION_TAG(12, R.string.actionbar_title_question,
            QuestionTagFragment.class),

    MESSAGE_DETAIL(13, R.string.actionbar_title_message_detail,
            MessageDetailFragment.class),

    USER_FAVORITE(14, R.string.actionbar_title_user_favorite,
            UserFavoriteViewPagerFragment.class),

    SETTING(15, R.string.actionbar_title_setting, SettingsFragment.class),

    SETTING_NOTIFICATION(16, R.string.actionbar_title_setting_notification,
            SettingsNotificationFragment.class),

    RECORD(19, R.string.actionbar_title_tweetpub, TweetRecordFragment.class),

    SEARCH(20, R.string.actionbar_title_search, SearchViewPageFragment.class),

    EVENT_APPLY(22, R.string.actionbar_title_event_apply,
            EventAppliesFragment.class),

    SAME_CITY(23, R.string.actionbar_title_same_city, EventFragment.class),

    BROWSER(26, R.string.app_name, BrowserFragment.class),

    MY_INFORMATION_DETAIL(28, R.string.actionbar_title_my_information,
            MyInformationFragmentDetail.class),

    FEED_BACK(29, R.string.str_feedback_title, FeedBackFragment.class),


    TWEET_LIKE_USER_LIST(41, 0, TweetLikeUsersFragment.class),

    TWEET_TOPIC_LIST(42, 0, TweetsFragment.class),

    MY_FOLLOWER(45, R.string.follower,
            FriendsViewPagerFragment.class);

    private int title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }


}

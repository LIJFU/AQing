package com.xiaoyuan54.child.edu.app.improve.tweet.contract;

import com.xiaoyuan54.child.edu.app.bean.User;
import com.xiaoyuan54.child.edu.app.improve.bean.Tweet;
import com.xiaoyuan54.child.edu.app.improve.bean.simple.TweetComment;

/**
 * Created by thanatosx
 * on 16/5/28.
 */

public interface TweetDetailContract {

    interface Operator {

        Tweet getTweetDetail();

        void toReply(TweetComment comment);

        void onScroll();
    }

    interface ICmnView {
        void onCommentSuccess(TweetComment comment);
    }

    interface IThumbupView {
        void onLikeSuccess(boolean isUp, User user);
    }

    interface IAgencyView {
        void resetLikeCount(int count);

        void resetCmnCount(int count);
    }

}

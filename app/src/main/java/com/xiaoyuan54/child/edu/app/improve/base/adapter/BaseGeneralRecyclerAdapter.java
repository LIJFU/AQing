package com.xiaoyuan54.child.edu.app.improve.base.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;

import com.bumptech.glide.RequestManager;

import com.xiaoyuan54.child.edu.app.emoji.InputHelper;
import com.xiaoyuan54.child.edu.app.improve.utils.AssimilateUtils;

import com.xiaoyuan54.child.edu.app.widget.TweetTextView;

import java.util.Date;

/**
 * Created by huanghaibin_dev
 * on 2016/8/18.
 */

public abstract class BaseGeneralRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {
    protected Callback mCallBack;

    public BaseGeneralRecyclerAdapter(Callback callback, int mode) {
        super(callback.getContext(), mode);
        mCallBack = callback;
        setState(STATE_LOADING, true);
    }

    protected void parseAtUserContent(TweetTextView textView, String text) {
        String content = "";
        if (TextUtils.isEmpty(text)) return;
        content = text.replaceAll("[\n\\s]+", " ");
        Spannable spannable = AssimilateUtils.assimilateOnlyAtUser(mCallBack.getContext(), content);
        spannable = AssimilateUtils.assimilateOnlyTag(mCallBack.getContext(), spannable);
        spannable = AssimilateUtils.assimilateOnlyLink(mCallBack.getContext(), spannable);
        spannable = AssimilateUtils.assimilateOnlyTeamTask(mCallBack.getContext(),spannable);
        spannable = InputHelper.displayEmoji(mCallBack.getContext().getResources(), spannable);
        textView.setText(spannable);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setFocusable(false);
        textView.setDispatchToParent(true);
        textView.setLongClickable(false);
    }

    public interface Callback {
        RequestManager getImgLoader();

        Context getContext();

        Date getSystemTime();
    }
}

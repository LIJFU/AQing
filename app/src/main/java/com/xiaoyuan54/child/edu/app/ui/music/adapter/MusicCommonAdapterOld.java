package com.xiaoyuan54.child.edu.app.ui.music.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.AppContext;
import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.adapter.ViewHolder;
import com.xiaoyuan54.child.edu.app.improve.base.adapter.BaseListAdapter;
import com.xiaoyuan54.child.edu.app.improve.bean.Question;
import com.xiaoyuan54.child.edu.app.improve.general.fragments.QuestionFragment;
import com.xiaoyuan54.child.edu.app.improve.user.fragments.UserQuestionFragment;

/**
 * 儿歌通用fragment通用适配器
 */
public class MusicCommonAdapterOld extends BaseListAdapter<Question> {

    private int actionPosition = 0;
    private boolean isUserQuestion;

    public void setActionPosition(int actionPosition) {
        this.actionPosition = actionPosition;
    }

    public void setUserQuestion(boolean userQuestion) {
        this.isUserQuestion = userQuestion;
    }

    public MusicCommonAdapterOld(Callback callback) {
        super(callback);
    }

    @Override
    protected int getLayoutId(int position, Question item) {
        return R.layout.fragment_item_music_common;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void convert(ViewHolder vh, Question item, int position) {
        String authorPortrait = item.getAuthorPortrait();
        if (authorPortrait != null) {
            vh.setImageForNet(R.id.iv_ques_item_icon, authorPortrait.trim(), R.mipmap.widget_dface);
        }
        TextView title = vh.getView(R.id.tv_ques_item_title);
        String title1 = item.getTitle();
        if (title1 != null) {
            title.setText("ABCD英文歌");
        }
        TextView content = vh.getView(R.id.tv_ques_item_content);
        String body = item.getBody();
        if (body != null) {
            body = body.trim();
            if (!TextUtils.isEmpty(body)) {
                content.setText("下载1.3w次");
                content.setVisibility(View.VISIBLE);
            } else {
                content.setVisibility(View.GONE);
            }
        }

        String cacheName = verifyFileName();

        if (isUserQuestion) {
            cacheName = UserQuestionFragment.HISTORY_MY_QUESTION;
        }

        if (AppContext.isOnReadedPostList(cacheName, String.valueOf(item.getId()))) {
            title.setTextColor(mCallback.getContext().getResources().getColor(R.color.count_text_color_light));
            content.setTextColor(mCallback.getContext().getResources().getColor(R.color.count_text_color_light));
        } else {
            title.setTextColor(mCallback.getContext().getResources().getColor(R.color.blog_title_text_color_light));
            content.setTextColor(mCallback.getContext().getResources().getColor(R.color.ques_bt_text_color_dark));
        }

        /*
        TextView history = vh.getView(R.id.tv_ques_item_history);

        String author = item.getAuthor();
        if (author != null) {
            author = author.trim();
            history.setText((author.length() > 9 ? author.substring(0, 9) : author.trim()) + "  " + StringUtils.formatSomeAgo(item.getPubDate().trim()));
        }
        history.setText("下载1.3w次");
        TextView see = vh.getView(R.id.tv_info_view);
        see.setText(String.valueOf(item.getViewCount()));
        TextView answer = vh.getView(R.id.tv_info_comment);
        answer.setText(String.valueOf(item.getCommentCount()));

        */
    }

    private String verifyFileName() {
        switch (actionPosition) {
            case 1:
                return QuestionFragment.QUES_ASK;
            case 2:
                return QuestionFragment.QUES_SHARE;
            case 3:
                return QuestionFragment.QUES_COMPOSITE;
            case 4:
                return QuestionFragment.QUES_PROFESSION;
            case 5:
                return QuestionFragment.QUES_WEBSITE;
            default:
                return QuestionFragment.QUES_ASK;
        }
    }

}

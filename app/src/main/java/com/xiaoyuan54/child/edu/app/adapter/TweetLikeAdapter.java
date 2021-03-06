package com.xiaoyuan54.child.edu.app.adapter;

import android.annotation.SuppressLint;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoyuan54.child.edu.app.R;
import com.xiaoyuan54.child.edu.app.base.ListBaseAdapter;
import com.xiaoyuan54.child.edu.app.bean.TweetLike;
import com.xiaoyuan54.child.edu.app.util.PlatfromUtil;
import com.xiaoyuan54.child.edu.app.util.StringUtils;
import com.xiaoyuan54.child.edu.app.util.UIHelper;
import com.xiaoyuan54.child.edu.app.widget.AvatarView;
import com.xiaoyuan54.child.edu.app.widget.MyLinkMovementMethod;
import com.xiaoyuan54.child.edu.app.widget.MyURLSpan;
import com.xiaoyuan54.child.edu.app.widget.TweetTextView;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * 动弹点赞适配器 TweetLikeAdapter.java
 *
 * @author 火蚁(http://my.oschina.net/u/253900)
 * @data 2015-4-10 上午10:19:19
 */
public class TweetLikeAdapter extends ListBaseAdapter<TweetLike> {

    @SuppressLint("InflateParams")
    @Override
    protected View getRealView(int position, View convertView,
                               final ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(
                    R.layout.list_cell_tweet_like, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final TweetLike item = (TweetLike) mDatas.get(position);

        vh.name.setText(item.getUser().getName().trim());

        vh.action.setText("赞了我的动弹");

        vh.time.setText(StringUtils.formatSomeAgo(item.getDatatime().trim()));

        PlatfromUtil.setPlatFromString(vh.from, item.getAppClient());
        vh.avatar.setUserInfo(item.getUser().getId(), item.getUser().getName());
        vh.avatar.setAvatarUrl(item.getUser().getPortrait());

        vh.reply.setMovementMethod(MyLinkMovementMethod.a());
        vh.reply.setFocusable(false);
        vh.reply.setDispatchToParent(true);
        vh.reply.setLongClickable(false);
        Spanned span = UIHelper.parseActiveReply(item.getTweet().getAuthor(),
                item.getTweet().getBody());
        vh.reply.setText(span);
        MyURLSpan.parseLinkText(vh.reply, span);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView name;
        @Bind(R.id.tv_from)
        TextView from;
        @Bind(R.id.tv_time)
        TextView time;
        @Bind(R.id.tv_action)
        TextView action;
        @Bind(R.id.tv_reply)
        TweetTextView reply;
        @Bind(R.id.iv_avatar)
        AvatarView avatar;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.xiaoyuan54.child.edu.app.improve.detail.contract;

import com.xiaoyuan54.child.edu.app.bean.EventApplyData;
import com.xiaoyuan54.child.edu.app.improve.bean.EventDetail;

/**
 * Created by huanghaibin
 * on 16-6-13.
 */
public interface EventDetailContract {
    interface View extends DetailContract.View {
        void toFavOk(EventDetail detail);

        void toSignUpOk(EventDetail detail);
    }

    interface Operator extends DetailContract.Operator<EventDetail, View> {
        void toFav();

        void toSignUp(EventApplyData data);
    }
}

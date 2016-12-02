package com.xiaoyuan54.child.edu.app.ui.login.widget;
import android.os.CountDownTimer;
/**
 * Created by m on 2016-11-30.
 */
public class Countdown<T> extends CountDownTimer {

    private OnExecuteListener finishCallback;

    private T view;

    public Countdown(long millisInFuture, long countDownInterval,OnExecuteListener<T> onFinishListener,T t) {
        super(millisInFuture, countDownInterval);
        this.finishCallback = onFinishListener;
        this.view = t;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        if(view != null) {
            finishCallback.onTick(view, millisUntilFinished);
        }else{
            this.cancel();
        }
    }

    @Override
    public void onFinish() {
        if(view!=null) {
            finishCallback.onFinish(view);
        }else {
            this.cancel();
        }
    }


    public interface OnExecuteListener<T>{
        void onTick(T t,long millisUntilFinished);
        void onFinish(T t);
    }
}

package com.chuxin.law.ui.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2516:45
 * @Email zyp@jusfoun.com
 * @Description ${限时免费 倒计时view}
 */
public class LimitedTimeView extends BaseView {
    protected TextView textTime;
    private MyCountDownTimer myCountDownTimer;

    public LimitedTimeView(Context context) {
        super(context);
    }

    public LimitedTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LimitedTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_limited_time, this, true);
        textTime = (TextView) findViewById(R.id.text_time);
    }

    @Override
    protected void initActions() {

    }


    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            textTime.setText(stringForTime(l));
        }

        @Override
        public void onFinish() {

        }

        private String stringForTime(long timeSec) {
            long totalSeconds = timeSec;
            long seconds = totalSeconds % 60;
            long minutes = totalSeconds / 60 % 60;
            long hours = totalSeconds / 3600;
            return hours+":"+minutes+":"+seconds;
        }
    }

    public void setData(long time){
        if(myCountDownTimer!=null){
            myCountDownTimer.cancel();
        }
        myCountDownTimer = new MyCountDownTimer(time,1000);
        myCountDownTimer.start();
    }

}

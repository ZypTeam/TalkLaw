package com.chuxin.law.ui.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.chuxin.law.R;
import com.chuxin.law.base.BaseView;

import java.text.SimpleDateFormat;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2516:45
 * @Email zyp@jusfoun.com
 * @Description ${限时免费 倒计时view}
 */
public class LimitedTimeView extends BaseView {
    protected TextView textTime,desText;
    private MyCountDownTimer myCountDownTimer;

    private SimpleDateFormat format =  new SimpleDateFormat( "hh:mm:ss");



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
        desText = (TextView)findViewById(R.id.text_des);
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
            Log.e("tag","onTick==="+l);
            time = time-1000;
            if(time<=0){
                textTime.setText("已结束");
                desText.setVisibility(GONE);
            }else {
                desText.setVisibility(VISIBLE);
                textTime.setText(ms2HMS(time));
            }
        }

        @Override
        public void onFinish() {
            textTime.setText("已结束");
            desText.setVisibility(GONE);
            Log.e("tag","stringForTime===");
        }

        private String stringForTime(long timeSec) {


            long totalSeconds = timeSec;
            long seconds = totalSeconds % 60;
            long minutes = totalSeconds / 60 % 60;
            long hours = totalSeconds / 3600;


            Log.e("tag","stringForTime==="+hours+":"+minutes+":"+seconds);
            return hours+":"+minutes+":"+seconds;
        }
    }

    private long time;
    public void setData(long time){
//        if(System.currentTimeMillis()>=time){
//            textTime.setText("已结束");
//            desText.setVisibility(GONE);
//            return;
//        }
        this.time= time*1000;
        Log.e("tag","timetime="+time);
        if(myCountDownTimer!=null){
            myCountDownTimer.cancel();
        }
        myCountDownTimer = new MyCountDownTimer(time,1000);
        myCountDownTimer.start();
    }

    public static String ms2HMS(long _ms){
        String HMStime;
        _ms/=1000;
        long hour=_ms/3600;
        long mint=(_ms%3600)/60;
        long sed=_ms%60;
        String hourStr=String.valueOf(hour);
        if(hour<10){
            hourStr="0"+hourStr;
        }
        String mintStr=String.valueOf(mint);
        if(mint<10){
            mintStr="0"+mintStr;
        }
        String sedStr=String.valueOf(sed);
        if(sed<10){
            sedStr="0"+sedStr;
        }
        HMStime=hourStr+":"+mintStr+":"+sedStr;
        return HMStime;
    }


}

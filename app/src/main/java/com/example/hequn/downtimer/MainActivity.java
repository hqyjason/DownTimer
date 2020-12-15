package com.example.hequn.downtimer;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TimeUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView tvsssss;
    private PeterTimeCountRefresh timer;
    private RelativeLayout countDown;
    // 倒计时
    private TextView mDays_Tv, mHours_Tv, mMinutes_Tv, mSeconds_Tv;

    //private long mDay = 23;// 天
    private long mHour = 0;//小时,
    private long mMin = 1;//分钟,
    private long mSecond = 32;//秒

    private Timer mTimer;

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                //mDays_Tv.setText(mDay+"");//天数不用补位
                mHours_Tv.setText(getTv(mHour));
                mMinutes_Tv.setText(getTv(mMin));
                mSeconds_Tv.setText(getTv(mSecond));
                if (mSecond == 0 && mHour == 0 && mMin == 0 ) {
                    mTimer.cancel();
                }
            }
        }
    };

    private String getTv(long l){
        if(l>=10){
            return l+"";
        }else{
            return "0"+l;//小于10,,前面补位一个"0"
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tvsssss = (TextView)findViewById(R.id.tvsssss);
/*
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvsssss.setText("倒计时" + millisUntilFinished / 1000 + "秒");
            }

            public void onFinish() {
                Toast.makeText(MainActivity.this,"倒计时结束",Toast.LENGTH_SHORT).show();
            }
        };
        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        timer.start();*/
        mTimer = new Timer();
        countDown = (RelativeLayout) findViewById(R.id.countdown_layout);
        //mDays_Tv = (TextView) findViewById(R.id.days_tv);
        mHours_Tv = (TextView) findViewById(R.id.hours_tv);
        mMinutes_Tv = (TextView) findViewById(R.id.minutes_tv);
        mSeconds_Tv = (TextView) findViewById(R.id.seconds_tv);
        startRun();
    }


    /**
     * 开启倒计时
     *  //time为Date类型：在指定时间执行一次。
     *        timer.schedule(task, time);
     *  //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
     *       timer.schedule(task, firstTime,period);
     *  //delay 为long类型：从现在起过delay毫秒执行一次。
     *       timer.schedule(task, delay);
     *  //delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
     *       timer.schedule(task, delay,period);
     */
    private void startRun() {
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,0,1000);
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mMin = 0;
                    mSecond = 0;
                    //mDay--;
/*                    if(mDay < 0){
                        // 倒计时结束
                        //mDay = 0;
                        mHour= 0;
                        mMin = 0;
                        mSecond = 0;
                    }*/
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

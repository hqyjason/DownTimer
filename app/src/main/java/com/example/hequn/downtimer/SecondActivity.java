package com.example.hequn.downtimer;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cc.ibooker.zcountdownviewlib.CountDownView;
import cc.ibooker.zcountdownviewlib.SingleCountDownView;

public class SecondActivity extends AppCompatActivity {


    private CountDownView countdownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        countdownView = findViewById(R.id.countdownView);

// 基本属性设置
        countdownView.setCountTime(10) // 设置倒计时时间戳
                .setHourTvBackgroundRes(R.drawable.white)
                .setHourTvTextColorHex("#000000")
                .setHourTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourTvTextSize(21)

                .setHourColonTvBackgroundColorHex("#00FFFFFF")
                .setHourColonTvSize(18, 0)
                .setHourColonTvTextColorHex("#ffffff")
                .setHourColonTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourColonTvTextSize(21)

                .setMinuteTvBackgroundRes(R.drawable.white)
                .setMinuteTvTextColorHex("#FFFFFF")
                .setMinuteTvTextSize(21)

                .setMinuteColonTvSize(18, 0)
                .setMinuteColonTvTextColorHex("#ffffff")
                .setMinuteColonTvTextSize(21)

                .setSecondTvBackgroundRes(R.drawable.white)
                .setSecondTvTextColorHex("#FFFFFF")
                .setSecondTvTextSize(21)

//      .setTimeTvWH(18, 40)
//      .setColonTvSize(30)

                // 开启倒计时
                .startCountDown()

                // 设置倒计时结束监听
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        Toast.makeText(SecondActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
                    }
                });

// 测试暂停倒计时
/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 暂停倒计时
                countdownView.pauseCountDown();
            }
        }, 5000);*/

// 测试停止倒计时
/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 停止倒计时
                countdownView.stopCountDown();
            }
        }, 15000);*/
    }
}

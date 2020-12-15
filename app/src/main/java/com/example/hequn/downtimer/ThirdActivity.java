package com.example.hequn.downtimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daidingkang.SnapUpCountDownTimerView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        SnapUpCountDownTimerView rushBuyCountDownTimerView = (SnapUpCountDownTimerView) findViewById(R.id.RushBuyCountDownTimerView);
        rushBuyCountDownTimerView.setTime(1,55,3);//设置小时，分钟，秒。注意不能大于正常值，否则会抛出异常

        rushBuyCountDownTimerView.start();//开始倒计时
    }
}

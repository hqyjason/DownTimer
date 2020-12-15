package com.example.hequn.downtimer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.iwgang.countdownview.CountdownView;

public class FourActivity extends AppCompatActivity implements CountdownView.OnCountdownEndListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

    }

    @Override
    public void onEnd(CountdownView cv) {

    }
}

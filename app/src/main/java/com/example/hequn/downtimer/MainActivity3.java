package com.example.hequn.downtimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hequn.downtimer.retrofit.Articles;
import com.example.hequn.downtimer.retrofit.GetRequest_Interface;
import com.example.hequn.downtimer.retrofit.Translation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity3 extends AppCompatActivity {

    private Button btn_one;

    private static final String TAG = "Rxjava";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        EventBus.getDefault().register(this);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendRequest();
                sendTimesRequest();
/*                Intent intent = new Intent(MainActivity3.this , MainActivity2.class);
                startActivity(intent);*/
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){

            EventBus.getDefault().unregister(this);

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(MessageEvent messageEvent){

        String text = messageEvent.getMessage();

        btn_one.setText(text);

    }

    private void sendTimesRequest(){

        /*
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         **/
        Observable.interval(2,1, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                /*
                 * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                 * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                 **/
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long integer) throws Exception {
                        Log.e(TAG, "第 " + integer + " 次轮询" );

                        /*
                         * 步骤3：通过Retrofit发送网络请求
                         **/
                        // a. 创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://www.wanandroid.com/navi/") // 设置 网络请求 Url
                                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                                .build();

                        // b. 创建 网络请求接口 的实例
                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

                        // c. 采用Observable<...>形式 对 网络请求 进行封装
                        Observable<Articles> observable = request.getCall();
                        // d. 通过线程切换发送网络请求
                        observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                                .subscribe(new Observer<Articles>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        Log.e(TAG, "请求开始");
                                    }

                                    @Override
                                    public void onNext(Articles result) {
                                        // e.接收服务器返回的数据
                                       Log.e(TAG , result.toString());
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG, "请求失败");
                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.e(TAG, "请求完成");
                                    }
                                });

                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(Long value) {

                Log.e("value" , value.toString());

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "对Complete事件作出响应");
            }
        });

    }

    private void sendRequest(){

        //创建retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/navi/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //创建网络请求接口的实例

        GetRequest_Interface request_interface = retrofit.create(GetRequest_Interface.class);

        //采用Observable<>形式对网络请求进行封装
        Observable<Articles> observable = request_interface.getCall();

        //发送网络请求
        observable.subscribeOn(Schedulers.io())     //在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())      //回到主线程处理请求结果
                .subscribe(new Observer<Articles>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        Log.e(TAG, "开始采用subscribe连接");

                    }
                    @Override
                    public void onNext(@NonNull Articles translation) {

                        //对返回数据进行处理
                        Log.e("返回数据" , translation.toString());



                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log.e(TAG , "请求失败");

                    }

                    @Override
                    public void onComplete() {

                        Log.e(TAG , "请求成功");

                    }
                });

    }
}
package com.example.hequn.downtimer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private ScrollFloatingButton sfb_button;
    private Button btn_start;
    private TextView tv_hour;
    private TextView tv_minutes;

    private long leftTime = 10;

    private Handler handler = new Handler();
    private Runnable update_thread = new Runnable() {
        @Override
        public void run() {

            leftTime--;
            Log.e("测试分支" , "yes");
            Log.e("我是一个测试分支" , "yes");
            if (leftTime > 0) {
                //倒计时效果展示
                String formatLongToTimeStr = formatLongToTimeStr1(leftTime);
                tv_hour.setText(formatLongToTimeStr);
                String formatLongToTimeSt2r = formatLongToTimeStr2(leftTime);
                tv_minutes.setText(formatLongToTimeSt2r);
                //每一秒执行一次
                handler.postDelayed(this,1000);
            } else {//倒计时结束
                //处理业务流程
                //发送消息，结束倒计时
                tv_hour.setText("00");
                tv_minutes.setText("00");
                Message message = new Message();
                message.what = 1;
                handlerStop.sendMessage(message);
            }

        }
    };

    private final Handler handlerStop = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    leftTime = 0;
                    handler.removeCallbacks(update_thread);
                    break;
            }
            super.handleMessage(msg);
        }

    };
    private RecyclerView rlv_data;
    private List<News> list = new ArrayList<>();
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sfb_button = (ScrollFloatingButton)findViewById(R.id.sfb_button);

        sfb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectTimeBottom();
                Log.e("点击了滑动按钮" , "yes");

            }
        });

        btn_start = (Button) findViewById(R.id.btn_start);
        tv_hour = (TextView) findViewById(R.id.tv_hour);
        tv_minutes = (TextView) findViewById(R.id.tv_minutes);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handler.postDelayed(update_thread , 1000);

            }
        });
        rlv_data = (RecyclerView) findViewById(R.id.rlv_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rlv_data.setLayoutManager(linearLayoutManager);

        News news1 = new News();
        news1.setDriverName("不定向转单");
        list.add(news1);
        News news2 = new News();
        news2.setDriverId("123");
        news2.setDriverName("不定向转单");
        list.add(news2);
        News news3 = new News();
        news3.setDriverName("定向转单");
        list.add(news3);
        for (int i = 0; i < 3; i++) {
            News news = new News();
            news.setDriverName("骑手"+i+1);
            news.setDriverId(""+i);
            list.add(news);
        }

        Log.e("数据源" , list.toString());
        adapter = new NewsAdapter(this , list);
        rlv_data.setAdapter(adapter);
    }

    //显示底部选择时间
    private void showSelectTimeBottom() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.MyDialogStyleBottom);
        //2、设置布局
        View view = View.inflate(this, R.layout.select_issue_dialog, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        // window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        leftTime = 0;
        handler.removeCallbacks(update_thread);
    }

    public String formatLongToTimeStr1(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() ;
        if (second > 60) {
            minute = second / 60;  //取整
            second = second % 60;  //取余
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = "剩余："+hour+"小时"+minute+"分"+second+"秒";
        if (minute <10){
            return "0"+minute;
        }else {
            return ""+minute;
        }

    }


    public String formatLongToTimeStr2(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() ;
        if (second > 60) {
            minute = second / 60;  //取整
            second = second % 60;  //取余
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = "剩余："+hour+"小时"+minute+"分"+second+"秒";
        if (second <10){
            return "0"+second;
        }else {
            return ""+second;
        }

    }

    private class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private final int TITLE = 1;
        private final int DRIVER = 2;

        private Context context;
        private List<News> list;
        private AdapterView.OnItemClickListener clickListener;

        public NewsAdapter(Context context, List<News> list) {
            this.context = context;
            this.list = list;
        }


        @Override
        public int getItemViewType(int position) {
            if (list.get(position).getDriverId() == null){

                return TITLE;

            }else if (list.get(position).getDriverName().length()>0){

                return DRIVER;

            }else {
                return super.getItemViewType(position);
            }

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            Log.e("viewType类型" , ""+viewType);
            if (viewType == TITLE){
                view = LayoutInflater.from(context).inflate(R.layout.title , parent ,false);
                return new TitleHolder(view);
            }else if (viewType == DRIVER){
                view = LayoutInflater.from(context).inflate(R.layout.driver , parent ,false);
                return new InfoHolder(view);
            }else {
                view = LayoutInflater.from(context).inflate(R.layout.title , parent ,false);
                return new TitleHolder(view);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            if (holder instanceof TitleHolder){

                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.tv_title.setText(list.get(position).getDriverName());
                titleHolder.ll_title_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("title点击了" , ""+position);
                    }
                });
            }else if (holder instanceof InfoHolder){

                                InfoHolder infoHolder = (InfoHolder) holder;
                        infoHolder.tv_tpi_name.setText(list.get(position).getDriverName());

                        infoHolder.ll_tpi_content.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("driver点击了" , ""+position);
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class TitleHolder extends RecyclerView.ViewHolder{

            private final TextView tv_title;
            private final LinearLayout ll_title_content;

            public TitleHolder(@NonNull View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.tv_title);
                ll_title_content = itemView.findViewById(R.id.ll_title_content);
            }
        }

        class InfoHolder extends RecyclerView.ViewHolder{

            private final TextView tv_tpi_name;
            private final LinearLayout ll_tpi_select;
            private final LinearLayout ll_tpi_content;

            public InfoHolder(@NonNull View itemView) {
                super(itemView);
                tv_tpi_name = itemView.findViewById(R.id.tv_tpi_name);
                ll_tpi_select = itemView.findViewById(R.id.ll_tpi_select);
                ll_tpi_content = itemView.findViewById(R.id.ll_tpi_content);
            }
        }
    }
}
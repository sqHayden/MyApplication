package com.idx.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import Interface.CallBack;
import adapter.MyAdapter;
import bean.Card;
import utils.AnimUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //接收刷新倒计时
    private final int MSG_ONE = 1;
    //接收刷新分数
    private final int MSG_TWO = 2;
    //接收显示-3
    private final int MSG_THREE = 3;
    //接收清空TimeText
    private final int MSG_FOUR = 4;
    //设置定时器
    Timer timer = new Timer();
    //时间控制
    TextView timerText;
    //分数减三
    TextView threeText;
    //卡片布局
    private GridView gridView;
    //存储图片的数组
    private List<Card> cardList = new ArrayList<>();
    //自定义Adapter
    private MyAdapter myAdapter;
    //重置图片
    private Button start;
    //相关视图
    private LinearLayout game_main, game_start;
    private int count = 5;
    private boolean isFirst;
    //保存相关
    private View cardView = null;
    private String cardName = "";
    private int cardPos = -1;
    //分数
    private double score;
    //统计消失数
    private int number;
    //统计时间
    private long startTime;
    private long endTime;
    //控制速度的标志位
    private boolean flag;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_ONE:
                    //改变Text
                    String s = "即将闭合，剩余时间： " + Html.fromHtml("<font color='red'>" + count + "</font>" + " s");
                    timerText.setText(s);
                    count--;
                    Log.i(TAG, "count:" + count);
                    if (count == -1) {
                        //改变Text
                        String s1 = "当前分数： 0 ";
                        timerText.setText(s1);
                        //开启旋转
                        isFirst = false;
                        myAdapter.setBoolean(isFirst);
                        myAdapter.notifyDataSetChanged();
                        //开始计时
                        startTime = System.currentTimeMillis();
                    }
                    Log.i(TAG, "handleMessage: " + count);
                    break;
                case MSG_TWO:
                    //关闭-3
                    if (threeText.getVisibility() == View.VISIBLE) {
                        threeText.setVisibility(View.GONE);
                    }
                    //改变Text
                    String s2 = "当前分数： " + Html.fromHtml("<font color='red'>" + getInt(score) + "</font>");
                    timerText.setText(s2);
                    break;
                case MSG_THREE:
                    //显示-3
                    threeText.setVisibility(View.VISIBLE);
                    break;
                case MSG_FOUR:
                    //清空TimeText
                    timerText.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //构造数据源
        initList();
        //初始化数据
        initData();
        myAdapter = new MyAdapter(getApplicationContext(), cardList, isFirst);
        gridView.setAdapter(myAdapter);
    }

    /**
     * 组件初始化
     */
    private void initView() {
        //拿到组件
        gridView = findViewById(R.id.girls);
        //监听
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewBack, int positionBack, long id) {
                if (flag) {
                    final int position = positionBack;
                    final View view = viewBack;
                    Log.i(TAG, "onItemClick: view的地址:" + view.toString());
                    //处理动画
                    final ImageView oldImage = view.findViewById(R.id.old_img);
                    final ImageView newImage = view.findViewById(R.id.new_img);
                    //在这里处理
                    if ("".equals(cardName)) {
                        //第一次点击
                        AnimUtil.FlipAnimatorXViewShow(oldImage, newImage, 500, null);
                        cardName = cardList.get(position).getCardName();
                        cardView = view;
                        cardPos = position;
                    } else {
                        //第二次点击
                        if (cardPos == position) {

                        } else {
                            flag = false;
                            AnimUtil.FlipAnimatorXViewShow(oldImage, newImage, 500, new CallBack() {
                                @Override
                                public void call() {
                                    if (cardName.equals(cardList.get(position).getCardName())) {
                                        //点了相同的
                                        //隐藏第一次点击
                                        cardView.setVisibility(View.GONE);
                                        //隐藏第二次点击
                                        view.setVisibility(View.GONE);
                                        //得分
                                        score += 12.5;
                                        //发送分数数据
                                        Message msg = new Message();
                                        msg.what = MSG_TWO;
                                        handler.sendMessage(msg);
                                        //置空
                                        cardView = null;
                                        cardName = "";
                                        number = number + 2;
                                        if (number == 16) {
                                            endTime = System.currentTimeMillis();
                                            final String time = dealWithTime();
                                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                                    .setTitle("您已顺利通关，恭喜！")
                                                    .setMessage("本次通关" + time + ", 总得分为 " + getInt(score) + " 分")
                                                    .setPositiveButton("再来一次", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Toast.makeText(getApplicationContext(), "您点击了再来一次", Toast.LENGTH_SHORT).show();
                                                            //重置List
                                                            //刷新数据
                                                            initData();
                                                            //重置刷新时间
                                                            count = 5;
                                                            Collections.shuffle(cardList);
                                                            myAdapter = new MyAdapter(getApplicationContext(), cardList, isFirst);
                                                            gridView.setAdapter(myAdapter);
                                                            timer = new Timer();
                                                            timer.schedule(new TimerTask() {
                                                                @Override
                                                                public void run() {
                                                                    try {
                                                                        for (int i = 0; i < 6; i++) {
                                                                            Message msg = new Message();
                                                                            msg.what = MSG_ONE;
                                                                            handler.sendMessage(msg);
                                                                            Thread.sleep(1000);
                                                                            Log.i(TAG, "run: ");
                                                                        }
                                                                    } catch (InterruptedException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }, 2000);
                                                        }
                                                    })
                                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Toast.makeText(getApplicationContext(), "您点击了取消", Toast.LENGTH_SHORT).show();
                                                            //发消息重置一下TimeText
                                                            Message message = new Message();
                                                            message.what = MSG_FOUR;
                                                            handler.sendMessage(message);
                                                            //重置数据
                                                            initData();
                                                            count = 5;
                                                            //重置adapter
                                                            Collections.shuffle(cardList);
                                                            myAdapter = new MyAdapter(getApplicationContext(), cardList, isFirst);
                                                            gridView.setAdapter(myAdapter);
                                                            //显示主界面
                                                            game_main.setVisibility(View.GONE);
                                                            game_start.setVisibility(View.VISIBLE);
                                                        }
                                                    }).create();
                                            alertDialog.setCancelable(false);
                                            alertDialog.show();
                                        }
                                    } else {
                                        if (cardView != null) {
                                            //点了不同的
                                            //本次View恢复
                                            AnimUtil.FlipAnimatorXViewShow(newImage, oldImage, 500, null);
                                            //上一个view恢复
                                            ImageView oldImage = cardView.findViewById(R.id.old_img);
                                            ImageView newImage = cardView.findViewById(R.id.new_img);
                                            AnimUtil.FlipAnimatorXViewShow(newImage, oldImage, 500, null);
                                            //分数减三
                                            if (score >= 3) {
                                                score -= 3;
                                                //发送消息显示-3
                                                Message msg1 = new Message();
                                                msg1.what = MSG_THREE;
                                                handler.sendMessage(msg1);
                                            }else if(score>0&&score<3){
                                                score = 0;
                                                //发送消息显示-3
                                                Message msg1 = new Message();
                                                msg1.what = MSG_THREE;
                                                handler.sendMessage(msg1);
                                            }else if(score==0){
                                                score = 0;
                                            }
                                            //置空
                                            cardName = "";
                                            cardView = null;
                                            //延时发送
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //发送消息恢复
                                                    Message msg2 = new Message();
                                                    msg2.what = MSG_TWO;
                                                    handler.sendMessage(msg2);
                                                }
                                            }, 500);
                                        }
                                    }
                                    flag = true;
                                }
                            });
                        }
                    }
                }
            }
        });
        game_main = findViewById(R.id.game_main);
        game_start = findViewById(R.id.game_start);
        //按钮点击
        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示出来东西
                if (game_main.getVisibility() == View.GONE) {
                    game_start.setVisibility(View.GONE);
                    game_main.setVisibility(View.VISIBLE);
                }
                //定时
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i < 6; i++) {
                                Message msg = new Message();
                                msg.what = MSG_ONE;
                                handler.sendMessage(msg);
                                Thread.sleep(1000);
                                Log.i(TAG, "run: ");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, 2000);
            }
        });
        //获取时间Text
        timerText = findViewById(R.id.time_girl);
        //获取减分数Text
        threeText = findViewById(R.id.number_three);
    }

    /**
     * 清除数据
     */
    private void clearData() {

    }

    /**
     * 处理时间
     */
    private String dealWithTime() {
        StringBuilder sb = new StringBuilder();
        if ((endTime - startTime) > 0) {
            int time = (int) ((endTime - startTime) / 1000);
            int hour = 0, min = 0, sec = 0;
            if (time >= 3600) {//处理小时
                hour = time / 3600;
                if (time >= (3600 * hour)) {
                    time = time - (3600 * hour);
                }
                sb.append("总耗时： " + hour + " 小时");
            }
            if (time >= 60) {//处理分钟
                min = time / 60;
                if (time >= (60 * min)) {
                    time = time - (60 * min);
                }
                if (hour == 0) {
                    sb.append("总耗时： " + min + " 分钟 " + time + " 秒");
                    time = 0;
                } else {
                    sb.append(" " + min + "分钟 " + time + " 秒");
                    time = 0;
                }
            }
            if (time > 0 && time < 60) {
                if (hour == 0) {
                    sb.append("总耗时： " + time + " 秒");
                } else {
                    sb.append(" " + time + " 秒");
                }
            }
            Log.i(TAG, sb.toString());
            return sb.toString();
        }
        return "";
    }

    /**
     * 构造数据源
     */
    private void initList() {
        cardList.add(new Card("美女一", R.mipmap.a01));
        cardList.add(new Card("美女一", R.mipmap.a01));
        cardList.add(new Card("美女二", R.mipmap.a02));
        cardList.add(new Card("美女二", R.mipmap.a02));
        cardList.add(new Card("美女三", R.mipmap.a03));
        cardList.add(new Card("美女三", R.mipmap.a03));
        cardList.add(new Card("美女四", R.mipmap.a04));
        cardList.add(new Card("美女四", R.mipmap.a04));
        cardList.add(new Card("美女五", R.mipmap.a10));
        cardList.add(new Card("美女五", R.mipmap.a10));
        cardList.add(new Card("美女六", R.mipmap.a11));
        cardList.add(new Card("美女六", R.mipmap.a11));
        cardList.add(new Card("美女七", R.mipmap.a12));
        cardList.add(new Card("美女七", R.mipmap.a12));
        cardList.add(new Card("美女八", R.mipmap.a13));
        cardList.add(new Card("美女八", R.mipmap.a13));
        //打乱次序
        Collections.shuffle(cardList);
    }

    /**
     * 数据初始化
     */
    private void initData() {
        isFirst = true;
        score = 0;
        number = 0;
        startTime = 0;
        endTime = 0;
        flag = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 转化整数
     */
    private int getInt(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }
}
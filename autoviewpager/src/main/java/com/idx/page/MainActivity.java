package com.idx.page;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fragment.ChatFragment;
import fragment.ContractFragment;
import fragment.FindFragment;
import fragment.MeFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private MyAdapter myAdapter;
    private ImageView pic_0,pic_1,pic_2,pic_3;
    private LinearLayout pic0_lay,pic1_lay,pic2_lay,pic3_lay;
    private final int MSG_ONE = 1;
    private boolean flag;
    private int position = 0;
    private final int FIRST_PAGE = 1;

    //定时器设置
    private Timer timer = new Timer();
    //定时器任务
    private TimerTask timerTask;
    //下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_ONE:
                    //定时变更子项显示
                    if(position<5){
                        //变换
                        viewPager.setCurrentItem(position,false);
                        //+1操作
                        position++;
                    }else{
                        position = 1;
                        //变换
                        viewPager.setCurrentItem(position,false);
                        position++;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_main);
        initView();
        myAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);
        //设置为1位置先显示
        viewPager.setCurrentItem(FIRST_PAGE);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position1) {
                position = position1;
                if (position == fragmentList.size()-1) {
                    // 设置当前值为1
                    position = FIRST_PAGE;
                } else if (position == 0) {
                    // 如果索引值为0了,就设置索引值为倒数第二个
                    position = fragmentList.size()-2;
                } else {
                    position = position1;
                }
                //翻页后需要去改变对应位置的图片
                switch (position){
                    case 1:
                        pic_0.setBackgroundResource(R.drawable.circle_check_style);
                        pic_1.setBackgroundResource(R.drawable.circle_uncheck_style);
                        pic_2.setBackgroundResource(R.drawable.circle_uncheck_style);
                        pic_3.setBackgroundResource(R.drawable.circle_uncheck_style);
                        break;
                    case 2:
                        pic_1.setBackgroundResource(R.drawable.circle_check_style);
                        pic_0.setBackgroundResource(R.drawable.circle_uncheck_style);
                        pic_2.setBackgroundResource(R.drawable.circle_uncheck_style);
                        pic_3.setBackgroundResource(R.drawable.circle_uncheck_style);
                        break;
                    case 3:
                        pic_2.setBackgroundResource(R.drawable.circle_check_style);
                        pic_0.setBackgroundResource(R.drawable.circle_uncheck_style);
                        pic_1.setBackgroundResource(R.drawable.circle_uncheck_style);
                        pic_3.setBackgroundResource(R.drawable.circle_uncheck_style);
                        break;
                    case 4:
                        pic_3.setBackgroundResource(R.drawable.circle_check_style);
                        pic_0.setBackgroundResource(R.drawable.circle_uncheck_style);
                        pic_1.setBackgroundResource(R.drawable.circle_uncheck_style);
                        pic_2.setBackgroundResource(R.drawable.circle_uncheck_style);
                        break;
                    default:
                        break;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                //滑动状态改变的方法
                //state :dragging 拖拽 idle 静止 settling 惯性过程
                //如果是静止状态,将当前页进行替换
                if(state==ViewPager.SCROLL_STATE_IDLE){
                    viewPager.setCurrentItem(position, false);
                }
            }
        });

        //添加一个定时器任务
        timerTask = new TimerTask() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        Thread.sleep(2000);
                        //向主线程每隔2s发送一次消息
                        Message msg = new Message();
                        msg.what = MSG_ONE;
                        handler.sendMessage(msg);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.schedule(timerTask,0);
    }

    /**
     * 数据填充
     */
    private void initData() {
        //设置起始位
        position = 2;
        if(fragmentList==null){
            fragmentList = new ArrayList<>();
        }
        //添加第四个
        fragmentList.add(new MeFragment());
        //顺序添加
        fragmentList.add(new ChatFragment());
        fragmentList.add(new ContractFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new MeFragment());
        //添加第一个
        fragmentList.add(new ChatFragment());
        flag = true;
    }

    /**
     * 布局相关
     */
    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        pic_0 = findViewById(R.id.pic_0);
        pic0_lay = findViewById(R.id.pic0_lay);
        pic0_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1,false);
            }
        });
        pic_1 = findViewById(R.id.pic_1);
        pic1_lay = findViewById(R.id.pic1_lay);
        pic1_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2,false);
            }
        });
        pic_2 = findViewById(R.id.pic_2);
        pic2_lay = findViewById(R.id.pic2_lay);
        pic2_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3,false);
            }
        });
        pic_3 = findViewById(R.id.pic_3);
        pic3_lay = findViewById(R.id.pic3_lay);
        pic3_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(4,false);
            }
        });
        //下拉刷新控件
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        //监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(),"刷新了一下",Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(2000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //改变刷新状态
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //取消定时器
        if(timer!=null) {
            timer.cancel();
            timer = null;
        }
        if(timerTask!=null) {
            timerTask.cancel();
            timerTask = null;
        }
        flag = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
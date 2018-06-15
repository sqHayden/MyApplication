package com.demo.gank;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.demo.gank.adapter.MyAdapter;
import com.demo.gank.decoration.OffsetDecoration;
import com.demo.gank.json.Data;
import com.demo.gank.json.Result;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final String url = "http://gank.io/api/data/福利/10/";
    //动画
    protected OffsetDecoration decoration = new OffsetDecoration();
    private RecyclerView mRecyclerView;
    private List<Result> results;
    private List<Result> returnResults;
    private MyAdapter myAdapter;
    private int page_number;
    private boolean mBottomRefreshing;
    private SwipeRefreshLayout refreshLayout;

    //滑动监听
    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private boolean mAlreadyRefreshed = false;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //如果滑动停止且刷新过了,就恢复原态
                if (mAlreadyRefreshed) {
                    mAlreadyRefreshed = false;
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //如果在刷新，则不再执行刷新
            if (isBottomRefreshing() || mAlreadyRefreshed) {
                return;
            }
            //否则执行刷新
            if (isBottomViewVisible()) {
                //改变页数
                page_number++;
                //执行刷新
                sendRequestWithOkHttp(true);
                mBottomRefreshing = true;
                mAlreadyRefreshed = true;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        //通过findViewById拿到RecyclerView实例
        mRecyclerView = findViewById(R.id.img_list);
        //拿到头刷新
        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //清空缓存
                results.clear();
                //请求最新
                sendRequestWithOkHttp(false);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        //动画添加
        mRecyclerView.addItemDecoration(decoration);
        //添加第一页显示
        sendRequestWithOkHttp(true);
    }

    /**
     * 数据初始化
     */
    private void initData() {
        //创建空集合
        if (results == null) {
            results = new ArrayList<>();
        }
        //页数初始化
        page_number = 1;
        mBottomRefreshing = false;
    }

    /**
     * 该方法用来获取网络返回的Json
     */
    public void sendRequestWithOkHttp(final boolean isLoad) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();


                    if(!isLoad) {
                        page_number = 1;
                    }
                    String send_url;send_url = url + page_number;
                    Request request = new Request.Builder().url(send_url).build();
                    Response response = okHttpClient.newCall(request).execute();
                    String json = response.body().string();
                    //子线程等待1s
                    Thread.sleep(1000);
                    dealWithJson(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 处理网络获取的Json
     *
     * @param json 获取的Json
     */
    private void dealWithJson(String json) {
        Gson gson = new Gson();
        Data data = gson.fromJson(json, Data.class);
        //拿到网络数据
        returnResults = data.getResults();
        //添加到集合中
        Boolean add = results.addAll(returnResults);
        for (Result result : returnResults) {
            Log.i(TAG, "dealWithJson:" + result.getUrl());
        }
        if (add) {
            //主线程更新视图
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //初始化适配器
                    if (myAdapter == null) {
                        myAdapter = new MyAdapter(MainActivity.this, results);
                        //设置适配器
                        mRecyclerView.setAdapter(myAdapter);
                    } else {
                        myAdapter.notifyItemRangeInserted(results.size(), 10);
                    }
                    //重置
                    onBottomRefreshComplete();
                    refreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private int getLastVisibleItemPosition() {
        RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) manager).findLastVisibleItemPosition();
        }
        return NO_POSITION;
    }

    private boolean isBottomViewVisible() {
        int lastVisibleItem = getLastVisibleItemPosition();
        return lastVisibleItem != NO_POSITION && lastVisibleItem == myAdapter.getItemCount() - 1;
    }

    public boolean isBottomRefreshing() {
        return mBottomRefreshing;
    }

    public void onBottomRefreshComplete() {
        mBottomRefreshing = false;
    }
}

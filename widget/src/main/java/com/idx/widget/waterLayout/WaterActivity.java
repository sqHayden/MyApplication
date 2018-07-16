package com.idx.widget.waterLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.idx.widget.R;

/*
 * Created by hayden on 18-7-2.
 */

public class WaterActivity extends AppCompatActivity {

    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private int[] arr = new int[12];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        initData();
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //防止跳跃
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView = findViewById(R.id.recycler_list);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //防止第一行到顶部有空白区域
                manager.invalidateSpanAssignments();
            }
        });
        //设置布局
        recyclerView.setLayoutManager(manager);
        //加载数据
        myAdapter = new MyAdapter(this,arr);
        recyclerView.setAdapter(myAdapter);
    }

    private void initData() {
        arr[0] = R.drawable.girl_01;
        arr[1] = R.drawable.girl_02;
        arr[2] = R.drawable.girl_03;
        arr[3] = R.drawable.girl_04;
        arr[4] = R.drawable.girl_05;
        arr[5] = R.drawable.girl_06;
        arr[6] = R.drawable.girl_07;
        arr[7] = R.drawable.girl_08;
        arr[8] = R.drawable.girl_09;
        arr[9] = R.drawable.girl_10;
        arr[10] = R.drawable.girl_11;
        arr[11] = R.drawable.girl_12;
    }
}

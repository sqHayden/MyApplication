package com.idx.chatdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ActivityDemo extends AppCompatActivity {

    private TabLayout tabLayout;
    private String[] strings = {"标签1","标签2","标签3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        tabLayout = findViewById(R.id.tab_layout1);
        //设置布局填充
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //设置固定
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //内容填充
        // 添加多个tab
        for (int i = 0; i < strings.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(strings[i]);
            tabLayout.addTab(tab);
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getApplicationContext(),tab.getText()+"被选中了",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
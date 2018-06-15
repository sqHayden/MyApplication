package com.idx.chatdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import fragment.ChatFragment;
import fragment.ContractFragment;
import fragment.FindFragment;
import fragment.MeFragment;

public class MainActivity extends AppCompatActivity {
    //底部菜单栏布局
    private TabLayout mTabLayout;
    //ViewPager对象
    private ViewPager mViewPager;
    //fragment集合
    private List<Fragment> fragmentList;
    //标题集合
    private String[] strings = {"微信","通讯录","发现","我"};
    //图片集合
    private int[] img_Id = {R.drawable.tab_chat_selector,R.drawable.tab_contract_selector,
            R.drawable.tab_find_selector,R.drawable.tab_me_selector};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    /**
     * 添加Fragment
     */
    private void initData() {
        if(fragmentList==null){
            fragmentList= new ArrayList<>();
        }
        fragmentList.add(new ChatFragment());
        fragmentList.add(new ContractFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new MeFragment());
    }

    private void initViews() {
        //得到tab控件
        mTabLayout = (TabLayout)findViewById(R.id.tab_layout);
        //设置布局填充
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //设置固定
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //隐藏底部横线
        mTabLayout.setSelectedTabIndicatorHeight(0);

        //得到viewPager
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        //设置Adapter
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        //关联Tab
        mTabLayout.setupWithViewPager(mViewPager);

        //设置Item项的参数
        for(int i=0;i<strings.length;i++){
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                //设置自定义的标题
                View view = tab.setCustomView(R.layout.tab_custom).getCustomView();
                if(view!=null) {
                    TextView textView = view.findViewById(R.id.tab_text);
                    textView.setText(strings[i]);
                    ImageView imageView = view.findViewById(R.id.tab_img);
                    imageView.setImageResource(img_Id[i]);
                }
            }
        }
    }

    /**
     * 适配器
     * Created by lijuan on 2016/8/23.
     */
    public class MyAdapter extends FragmentPagerAdapter {

        /**
         * 构造方法
         */
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * 返回显示的Fragment总数
         */
        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * 返回要显示的Fragment的某个实例
         */
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        /**
         * 返回每个Tab的标题
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
}
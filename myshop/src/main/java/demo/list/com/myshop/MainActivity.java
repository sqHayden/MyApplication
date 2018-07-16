package demo.list.com.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import com.idx.aidldemo.bean.AdapterSell;
import com.idx.aidldemo.bean.order.OrderDao;
import com.idx.aidldemo.bean.sell.SellDao;
import com.idx.aidldemo.bean.user.User;
import com.idx.aidldemo.bean.user.UserDao;
import utils.DaoManger;

/**
 * Created by hayden on 18-5-30.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    //管理类
    private UserDao mUserDao;
    private OrderDao mOrderDao;
    private SellDao mSellDao;
    //数据库管理类
    private DaoManger mDaoManger;
    //已登录用户
    private User loginUser;
    private Button naviLogin;
    private TextView naviName;
    //主界面视图
    private GridView gridView;
    //存储数据集
    private List<AdapterSell> adapterSellList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据库
        initDataBase();
        //初始化数据
        initData();
        //初始化视图
        initView();
        //设置属性
        myAdapter = new MyAdapter(getApplicationContext(), adapterSellList);
        gridView.setAdapter(myAdapter);
    }

    /**
     * 数据库创建
     */
    private void initDataBase() {
        //获取数据库管理对象
        mDaoManger = DaoManger.getInstance();
        //传递
        mDaoManger.init(getApplicationContext());
        //获取操作对象
        mUserDao = mDaoManger.getDaoSession().getUserDao();
        //获取订单对象
        mOrderDao = mDaoManger.getDaoSession().getOrderDao();
        //获取商品对象
        mSellDao = mDaoManger.getDaoSession().getSellDao();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        adapterSellList = new ArrayList<>();
        adapterSellList.add(new AdapterSell(R.mipmap.lajiao,"辣椒",12.5));
        adapterSellList.add(new AdapterSell(R.mipmap.baicai,"白菜",11.4));
        adapterSellList.add(new AdapterSell(R.mipmap.qiezi,"茄子",10.6));
        adapterSellList.add(new AdapterSell(R.mipmap.pinguo,"苹果",6.5));
        adapterSellList.add(new AdapterSell(R.mipmap.xigua,"西瓜",30.2));
        adapterSellList.add(new AdapterSell(R.mipmap.xiangjiao,"香蕉",11.3));
        adapterSellList.add(new AdapterSell(R.mipmap.shaoji,"烧鸡",50.8));
        adapterSellList.add(new AdapterSell(R.mipmap.shaoya,"烧鸭",40.2));
        adapterSellList.add(new AdapterSell(R.mipmap.shaoe,"烧鹅",30.1));
        adapterSellList.add(new AdapterSell(R.mipmap.mifan,"米饭",2));
        adapterSellList.add(new AdapterSell(R.mipmap.miantiao,"面条",10));
        adapterSellList.add(new AdapterSell(R.mipmap.mantou,"馒头",5));
    }

    /**
     * 设置视图
     */
    private void initView() {
        //获取Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //获取主界面视图
        gridView = findViewById(R.id.sells);
//        获取总视图
        mDrawerLayout = findViewById(R.id.drawer_layout);
        //获取侧滑栏
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我是ToolBar");
        if(actionBar!=null){
            //设置HomeAsUp控件显示
            actionBar.setDisplayHomeAsUpEnabled(true);
            //变更HomeAsUp控件的图片
            actionBar.setHomeAsUpIndicator(R.mipmap.toleft);
        }
        //设置菜单选中监听器，关闭侧滑栏
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_order:
                        //查询数据库看状态
                        User user = mUserDao.queryBuilder().where(UserDao.Properties.State.eq(true)).unique();
                        if(user!=null){

                        }else{
                            //去登录界面
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.nav_identity:
                        Toast.makeText(getApplicationContext(), "这是个人信息被点击了", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_setting:
                        Toast.makeText(getApplicationContext(), "这是设置被点击了", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        View view = navView.inflateHeaderView(R.layout.nav_header);
        //获取登录控件
        naviLogin = view.findViewById(R.id.nav_login);
        //获取姓名控件
        naviName = view.findViewById(R.id.head_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //做校验
        loginUser = mUserDao.queryBuilder().where(UserDao.Properties.State.eq(true)).unique();
        if(loginUser!=null){
            Log.i(TAG, "onResume: "+loginUser.toString());
            if(naviLogin.getVisibility()==View.VISIBLE){
                naviLogin.setVisibility(View.GONE);
            }
            naviName.setText(loginUser.getName());
            if(naviName.getVisibility()==View.GONE){
                naviName.setVisibility(View.VISIBLE);
            }
            //将用户信息放在Adapter中
            myAdapter.setLoginUser(loginUser);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

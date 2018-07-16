package com.idx.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.idx.aidl.bean.User;

public class MainActivity extends AppCompatActivity {


    private EditText account,password;
    private IService myService;
    private static final String TAG = "MainActivity";

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: ");
            myService = (IService)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    /**
     * 视图加载
     */
    private void initView() {
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
    }

    //按钮服务内部添加人员
    public void addUser(View view){
        try {
            //获取用户名
            String username = account.getText().toString().trim();
            //获取密码
            String pass = password.getText().toString().trim();
            if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(pass)){
                //处理密码
                int password = Integer.parseInt(pass);
                //赋值
                User user = new User(username,password);
                myService.addUser(user);
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    //获取服务中添加的人员
    public void getUser(View view) {
        try {
            myService.getUser();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}

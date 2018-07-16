package service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import shaoqi.myapplication.R;

/**
 * Created by hayden on 18-3-28.
 */

public class ServiceActivity extends AppCompatActivity{

    private static final String TAG = "Main1Activity";
    private Button start;
    private Intent serviceIntent;
    private MyService.MyBinder binder;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接了
            binder = (MyService.MyBinder)service;
            binder.setData();


            //
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开启服务
        serviceIntent = new Intent(this,MyService.class);
//        startService(serviceIntent);

        start = findViewById(R.id.start);
        //控制
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭
                stopService(serviceIntent);
                unbindService(conn);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //绑定服务
        bindService(serviceIntent,conn,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}

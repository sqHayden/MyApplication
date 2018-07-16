package com.idx.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import com.idx.aidl.bean.User;

/**
 * Created by hayden on 18-6-19.
 */

public class MyService extends Service {

    private static final String TAG = "MyService";
    private User user;

    @Override
    public IBinder onBind(Intent intent) {
        return iService;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }


    private User getServiceUser(){
        return user;
    }

    private final IService.Stub iService = new IService.Stub() {
        @Override
        public void addUser(User userData) throws RemoteException {
            //保存别人添加的对象
            user = userData;
            //弹个提示
            Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public User getUser() throws RemoteException {
            Log.i(TAG, "getUser: 这里走了");
            User user = getServiceUser();
//            Toast.makeText(getApplicationContext(),user.toString(),Toast.LENGTH_SHORT).show();
            //获取添加的对象
            return getServiceUser();
        }
    };
}

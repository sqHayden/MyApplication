package com.demo.clock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建一个Dialog
        MyDialog myDialog = new MyDialog(getApplicationContext());
        myDialog.getfewrfewr(getApplicationContext());
    }
}

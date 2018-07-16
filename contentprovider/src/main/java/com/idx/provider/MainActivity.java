package com.idx.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button check,insert;
    private TextView content;
    private static final String TAG = "MainActivity";
    private Uri uri = Uri.parse("content://sms");;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //查询按钮
        check = findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //操作查询短信
                checkMessage();
            }
        });
        //插入按钮
        insert = findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //插入一条短信
                insertMessage();
            }
        });
        content = findViewById(R.id.showMessage);
    }

    /**
     * 短信获取相关操作
     */
    private void checkMessage() {
        //查询操作
        Cursor cursor = getContentResolver().query(uri,new String[]{"address","body"},null,null,null);
        if(cursor!=null){
            StringBuilder sb = new StringBuilder();
            int count = 0;
            while (cursor.moveToNext()){
                count++;
                //获取短信发件人
                String address = cursor.getString(0);
                //获取短信内容
                String body = cursor.getString(1);
                sb.append(""+count+". "+"发件人是："+address+"\n"+"内容是："+body+"\n"+"\n");
            }
            content.setText(sb.toString());
            cursor.close();
        }
    }

    /**
     * 短信插入相关操作
     */
    private void insertMessage() {
        //插入操作
        ContentValues contentValues = new ContentValues();
        //插入第一条
        contentValues.put("address","110");
        contentValues.put("body","您好，请配合我们走一趟");

        //插入第二条
        contentValues.put("address","10000");
        contentValues.put("body","您可以不说话，但您所说的一切将作为呈堂证供");


        Uri data = getContentResolver().insert(uri,contentValues);
        Toast.makeText(getApplicationContext(),"插入成功"+data,Toast.LENGTH_SHORT).show();
    }
}

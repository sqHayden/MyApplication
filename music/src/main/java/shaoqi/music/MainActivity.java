package shaoqi.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import shaoqi.myapplication.R;

/**
 * Created by hayden on 18-3-28.
 */

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "Main1Activity";
    private Button start;

    public static A a;
    public static B b;
    private String[] str = new String[10];
    public A aa;
    public B bb;

//    private String str1 = "123";
//    private String str2 = "1"+"23";
//    private String str3 = "123";
//    private String str4 = new String("123");
//    private String str5 = new String("123");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
//        alertDialog.show();
//        A a = new B();
//        Log.i(TAG, "onCreate: "+a.name);

//        Log.i(TAG, "onCreate: "+str.length);
//        str[0] = "1";
//        Log.i(TAG, "onCreate: "+str.length);
//        b = new B();
//        bb = new B();
//        Log.i(TAG, "onCreate: "+"方法被执行");
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main1Activity.class);
//                intent.pu
                startActivity(intent);
            }
        });
//        Log.i(TAG, "str1==str2   "+(str1==str2));  true  //拼起来直接去常量池中找
//        Log.i(TAG, "str1==str3   "+(str1==str3));  true  //直接去常量池找
//        Log.i(TAG, "str1==str4   "+(str1==str4));  false  //s1代表常量池中地址，s4堆中地址
//        Log.i(TAG, "str1==str5   "+(str1==str5));  false  //s1代表常量池中地址，s5堆中地址
//        Log.i(TAG, "str2==str3   "+(str2==str3));  true  //拼起来直接去常量池中找
//        Log.i(TAG, "str2==str4   "+(str2==str4));  false  //s1代表常量池中地址，s4堆中地址
//        Log.i(TAG, "str2==str5   "+(str2==str5));  false
//        Log.i(TAG, "str3==str4   "+(str3==str4));  false
//        Log.i(TAG, "str3==str5   "+(str3==str5));  false
//        Log.i(TAG, "str14==str5   "+(str4==str5));  false
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: "+"方法被执行");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: "+"方法被执行");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: "+"方法被执行");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: "+"方法被执行");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: "+"方法被执行");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: "+"方法被执行");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: "+"方法被执行");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: "+"方法被执行");
    }
}

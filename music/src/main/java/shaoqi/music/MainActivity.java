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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: "+"方法被执行");
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main1Activity.class);
                startActivity(intent);
            }
        });
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

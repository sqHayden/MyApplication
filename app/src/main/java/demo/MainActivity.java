package demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import shaoqi.myapplication.R;
import shaoqi.myapplication.ScrollActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
//    @BindView(R.id.cpu_Minusage)
//    TextView mCpuMinusage;
//    @BindView(R.id.cpu_Maxusage) TextView mCpuMaxusage;
//    @BindView(R.id.cpu_Avgusage) TextView mCpuAvgusage;
//    @BindView(R.id.tv_cpusage) TextView mCpuUsage;
//    @BindView(R.id.tv_maxFreq) TextView mMaxFreq;
//    @BindView(R.id.tv_minFreq) TextView mMinFreq;
//    @BindView(R.id.tv_nowFreq) TextView mNowFreq;
//    @BindView(R.id.tv_cpuTemp) TextView mCpuTemp;
     @BindView(R.id.start)  Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, ScrollActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }
}

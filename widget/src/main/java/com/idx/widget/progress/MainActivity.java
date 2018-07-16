package com.idx.widget.progress;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import com.idx.widget.R;
import com.idx.widget.progress.widget.CustomCircleProgressBar;

public class MainActivity extends Activity {

    private CustomCircleProgressBar progressBar;
    private SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        //自定义圆形进度条
        progressBar = (CustomCircleProgressBar) findViewById(R.id.progressbar);
        //手动SeekBar
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //拿到进度
                progressBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
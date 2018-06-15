package shaoqi.music;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;

import shaoqi.myapplication.R;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{

    private static final String TAG = "MainActivity";
    private Button play,pause,stop;
    //音乐播放类
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean flag;
    //进度条
    private SeekBar mSeekBar;
    //时间
    private TextView mText;
    private static String time;
    //线程
    private Thread thread1, thread2;
    public static final int MSG_ONE = 1;

    //提示主线程UI进行时间更新操作
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //通过消息的内容msg.what分别更新ui
            switch (msg.what) {
                case MSG_ONE:
                    try {
                        int musicTime = 0;
                        //显示在textview上
                        if (mediaPlayer != null) {
                            try {
                                musicTime = mediaPlayer.getCurrentPosition() / 1000;
                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }
                            DecimalFormat df = new DecimalFormat("00");
                            int minute = musicTime / 60;
                            String minuteStr = df.format(minute);
                            int seconds = musicTime % 60;
                            String secondsStr = df.format(seconds);
                            String show = minuteStr + ":" + secondsStr;
                            mText.setText(show + "/" + time);
                            break;
                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        //播放按钮
        play = (Button) findViewById(R.id.play);
        //暂停按钮
        pause = (Button) findViewById(R.id.pause);
        //结束按钮
        stop = (Button) findViewById(R.id.stop);
        //进度条
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(this);
        //时间条
        mText = (TextView) findViewById(R.id.time_bar);
        //注册
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        //初始化音乐资源
        initMediaPlayer();
    }

    //资源初始化
    private void initMediaPlayer() {
        flag = true;
        //创建Uri对象
        Uri uri = Uri.parse("http://www.tingge123.com/mp3/2017-09-27/1506496866.mp3");
        try {
            mediaPlayer.setDataSource(this,uri);
            //准备
            mediaPlayer.prepare();
            Log.i(TAG, "initMediaPlayer: 初始化完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                playMusic();
                break;
            case R.id.pause:
                pauseMusic();
                break;
            case R.id.stop:
                stopMusic();
                break;
            default:
        }
    }

    //播放方法
    private void playMusic() {
        if(!mediaPlayer.isPlaying()){
            Log.i(TAG, "playMusic: 进入播放方法");
            //开始播放
            mediaPlayer.start();
            // 设置seekbar的最大值
            mSeekBar.setMax(mediaPlayer.getDuration());
            // 创建进度条线程
            thread1 = new Thread(new SeekBarThread());
            //创建时间线程
            thread2 = new Thread(new TimeThread());
            // 启动线程
            thread1.start();
            thread2.start();
            Log.d("进入音乐播放并启动线程", "Test()...");
            //定义总时间
            int time_all = mediaPlayer.getDuration();
            int musicTime = time_all / 1000;
            DecimalFormat df = new DecimalFormat("00");
            int minute = musicTime / 60;
            String minuteStr = df.format(minute);
            int seconds = musicTime % 60;
            String secondsStr = df.format(seconds);
            time = minuteStr + ":" + secondsStr;
        }
    }
    //暂停方法
    private void pauseMusic(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
    //结束
    private void stopMusic(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.reset();
            initMediaPlayer();
        }
    }

    //资源回收
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int musicTime = 0;
        try {
           musicTime  = progress / 1000;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("00");
        int minute = musicTime / 60;
        String minuteStr = df.format(minute);
        int seconds = musicTime % 60;
        String secondsStr = df.format(seconds);
        String show = minuteStr + ":" + secondsStr;
        mText.setText(show + "/" + time);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mediaPlayer.seekTo(seekBar.getProgress());

    }

    // 自定义的滑动条线程
    private class SeekBarThread implements Runnable {
        @Override
        public void run() {
            try {
                while (mediaPlayer != null && mediaPlayer.isPlaying() && flag) {
                    try {
                        // 将SeekBar位置设置到当前播放位置
                        mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                        // 每100毫秒更新一次位置
                        Thread.sleep(100);
                    }catch (NullPointerException e){
                        e.printStackTrace();
                        flag = false;
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                flag = false;
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }

    //自定义的时间线程
    private class TimeThread implements Runnable {
        @Override
        public void run() {
            while (mediaPlayer != null && flag) {
                try {
                    if (mediaPlayer.isPlaying() & flag) {
                        // 每1秒更新一次时间
                        Thread.sleep(1000);
                        Message msg = new Message();
                        msg.what = MSG_ONE;
                        //发送
                        if (handler!=null) {
                            handler.sendMessage(msg);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    flag = false;
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.idx.picturecache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String path = "http://ww1.sinaimg.cn/large/0065oQSqly1fs1vq7vlsoj30k80q2ae5.jpg";
    private ImageView imageView;
    private Bitmap girlBitmap;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件
        imageView = findViewById(R.id.girl_img);
        //请求
        requestNetWork();
    }

    /**
     * 请求网络获取图片
     */
    private void requestNetWork() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //请求方式
                    httpURLConnection.setRequestMethod("GET");
                    //超时时间
                    httpURLConnection.setConnectTimeout(8000);
                    //读取超时
                    httpURLConnection.setReadTimeout(8000);
                    //获取流
                    InputStream in = httpURLConnection.getInputStream();
                    //创建相关的对象
                    if(in!=null){
                        createBitMap(in);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 创建BitMap
     */
    private void createBitMap(final InputStream input) {
//        FileInputStream fileInputStream = new FileInputStream()
//        FileDescriptor fd = input.get
        //设置Options
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //假解析
        BitmapFactory.decodeStream(input,null,options);
        //获取缩放量
        options.inSampleSize = 2;
        //设置真解析
        options.inJustDecodeBounds = false;
        final Bitmap bitmap = BitmapFactory.decodeStream(input,null,options);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    /**
     * 计算缩放比例
     * @param options 参数获取对象
     * @param reqWidth 要求的宽度
     * @param reqHeight 要求的高度
     */
    private int calculateSize(BitmapFactory.Options options,int reqWidth,int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        Log.i(TAG, "calculateSize: width:"+width+",height:"+height);
        int size = 1;
        if(width>reqWidth || height>reqHeight){
            int halfWidth = width/2;
            int halfHeight = height/2;
            while ((halfWidth/size)>reqWidth || (halfHeight/size)>reqHeight){
                size = size * 2;
            }
        }
        Log.i(TAG, "calculateSize: size:"+size);
        return size;
    }
}

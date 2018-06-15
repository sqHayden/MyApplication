package screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hayden on 18-5-23.
 */

public class HttpConnectDemo extends AppCompatActivity {

    public static final String APPKEY = "d5c3c1cca232fcd5";// 你的appkey
    public static final String REQUEST_URL = "http://api.jisuapi.com/cidian/word";
    public static final String word = "好像";// utf-8
    private String urlNet;
    private static final String TAG = "HttpConnectDemo";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            urlNet = REQUEST_URL + "?appkey=" + APPKEY + "&word=" + URLEncoder.encode(word, "utf-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //发送数据
        sendDataToNet();
    }

    /**
     * 发送数据
     */
    private void sendDataToNet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //定义
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                try {
                    //定义请求源
                    URL url = new URL(urlNet);
                    //获取connection对象
                    urlConnection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    urlConnection.setRequestMethod("GET");
                    //设置超时时间(8s)
                    urlConnection.setConnectTimeout(8000);
                    InputStream ios = urlConnection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(ios));
                    StringBuilder sb = new StringBuilder();
                    String str;
                    while ((str=reader.readLine())!=null){
                        sb.append(str);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

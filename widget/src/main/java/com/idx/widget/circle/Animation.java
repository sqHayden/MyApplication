package com.idx.widget.circle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;

public class Animation extends AppCompatActivity {

    private CustomView mCustomView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        if(wm!=null) {
            mCustomView=new CustomView(this, wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight());
            setContentView(mCustomView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCustomView.stopDrawing();
    }
}

package shaoqi.music;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import shaoqi.myapplication.R;

/**
 * Created by hayden on 18-4-12.
 */

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.ll_search)
    LinearLayout mSearchLayout;
    @BindView(R.id.click)
    Button click;
    private TransitionSet mSet;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        flag = true;
        //设置全屏透明状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
//            ViewCompat.setFitsSystemWindows(rootView, false);
//            rootView.setClipToPadding(true);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    expand();
                    flag = false;
                }else {
                    reduce();
                    flag = true;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void expand() {
        //设置伸展状态时的布局
        tvSearch.setHint("搜索喜欢的内容和朋友");
        ViewGroup.LayoutParams LayoutParams = mSearchLayout.getLayoutParams();
        LayoutParams.width = LayoutParams.MATCH_PARENT;
//        LayoutParams.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        mSearchLayout.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(mSearchLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void reduce() {
        //设置收缩状态时的布局
        tvSearch.setHint("搜索");
        ViewGroup.LayoutParams LayoutParams = mSearchLayout.getLayoutParams();
        LayoutParams.width = dip2px(100);
//        LayoutParams.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        mSearchLayout.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(mSearchLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void beginDelayedTransition(ViewGroup view) {
        mSet = new AutoTransition();
        mSet.setDuration(300);
        TransitionManager.beginDelayedTransition(view, mSet);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }
}

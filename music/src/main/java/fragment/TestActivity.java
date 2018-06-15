package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import shaoqi.myapplication.R;

/**
 * Created by hayden on 18-4-17.
 */

public class TestActivity extends AppCompatActivity implements CallBack{

    private FrameLayout contains;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment fragment;
    private Boolean flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        contains = (FrameLayout) findViewById(R.id.contains);
        manager = getSupportFragmentManager();
        flag = true;
    }

    @Override
    public void callFromA() {
        //动态替换并显示
        if(fragment==null){
            fragment = new BFragment();
            transaction = manager.beginTransaction();
            transaction.add(R.id.contains,fragment);
            transaction.commit();
        }
        if(flag){
            if(contains.getVisibility()!= View.VISIBLE){
                contains.setVisibility(View.VISIBLE);
            }
            flag = false;
        }else {
            if(contains.getVisibility()!= View.GONE){
                contains.setVisibility(View.GONE);
            }
            flag = true;
        }
    }

    @Override
    public void callFromB() {
        fragment = new CFragment();
        //替换为新的Fragment并且不销毁
        transaction = manager.beginTransaction();
        transaction.hide(getSupportFragmentManager().findFragmentById(R.id.contains));
        transaction.add(R.id.contains,fragment);
        transaction.commit();
    }
}

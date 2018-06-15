package com.idx.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.idx.widget.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import bean.Bean;

/**
 * Created by hayden on 18-6-11.
 */

public class MyDialog extends AlertDialog {

    private EditText mEditText;
    private ListView mListView;
    private ImageView clear;
    private List<Bean> beanList;
    private MyAdapter myAdapter;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mEditText = findViewById(R.id.edit);
        mListView = findViewById(R.id.list);
        clear = findViewById(R.id.clear);
        //设置
        //设置改变时属性
        final int inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
        mEditText.setInputType(inputType);
        //设置内容变化监听
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //填充listView
                initData();
                if(myAdapter==null) {
                    myAdapter = new MyAdapter(getContext(),beanList);
                    mListView.setAdapter(myAdapter);
                }else{
                    //通知
                    myAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void initData(){
        //封装一个Bean
        Bean bean = new Bean("邵氏餐厅"+count,"陕西省宝鸡市麟游县农业机械公司B栋",30.2);
        count++;
        if(beanList==null){
            beanList = new ArrayList<>();
        }else{
            beanList.clear();
        }
        beanList.add(bean);
    }

    protected MyDialog(Context context) {
        super(context);
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}

package com.demo.gank.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.gank.R;
import com.demo.gank.json.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hayden on 18-6-12.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Result> mResultList;
    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context, List<Result> list) {
        mContext = context;
        mResultList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 如果是底部刷新View，则加载底部刷新View布局，并创建底部刷新View对应的ViewHolder
        if (viewType == -1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.refresh_footer, parent, false);
            return new FooterHolder(view);
        }
        // 如果是其他类型的View，则按照正常流程创建普通的ViewHolder
        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
            return new MyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderBack, int position) {
        if (!(holderBack instanceof FooterHolder)) {
            MyHolder holder = (MyHolder) holderBack;
            //使用Picasso来加载网络图片赋值给Adapter中对应控件
            Picasso.with(mContext).load(mResultList.get(position).getUrl()).placeholder(R.mipmap.avatar).into(holder.imageView);
        }
    }


    @Override
    public int getItemCount() {
        return mResultList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mResultList.size()) {
            return super.getItemViewType(position);
        } else {
            return -1;
        }
    }

    //定义正常的View
    private class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    // 定义底部刷新View对应的ViewHolder
    private class FooterHolder extends RecyclerView.ViewHolder {
        FooterHolder(View view){
            super(view);
        }
    }
}

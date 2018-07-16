package com.idx.widget.waterLayout;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.idx.widget.R;

/**
 * Created by hayden on 18-6-12.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int[] arrys;
    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context, int[] arr) {
        mContext = context;
        arrys = arr;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderBack, int position) {
        MyHolder holder = (MyHolder) holderBack;
        //使用Picasso来加载网络图片赋值给Adapter中对应控件
//      Picasso.with(mContext).load(mResultList.get(position).getUrl()).placeholder(R.mipmap.avatar).into(holder.imageView);
        holder.imageView.setImageResource(arrys[position]);
        holder.imageView.getLayoutParams().height = (int) (Math.random() * 100) + 400; //
    }


    @Override
    public int getItemCount() {
        return arrys.length;
    }

    //定义正常的View
    private class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

}

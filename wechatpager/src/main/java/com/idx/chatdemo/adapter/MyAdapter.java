package com.idx.chatdemo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.idx.chatdemo.R;

/**
 * Created by hayden on 18-6-12.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,true);
            return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderBack, int position) {
            MyHolder holder = (MyHolder) holderBack;
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
    }


    @Override
    public int getItemCount() {
        return 10;
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

package shaoqi.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import shaoqi.myapplication.R;

/**
 * Created by hayden on 18-4-20.
 */

public class ListViewActivity extends AppCompatActivity{

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new MyAdapter());
    }


    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder;
            Log.i("TestDemo", "position"+position+"被刷出来");
            if (convertView == null) {
                Log.i("TestDemo", "convertView 是空的");
                holder = new Holder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_list_item,parent,false);
                Button click = (Button) convertView.findViewById(R.id.click);
                holder.mButton = click;
                click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (Integer)holder.mButton.getTag();
                        Toast.makeText(getApplicationContext(),"按钮"+i+"被点击",Toast.LENGTH_SHORT).show();
                    }
                });
                holder.mTittle = (TextView) convertView.findViewById(R.id.title);
                holder.mAddress = (TextView) convertView.findViewById(R.id.content);
                convertView.setTag(holder);
            } else{
                Log.i("TestDemo", "convertView 已经不是空的了");
                holder = (Holder)convertView.getTag();
                holder.mTittle.setText("标题"+String.valueOf(position));
                holder.mAddress.setText("内容"+String.valueOf(position));
            }
            holder.mButton.setTag(position);
            return convertView;
        }

        class Holder {
            TextView mTittle;
            TextView mAddress;
            Button mButton;
        }
    }
}

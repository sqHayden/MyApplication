package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.idx.widget.R;

import java.util.List;

import bean.Bean;

/**
 * Created by hayden on 18-6-11.
 */

public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private List<Bean> mBeanList;

    public MyAdapter(Context context, List<Bean> beanList) {
        mContext = context;
        mBeanList = beanList;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return mBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.list_item,null);
            holder.name = convertView.findViewById(R.id.poi_name);
            holder.address = convertView.findViewById(R.id.poi_address);
            holder.distance = convertView.findViewById(R.id.poi_distance);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        //赋值
        holder.name.setText(""+mBeanList.get(0).getName());
        holder.address.setText(""+mBeanList.get(0).getAddress());
        holder.distance.setText(""+mBeanList.get(0).getDistance());
        return convertView;
    }

    class ViewHolder{
        private TextView name;
        private TextView address;
        private TextView distance;
    }
}

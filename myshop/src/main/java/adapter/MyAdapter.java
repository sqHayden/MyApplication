package adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import bean.AdapterSell;
import bean.order.DaoSession;
import bean.order.Order;
import bean.order.OrderDao;
import bean.sell.Sell;
import bean.sell.SellDao;
import bean.user.User;
import bean.user.UserDao;
import demo.list.com.myshop.R;
import utils.DaoManger;
import utils.RoundImageUtils;

/**
 * Created by hayden on 18-5-31.
 */

public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private List<AdapterSell> mAdapterSellList;
    private DaoManger mDaoManager;
    private DaoSession mDaoSession;
    private UserDao mUserDao;
    private OrderDao mOrderDao;
    private SellDao mSellDao;
    private User loginUser;
    private Order userOrder;
    private int[] array;
    private double orderCost = 0;

    public MyAdapter(Context context, List<AdapterSell> adapterSells) {
        mContext = context;
        mAdapterSellList = adapterSells;
        mDaoManager = DaoManger.getInstance();
        mDaoManager.init(mContext);
        mDaoSession = mDaoManager.getDaoSession();
        mUserDao = mDaoSession.getUserDao();
        mOrderDao = mDaoSession.getOrderDao();
        mSellDao = mDaoSession.getSellDao();
        array = new int[12];
    }

    public void setLoginUser(User login){
        loginUser = login;
    }

    @Override
    public int getCount() {
        return mAdapterSellList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAdapterSellList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.griditem, null);
        //图片
        ImageView imageView = view.findViewById(R.id.sell_img);
        //转为圆角图
        Glide.with(mContext).load(mAdapterSellList.get(position).getSell_img())
                .transform(new RoundImageUtils(mContext, 10))
                .into(imageView);
        //名称
        TextView name = view.findViewById(R.id.sell_name);
        name.setText(mAdapterSellList.get(position).getSell_name());
        //价格
        TextView price = view.findViewById(R.id.sell_price);
        price.setText("" + mAdapterSellList.get(position).getSell_price());
        //加号
        ImageView add = view.findViewById(R.id.sell_add);
        //减号
        final ImageView reduce = view.findViewById(R.id.sell_reduce);
        //数量
        final TextView count = view.findViewById(R.id.sell_count);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reduce.getVisibility()==View.GONE){
                    //显示减号及数量
                    reduce.setVisibility(View.VISIBLE);
                    count.setVisibility(View.VISIBLE);
                }
                //生成一个空订单
                if (userOrder == null&& loginUser!=null) {
                    userOrder = new Order(null,100,0,loginUser.getId());
                    //创建出来
                    mOrderDao.insert(userOrder);
                }
                //商品+1
                array[position]++;
                //修改数量的视图
                count.setText(""+array[position]);
                //看数据库有木有这个商品存在
                Sell querySell = mSellDao.queryBuilder().where(SellDao.Properties.Sell_name.eq(mAdapterSellList.get(position).getSell_name())).unique();
                if(querySell!=null){
                    //有就修改
                    querySell.setSell_count(array[position]);
                    mSellDao.update(querySell);
                }else {
                    //没有就创建
                    //名字，数量，价格，订单
                    Sell sell = new Sell(null, mAdapterSellList.get(position).getSell_name(), array[position],
                            mAdapterSellList.get(position).getSell_price(), userOrder.getOrder_id());
                    //存入数据库
                    mSellDao.insert(sell);
                }
                orderCost = orderCost + array[position]*mAdapterSellList.get(position).getSell_price();
                //修改订单花费
                userOrder.setOrder_cost(orderCost);
                mOrderDao.update(userOrder);
            }
        });
        //减号
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //商品-1
                array[position]--;
                if(array[position]==0){
                    //隐藏减号及数量
                    reduce.setVisibility(View.GONE);
                    count.setVisibility(View.GONE);
                }
                //显示数量
                count.setText(""+array[position]);
                //通过名字去改
                Sell sell = mSellDao.queryBuilder().where(SellDao.Properties.Sell_name.eq(mAdapterSellList.get(position).getSell_name())).unique();
                sell.setSell_count(array[position]);
                mSellDao.update(sell);
                //查看
                List<Sell> sells = mSellDao.loadAll();
                for(Sell sell1 : sells){
                    Log.i("justAA","商品名字："+sell1.getSell_name()+",商品数量："+sell1.getSell_count());
                }
            }
        });
        return view;
    }
}

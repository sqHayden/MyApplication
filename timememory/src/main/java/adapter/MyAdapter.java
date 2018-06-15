package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.idx.game.R;
import java.util.List;
import bean.Card;
import utils.AnimUtil;
import utils.RoundImageUtils;

/**
 * Created by hayden on 18-5-22.
 */

public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private List<Card> cardList;
    private boolean isFirst;
    private String cardName = "";
    private ImageView cardImageView;
    private static final String TAG = "MyAdapter";

    public MyAdapter(Context mContext, List<Card> list, boolean isFirst) {
        this.mContext = mContext;
        cardList = list;
        this.isFirst = isFirst;

    }

    public void setBoolean(boolean isFirst){
        this.isFirst = isFirst;
    }

    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.griditem,null);
        //旧控件
        final ImageView oldImg = view.findViewById(R.id.old_img);
        Log.i("BlueTest","位置："+position+","+"old控件的地址是: "+oldImg.toString());
        //统一为一种
        Glide.with(mContext).load(R.mipmap.girls_bg)
             .transform(new RoundImageUtils(mContext, 10))
             .into(oldImg);
        //新控件
        final ImageView newImg = view.findViewById(R.id.new_img);
        Log.i("BlueTest","位置："+position+","+ "new控件的地址是: "+newImg.toString());
        //分别去配图
        Glide.with(mContext).load(cardList.get(position).getCardId())
                .transform(new RoundImageUtils(mContext, 10))
                .into(newImg);
        //动画显示新的
        if(isFirst) {
            AnimUtil.FlipAnimatorXViewShow(oldImg, newImg, 500,null);
        }
        return view;
    }
}

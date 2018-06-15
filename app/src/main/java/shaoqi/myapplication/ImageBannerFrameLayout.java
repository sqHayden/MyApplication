package shaoqi.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by hayden on 17-11-14.
 */

public class ImageBannerFrameLayout extends FrameLayout implements ImageBarnnerViewGroup.ImageButtonListener,ImageBarnnerViewGroup.ImageListener{

    //图片轮播核心布局类
    private ImageBarnnerViewGroup groupView;
    //底部线性布局类
    private LinearLayout linearLayout;

    public ImageBannerFrameLayout(Context context) {
        super(context);
        initImageBarnnerViewGroup();
        initDotLinearlayout();
    }

    public ImageBannerFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initImageBarnnerViewGroup();
        initDotLinearlayout();
    }

    public ImageBannerFrameLayout(Context context, AttributeSet attrs,  int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBarnnerViewGroup();
        initDotLinearlayout();
    }

    //初始化图片轮播功能的核心类
    private void initImageBarnnerViewGroup(){
        groupView = new ImageBarnnerViewGroup(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        groupView.setLayoutParams(layoutParams);
        //给监听圆点状态注册监听器
        groupView.setImageButtonListener(this);
        //给用户点击图片事件主注册监听器
        groupView.setImageListener(this);
        addView(groupView);
    }

    //初始化底部圆点的布局
    private void initDotLinearlayout(){
        linearLayout = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,40);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.RED);
        addView(linearLayout);
        LayoutParams lin_layoutParams = (LayoutParams) linearLayout.getLayoutParams();
        lin_layoutParams.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(lin_layoutParams);
        linearLayout.setAlpha(0.5f);
    }

    //接收资源图片对象
    public void addBitMaps(List<Bitmap> list){
        for(int i=0;i<list.size();i++){
            Bitmap bitmap = changeBitmapSize(list.get(i));
            Log.d(new Integer(bitmap.getWidth()).toString(),new Integer(bitmap.getHeight()).toString());
            addBitMapToImageBannerViewGroup(bitmap);
            //添加圆点
            addDotToLinearlayout();
        }
    }

    //修改图片尺寸
    private Bitmap changeBitmapSize(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.e("width","width:"+width);
        Log.e("height","height:"+height);
        // 通过Resources获取
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        System.out.println("heigth2 : " + dm2.heightPixels);
        System.out.println("width2 : " + dm2.widthPixels);
        //设置想要的大小
        int newWidth=dm2.widthPixels;
        int newHeight=341;
        //计算压缩的比率
        float scaleWidth=((float)newWidth)/width;
        float scaleHeight=((float)newHeight)/height;
        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        //获取新的bitmap
        bitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        bitmap.getWidth();
        bitmap.getHeight();
        return bitmap;
    }

    //将资源对象传递给轮播对象
    private void addBitMapToImageBannerViewGroup(Bitmap bitmap){
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(bitmap);
        groupView.addView(imageView);
    }

    //添加圆点对象视图
    private void addDotToLinearlayout(){
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,5,5,5);
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.shape_uncheck);
        linearLayout.addView(imageView);
    }

    //图片改变时监听对底部圆点状态进行改变
    @Override
    public void changeButton(int index) {
        //获取圆点数量
        int count = linearLayout.getChildCount();
        for(int i=0;i<count;i++){
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if(index == i){
                imageView.setImageResource(R.drawable.shape_checked);
            }else{
                imageView.setImageResource(R.drawable.shape_uncheck);
            }
        }
    }

    //点击事件发生监听
    @Override
    public void clickImageIndex(int pos) {
        frameLayoutListener.clickImageIndex(pos);
    }

    private FrameLayoutListener frameLayoutListener;

    public FrameLayoutListener getFrameLayoutListener() {
        return frameLayoutListener;
    }

    public void setFrameLayoutListener(FrameLayoutListener frameLayoutListener) {
        this.frameLayoutListener = frameLayoutListener;
    }

    //通知给MainActivity进行点击事件监听的接口
    public interface FrameLayoutListener{
        void clickImageIndex(int pos);
    }
}

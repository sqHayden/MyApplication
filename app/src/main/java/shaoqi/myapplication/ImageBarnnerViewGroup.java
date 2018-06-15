package shaoqi.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 实现图片轮播的核心类
 */

public class ImageBarnnerViewGroup extends ViewGroup{

    //view_group子视图的总个数
    private int children;
    //子视图宽度
    private int childwidth;
    //子视图高度
    private int childheight;
    //第一次按下位置的横坐标、每一次移动中移动前的横坐标
    private int x;
    //每张图片的索引
    private int index=-1;
    //移动距离
    private int distance;
    //定时器
    private Timer timer = new Timer();
    //定时任务
    private TimerTask timerTask;
    //设置常量
    private static final int msg = 1;
    //设置自动轮播布尔控制
    private boolean start = true;
    //判断点击事件的布尔控制
    private boolean isClick;
    //消息队列
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1://开启轮播图
                    if(++index>=children){
                        index = 0;
                    }
                    //滚动图片
                    scrollTo(index*childwidth,0);
                    //圆点接口实现
                    imageButtonListener.changeButton(index);
            }
        }
    };

    /*
    * 必须实现构造方法
    * */
    public ImageBarnnerViewGroup(Context context) {
        super(context);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    //初始化方法
    public void initObj(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(start) {
                    Message message = new Message();
                    message.what = msg;
                    handler.sendMessage(message);
                }
            }
        };
        timer.schedule(timerTask,100,2000);
    }

    //图片点击事件接口相关
    private ImageListener imageListener;

    public void setImageListener(ImageListener imageListener) {
        this.imageListener = imageListener;
    }

    public ImageListener getImageListener() {
        return imageListener;
    }

    //定义图片点击事件接口信息
    public interface ImageListener{
        void clickImageIndex(int pos);
    }


    //轮播圆点通知接口相关
    private ImageButtonListener imageButtonListener;

    public ImageButtonListener getImageButtonListener() {
        return imageButtonListener;
    }

    public void setImageButtonListener(ImageButtonListener imageButtonListener) {
        this.imageButtonListener = imageButtonListener;
    }

    //定义轮播圆点选择通知接口
    public interface ImageButtonListener{
        void changeButton(int index);
    }


    //实现测量的方法
    /*ViewGroup的宽高由其子视图来决定
       1.求出子视图的个数
       2.测量子视图宽高
       3.求出父组件宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //求出子视图数量
        children = getChildCount();
        if(0==children){
            setMeasuredDimension(0,0);
        }else{
            //测量子视图宽高
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            //父组件的高度为第一个子视图的高度，宽度为子视图的宽度×字数图数量
            View view = getChildAt(0);
            childwidth = view.getMeasuredWidth();
            childheight = view.getMeasuredHeight();
            int width  = view.getMeasuredWidth()*children;
            setMeasuredDimension(width,childheight);
        }
    }

    /*
     * 必须实现布局onLayout方法
     * can 1 :当我们viewgroup布局位置发生改变时为true,没则为false
     * can :
     * */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int leftMargin = 0;
            for(int i=0;i<children;i++){
                //获取每个视图
                View view = getChildAt(i);
                view.layout(leftMargin,0,leftMargin+childwidth,childheight);
                leftMargin += childwidth;
            }
        }
    }

    /**
     * 事件的传递过程
     * 容器拦截方法，为true将会去处理此次的时间相应操作。
     * **/
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /*
     *事件具体的处理过程
     * 完成轮播图的轮播效果
     * **/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://表示用户按下瞬间
                start = false;
                isClick = true;
                x = (int) event.getX();
                Log.d("按下瞬间坐标：",new Integer(x).toString());
                break;
            case MotionEvent.ACTION_MOVE://表示用户按下后在屏幕上的移动过程
                int moveX = (int)event.getX();
                isClick = false;
                Log.d("按下移动完成时坐标：",new Integer(moveX).toString());
                distance = moveX - x;
                Log.d("移动距离：",new Integer(distance).toString());
                //右移
                if(distance<0){
                    if(index!=children-1){
                        scrollBy(-distance, 0);
                    }
                }else{//左移
                    if(index!=0){
                        scrollBy(-distance,0);
                    }
                }
                x = moveX;
                break;
            case MotionEvent.ACTION_UP://表示的是用户抬手瞬间
                start = true;
                int scrollX = getScrollX();
                Log.d("从按下到结束的移动距离(换图则+width)：", new Integer(scrollX).toString());
                //处理正向滑动
                if (distance < 0) {
                    index = (scrollX + (int) (0.75 * childwidth)) / childwidth;
                    if (index > children - 1) {//滑到了最右边
                        index = children - 1;
                    }
                } else {//处理左划
                    index = (scrollX + (int) (0.25 * childwidth)) / childwidth;
                }
                //是否是点击事件
                if(isClick){
                    imageListener.clickImageIndex(index);
                }else {
                    scrollTo(index * childwidth, 0);
                    //圆点接口实现
                    imageButtonListener.changeButton(index);
                }
                break;
            default:
                break;
        }
        //表示告诉容器出发时间的处理已经完成
        return true;
    }
}

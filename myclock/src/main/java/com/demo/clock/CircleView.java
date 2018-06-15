package com.demo.clock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hayden on 18-6-9.
 */

/* 用于绘制自定义View的具体内容
 * 具体的绘制工作在onDraw()内实现
 */
public class CircleView extends View {

    //设置头部画笔
    private Paint mPaint1;
    //设置器官画笔
    private Paint mPaint2;
    //全局背景色变量
    private int mColor;
    //控制变量
    private boolean mDrawing = false;
    //弧度控制变量
    private int num1 = -30;
    private int num2 = 100;
    //控侏逻辑变量
    private boolean isNotChange = true;

    //自定义View的四个构造函数
    //如果自定义View是在代码里New出来的，就调用第一个构造函数即可
    public CircleView(Context context){
        super(context);
        //在构造里初始化画笔
        initPaint();
    }

    //如果View是在.xml中声明的，就调用第二个构造函数
    //自定义属性值是从AttributeSet参数传过来的
    //重写该构造函数
    public CircleView(Context context, AttributeSet attrs){
//        super(context,attrs);
        this(context,attrs,0);
        //在这里初始化画笔
        initPaint();
        //启动界面线程，开始更新界面
        mDrawing = true;
        new Thread(mRunnable).start();
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (mDrawing) {
                try {
                    //更新眉毛的弧度
                    if(num1<80&&isNotChange){
                        num1++;
                        num2--;
                    }else if(num1>-50){
                        isNotChange = false;
                        num1--;
                        num2++;
                    }else{
                        isNotChange = true;
                    }
                    //通知系统更新界面
                    postInvalidate();
                    //更新频率
                    Thread.sleep(30);
                } catch (InterruptedException e) {

                }
            }
        }
    };

    //不会自动调用
    //一般是在第二个参数构造里主动调用
    //如View有style属性时
    public CircleView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载自定义的属性集合CircleView
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CircleView);
        //解析该集合中的circle_color属性
        //该集合的id为：R.styleable.CircleView_circle_color
        //将解析的属性传入画圆的画笔颜色变量当中（本质上是自定义画圆画笔的颜色）
        //第二个参数是默认设置颜色（即无指定circle_color情况下使用）
        mColor = typedArray.getColor(R.styleable.CircleView_circle_color,Color.RED);
        //解析后释放资源
        typedArray.recycle();
        //画笔加载
        initPaint();
    }

    //API21之后才使用
    //不会自动调用，一般是在第二个构造函数里主动调用
    //如View有style时
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //画笔的初始化
    private void initPaint(){
        //创建画笔
        mPaint1 = new Paint();
        mPaint2 = new Paint();
        //设置画笔颜色为蓝色
        mPaint1.setColor(mColor);
        mPaint2.setColor(Color.GREEN);
        //设置画笔宽度为10px
        mPaint1.setStrokeWidth(5f);
        mPaint2.setStrokeWidth(5f);
        //设置画笔模式为填充
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2.setStyle(Paint.Style.STROKE);
        //设置抗锯齿
        mPaint1.setAntiAlias(true);
        mPaint2.setAntiAlias(true);
    }

    //复写onDraw()进行绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取控件的宽高
        int width = getWidth();
        int height = getHeight();
        //设置圆的半径 = 宽，高最小值的1/2
        int r = Math.min(width,height)/2;
        //画出圆(蓝色)
        //圆心在控件的中央，半径=宽高较小值的1/2
        canvas.drawCircle(width/2,height/2,r,mPaint1);
        //绘制曲线
        Path mPath = new Path();
        //第一个眉毛
        mPath.moveTo((width/2)-80,getHeight()/3);
        mPath.quadTo((width/2)-50, getHeight()/4+num1,(width/2)-20, getHeight()/3);
        canvas.drawPath(mPath, mPaint2);
        //第二个眉毛
        mPath.moveTo((width/2)+20,getHeight()/3);
        mPath.quadTo((width/2)+50, getHeight()/4+num1,(width/2)+80, getHeight()/3);
        canvas.drawPath(mPath, mPaint2);
        //嘴巴
        mPath.moveTo((width/2)-50,getHeight()/2+50);
        mPath.quadTo(width/2, (getHeight()/2)+num2,(width/2)+50, getHeight()/2+50);
        canvas.drawPath(mPath, mPaint2);
    }

    public void stopDrawing(){
        mDrawing = false;
    }
}

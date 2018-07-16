package com.idx.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hayden on 18-6-20.
 */

public class MyView extends View {

    private Paint paint1,paint2;
    private static final String TAG = "MyView";
    
    public MyView(Context context) {
        super(context);
        initPaint();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 画笔
     */
    private void initPaint() {
        paint1 = new Paint();
        //颜色
        paint1.setColor(Color.RED);
        //边框
        paint1.setStrokeWidth(10f);
        //填充
        paint1.setStyle(Paint.Style.FILL);
        //抗锯齿
        paint1.setAntiAlias(true);

        paint2 = new Paint();
        paint2.setColor(Color.YELLOW);
        //边框
        paint2.setStrokeWidth(5f);
        paint2.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取模式
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取大小
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //指定默认宽高
        int width = MeasureSpec.makeMeasureSpec(200,MeasureSpec.EXACTLY);
        int height = MeasureSpec.makeMeasureSpec(200,MeasureSpec.EXACTLY);
        //处理情况Wrap_Content
        if(widthSpecMode==MeasureSpec.AT_MOST&&heightSpecMode==MeasureSpec.AT_MOST){
            //自己定义一个默认宽高
            setMeasuredDimension(width,height);
        }else if(widthSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(width,heightSpecSize);
        }else if(heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取Padding相关
        //左
        int leftPadding = getPaddingLeft();
        //右
        int rightPadding = getPaddingRight();
        //上
        int topPadding = getPaddingTop();
        //下
        int bottomPadding = getPaddingBottom();

        int width = getWidth()-leftPadding-rightPadding;
        int height = getHeight()-topPadding-bottomPadding;
        int r = Math.min(width,height)/2;
        //画圆
        canvas.drawCircle((getWidth()/2),(getHeight()/2),r,paint1);
        //画横线
//        canvas.drawLine(0,r,2*r,r,paint2);
        //画竖线
//        canvas.drawLine(r,0,r,2*r,paint2);
    }
}

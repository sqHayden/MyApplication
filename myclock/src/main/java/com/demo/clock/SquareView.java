package com.demo.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hayden on 18-6-9.
 */

//自定义正方形View
public class SquareView extends View{

    private Paint mPaint;

    public SquareView(Context context) {
        super(context);
        initPaint();
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    /**
     * 设置画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取正方形宽度的一半
        int half_square = getHeight()/2;
        //获取屏幕宽度/2
        int half_width = getWidth()/2;
        //起始绘制横坐标
        int start = half_width-half_square;
        canvas.drawRect(start,0,start+getHeight(),getHeight(),mPaint);
    }
}

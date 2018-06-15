package com.idx.widget.circle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by danny on 4/9/18.
 */

public class CustomView extends View {
    //界面主线程控制变量
    private boolean mDrawing = false;
    //存储当前已有球信息
    private ArrayList<Circle> mCircles;
    public int width,height;
    public static final double PI = 3.14159265;
    private Paint mPaint,mPaint1,mPaint2;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, int wid, int hei) {
        super(context);
        width = wid;
        height = hei-120;
        mCircles = new ArrayList<>();
        mCircles.add(new Circle(100,100,80,width,height));
        mCircles.add(new Circle(200,300,80,width,height));

        //画布画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        //颜色画笔1
        mPaint1 = new Paint();
        mPaint1.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint1.setColor(Color.YELLOW);
        mPaint1.setAntiAlias(true);
        //颜色画笔2
        mPaint2 = new Paint();
        mPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint2.setColor(Color.RED);
        mPaint2.setAntiAlias(true);

        //启动界面线程，开始更新界面
        mDrawing = true;
        new Thread(mRunnable).start();
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (mDrawing) {
                try {
                    //更新球位置
                    update();
                    //通知系统更新界面
                    postInvalidate();
                    //更新频率
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
            }
        }
    };

    public void stopDrawing() {
        mDrawing = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas上绘边框
        canvas.drawRect(0, 0, width, height, mPaint);
        //用画笔一去画
        Circle circle1 = mCircles.get(0);
        canvas.drawCircle(circle1.x,circle1.y,circle1.radius,mPaint1);
        //画笔2
        Circle circle2 = mCircles.get(1);
        canvas.drawCircle(circle2.x,circle2.y,circle1.radius,mPaint2);
    }

    public void update() {
        if (mCircles.size() > 1) {
            for (int i = 0; i < mCircles.size() - 1; i++) {
                //碰撞交换两球角度值
                for (int j = 0; j < mCircles.size(); j++) {
                    if (checkBumb(mCircles.get(i), mCircles.get(j))) {
                        mCircles.get(i).changeDerection(mCircles.get(j));
                    }
                }
            }
        }
        for (Circle circle : mCircles) {
            circle.updateLocate();
        }
    }

    private boolean checkBumb(Circle c1, Circle c2) {
        return (c1.x - c2.x) * (c1.x - c2.x) + (c1.y - c2.y) * (c1.y - c2.y) <= (c1.radius + c2.radius) * (c1.radius + c2.radius);
    }
}

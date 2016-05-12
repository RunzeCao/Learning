package com.example.myapplication.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 123 on 2016/5/12.
 */
public class MyView extends View {
    private Paint mPaint;
    /**
     * 圆环的半径
     */
    private int radius;

    public MyView(Context context) {
        super(context);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        //画线条
        mPaint.setStyle(Paint.Style.STROKE);
        //设置圆环的宽度
        // mPaint.setStrokeWidth(20);
        //开始设置圆环初始值
        radius = 30;
        mPaint.setStrokeWidth(radius / 3);

        //设置透明度,0完全透明，255完全不透明
        mPaint.setAlpha(255);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //1.改变圆环的参数：半径、宽、透明
            //半径
            radius += 5;
            //透明度
            int alpha = mPaint.getAlpha();
            alpha -= 10;

            //alpha 是对alpha%255 的结果值作为透明
            if (alpha <= 20) {
                alpha = 0;
            }
            mPaint.setAlpha(alpha);
            mPaint.setStrokeWidth(radius / 3);
            //2.刷新 -导致onDraw方法重新执行
            invalidate();
        }
    };

    /**
     * 绘制内容
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(200, 200, 100, mPaint);
        if (pointX != 0 && pointY != 0) {
            canvas.drawCircle(pointX, pointY, 100, mPaint);
            if (mPaint.getAlpha() > 0) {
                handler.sendEmptyMessageDelayed(0, 50);
            }
        }

    }

    /**
     * 圆心的X坐标
     */
    private float pointX;
    /**
     * 圆心的Y坐标
     */
    private float pointY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下屏幕
                pointX = event.getX();
                pointY = event.getY();

                //对圆进行初始化
                init(null);

                //刷新
                invalidate();
                break;

            default:
                break;
        }
        return true;
    }

}

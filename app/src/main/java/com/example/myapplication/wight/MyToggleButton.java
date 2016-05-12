package com.example.myapplication.wight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;

/**
 * Created by 123 on 2016/5/12.
 */
public class MyToggleButton extends View implements View.OnClickListener {

    private Paint mPaint;

    private Bitmap backGroundBitmap;
    private Bitmap slideBitmap;
    private Context context;
    /**
     * 距离左边的距离
     */
    private float slideLeft;
    /**
     * 判断当前开关状态
     * true为开
     * false为关
     */
    private boolean curState = false;

    /**
     * 第一次按下的x坐标
     */
    int startX = 0;
    /**
     * 最初的历史位置
     */
    int lastX = 0;
    /**
     * 点击事件是否可用
     * true 可用
     * false 不可用
     */
    boolean isClickEnable = true;
    int maxLeft;

    // 在代码中new实例化时调用
    public MyToggleButton(Context context) {
        super(context);
        init(context);
    }

    // 在布局文件中声明view的时候，该方法有系统调用
    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 增加一个默认显示样式时候使用
    public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        backGroundBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);
        slideBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.slide_button);
        setOnClickListener(MyToggleButton.this);
        maxLeft = backGroundBitmap.getWidth() - slideBitmap.getWidth();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(backGroundBitmap.getWidth(), backGroundBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        // canvas.drawColor(Color.RED);
        //canvas.drawCircle(100,200,90,mPaint);
        canvas.drawBitmap(backGroundBitmap, 0, 0, mPaint);
        canvas.drawBitmap(slideBitmap, slideLeft, 0, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getRawX();
                isClickEnable = true;
                break;
            case MotionEvent.ACTION_MOVE:
                int newX = (int) event.getRawX();
                int dX = newX - startX;
                slideLeft += dX;
                if (Math.abs(event.getRawX() - lastX) > 5) {
                    isClickEnable = false;
                }
                flushView();
                startX = (int) event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                if (!isClickEnable) {
                    if (slideLeft >= maxLeft / 2) {
                        curState = true;
                    } else {
                        curState = false;
                    }
                    flushState();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void flushView() {
        if (slideLeft < 0) {
            slideLeft = 0;
        }
        if (slideLeft > maxLeft) {
            slideLeft = maxLeft;
        }
        invalidate();
    }

    @Override
    public void onClick(View v) {
        if (isClickEnable) {
            curState = !curState;
            flushState();
        }

    }

    private void flushState() {
        if (curState) {
            slideLeft = backGroundBitmap.getWidth() - slideBitmap.getWidth();
        } else {
            slideLeft = 0;
        }
        flushView();

    }
}

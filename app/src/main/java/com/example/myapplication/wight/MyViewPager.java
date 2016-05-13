package com.example.myapplication.wight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by 123 on 2016/5/12.
 */
public class MyViewPager extends ViewGroup {

    /**
     * Construct a new Switch with default styling.
     *
     * @param context The Context that will determine this widget's theming.
     */
    public MyViewPager(Context context) {
        super(context);
        init(context);
    }

    /**
     * Construct a new Switch with default styling, overriding specific style
     * attributes as requested.
     *
     * @param context The Context that will determine this widget's theming.
     * @param attrs   Specification of attributes that should deviate from default styling.
     */

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Construct a new Switch with a default style determined by the given theme attribute,
     * overriding specific style attributes as requested.
     *
     * @param context      The Context that will determine this widget's theming.
     * @param attrs        Specification of attributes that should deviate from the default styling.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     */
    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Construct a new Switch with a default style determined by the given theme
     * attribute or style resource, overriding specific style attributes as
     * requested.
     *
     * @param context      The Context that will determine this widget's theming.
     * @param attrs        Specification of attributes that should deviate from the
     *                     default styling.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     * @param defStyleRes  A resource identifier of a style resource that
     *                     supplies default values for the view, used only if
     *                     defStyleAttr is 0 or can not be found in the theme. Can be 0
     *                     to not look for defaults.
     */
    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * 指定当前View的位置
     * 如果当前View是viewGroup的话，应该在此方法指定子View的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(i * getWidth(), 0, getWidth() + i * getWidth(), getHeight());
        }
    }
    /**
     * 对View进行测量
     * 如果当前View是ViewGroup的话，那么ViewGroup有义务对每个子View测量大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for(int i=0;i<getChildCount();i++){
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }
    /**
     *  第一次按下的X的坐标
     */
    private float downX;
    /**
     *  第一次按下的Y的坐标
     */
    private float downY;

    /**
    /**
     * 是否中断事件的传递，默认返回false,意思为，不中断，按正常情况，传递事件
     * 如果为true，就将事件中断，直接执行自己的onTounchEvent方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = false;
        // 如果水平方向滑动的距离大于竖直方向滑动，就是左右滑动，中断事件；否则事件继续传递；
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                detector.onTouchEvent(ev);
                //第一次按下的坐标
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //2.来到新的坐标
                float newDownX =  ev.getX();
                float newDownY =  ev.getY();
                //3.计算距离
                int distanceX = (int) Math.abs(newDownX - downX);
                int distanceY = (int) Math.abs(newDownY - downY);
                //distanceX > 10 防止抖动为1左右的情况
                if(distanceX > distanceY && distanceX > 100){
                    result = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;

        }
        return result;
    }

    private GestureDetector detector;
    private Scroller scroller;

    //回调
    private PageChangeListener changeListener;

    public PageChangeListener getChangeListener() {
        return changeListener;
    }

    /**
     * 由外界实例化传进来
     *
     * @param changeListener
     */
    public void setChangeListener(PageChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    /**
     * 监听页面改变时得到下标
     *
     * @author afu
     */
    public interface PageChangeListener {
        /**
         * 当页面改变的时候移动到指定的下标
         *
         * @param curIndex 指定下标
         */
        public void moveTo(int curIndex);
    }


    private void init(Context context) {
        scroller = new Scroller(context);
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {


            @Override
            /**
             * 当手指在屏幕上触摸滑动的时候回调这个方法
             */
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                /**
                 * 移动View中内容
                 * 竖直方法设置为0
                 * x：距离X的方向的距离
                 * y：距离Y的方向的距离
                 */
                scrollBy((int) distanceX, 0);
                /**
                 * 移动到某个点
                 * x,y是坐标点
                 * scrollTo(int x, int y)
                 */
                return true;
            }
        });

    }

    /**
     * 手指第一次按下屏幕的X轴的坐标
     */
    private int startX = 0;
    /**
     * 当前显示的子View的下标
     */
    private int curIndex = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //super.onTouchEvent(event);
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录坐标
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //2.来电新的坐标
                int endX = (int) event.getX();
                //3.计算位置，得出该定位到那个子View的坐标
                int tempIndex = curIndex;
                if (endX - startX > getWidth() / 5) {
                    //显示上一个子View
                    tempIndex--;
                } else if (startX - endX >= getWidth() / 5) {
                    //显示下一个子View
                    tempIndex++;
                }
                moveTo(tempIndex);
                break;
            default:
                break;
        }
        return true;
    }

    public void moveTo(int tempIndex) {
        //屏幕非法操作
        if (tempIndex < 0) {
            tempIndex = 0;
        }
        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        curIndex = tempIndex;

        if (changeListener != null) {
            changeListener.moveTo(tempIndex);
        }
        //定位到指定的子View里
        //得到要移动的距离
        int distance = curIndex * getWidth() - getScrollX();
        scroller.startScroll(getScrollX(), 0, distance, 0);
        //下面是瞬间移动，者效果不好，我们需要在有些时间的移动。
        // scrollTo(curIndex*getWidth(),0);
        /**
         * invalidate 会导致computeScroll 的执行，该方法是空的
         */
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            float curX = scroller.getCurrX();
            scrollTo((int) curX, 0);
            /**
             * 会导致computeScroll 的执行
             */
            invalidate();
        }
    }
}

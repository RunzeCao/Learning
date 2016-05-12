package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ADActivity extends BaseActivity {
    private ViewPager viewpager;

    private LinearLayout pointGroup;

    //图片资源ID
    private final int[] imageIds = {
            R.mipmap.a,
            R.mipmap.b,
            R.mipmap.c,
            R.mipmap.d,
            R.mipmap.e
    };

    //图片标题集合
    private final String[] imageDescriptions = {
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀"
    };

    private ArrayList<ImageView> imageList;

    /**
     * 页面图片的描述文字
     */
    private TextView imageDesc;

    /**
     * 上一个指示点的下标
     */
    private int lastPointIdex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        pointGroup = (LinearLayout) findViewById(R.id.ll_point_group);

        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            imageList.add(imageView);
            //添加指示点
            ImageView point = new ImageView(this);
            //添加指示点之间的间距
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 15;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point_selsetor);
            pointGroup.addView(point);
            //默认情况下，第一个小点enable为true
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
        }
        imageDesc = (TextView) findViewById(R.id.tv_image_desc);

        imageDesc.setText(imageDescriptions[0]);
        viewpager.setAdapter(new MyPagerAdapter());
        //左滑 要求刚好是imageViews.size()的整数倍
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageList.size();
        viewpager.setCurrentItem(item);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            /**
             * 当页面滑动了调用该方法
             */
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            /**
             * 当页面被选择了回调
             * position 当前被显示的页面的位置：从0开始
             */
            public void onPageSelected(int position) {
                int myIndex = position % imageList.size();
                imageDesc.setText(imageDescriptions[myIndex]);
                pointGroup.getChildAt(myIndex).setEnabled(true);
                pointGroup.getChildAt(lastPointIdex).setEnabled(false);
                lastPointIdex = myIndex;
            }

            @Override
            /**
             * 当页面状态发送变化的调用防方法
             * 静止--滑动
             * 滑动-静止
             */
            public void onPageScrollStateChanged(int state) {

            }
        });
        isRunning = true;
        handler.sendEmptyMessageDelayed(0, 2000);

    }

    //自动翻页
    private boolean isRunning = false;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
           viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
            if (isRunning){
                handler.sendEmptyMessageDelayed(0,4000);
            }
        }
    };

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        /**
         * 给viewPager添加指定位置的view
         * container 要添加的view的父view  其实就是viewpager自身
         * position 指定的位置
         */
        public Object instantiateItem(ViewGroup container, int position) {
            View view = imageList.get(position % imageList.size());
            container.addView(view);
            return view;
        }

        @Override
        /**
         * 判断某个view和object之间的关系 ,object 是instantiateItem返回的object
         */
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        /**
         * 销毁指定位置上的view或object
         *
         * object 就是instantiateItem 方法 返回的obect
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

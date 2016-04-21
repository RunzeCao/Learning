package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.mine.Constant;
import com.example.myapplication.utils.PreferenceUitl;
import com.example.myapplication.wight.indicator.PageIndicator;

import java.util.ArrayList;

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private ViewPager viewPager;
    private PageIndicator pageIndicator;
    private final int PAGER_NUMBER = 3;
    private ArrayList<View> mSplashList = new ArrayList<>();
    ArrayList<PageIndicator.PageMarkerResources> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
        initView();
    }

    private void initData() {
        final int[] resColor = {getResources().getColor(android.R.color.holo_green_light), getResources().getColor(android.R.color.holo_red_light), getResources().getColor(android.R.color.holo_orange_light)};
        for (int i = 0; i < PAGER_NUMBER; i++) {
            if (i == PAGER_NUMBER - 1) {
                View view = getLayoutInflater().inflate(R.layout.splash_item, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.splash_img);
                imageView.setBackgroundColor(resColor[i]);
                TextView textView = (TextView) view.findViewById(R.id.splash_start);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goAction(LoginActivity.class, true);
                        PreferenceUitl.getInstance(mContext).saveBoolean(Constant.PRF_FIRST_LOGIN, false);
                    }
                });
                mSplashList.add(view);
            } else {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                ImageView iv = new ImageView(this);
                iv.setBackgroundColor(resColor[i]);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setLayoutParams(params);
                mSplashList.add(iv);
            }
        }

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pageIndicator = (PageIndicator) findViewById(R.id.activity_splash_ind);
        for (int i = 0;i<PAGER_NUMBER;i++){
            markers.add(new PageIndicator.PageMarkerResources());
        }
        pageIndicator.addMarkers(markers,true,true);
        pageIndicator.setActiveMarker(0);
        PagerAdapter pagerAdapter = new MyPagerAdapter(mSplashList);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicator.setActiveMarker(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    private class MyPagerAdapter extends PagerAdapter {
        ArrayList<View> splashList;

        public MyPagerAdapter(ArrayList<View> mSplashList) {
            this.splashList = mSplashList;
        }


        @Override
        public int getCount() {
            return splashList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(splashList.get(position));

        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(splashList.get(position));
            return splashList.get(position);
        }


    }
}

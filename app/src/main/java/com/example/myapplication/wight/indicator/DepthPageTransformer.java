package com.example.myapplication.wight.indicator;

import android.support.v4.view.ViewPager;
import android.view.View;

public class DepthPageTransformer implements ViewPager.PageTransformer{
    @Override
    public void transformPage(View page, float position) {
        if(position <= 0 ){
            page.setTranslationX(0);
        }else if(position <= 1){
            page.setTranslationX(-page.getWidth() * position / 3);
        }
    }
}

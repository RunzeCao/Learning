package com.example.myapplication.utils;


import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


public class GuideUtil {
    private static final String TAG = GuideUtil.class.getSimpleName();
    private ImageView imgView;
    private FrameLayout mParentView;
    private static GuideUtil instance = null;

    private GuideUtil() {
    }

    public static GuideUtil getInstance() {
        synchronized (GuideUtil.class) {
            if (null == instance) {
                instance = new GuideUtil();
            }
        }
        return instance;
    }


    public void showGuide(Activity context, int drawableResourcesId, String type) {

        SharedPreferences sp = context.getSharedPreferences(TAG, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(TAG + type, false);
        editor.apply();

        mParentView = (FrameLayout) context.getWindow().getDecorView();
        imgView = new ImageView(context);
        imgView.setLayoutParams(new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT));
        imgView.setScaleType(ScaleType.FIT_XY);
        imgView.setImageResource(drawableResourcesId);
        mParentView.addView(imgView);
        imgView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mParentView.removeView(imgView);
            }
        });
    }


    /**
     * 判断新手引导也是否已经显示了
     */
    public static boolean isNeverShowed(Activity activity, String type) {
        return activity.getSharedPreferences(TAG, Activity.MODE_PRIVATE).getBoolean(TAG + type, true);
    }
}

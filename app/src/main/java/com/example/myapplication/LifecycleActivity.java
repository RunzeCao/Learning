package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class LifecycleActivity extends BaseActivity {

    private ImageView iv;

    //Activity被创建时调用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);


        iv = (ImageView) findViewById(R.id.iv);
        executeRequest(new ImageRequest("https://lh3.googleusercontent.com/-Iwi6-i6IexY/URqvAYZHsVI/AAAAAAAAAbs/5ETWl4qXsFE/s1024/Shinjuku%252520Street.jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                iv.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                iv.setImageResource(R.mipmap.ic_launcher);
            }
        }));
        iv.setImageResource(R.mipmap.ad);

    }

    //Activity被创建或者从后台重新回到前台时调用
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Activity被系统杀死后再重建时被调用.
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死,用户又启动该Activity.
     * 这两种情况下onRestoreInstanceState都会被调用,在onStart之后.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    //Activity从后台重新回到前台时调用
    @Override
    protected void onRestart() {
        super.onRestart();
    }


    //Activity被创建或从被覆盖、后台重新回到前台时调用
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Activity被系统杀死时被调用.
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死.
     * 另外,当跳转到其他Activity或者按Home键回到主屏时该方法也会被调用,系统是为了保存当前View组件的状态.
     * 在onPause之前被调用.
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    //Activity被覆盖到下面或锁屏时调用
    @Override
    protected void onPause() {
        super.onPause();
    }

    //退出当前Activity或者跳转到新的Activity时被调用
    @Override
    protected void onStop() {
        super.onStop();
    }

    //退出当前Activity时调用，调用过后Activity结束
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

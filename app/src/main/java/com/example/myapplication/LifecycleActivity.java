package com.example.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.utils.ToastUtils;

public class LifecycleActivity extends AppCompatActivity {

    private Button button,button1,button2,button3;
    //Activity被创建时调用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileDir = getFilesDir().toString();
                ToastUtils.makeText(LifecycleActivity.this,fileDir);
            }
        });
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cacheDir = getCacheDir().toString();
                ToastUtils.makeText(LifecycleActivity.this,cacheDir);
            }
        });
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String externalStorageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
                ToastUtils.makeText(LifecycleActivity.this,externalStorageDirectory);
            }
        });
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String externalStoragePublicDirectory = getExternalCacheDir().toString();
                ToastUtils.makeText(LifecycleActivity.this,externalStoragePublicDirectory);
            }
        });
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

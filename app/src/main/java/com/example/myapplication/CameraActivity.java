        package com.example.myapplication;

import android.os.Bundle;

public class CameraActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState){
            getFragmentManager().beginTransaction().replace(R.id.container,Camera2BasicFragment.newInstance()).commit();
        }
    }
}

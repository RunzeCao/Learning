package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";
    private Button btn_info_fix, btn_baidu, btn_ble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_info_fix = (Button) findViewById(R.id.btn_info_fix);
        btn_baidu = (Button) findViewById(R.id.btn_baidu);
        btn_ble = (Button) findViewById(R.id.btn_ble);
        btn_info_fix.setOnClickListener(this);
        btn_baidu.setOnClickListener(this);
        btn_ble.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_info_fix:
                goAction(MineActivity.class, false);
                break;
            case R.id.btn_baidu:
                goAction(BaiduActivity.class, false);
                break;
            case R.id.btn_ble:
                goAction(BleScanActivity.class, false);
                break;
        }
    }
}

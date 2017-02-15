package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.ble.BleCallback;
import com.example.myapplication.ble.BleDataset;
import com.example.myapplication.ble.BleService;
import com.example.myapplication.utils.ToastUtils;

public class DeviceControlActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public BleService bleService;
    private String mDeviceName;
    private String mDeviceAddress;
    private boolean mConnected = false;
    private TextView tv_info;
   /* private Button btn_write, btn_read, btn_persional_info, btn_device_type, btn_device_code, btn_step,
            btn_light_open;*/
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            BleService.LocalBinder binder = (BleService.LocalBinder) service;
            bleService = (BleService) binder.getService();
            if (!bleService.initialize()) {
                Log.e(TAG, "无法初始化蓝牙");
                finish();
            }
            bleService.connect(mDeviceAddress);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bleService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_control);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDeviceAddress = getIntent().getStringExtra(EXTRAS_DEVICE_ADDRESS);
        mDeviceName = getIntent().getStringExtra(EXTRAS_DEVICE_NAME);
        setTitle(mDeviceName);

        tv_info = (TextView) findViewById(R.id.tv_info);
     /*   btn_write = (Button) findViewById(R.id.btn_write);
        btn_write.setOnClickListener(this);
        btn_read = (Button) findViewById(R.id.btn_read);
        btn_read.setOnClickListener(this);
        btn_persional_info = (Button) findViewById(R.id.btn_persional_info);
        btn_persional_info.setOnClickListener(this);
        btn_device_type = (Button) findViewById(R.id.btn_device_type);
        btn_device_type.setOnClickListener(this);
        btn_device_code = (Button) findViewById(R.id.btn_device_code);
        btn_device_code.setOnClickListener(this);
        btn_step = (Button) findViewById(R.id.btn_step);
        btn_step.setOnClickListener(this);
        btn_light_open = (Button) findViewById(R.id.btn_light_open);
        btn_light_open.setOnClickListener(this);*/

        Intent intent = new Intent(this, BleService.class);
        this.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, makeGattUpdateIntentFilter());

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);

        if (!mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_reconnect).setVisible(false);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_reconnect).setVisible(true);

        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                //bleService.connect(mDeviceAddress);
                ToastUtils.makeText(this, "蓝牙已连接");
                break;
            case R.id.menu_reconnect:
                bleService.connect(mDeviceAddress);
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        serviceConnection = null;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BleCallback.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                invalidateOptionsMenu();

            } else if (BleCallback.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                invalidateOptionsMenu();

            }
        }
    };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BleCallback.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BleCallback.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BleCallback.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BleCallback.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_write:
                bleService.writeToBle(BleDataset.getSendBleData());
                break;
            case R.id.btn_read:
                bleService.readFromBle();
                break;
            case R.id.btn_persional_info:
                bleService.writeToBle(BleDataset.setPersonInfo(this));
                break;
            case R.id.btn_device_type:
                bleService.writeToBle(BleDataset.setDeviceType());
                break;
            case R.id.btn_device_code:
                bleService.writeToBle(BleDataset.setDeviceCode());
                break;
            case R.id.btn_step:
                bleService.writeToBle(BleDataset.setTargetStep(this, 10000));
                break;
            case R.id.btn_light_open:
                if (LightTouch) {
                    bleService.writeToBle(BleDataset.setLightTouch(1));
                } else {
                    bleService.writeToBle(BleDataset.setLightTouch(0));
                }
                LightTouch = !LightTouch;
                break;
        }
    }

    private boolean LightTouch = true;//触摸开关
}

package com.example.myapplication;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.ble.BleService;

import java.util.List;

public class DeviceControlActivity extends AppCompatActivity {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public BleService bleService;
    String address;
    final private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("YWS","onServiceConnected");
            BleService.LocalBinder binder = (BleService.LocalBinder) service;
            bleService = (BleService) binder.getService();
            bleService.bleConnect(address);


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
        address = getIntent().getStringExtra(EXTRAS_DEVICE_ADDRESS);
        int pid = android.os.Process.myPid();
        String proName = getProcessName(this, pid);
//        if (TextUtils.equals(proName, getPackageName())) { //主进程
            Intent intent = new Intent(this,BleService.class);
        Log.i("YWS","1111");
        this.bindService(intent,serviceConnection,BIND_AUTO_CREATE);
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
    /**
     * 根据pid获取当前的进程名
     */
    public static String getProcessName(Context context, int pid) {
        if (context == null) {
            return null;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

}

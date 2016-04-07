package com.example.myapplication;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;

public class BleService extends Service {
    private final String TAG = BleService.class.getSimpleName();
    private final IBinder mIBinder = new LocalBinder();
    public BluetoothGatt gatt;

    public BleService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    private class LocalBinder extends Binder {
        public BleService getService() {
            return BleService.this;
        }
    }

    public boolean bleConnect(String address) {
        if (TextUtils.isEmpty(address)) {
            return false;
        }
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            LogUtils.e(TAG, "adapter == null");
            return false;
        }
        BluetoothDevice device = adapter.getRemoteDevice(address);
        if (device == null) {
            LogUtils.e(TAG, "device == null");
            return false;
        }
        if (gatt == null) {
            gatt = device.connectGatt(this, false, new BleCallback(BleService.this));
        }
        return gatt.connect();
    }
}

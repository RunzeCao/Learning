/*
package com.example.myapplication;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

public class BleService extends Service {
    private final String TAG = BleService.class.getSimpleName();
    private final IBinder mIBinder = new LocalBinder();
    public BluetoothGatt gatt;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG,"onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    public class LocalBinder extends Binder {
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
        Log.i("YWS","-->"+gatt+"<--");
        return gatt.connect();
    }
}
*/

package com.example.myapplication.ble;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class BleService extends Service {
    private final String TAG = BleService.class.getSimpleName();
    private final IBinder mIBinder = new LocalBinder();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    public BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;


    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class LocalBinder extends Binder {
        public BleService getService() {
            return BleService.this;
        }
    }

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     * @return Return true if the connection is initiated successfully. The connection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, new BleCallback(BleService.this));
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * 向BLE设备写数据
     */  public boolean writeToBle(byte[] bytes) {
        if (mBluetoothGatt == null) {
            Log.e(TAG, "gatt == null");
            return false;
        }
        BluetoothGattService gattService = mBluetoothGatt.getService(BleContants.UUID_NUS_SERVICE);
        if (gattService == null) {
            Log.e(TAG, "gattService == null");
            return false;
        }
        BluetoothGattCharacteristic gattCharacteristic = gattService.getCharacteristic(BleContants.UUID_NUS_CHARACTER);
        if (gattCharacteristic == null) {
            Log.e(TAG, "gattCharacteristic == null");
            return false;
        }
        gattCharacteristic.setValue(bytes);
        return mBluetoothGatt.writeCharacteristic(gattCharacteristic);
    }
  /*  public boolean writeToBle(BluetoothGatt gatt, byte[] bytes) {
        if (gatt == null) {
            Log.e(TAG, "gatt == null");
            return false;
        }
        BluetoothGattService gattService = gatt.getService(BleContants.UUID_NUS_SERVICE);
        if (gattService == null) {
            Log.e(TAG, "gattService == null");
            return false;
        }
        BluetoothGattCharacteristic gattCharacteristic = gattService.getCharacteristic(BleContants.UUID_NUS_CHARACTER);
        if (gattCharacteristic == null) {
            Log.e(TAG, "gattCharacteristic == null");
            return false;
        }
        gattCharacteristic.setValue(bytes);
        return gatt.writeCharacteristic(gattCharacteristic);
    }*/

    /**
     * 读BLE设备数据
     */
    public boolean readFromBle() {
        if (mBluetoothGatt == null) {
            Log.e(TAG, "gatt == null");
            return false;
        }
        BluetoothGattService gattService = mBluetoothGatt.getService(BleContants.UUID_NUS_SERVICE);
        if (gattService == null) {
            Log.e(TAG, "gattService == null");
            return false;
        }
        BluetoothGattCharacteristic gattCharacteristic = gattService.getCharacteristic(BleContants.UUID_NUS_CHARACTER);
        if (gattCharacteristic == null) {
            Log.e(TAG, "gattCharacteristic == null");
            return false;
        }
        return mBluetoothGatt.readCharacteristic(gattCharacteristic);
    }


}


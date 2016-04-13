package com.example.myapplication.ble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.Context;

import com.example.myapplication.utils.LogUtils;

/**
 * Created by 123 on 2016/4/7.
 *
 */
public class BleCallback extends BluetoothGattCallback {

    private String TAG = BleCallback.class.getSimpleName();

    private Context context;

    public BleCallback(Context context) {
        super();
        this.context = context;
    }

    public BleCallback() {
        super();
    }

    @Override
    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        super.onMtuChanged(gatt, mtu, status);
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        super.onReadRemoteRssi(gatt, rssi, status);
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorRead(gatt, descriptor, status);
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
    }

    @Override
    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
        super.onReliableWriteCompleted(gatt, status);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            LogUtils.i(TAG, "Discover GattService:STATE_DISCONNECTED");
        } else if (newState == BluetoothProfile.STATE_CONNECTING) {
            LogUtils.i(TAG, "Discover GattService:STATE_CONNECTING");
        } else if (newState == BluetoothProfile.STATE_CONNECTED) {
            LogUtils.i(TAG, "Discover GattService:STATE_CONNECTED");
            //App.STATE_BLE_CONNECTED = true;
            gatt.discoverServices();
        } else if (newState == BluetoothProfile.STATE_DISCONNECTING) {
            //App.STATE_BLE_CONNECTED = false;
            LogUtils.i(TAG, "Discover GattService:STATE_DISCONNECTING");
        }
    }
}

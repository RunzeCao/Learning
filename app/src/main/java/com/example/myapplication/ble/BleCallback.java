package com.example.myapplication.ble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.myapplication.utils.LogUtils;
import com.example.myapplication.utils.StringUtils;

/**
 * Created by 123 on 2016/4/7.
 *
 */
public class BleCallback extends BluetoothGattCallback {

    private String TAG = BleCallback.class.getSimpleName();

    public final static String ACTION_GATT_CONNECTED = "com.example.myaplication.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.example.myaplication.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.myaplication.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.example.myaplication.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA = "com.example.myaplication.EXTRA_DATA";


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
        Log.d(TAG,"onDescriptorWrite:"+status);
    }

    @Override
    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
        super.onReliableWriteCompleted(gatt, status);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        byte[] bytes = characteristic.getValue();
        LogUtils.d(TAG, "Reply:" + StringUtils.bytes2HexString(bytes));
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            byte[] data = characteristic.getValue();
            if (data != null) {
                LogUtils.d(TAG, StringUtils.bytes2HexString(data));
            } else {
                LogUtils.e(TAG, "onCharacteristicRead: data == null !");
            }
        } else {
            LogUtils.e(TAG, "onCharacteristicRead: GATT_FAILURE!");
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        byte[] data = characteristic.getValue();
        Log.d(TAG,"onCharacteristicWrite: "+ StringUtils.bytes2HexString(data));
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        LogUtils.d(TAG, "onServicesDiscovered-->status:" + status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            enbleNotification(gatt);
        } else {
            LogUtils.e(TAG, "onServicesDiscovered: GATT_FAILURE!");
        }
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        String intentAction;
        if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            Log.i(TAG, "Discover GattService:STATE_DISCONNECTED");
            intentAction = ACTION_GATT_DISCONNECTED;
            broadcastUpdate(intentAction,context);

        } else if (newState == BluetoothProfile.STATE_CONNECTING) {
            Log.i(TAG, "Discover GattService:STATE_CONNECTING");
        } else if (newState == BluetoothProfile.STATE_CONNECTED) {
            Log.i(TAG, "Discover GattService:STATE_CONNECTED");
            intentAction = ACTION_GATT_CONNECTED;
            broadcastUpdate(intentAction,context);
            gatt.discoverServices();
        } else if (newState == BluetoothProfile.STATE_DISCONNECTING) {
            Log.i(TAG, "Discover GattService:STATE_DISCONNECTING");

        }
    }


    private void broadcastUpdate(final String action,Context context) {
        final Intent intent = new Intent(action);
        context.sendBroadcast(intent);
    }
    /**
     * 打开通知
     */
    public boolean enbleNotification(BluetoothGatt gatt) {
        if (gatt == null) {
            return false;
        }
        BluetoothGattService bleGattService = gatt.getService(BleContants.UUID_NUS_SERVICE);
        if (bleGattService == null) {
            return false;
        }
        BluetoothGattCharacteristic character = bleGattService.getCharacteristic(BleContants.UUID_NUS_NOTIFY);
        if (character == null) {
            return false;
        }
        return setCharacteristicNotification(gatt, character, true);
    }

    /**
     * 写入Descriptor
     */
    public boolean setCharacteristicNotification(BluetoothGatt gatt, BluetoothGattCharacteristic character, boolean enabled) {
        if (gatt == null) {
            return false;
        }
        gatt.setCharacteristicNotification(character, enabled);
        if (BleContants.UUID_NUS_NOTIFY.equals(character.getUuid())) {
            BluetoothGattDescriptor descriptor = character.getDescriptor(BleContants.UUID_NUS_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            return gatt.writeDescriptor(descriptor);
        }
        return false;
    }

}

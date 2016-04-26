package com.example.myapplication.ble;

import java.util.UUID;

/**
 * Created by 123 on 2016/4/26.
 */
public class BleContants {
    /**
     * 模拟设备
     */
   /* public final static UUID UUID_NUS_SERVICE = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_NUS_NOTIFY = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_NUS_CHARACTER= UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_NUS_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");*/
    /**
     * 串口服务(命令数据通信)
     */
    public final static UUID UUID_NUS_SERVICE = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    public final static UUID UUID_NUS_CHARACTER = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    public final static UUID UUID_NUS_NOTIFY = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    public final static UUID UUID_NUS_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
}

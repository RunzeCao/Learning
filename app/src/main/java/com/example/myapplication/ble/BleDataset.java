package com.example.myapplication.ble;

import android.content.Context;
import android.text.TextUtils;

import com.example.myapplication.R;
import com.example.myapplication.mine.UserConstant;
import com.example.myapplication.utils.DateUtil;
import com.example.myapplication.utils.StringUtils;

/**
 * Created by 123 on 2016/4/26.
 */
public class BleDataset {

    public static byte[] getSendBleData() {
        byte[] data = new byte[4];
        data[0] = (byte) 0x10;
        data[1] = 0x30;
        data[2] = 0x20;
        data[3] = (byte) (data[0] + data[1] + data[2]);
        return data;
    }

    /**
     * 个人信息数据
     */
    public static byte[] setPersonInfo(Context context) {
        byte[] data = new byte[20];
        String date = DateUtil.getCurrentTime2();
        String birthday = UserConstant.getUserBirthday(context);
        int age = 20;
        //日期
        byte year = 0x0F;
        byte month = 0x01;
        byte day = 0x01;
        byte hour = 0x00;
        byte minute = 0x00;
        byte second = 0x00;
        if (!TextUtils.isEmpty(date)) {
            String[] time = date.split(" ");
            if (time != null && time.length > 5) {
                year = (byte) (((Integer.parseInt(time[0]) - 2000)) & 0xFF);
                month = (byte) (Integer.parseInt(time[1]) & 0xFF);
                day = (byte) (Integer.parseInt(time[2]) & 0xFF);
                hour = (byte) (Integer.parseInt(time[3]) & 0xFF);
                minute = (byte) (Integer.parseInt(time[4]) & 0xFF);
                second = (byte) (Integer.parseInt(time[5]) & 0xFF);
                if (!TextUtils.isEmpty(birthday)) {
                    String[] birth = birthday.split("-");
                    if (birth != null & birth.length > 0) {
                        age = (Integer.parseInt(time[0]) - Integer.parseInt(birth[0]));
                    }
                }
            }
        }
        //性别年龄
        byte sa; //性别年龄
        if (context.getResources().getString(R.string.gender_male).equalsIgnoreCase(UserConstant.getUserSex(context))) { //男
            sa = (byte) (age & 0xFF);
        } else {
            sa = (byte) ((age | 0x80) & 0xFF);
        }
        //身高
        byte height = 0x00;
        String heightStr = UserConstant.getUserHeight(context);
        if (!TextUtils.isEmpty(heightStr)) {
            height = (byte) (Integer.parseInt(heightStr) & 0xFF);
        }
        //体重
        byte weight = 0x00;
        String weightStr = UserConstant.getUserWeight(context);
        if (!TextUtils.isEmpty(weightStr)) {
            weight = (byte) (Integer.parseInt(weightStr) & 0xFF);
        }
        //公英制
        byte unit = 0x00;
        data[0] = 0x5A;
        data[1] = 0x01;
        data[2] = 0x00;
        data[3] = year;     //年
        data[4] = month;     //月
        data[5] = day;     //日
        data[6] = hour;     //时
        data[7] = minute;     //分
        data[8] = second;     //秒
        data[9] = 0x00;     //系统类型
        data[10] = sa;    //性别年龄
        data[11] = height;    //身高
        data[12] = weight;    //体重
        data[13] = unit;    //公英制
        int count = StringUtils.bytesCheckAnd(data);//校验和
        data[18] = (byte) ((count >> 8) & 0xFF);
        data[19] = (byte) (count & 0xFF);
        return data;
    }
    /**
     * 设备型号
     */
    public static byte[] setDeviceType() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x02;
        return data;
    }

    /**
     * 设备编号
     */
    public static byte[] setDeviceCode() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x03;
        return data;
    }

    /**
     * 目标步数设定值
     */
    public static byte[] setTargetStep(Context context, int steps) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x04;
        data[2] = 0x00;
        data[3] = 0x00;
        data[4] = (byte) ((steps >> 24) & 0xFF);
        data[5] = (byte) ((steps >> 16) & 0xFF);
        data[6] = (byte) ((steps >> 8) & 0xFF);
        data[7] = (byte) (steps & 0xFF);
        int count = StringUtils.bytesCheckAnd(data);//校验和
        data[18] = (byte) ((count >> 8) & 0xFF);
        data[19] = (byte) (count & 0xFF);
        return data;
    }

    /**
     * 目标公里设定值
     */
    public static byte[] setTargetKilo(Context context, int kilos) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x04;
        data[2] = 0x00;
        data[3] = 0x01;
        data[4] = (byte) ((kilos >> 24) & 0xFF);
        data[5] = (byte) ((kilos >> 16) & 0xFF);
        data[6] = (byte) ((kilos >> 8) & 0xFF);
        data[7] = (byte) (kilos & 0xFF);
        int count = StringUtils.bytesCheckAnd(data);//校验和
        data[18] = (byte) ((count >> 8) & 0xFF);
        data[19] = (byte) (count & 0xFF);
        return data;
    }

    /**
     * 目标卡路里设定值
     */
    public static byte[] setTargetCalo(Context context, int calos) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x04;
        data[2] = 0x00;
        data[3] = 0x02;
        data[4] = (byte) ((calos >> 24) & 0xFF);
        data[5] = (byte) ((calos >> 16) & 0xFF);
        data[6] = (byte) ((calos >> 8) & 0xFF);
        data[7] = (byte) (calos & 0xFF);
        int count = StringUtils.bytesCheckAnd(data);//校验和
        data[18] = (byte) ((count >> 8) & 0xFF);
        data[19] = (byte) (count & 0xFF);
        return data;
    }

    /**
     * 开启(关闭)触摸发光
     */
    public static byte[] setLightTouch(int state) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x1A;
        data[2] = 0x00;
        data[3] = (byte) (state & 0xFF);
        return data;
    }
}

package com.example.myapplication.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 验证是否是联通手机号码
     * 联通
     * 2G号段（GSM网络）130、131、132、155、156
     * 3G上网卡145
     * 3G号段（WCDMA网络）185、186
     * 4G号段 176、185
     * <p/>
     * 虚拟运营商专属号段
     * “170”号段为虚拟运营商专属号段，“170”号段的11位手机号前四位来区分基础运营商，
     * 其中“1700”中国电信的转售号码标识，“1705”为中国移动，“1709”为中国联通。
     */
    public static boolean isLianTongMobileNo(String numbner) {
        //普通号码匹配
        String regex = "^((13[0-2])|(15[5-6])|(18[5-6]))\\d{8}$";
        //虚拟运营商专属号段匹配
        String regex1 = "^(1709)\\d{7}$";
        return numbner.matches(regex) || numbner.matches(regex1);
    }

    /**
     * 验证电话号码
     */
    public static boolean isMobileNO(String mobiles) {
        String regex = "^(1[3|4|5|7|8][0-9])\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 密码验证，只能是英文字母或数字
     */
    public static boolean isMatchPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return password.matches("^[a-zA-Z\\d]+$");
    }

    /**
     * 验证邮箱(费时)
     */
    public static boolean isEmail(String email) {
//        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        String str = "/^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$/";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isNoData(String mResult) {
        if (mResult.equals("-2") || mResult.equals("300")
                || mResult.equals("500")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDatabasePhotoPath(Context mContext, Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    /**
     * 将InputStream转换成某种字符编码的String
     */
    public static String InputStreamTOString(InputStream in, String encoding) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count = -1;
        while ((count = in.read(data, 0, 1024)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), encoding);
    }

    /**
     * 替换服务器下发的文本中的"&nbsp;"和中文括号
     */
    public static String formatStringBlank(String str) {
        if (str.contains("&nbsp;")) {
            str = str.replaceAll("&nbsp;", " ");
        }
        if (str.contains("（") || str.contains("）")) {
            str = str.replaceAll("（", "(");
            str = str.replaceAll("）", ") ");
        }

        return str;
    }

}

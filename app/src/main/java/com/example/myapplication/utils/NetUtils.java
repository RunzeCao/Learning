package com.example.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class NetUtils {

    /** 无网络 */
    public static final int Net_CONNECT_NONE = 0;
    /** WIFI */
    public static final int Net_CONNECT_WIFI = 1;
    /** GPRS或者3G */
    public static final int Net_CONNECT_MOBILE = 2;
    /** other eg.HSDPA */
    public static final int Net_CONNECT_OTHER = 3;

    /** 调用外部浏览器访问 */
    public static void openBrowser(Context context, String paramString) {
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(paramString));
        context.startActivity(it);
    }

    /** 设置wifi */
    public static void isOpenWifi(Context context, boolean isOpen) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (isOpen == true && !wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        } else if (isOpen == false && wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

    /** 获取IP地址 */
    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /** 跳转到wifi网络设置界面 */
    public static void WifiSettings(Context context) {
        context.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    }

    /** 跳转到无线网络设置界面 */
    public static void WirelessSettings(Context context) {
        context.startActivity(new Intent(
                android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }

    /** 判断网络是否可用 */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            try {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    /** 获取连接网络类型 */
    public static int getNetworkState(Context context) {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (localNetworkInfo != null) {
            boolean localBoolean = localNetworkInfo.isAvailable();
            if (localBoolean) {
                if (localNetworkInfo != null && localNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return Net_CONNECT_WIFI;
                } else if (localNetworkInfo != null
                        && localNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return Net_CONNECT_MOBILE;
                } else {
                    return Net_CONNECT_OTHER;
                }
            } else {
                return Net_CONNECT_NONE;
            }
        } else {
            return Net_CONNECT_NONE;
        }
    }

    /** 判断是否wifi连接 */
    public static boolean isWifi(Context context) {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (localNetworkInfo != null && localNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /** 判断是否GPRS或3G连接 */
    public static boolean isMobile(Context context) {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (localNetworkInfo != null
                && localNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }
}

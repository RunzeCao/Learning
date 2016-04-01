package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;

/**
 * @ClassName: ToastUtil
 * @Description:
 * @date 2013年12月27日 上午11:56:54
 */
public class ToastUtil {

    private static HashMap<Object, Long> map = new HashMap<Object, Long>();

    public static void makeText(Context context, String text) {
        if (map.get(text) != null) {
            long l1 = System.currentTimeMillis();
            long l2 = ((Long) map.get(text)).longValue();
            if (l1 - l2 <= 2000L) {
                return;
            }
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        Long localLong = Long.valueOf(System.currentTimeMillis());
        map.put(text, localLong);
        clearMap();
    }

    public static void makeText(Context context, int resID) {
        Integer localInteger1 = Integer.valueOf(resID);
        if (map.get(localInteger1) != null) {
            long l1 = System.currentTimeMillis();
            Integer localInteger2 = Integer.valueOf(resID);
            long l2 = ((Long) map.get(localInteger2)).longValue();
            if (l1 - l2 <= 2000L)
                return;
        }
        Toast.makeText(context, resID, Toast.LENGTH_SHORT).show();
        Integer localInteger3 = Integer.valueOf(resID);
        Long localLong = Long.valueOf(System.currentTimeMillis());
        map.put(localInteger3, localLong);
        clearMap();
    }

    private static void clearMap() {
        if (map != null && map.size() > 10) {
            map.remove(map.size() - 1);
        }
    }

}

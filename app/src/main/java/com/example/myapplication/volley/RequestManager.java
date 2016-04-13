package com.example.myapplication.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by 123 on 2016/4/13.
 */
public class RequestManager {
    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    public static void init(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getRequestQueue() {
        if (requestQueue != null) {
            return requestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    public static void addRequest(Request<?> request,Object tag){
        if (tag != null){
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    public static void cancelAll(Object tag){
        requestQueue.cancelAll(tag);
    }

}

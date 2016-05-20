package com.example.myapplication.volley1;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by 123 on 2016/5/20.
 */
public class VolleyApplication extends Application {

    private static final String TAG = VolleyApplication.class.getSimpleName();

    private RequestQueue requestQueue;

    //创建一个static VolleyApplication对象，便于全局调用
    private static VolleyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    //用于返回一个VolleyApplication单例
    private static synchronized VolleyApplication getInstance() {
        return mInstance;
    }

    //用于返回全局RequestQueue对象，如果为空则创建它
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * 将Request对象添加进RequestQueue，由于Request有*StringRequest,JsonObjectResquest...等多种类型，所以需要用到*泛型。同时可将*tag作为可选参数以便标示出每一个不同请求
     */
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    //通过各Request对象的Tag属性取消请求
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }

    }


    //===========================================================================
    private ImageLoader imageLoader;

    public ImageLoader getImageLoader() {
        getRequestQueue();
        //如果imageLoader为空则创建它，第二个参数代表处理图像缓存的类
        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.requestQueue, new LruBitmapCache());
        }
        return this.imageLoader;
    }

    private class LruBitmapCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> mCache;

        public LruBitmapCache() {
            int maxSize = (int) Runtime.getRuntime().maxMemory();
            int cacheSize = maxSize / 8;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };

        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }
}

package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.myapplication.db.DBHelper;
import com.example.myapplication.utils.ToastUtils;
import com.example.myapplication.weather.Today;
import com.example.myapplication.weather.TodayGson;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class BaiduActivity extends BaseActivity {

    private static final String TAG = BaiduActivity.class.getSimpleName();
    public LocationClient locationClient;
    public BDLocationListener myLocationListener;
    private TextView baiduLocarion, weather;
    private static final int REQUEST_FINE_LOCATION = 0;

    private Button ifStart;
    private String city, province, postID;
    String url;
    private static final String WEATHER_ALL = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=%s";
    DBHelper dbHelper;
    RequestQueue requestQueue;
    Today today;
    TodayGson todayGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu);
        baiduLocarion = (TextView) findViewById(R.id.baiduLocation);
        weather = (TextView) findViewById(R.id.weather);
        dbHelper = new DBHelper(BaiduActivity.this);

    }

    private void checkPermissionLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (location != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_FINE_LOCATION);
                return;
            }
        } else {
            startLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocation();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ifStart = (Button) findViewById(R.id.ifStart);
        ifStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionLocation();
            }
        });

    }

    private void startLocation() {
        locationClient = new LocationClient(getApplicationContext());
        initLocation();
        myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);

        if (locationClient == null)
            return;
        locationClient.start();
        locationClient.requestLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
            locationClient = null;
        }
    }

    private void initLocation() {

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        locationClient.setLocOption(option);
    }

    public static String getDeviceInfo(Context context) {
        Object[] objects = new Object[4];
        objects[0] = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        objects[1] = "pisces";
        objects[2] = "JXCCNBD20.0";
        objects[3] = "";
        return String.format("&imei=%s&device=%s&miuiVersion=%s&modDevice=%s&source=miuiWeatherApp", objects);
    }


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            province = location.getProvince();
            city = location.getCity();
            postID = dbHelper.getPostID(province.replace("省", ""), city.replace("市", ""));
            baiduLocarion.setText("province:" + province + " city:" + city + " postID:" + postID);
            url = String.format(WEATHER_ALL, new Object[]{postID});
//            url += getDeviceInfo(BaiduActivity.this);

            Log.d("URL", url);
            executeRequest(new MyStringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        locationClient.stop();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject todayJsonObject = jsonObject.getJSONObject("today");
                        Gson gson = new Gson();
                        todayGson = gson.fromJson(todayJsonObject.toString(), TodayGson.class);
                        ToastUtils.makeText(BaiduActivity.this, todayGson.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e(TAG, volleyError.toString());
                }
            }));

        }
    }
}


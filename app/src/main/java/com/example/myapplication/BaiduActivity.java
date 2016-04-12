package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.myapplication.db.DBHelper;
import com.example.myapplication.util.ToastUtil;
import com.example.myapplication.weather.Today;
import com.example.myapplication.weather.TodayGson;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class BaiduActivity extends AppCompatActivity {

    private static final String TAG = BaiduActivity.class.getSimpleName();
    public LocationClient locationClient;
    public BDLocationListener myLocationListener;
    private TextView baiduLocarion, weather;

    private Button ifStart,next;
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
        next = (Button) findViewById(R.id.next);
        dbHelper = new DBHelper(BaiduActivity.this);
        requestQueue = Volley.newRequestQueue(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        locationClient = new LocationClient(getApplicationContext());
        initLocation();
        myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        ifStart = (Button) findViewById(R.id.ifStart);
        ifStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationClient == null)
                    return;
                locationClient.start();
                locationClient.requestLocation();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaiduActivity.this,DisplayActivity.class);
                intent.putExtra("today",today);
                startActivity(intent);
            }
        });
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
            url += getDeviceInfo(BaiduActivity.this);
            locationClient.stop();
            Log.d("URL", url);
            MyStringRequest stringRequest = new MyStringRequest(url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "response: " + response.replace("forecast", "weatherinfo"));
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject todayJsonObject = jsonObject.getJSONObject("today");
               /*         today = new Today();

                        today.setDate(todayJsonObject.getString("date"));
                        today.setHumidityMax(todayJsonObject.getInt("humidityMax"));
                        today.setHumidityMin(todayJsonObject.getInt("humidityMin"));
                        today.setWindDirectionEnd(todayJsonObject.getString("windDirectionEnd"));
                        today.setWindDirectionStart(todayJsonObject.getString("windDirectionStart"));
                        today.setWindMax(todayJsonObject.getInt("windMax"));
                        today.setWindMin(todayJsonObject.getInt("windMin"));
                        today.setWeatherEnd(todayJsonObject.getString("weatherEnd"));
                        today.setWeatherStart(todayJsonObject.getString("weatherStart"));
                        today.setTempMax(todayJsonObject.getInt("tempMax"));
                        today.setWindMin(todayJsonObject.getInt("tempMin"));

                        weather.setText(today.toString());*/
                        Gson gson = new Gson();
                        todayGson = gson.fromJson(todayJsonObject.toString(),TodayGson.class);
                       // Log.d(TAG, todayGson.getDate().toString());
                        ToastUtil.makeText(BaiduActivity.this,todayGson.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, error.toString());
                }
            });

            requestQueue.add(stringRequest);
       /*     JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if(jsonObject == null){
                        Log.d("BaiduActivity","00000");
                    }else {
                        Log.d("BaiduActivity","00001");
                    }
                    String forecast =jsonObject.optString("forecast");
                    byte[] bytes = new byte[0];
                    try {
                        bytes = forecast.getBytes("iso8859-1");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    try {
                        Log.d("BaiduActivity", new String(bytes,"UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
//                    Log.d("BaiduActivity",jsonObject.toString().replace("forecast","weatherinfo"));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("TAG", volleyError.getMessage(), volleyError);
                }
            });*/


       /*     StringBuilder sb = new StringBuilder();
            sb.append("time : ");
            *//**
             * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
             * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
             *//*
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            sb.append("\nCountryCode : ");
            sb.append(location.getCountryCode());
            sb.append("\nCountry : ");
            sb.append(location.getCountry());
            sb.append("\nprovince : ");
            sb.append(location.getProvince());
            sb.append("\ncitycode : ");
            sb.append(location.getCityCode());
            sb.append("\ncity : ");
            sb.append(location.getCity());
            sb.append("\nDistrict : ");
            sb.append(location.getDistrict());
            sb.append("\nStreet : ");
            sb.append(location.getStreet());
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\nDescribe: ");
            sb.append(location.getLocationDescribe());
            baiduLocarion.setText(sb.toString());*/
        }
    }
}


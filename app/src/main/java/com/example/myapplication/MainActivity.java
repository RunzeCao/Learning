package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.utils.ToastUtils;

import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private LocationManager locationManager;
    private Location location;
    private TextView latitude, longitude, altitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        altitude = (TextView) findViewById(R.id.altitude);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ToastUtils.makeText(this, "请开启GPS导航...");
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            return;
        }
        String bestProvoder = locationManager.getBestProvider(getCtiteria(), true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final Location location = locationManager.getLastKnownLocation(bestProvoder);
        updateView(location);
        locationManager.addGpsStatusListener(new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                switch (event) {
                    //第一次定位
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        Log.i(TAG, "第一次定位");
                        break;
                    //卫星状态改变
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        Log.i(TAG, "卫星状态改变");
                        //获取当前状态
                        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                        //获取卫星颗数的默认最大值
                        int maxSatellites = gpsStatus.getMaxSatellites();
                        //创建一个迭代器保存所有卫星
                        Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                        int count = 0;
                        while (iters.hasNext() && count <= maxSatellites) {
                            GpsSatellite s = iters.next();
                            count++;
                        }

                        break;
                    //定位启动
                    case GpsStatus.GPS_EVENT_STARTED:
                        Log.i(TAG, "定位启动");
                        break;
                    //定位结束
                    case GpsStatus.GPS_EVENT_STOPPED:
                        Log.i(TAG, "定位结束");
                        break;
                }
            }
        });

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, new LocationListener() {
            /**
             * 位置信息变化时触发
             */
            public void onLocationChanged(Location location) {
                updateView(location);
                Log.i(TAG, "时间：" + location.getTime());
                Log.i(TAG, "经度：" + location.getLongitude());
                Log.i(TAG, "纬度：" + location.getLatitude());
                Log.i(TAG, "海拔：" + location.getAltitude());
            }


            /**
             * GPS状态变化时触发
             */
            public void onStatusChanged(String provider, int status, Bundle extras) {
                switch (status) {
                    //GPS状态为可见时
                    case LocationProvider.AVAILABLE:
                        Log.i(TAG, "当前GPS状态为可见状态");
                        break;
                    //GPS状态为服务区外时
                    case LocationProvider.OUT_OF_SERVICE:
                        Log.i(TAG, "当前GPS状态为服务区外状态");
                        break;
                    //GPS状态为暂停服务时
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Log.i(TAG, "当前GPS状态为暂停服务状态");
                        break;
                }
            }

            /**
             * GPS开启时触发
             */
            public void onProviderEnabled(String provider) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);
                updateView(location);
            }

            /**
             * GPS禁用时触发
             */
            public void onProviderDisabled(String provider) {
                updateView(null);
            }

        });
    }

    private void updateView(Location location) {
        if (location!=null) {
            longitude.setText("纬度:" + location.getLongitude());
            latitude.setText("经度:" + location.getLatitude());
            altitude.setText("海拔:" + location.getAltitude());
        }else{
            longitude.setText("location获取失败");
        }
    }


    private Criteria getCtiteria() {
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }


}

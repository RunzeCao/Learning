package com.example.myapplication;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.ble.ConnectDialog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";
    private Button btn_info_fix, btn_baidu, btn_ble;
    long downloadId;
    DownLoadCompleteReceiver downLoadCompleteReceiver;
    DownloadManager downloadManager;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_info_fix = (Button) findViewById(R.id.btn_info_fix);
        btn_baidu = (Button) findViewById(R.id.btn_baidu);
        btn_ble = (Button) findViewById(R.id.btn_ble);
        btn_info_fix.setOnClickListener(this);
        btn_baidu.setOnClickListener(this);
        btn_ble.setOnClickListener(this);
        downLoadCompleteReceiver = new DownLoadCompleteReceiver();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downLoadCompleteReceiver, mFilter);
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        queryDownTask(downloadManager, downloadId);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_info_fix:
                //goAction(MineActivity.class, false);
                String url = "http://120.25.12.47:801/File/AC44C71B490C4A3F3067460B5A422D9F/8010222_V1.0.4.zip";
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setDestinationInExternalPublicDir("Trinea", "MeiLiShuo.apk");
                request.setTitle("MeiLiShuo");
                request.setDescription("MeiLiShuo desc");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                request.setMimeType("application/cn.trinea.download.file");
                downloadId = downloadManager.enqueue(request);
                break;
            case R.id.btn_baidu:
                // goAction(BaiduActivity.class, false);
            /*    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {
                    callPhone();
                }*/
                callPhone();
                break;
            case R.id.btn_ble:
                //goAction(BleScanActivity.class, false);
                FragmentManager fm = getSupportFragmentManager();
                final ConnectDialog registerDialog = ConnectDialog.newInstance();
                registerDialog.show(fm, "fragment_register_success");
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(downLoadCompleteReceiver);
    }

    private class DownLoadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long id = downloadId;
                Toast.makeText(MainActivity.this, "编号：" + id + "的下载任务已经完成！", Toast.LENGTH_SHORT).show();
                queryDownTask(downloadManager, downloadId);
            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                Toast.makeText(MainActivity.this, "别瞎点！！！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void queryDownTask(DownloadManager downManager, long id) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(879);
        Cursor cursor = downManager.query(query);

        while (cursor.moveToNext()) {
            String downId = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String status = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String size = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            String sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            Log.i(TAG, "queryDownTask:\ndownId" + downId + "\ntitle:" + title + "\naddress:" + address + "\nsize:" + size + "\nsizeTotal:" + sizeTotal + "\nstatus: " + status);
        }
        cursor.close();
    }

    public void callPhone() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE)) {
              /*  AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("友情提示");
                builder.setMessage("打开权限才能打电话");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();*/
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + "10086");
            intent.setData(data);
            startActivity(intent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    /*    if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }*/
        Log.i(TAG, "onRequestPermissionsResult: ");
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult: " + "PERMISSION_GRANTED");
                    callPhone();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onRequestPermissionsResult: " + "PERMISSION_Denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

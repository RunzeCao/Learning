package com.example.myapplication;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.ble.ConnectDialog;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";
    private Button btn_info_fix, btn_baidu, btn_ble;
    long downloadId;
    DownLoadCompleteReceiver downLoadCompleteReceiver;
    DownloadManager downloadManager;

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

                // String apkUrl = "http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk";
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
            //String statuss = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String size = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            String sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            Log.i(TAG, "queryDownTask:\ndownId" + downId + "\ntitle:" + title + "\naddress:" + address + "\nsize:" + size + "\nsizeTotal:" + sizeTotal);
         /*   Map<String, String> map = new HashMap<String, String>();
            map.put("downid", downId);
            map.put("title", title);
            map.put("address", address);
            map.put("status", sizeTotal+":"+size);*/
        }
        cursor.close();
    }


}

package com.example.myapplication.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by 123 on 2016/4/1.
 */
public class DBHelper {
    private SQLiteDatabase db;
    private Context context;
    private DBManager dbManager;

    public DBHelper(Context context) {
        super();
        this.context = context;
        dbManager = new DBManager(context);

    }

    public String getPostID(String province, String city) {
        Log.i("YWS","province:"+province+",city:"+city);
        String postID = "";
        dbManager.openDatabase();
        Log.i("YWS","dbManager:"+dbManager);
        db = dbManager.getDatabase();
        Log.i("YWS","db:"+db);
        try {
            String sql = "select * from city where name='" + city + "' and province='" + province + "'";
            String sql1 = "select * from city where name='" + city + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null) {
                int count = cursor.getCount();
                if (count < 1) {
                    cursor = db.rawQuery(sql1, null);
                }
                cursor.moveToFirst();
                postID = cursor.getString(cursor.getColumnIndex("postID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDatabase();
            db.close();
        }
        return postID;
    }
}

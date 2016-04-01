package com.example.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 123 on 2016/4/1.
 */
public class DBManager {
    private final int BUFFER_SIZE = 1024;
    public static final String DB_NAME = "city.db";
    public String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/";
    private SQLiteDatabase database;
    private Context context;
    private File file = null;

    DBManager(Context context) {
        this.context = context;
        String packageName = context.getPackageName();
        DB_PATH = DB_PATH + packageName;
    }

    public void openDatabase() {
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    private SQLiteDatabase openDatabase(String dbFile) {
        try {
            file = new File(dbFile);
            if (!file.exists()) {
                InputStream is = context.getResources().openRawResource(R.raw.city);
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                    fos.flush();
                }
                fos.close();
                is.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
            return database;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeDatabase() {
        if (this.database != null)
            this.database.close();
    }

}

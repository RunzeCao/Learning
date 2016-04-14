package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.volley.RequestManager;

public class BaseActivity extends Activity {

    protected Context mContext;
    protected Activity mActivity;
    protected Resources mRes;

    private void init() {
        mContext = this;
        mActivity = this;
        mRes = this.getResources();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void goAction(Class cls, boolean closed) {
        Intent intent = new Intent(this, cls);
        mContext.startActivity(intent);
        if (closed) {
            this.finish();
        }
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }


}

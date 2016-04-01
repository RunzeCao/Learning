package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    private TextView display,display2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        display = (TextView) findViewById(R.id.display);
        display2 = (TextView) findViewById(R.id.display2);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        display.setText(displayMetrics.toString());
        float width=displayMetrics.widthPixels*displayMetrics.density;
        float height=displayMetrics.heightPixels*displayMetrics.density;
        display2.setText("width:"+width+"height:"+height);

    }
}

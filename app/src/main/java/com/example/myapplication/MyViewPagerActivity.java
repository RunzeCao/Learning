package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myapplication.wight.MyViewPager;

public class MyViewPagerActivity extends BaseActivity {

    private MyViewPager myViewPager;
    private RadioGroup radioGroup;
    private int[] ids = {R.mipmap.a1, R.mipmap.a2, R.mipmap.a3,
            R.mipmap.a4, R.mipmap.a5, R.mipmap.a6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_pager);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        myViewPager = (MyViewPager) findViewById(R.id.myViewPager);
        for (int i = 0; i < ids.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(ids[i]);
            myViewPager.addView(image);

        }
        View v = View.inflate(this,R.layout.test,null);
        myViewPager.addView(v);

        for (int i = 0; i < myViewPager.getChildCount(); i++) {
            final RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioGroup.addView(radioButton);
            if (i == 0) {
                radioButton.setChecked(true);
            }
        }
        myViewPager.setChangeListener(new MyViewPager.PageChangeListener() {
            @Override
            public void moveTo(int curIndex) {
                Log.d("setChangeListener","curIndex="+curIndex);
                radioGroup.check(curIndex);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                myViewPager.moveTo(checkedId);
            }
        });
    }
}

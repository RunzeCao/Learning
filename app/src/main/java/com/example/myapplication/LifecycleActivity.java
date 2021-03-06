package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.wight.CustomTitleView;
import com.example.myapplication.wight.MyListView;

import java.util.ArrayList;
import java.util.List;

public class LifecycleActivity extends BaseActivity {


    private MyAdapter adapter;
    //Activity被创建时调用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        final CustomTitleView customTitleView = (CustomTitleView) findViewById(R.id.myView);
        customTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customTitleView.setVisibility(View.INVISIBLE);
            }
        });

        initList();
        MyListView myListView = (MyListView) findViewById(R.id.myListView);
        adapter = new MyAdapter(this,0,contentList);
        myListView.setAdapter(adapter);
        myListView.setOnDeleteListener(new MyListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                contentList.remove(index);
                adapter.notifyDataSetChanged();
            }
        });
/*
        final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
        pie.addItem("Agamemnon", 2, getResources().getColor(R.color.seafoam));
        pie.addItem("Bocephus", 3.5f, getResources().getColor(R.color.chartreuse));
        pie.addItem("Calliope", 2.5f, getResources().getColor(R.color.emerald));
        pie.addItem("Daedalus", 3, getResources().getColor(R.color.bluegrass));
        pie.addItem("Euripides", 1, getResources().getColor(R.color.turquoise));
        pie.addItem("Ganymede", 3, getResources().getColor(R.color.slate));

        ((Button) findViewById(R.id.Reset)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pie.setCurrentItem(0);
            }
        });*/

    }

    //Activity被创建或者从后台重新回到前台时调用
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Activity被系统杀死后再重建时被调用.
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死,用户又启动该Activity.
     * 这两种情况下onRestoreInstanceState都会被调用,在onStart之后.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    //Activity从后台重新回到前台时调用
    @Override
    protected void onRestart() {
        super.onRestart();
    }


    //Activity被创建或从被覆盖、后台重新回到前台时调用
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Activity被系统杀死时被调用.
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死.
     * 另外,当跳转到其他Activity或者按Home键回到主屏时该方法也会被调用,系统是为了保存当前View组件的状态.
     * 在onPause之前被调用.
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    //Activity被覆盖到下面或锁屏时调用
    @Override
    protected void onPause() {
        super.onPause();
    }

    //退出当前Activity或者跳转到新的Activity时被调用
    @Override
    protected void onStop() {
        super.onStop();
    }

    //退出当前Activity时调用，调用过后Activity结束
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private List<String> contentList = new ArrayList<String>();

    private void initList() {
        contentList.add("Content Item 1");
        contentList.add("Content Item 2");
        contentList.add("Content Item 3");
        contentList.add("Content Item 4");
        contentList.add("Content Item 5");
        contentList.add("Content Item 6");
        contentList.add("Content Item 7");
        contentList.add("Content Item 8");
        contentList.add("Content Item 9");
        contentList.add("Content Item 10");
        contentList.add("Content Item 11");
        contentList.add("Content Item 12");
        contentList.add("Content Item 13");
        contentList.add("Content Item 14");
        contentList.add("Content Item 15");
        contentList.add("Content Item 16");
        contentList.add("Content Item 17");
        contentList.add("Content Item 18");
        contentList.add("Content Item 19");
        contentList.add("Content Item 20");
    }

    private class MyAdapter extends ArrayAdapter {
        public MyAdapter(Context context, int resource, List<String> lists) {
            super(context, resource, lists);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.my_list_view_item, null);
            } else {
                view = convertView;
            }
            TextView textView = (TextView) view.findViewById(R.id.text_view);
            textView.setText((String)getItem(position));
            return view;
        }
    }
}

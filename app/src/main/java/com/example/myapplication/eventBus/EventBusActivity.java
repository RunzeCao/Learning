package com.example.myapplication.eventBus;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.utils.GuideUtil;

public class EventBusActivity extends FragmentActivity {
    private GuideUtil guideUtil = null;
    private ImageView imgView;
    private LinearLayout linearLayout;
    private FrameLayout mParentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        guideUtil = GuideUtil.getInstance();
        if (GuideUtil.isNeverShowed(this, "801")) {
            guideUtil.showGuide(EventBusActivity.this, R.mipmap.guide_pic, "802");
        }
   /*     linearLayout = (LinearLayout) findViewById(R.id.ll);
        *//*guideUtil = GuideUtil.getInstance();
        guideUtil.initGuide(EventBusActivity.this, R.mipmap.guide_pic);*//*
       AlertDialog dialog = new AlertDialog.Builder(this, R.style.dialog).create();

        View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);*/

       /* final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 设置显示的类型，TYPE_PHONE指的是来电话的时候会被覆盖，其他时候会在最前端，显示位置在stateBar下面，其他更多的值请查阅文档
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        // 设置显示格式
        params.format = PixelFormat.RGBA_8888;
        // 设置对齐方式
        params.gravity = Gravity.START | Gravity.TOP;
        // 设置宽高
        params.width = UiUtil.getWindowsW(this);
        params.height = UiUtil.getWindowsH(this);
        LogUtils.d("guide", "" + UiUtil.getWindowsW(this) + UiUtil.getWindowsH(this));
        // 设置动画
       // params.windowAnimations = R.style.view_anim;

        // 添加到当前的窗口上
        windowManager.addView(imgView, params);

       */
    }

    @Override
    protected void onResume() {
        super.onResume();

    /*PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, new String[]{
        Manifest.permission.SYSTEM_ALERT_WINDOW}, new PermissionsResultAction() {
        @Override
        public void onGranted() {
            guideUtil.initGuide(EventBusActivity.this,R.mipmap.guide_pic);
        }

        @Override
        public void onDenied(String permission) {

        }
    });
    }*/
    }
}

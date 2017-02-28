package com.example.myapplication.ble;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.myapplication.R;


public class ConnectDialog extends DialogFragment {
    private static final String TAG = ConnectDialog.class.getSimpleName();
    ImageView imageView;

    public ConnectDialog() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setStyle(int style, @StyleRes int theme) {
        super.setStyle(style, theme);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: "+imageView.getAnimation().hasEnded());
        super.onDetach();
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: "+imageView.getAnimation().hasEnded());
        super.onStart();
    }

    public static ConnectDialog newInstance() {
        return new ConnectDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_connect, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.iv_dialog_connect);
        startAnim(imageView);
        Log.d(TAG, "onViewCreated: "+imageView.getAnimation().hasEnded());
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: "+imageView.getAnimation().hasEnded());
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: "+imageView.getAnimation().hasEnded());
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: "+imageView.getAnimation().hasEnded());
        super.onStop();
        //imageView.clearAnimation();
    }

    @Override
    public void onDestroyView() {
        //Log.d(TAG, "onDestroyView: "+imageView.getAnimation().hasEnded());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private void startAnim(View v) {
        v.clearAnimation();
        RotateAnimation ra = new RotateAnimation(360f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        ra.setInterpolator(lin);
        ra.setDuration(1000);
        ra.setRepeatCount(Animation.INFINITE);
        v.setAnimation(ra);
        ra.startNow();
    }
}

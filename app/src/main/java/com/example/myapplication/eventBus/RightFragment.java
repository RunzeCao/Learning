package com.example.myapplication.eventBus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment {

    private static final String TAG = RightFragment.class.getSimpleName();

    private TextView tv;

    public RightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_right, null);
        tv = (TextView) v.findViewById(R.id.tv_right);
        return v;
    }

    /**
     * 与发布者在同一个线程
     *
     * @param msg 事件1
     */
    @Subscribe
    public void onEvent(MsgEvent1 msg) {
        String content = msg.getMsg() + "\n ThreadName: " + Thread.currentThread().getName() + "\n ThreadId: " + Thread.currentThread().getId();
        Log.d(TAG, "onEvent(MsgEvent1 msg)收到" + content);
    }


    /**
     * 与发布者在同一个线程
     *
     * @param msg 事件2
     */
    @Subscribe
    public void onEvent(MsgEvent2 msg) {
        String content = msg.getMsg()
                + "\n ThreadName: " + Thread.currentThread().getName()
                + "\n ThreadId: " + Thread.currentThread().getId();
        System.out.println("onEvent(MsgEvent2 msg)收到" + content);
        tv.setText(content);
    }

    /**
     * 执行在主线程。
     * 非常实用，可以在这里将子线程加载到的数据直接设置到界面中。
     *
     * @param msg 事件1
     */
    @Subscribe
    public void onEventMainThread(MsgEvent1 msg) {
        String content = msg.getMsg()
                + "\n ThreadName: " + Thread.currentThread().getName()
                + "\n ThreadId: " + Thread.currentThread().getId();
        Log.d(TAG, "onEvent(MsgEvent1 msg)收到" + content);
        tv.setText(content);

    }

    /**
     * 执行在在一个新的子线程
     * 适用于多个线程任务处理， 内部有线程池管理。
     *
     * @param msg 事件1
     */
    @Subscribe
    public void onEventAsync(MsgEvent1 msg) {
        String content = msg.getMsg()
                + "\n ThreadName: " + Thread.currentThread().getName()
                + "\n ThreadId: " + Thread.currentThread().getId();
        Log.d(TAG, "onEvent(MsgEvent1 msg)收到" + content);
    }

    /**
     * 执行在子线程，如果发布者是子线程则直接执行，如果发布者不是子线程，则创建一个再执行
     * 此处可能会有线程阻塞问题。
     *
     * @param msg 事件1
     */
    @Subscribe
    public void onEventBackgroundThread(MsgEvent1 msg) {
        String content = msg.getMsg()
                + "\n ThreadName: " + Thread.currentThread().getName()
                + "\n ThreadId: " + Thread.currentThread().getId();
        Log.d(TAG, "onEvent(MsgEvent1 msg)收到" + content);
    }
}

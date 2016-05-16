package com.example.myapplication.eventBus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeftFragment extends Fragment {


    private static final String TAG = LeftFragment.class.getSimpleName();

    private ListView lv_left;

    String[] strs = new String[]{"主线程消息1", "子线程消息1", "主线程消息2"};

    public LeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_left, null);
        lv_left = (ListView) v.findViewById(R.id.lv_left);
        lv_left.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strs));
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Log.d(TAG, "----------------------主线程发的消息1"
                                + " threadName: " + Thread.currentThread().getName()
                                + " threadId: " + Thread.currentThread().getId());
                        EventBus.getDefault().post(new MsgEvent1("主线程消息1"));
                        break;
                    case 1:
                        // 子线程
                        new Thread() {
                            public void run() {
                                Log.d(TAG, "----------------------子线程发的消息1"
                                                + " threadName: " + Thread.currentThread().getName()
                                                + " threadId: " + Thread.currentThread().getId());
                                EventBus.getDefault().post(new MsgEvent1("子线程发的消息1"));
                            }

                            ;
                        }.start();
                        break;
                    case 2:
                        // 主线程
                        Log.d(TAG,
                                "----------------------主线程发的消息2"
                                        + " threadName: "+ Thread.currentThread().getName()
                                        + " threadId: " + Thread.currentThread().getId());
                        EventBus.getDefault().post(new MsgEvent2("主线程发的消息2"));
                        break;
                    default:
                        break;
                }
            }
        });
        return v;
    }

}

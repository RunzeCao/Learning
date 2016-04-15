package com.example.myapplication;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.mine.UserConstant;
import com.example.myapplication.utils.ToastUtils;

/**
 * Created by 123 on 2016/4/14.
 */
public class PersionalInfoActivity2 extends BaseActivity implements View.OnClickListener {
    private static final String TAG = PersionalInfoActivity2.class.getSimpleName();

    private LinearLayout info2__height_llt;
    private TextView info2_height;
    private LinearLayout info2__weight_llt;
    private TextView info2_weight;
    private Button info2_continue;
    private Button info2_have_account;

    private String[] mHeightData;
    private String[] mWeightData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_info2);
        initViews();
        initDatas();
    }

    private void initDatas() {
        mHeightData = mRes.getStringArray(R.array.height);
        mWeightData = mRes.getStringArray(R.array.weight);
    }

    private void initViews() {

        info2__height_llt = (LinearLayout) this.findViewById(R.id.info2__height_llt);
        info2__height_llt.setOnClickListener(this);
        info2_height = (TextView) this.findViewById(R.id.info2_height);
        info2__weight_llt = (LinearLayout) this.findViewById(R.id.info2__weight_llt);
        info2__weight_llt.setOnClickListener(this);
        info2_weight = (TextView) this.findViewById(R.id.info2_weight);

        info2_continue = (Button) this.findViewById(R.id.info2_continue);
        info2_continue.setOnClickListener(this);
        info2_have_account = (Button) this.findViewById(R.id.info2_have_account);
        info2_have_account.setOnClickListener(this);
    }

    public int lastHeightSelect = 0;
    public int lastWeightSelect = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info2__height_llt:

                new AlertDialog.Builder(mContext).setTitle("身高").setSingleChoiceItems(mHeightData, lastHeightSelect, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        info2_height.setText(mHeightData[which]);
                        lastHeightSelect = which;
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.info2__weight_llt:

                new AlertDialog.Builder(mContext).setTitle("体重").setSingleChoiceItems(mWeightData, lastWeightSelect, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        info2_weight.setText(mWeightData[which]);
                        lastWeightSelect = which;
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.info2_continue:
                continueHandle();
                break;
            case R.id.info2_have_account:
                break;
            default:
                break;
        }
    }

    private void continueHandle() {
        String userHeight = info2_height.getText().toString().trim();
        if (TextUtils.isEmpty(userHeight)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.info_user_height_empty));
            return;
        }
        String userWeight = info2_weight.getText().toString().trim();
        if (TextUtils.isEmpty(userWeight)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.info_user_weight_empty));
            return;
        }
        Log.d(TAG, "userHeight:" + userHeight + ",userWeight:" + userWeight);
        UserConstant.saveUserHeight(mContext, userHeight);
        UserConstant.saveUserWeight(mContext, userWeight);
        goAction(PersionalInfoActivity3.class, false);
    }
}

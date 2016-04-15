package com.example.myapplication;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.mine.UserConstant;
import com.example.myapplication.utils.LogUtils;
import com.example.myapplication.utils.ToastUtils;

import java.util.Calendar;

/**
 * Created by 123 on 2016/4/13.
 */
public class PersionalInfoActivity1 extends BaseActivity implements View.OnClickListener {
    private static final String TAG = PersionalInfoActivity1.class.getSimpleName();

    private EditText info1_name;
    private LinearLayout info1__birthday_llt;
    private TextView info1_birthday;
    private RadioGroup info1_gender;
    private RadioButton info1_male;
    private RadioButton info1_female;
    private Button info1_continue;
    private Button info1_have_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_info1);
        initViews();
    }

    private void initViews() {

        info1_name = (EditText) this.findViewById(R.id.info1_name);
        info1__birthday_llt = (LinearLayout) this.findViewById(R.id.info1__birthday_llt);
        info1__birthday_llt.setOnClickListener(this);
        info1_birthday = (TextView) this.findViewById(R.id.info1_birthday);
        info1_gender = (RadioGroup) this.findViewById(R.id.info1_gender);
        info1_male = (RadioButton) this.findViewById(R.id.info1_male);
        info1_female = (RadioButton) this.findViewById(R.id.info1_female);

        info1_continue = (Button) this.findViewById(R.id.info1_continue);
        info1_continue.setOnClickListener(this);
        info1_have_account = (Button) this.findViewById(R.id.info1_have_account);
        info1_have_account.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info1__birthday_llt:
                birthdayHandle();
                break;
            case R.id.info1_continue:
                continueHandle();
                break;
            case R.id.info1_have_account:
                break;
        }
    }

    private void continueHandle() {
        String userName = info1_name.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.info_user_name_empty));
            return;
        }
        String userBirthday = info1_birthday.getText().toString().trim();
        if (TextUtils.isEmpty(userBirthday)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.info_user_birthday_empty));
            return;
        }

        String userGender = getGender();
        if (TextUtils.isEmpty(userGender)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.info_user_gender_empty));
            return;
        }
        LogUtils.d(TAG, "userName:" + userName + ",userBirthday:" + userBirthday + ",userGender:" + userGender);
        UserConstant.saveUserName(mContext, userName);
        UserConstant.saveUserBirthday(mContext, userBirthday);
        UserConstant.saveUserSex(mContext, userGender);
        goAction(PersionalInfoActivity2.class, false);
    }

    private String getGender() {
        String userGender = null;
        int sexId = info1_gender.getCheckedRadioButtonId();
        if (sexId == -1) {

        } else {
            if (sexId == R.id.info1_male) {//男
                userGender = mRes.getString(R.string.gender_male);
            } else {//女
                userGender = mRes.getString(R.string.gender_femal);
            }
        }
        return userGender;
    }

    private void birthdayHandle() {
     /*   DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(),"datePicker");
        String birth =DatePickerFragment.birth;
        info1_birthday.setText(birth);*/
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(mContext, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear+1;
                String birth = year + "-" + month + "-" + dayOfMonth;
                info1_birthday.setText(birth);
            }
        }, year, month, day).show();
    }
}

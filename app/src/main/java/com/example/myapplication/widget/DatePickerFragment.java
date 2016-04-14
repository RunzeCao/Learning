package com.example.myapplication.widget;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.myapplication.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 123 on 2016/4/14.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

     public static String birth = null;
    Calendar calendar = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        LogUtils.i("OnDateSet", "select year:" + year + ";month:" + monthOfYear + ";day:" + dayOfMonth);

        birth = year+"-"+monthOfYear+"-"+dayOfMonth;
    }


}

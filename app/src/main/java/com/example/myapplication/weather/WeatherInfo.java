package com.example.myapplication.weather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 123 on 2016/4/5.
 */
public class WeatherInfo implements Parcelable{


    protected WeatherInfo(Parcel in) {
    }

    public static final Creator<WeatherInfo> CREATOR = new Creator<WeatherInfo>() {
        @Override
        public WeatherInfo createFromParcel(Parcel in) {
            return new WeatherInfo(in);
        }

        @Override
        public WeatherInfo[] newArray(int size) {
            return new WeatherInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}

package com.example.myapplication.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by 123 on 2016/4/5.
 * 通过writeToParcel将你的对象映射成Parcel对象，再通过createFromParcel将Parcel对象映射成你的对象。
 * 也可以将Parcel看成是一个流，通过writeToParcel把对象写到流里面，在通过createFromParcel从流里读取对象，只不过这个过程需要你来实现，因此写的顺序和读的顺序必须一致。
 */
public class Today implements Parcelable {
    private String date;
    private int humidityMax;
    private int humidityMin;
    private int tempMax;
    private int tempMin;
    private String weatherEnd;
    private String weatherStart;
    private String windDirectionEnd;
    private String windDirectionStart;
    private int windMax;
    private int windMin;

    public Today() {
    };

    protected Today(Parcel in) {
        date = in.readString();
        humidityMax = in.readInt();
        humidityMin = in.readInt();
        tempMax = in.readInt();
        tempMin = in.readInt();
        weatherEnd = in.readString();
        weatherStart = in.readString();
        windDirectionEnd = in.readString();
        windDirectionStart = in.readString();
        windMax = in.readInt();
        windMin = in.readInt();
    }

    //读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。因为实现类在这里还是不可知的，所以需要用到模板的方式，继承类名通过模板参数传入
    //为了能够实现模板参数的传入，这里定义Creator嵌入接口,内含两个接口函数分别返回单个和多个继承类实例
    public static final Creator<Today> CREATOR = new Creator<Today>() {
        @Override
        public Today createFromParcel(Parcel in) {
            return new Today(in);
        }

        @Override
        public Today[] newArray(int size) {
            return new Today[size];
        }
    };

    //内容描述接口，基本不用管
    @Override
    public int describeContents() {
        return 0;
    }

    //写入接口函数，打包
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeInt(humidityMax);
        dest.writeInt(humidityMin);
        dest.writeInt(tempMax);
        dest.writeInt(tempMin);
        dest.writeString(weatherEnd);
        dest.writeString(weatherStart);
        dest.writeString(windDirectionEnd);
        dest.writeString(windDirectionStart);
        dest.writeInt(windMax);
        dest.writeInt(windMin);
    }

    @Override
    public String toString() {
        return "Today{" +
                "date=" + date +
                ", humidityMax=" + humidityMax +
                ", humidityMin=" + humidityMin +
                ", tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                ", weatherEnd='" + weatherEnd + '\'' +
                ", weatherStart='" + weatherStart + '\'' +
                ", windDirectionEnd='" + windDirectionEnd + '\'' +
                ", windDirectionStart='" + windDirectionStart + '\'' +
                ", windMax=" + windMax +
                ", windMin=" + windMin +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWindMin() {
        return windMin;
    }

    public void setWindMin(int windMin) {
        this.windMin = windMin;
    }

    public int getWindMax() {
        return windMax;
    }

    public void setWindMax(int windMax) {
        this.windMax = windMax;
    }

    public String getWindDirectionStart() {
        return windDirectionStart;
    }

    public void setWindDirectionStart(String windDirectionStart) {
        this.windDirectionStart = windDirectionStart;
    }

    public String getWindDirectionEnd() {
        return windDirectionEnd;
    }

    public void setWindDirectionEnd(String windDirectionEnd) {
        this.windDirectionEnd = windDirectionEnd;
    }

    public String getWeatherStart() {
        return weatherStart;
    }

    public void setWeatherStart(String weatherStart) {
        this.weatherStart = weatherStart;
    }

    public String getWeatherEnd() {
        return weatherEnd;
    }

    public void setWeatherEnd(String weatherEnd) {
        this.weatherEnd = weatherEnd;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public int getHumidityMin() {
        return humidityMin;
    }

    public void setHumidityMin(int humidityMin) {
        this.humidityMin = humidityMin;
    }

    public int getHumidityMax() {
        return humidityMax;
    }

    public void setHumidityMax(int humidityMax) {
        this.humidityMax = humidityMax;
    }

}

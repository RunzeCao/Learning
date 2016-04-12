package com.example.myapplication.weather;

/**
 * Created by 123 on 2016/4/12.
 */
public class TodayGson {

    /**
     * cityCode :
     * date :
     * humidityMax : 0
     * humidityMin : 0
     * precipitationMax : 0
     * precipitationMin : 0
     * tempMax : 0
     * tempMin : 0
     * weatherEnd :
     * weatherStart :
     * windDirectionEnd :
     * windDirectionStart :
     * windMax : 0
     * windMin : 0
     */

    private String cityCode;
    private String date;
    private String humidityMax;
    private String humidityMin;
    private String precipitationMax;
    private String precipitationMin;
    private String tempMax;
    private String tempMin;
    private String weatherEnd;
    private String weatherStart;
    private String windDirectionEnd;
    private String windDirectionStart;
    private String windMax;
    private String windMin;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public String toString() {
        return "TodayGson{" +
                "cityCode='" + cityCode + '\'' +
                ", date='" + date + '\'' +
                ", humidityMax=" + humidityMax +
                ", humidityMin=" + humidityMin +
                ", precipitationMax=" + precipitationMax +
                ", precipitationMin=" + precipitationMin +
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

    public String getHumidityMax() {
        return humidityMax;
    }

    public void setHumidityMax(String humidityMax) {
        this.humidityMax = humidityMax;
    }

    public String getHumidityMin() {
        return humidityMin;
    }

    public void setHumidityMin(String humidityMin) {
        this.humidityMin = humidityMin;
    }

    public String getPrecipitationMax() {
        return precipitationMax;
    }

    public void setPrecipitationMax(String precipitationMax) {
        this.precipitationMax = precipitationMax;
    }

    public String getPrecipitationMin() {
        return precipitationMin;
    }

    public void setPrecipitationMin(String precipitationMin) {
        this.precipitationMin = precipitationMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getWeatherEnd() {
        return weatherEnd;
    }

    public void setWeatherEnd(String weatherEnd) {
        this.weatherEnd = weatherEnd;
    }

    public String getWeatherStart() {
        return weatherStart;
    }

    public void setWeatherStart(String weatherStart) {
        this.weatherStart = weatherStart;
    }

    public String getWindDirectionEnd() {
        return windDirectionEnd;
    }

    public void setWindDirectionEnd(String windDirectionEnd) {
        this.windDirectionEnd = windDirectionEnd;
    }

    public String getWindDirectionStart() {
        return windDirectionStart;
    }

    public void setWindDirectionStart(String windDirectionStart) {
        this.windDirectionStart = windDirectionStart;
    }

    public String getWindMax() {
        return windMax;
    }

    public void setWindMax(String windMax) {
        this.windMax = windMax;
    }

    public String getWindMin() {
        return windMin;
    }

    public void setWindMin(String windMin) {
        this.windMin = windMin;
    }

}

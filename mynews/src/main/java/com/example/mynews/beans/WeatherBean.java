package com.example.mynews.beans;

/**
 * Created by frank on 2016/9/16.
 */
public class WeatherBean {
   private String tempretrue;
    private String week;
    private String date;
    private String wind;
    private String weather;
    private int ImgRes;

    public String getTempretrue() {
        return tempretrue;
    }

    public void setTempretrue(String tempretrue) {
        this.tempretrue = tempretrue;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getImgRes() {
        return ImgRes;
    }

    public void setImgRes(int imgRes) {
        ImgRes = imgRes;
    }
}

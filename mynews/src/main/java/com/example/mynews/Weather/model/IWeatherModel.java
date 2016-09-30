package com.example.mynews.Weather.model;

import android.content.Context;

import com.example.mynews.beans.WeatherBean;

import java.util.List;

/**
 * Created by frank on 2016/9/16.
 */
public interface IWeatherModel {

    void loadWeather(String city, OnLoadWeatherListener onLoadWeatherListener);
    void loadLocation(Context context,OnLoadLocationListener onLoadLocationListener);

    interface OnLoadWeatherListener{
        void onSuccess(List<WeatherBean> list);
        void onFailed(String msg,Exception e);
    }

    interface OnLoadLocationListener{
        void onSuccess(String city);
        void onFailed(String msg);
    }
}

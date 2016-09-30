package com.example.mynews.Weather.view;

import com.example.mynews.beans.WeatherBean;

import java.util.List;

/**
 * Created by frank on 2016/9/16.
 */
public interface IWeatherView {

    void showProgress();
    void hideProgress();
    void showWeatherLayout();

    void setCity(String city);
    void setToday(String data);
    void setTemperature(String temperature);
    void setWind(String wind);
    void setWeather(String weather);
    void setWeatherImage(int res);
    void setWeatherData(List<WeatherBean> lists);

    void showErrorToast(String msg);
}

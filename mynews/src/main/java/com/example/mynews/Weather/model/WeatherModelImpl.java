package com.example.mynews.Weather.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.text.TextUtils;

import com.example.mynews.Constant.Urls;
import com.example.mynews.Utils.OkHttpUtil;
import com.example.mynews.Utils.ToolUtils;
import com.example.mynews.Weather.WeatherJsonUtil;
import com.example.mynews.beans.WeatherBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Permission;
import java.util.List;

/**
 * Created by frank on 2016/9/16.
 */
public class WeatherModelImpl implements IWeatherModel {


    @Override
    public void loadWeather(String city, final OnLoadWeatherListener weathercallback) {
        try {
            String url= Urls.WEATHER+ URLEncoder.encode(city,"utf-8");
            OkHttpUtil.ResultCallback<String> resultCallback=new OkHttpUtil.ResultCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    List<WeatherBean> weatherBeens= WeatherJsonUtil.getWeatherInfo(response);
                    weathercallback.onSuccess(weatherBeens);
                }

                @Override
                public void onFailure(Exception e) {
                    weathercallback.onFailed("load weather failed",e);
                }
            };
            OkHttpUtil.get(url,resultCallback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadLocation(Context context, final OnLoadLocationListener onLoadLocationListener) {

        LocationManager locationManager= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                onLoadLocationListener.onFailed("location failed");
                return;
            }
        }
        Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location==null){
            onLoadLocationListener.onFailed("location failed");
            return;
        }
        double lat=location.getLatitude();
        double lng=location.getLongitude();
        String url=getLocationUrl(lat,lng);
        OkHttpUtil.ResultCallback<String> resultCallback=new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                String city=WeatherJsonUtil.getCity(response);
                if(TextUtils.isEmpty(city)){
                    onLoadLocationListener.onFailed("location failed");
                }else {
                    onLoadLocationListener.onSuccess(city);
                }

            }

            @Override
            public void onFailure(Exception e) {
                onLoadLocationListener.onFailed("location failed");
            }
        };
        OkHttpUtil.get(url,resultCallback);
    }

    private String getLocationUrl(double lat, double lng) {
        StringBuffer sb = new StringBuffer(Urls.INTERFACE_LOCATION);
        sb.append("?output=json").append("&referer=32D45CBEEC107315C553AD1131915D366EEF79B4");
        sb.append("&location=").append(lat).append(",").append(lng);
        return sb.toString();
    }
}

package com.example.mynews.Weather;

import android.text.TextUtils;

import com.example.mynews.R;
import com.example.mynews.beans.WeatherBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2016/9/16.
 */
public class WeatherJsonUtil {

    public static String getCity(String json){
        JsonParser jsonParser=new JsonParser();
        JsonObject jsonObject=jsonParser.parse(json).getAsJsonObject();
        JsonElement jsonElement=jsonObject.get("status");
        if(jsonElement!=null && jsonElement.getAsString().equals("OK")){
            JsonObject result=jsonObject.getAsJsonObject("result");
            if(result!=null){
                JsonObject addressComponent=jsonObject.getAsJsonObject("addressComponent");
                if(addressComponent!=null){
                    JsonElement city=addressComponent.get("city");
                    if(city!=null){
                        return city.getAsString().replace("市","");
                    }
                }
            }
        }
        return null;
    }

    public static List<WeatherBean> getWeatherInfo(String json){
        List<WeatherBean> list=new ArrayList<WeatherBean>();

        JsonParser jsonParser=new JsonParser();
        JsonObject jsonObject=jsonParser.parse(json).getAsJsonObject();
        JsonElement jsonElement=jsonObject.get("status");
        if(jsonElement.toString().equals("1000")){
            JsonArray jsonArray=jsonObject.getAsJsonObject("data").getAsJsonArray("forecast");
            for(int i=0;i<jsonArray.size();i++){
                WeatherBean weatherBean=getWeatherFromJson(jsonArray.get(i).getAsJsonObject());
                list.add(weatherBean);
            }
        }

        return list;
    }

    private static WeatherBean getWeatherFromJson(JsonObject jsonObject) {
        String temperature = jsonObject.get("high").getAsString() + " " + jsonObject.get("low").getAsString();
        String weather = jsonObject.get("type").getAsString();
        String wind = jsonObject.get("fengxiang").getAsString();
        String date = jsonObject.get("date").getAsString();

        WeatherBean weatherBean = new WeatherBean();

        weatherBean.setDate(date);
        weatherBean.setTempretrue(temperature);
        weatherBean.setWeather(weather);
        weatherBean.setWeek(date.substring(date.length()-3));
        weatherBean.setWind(wind);
        weatherBean.setImgRes(getWeatherImage(weather));
        return weatherBean;
    }

    private static int getWeatherImage(String weather) {
        if (weather.equals("多云") || weather.equals("多云转阴") || weather.equals("多云转晴")) {
            return R.mipmap.biz_plugin_weather_duoyun;
        } else if (weather.equals("中雨") || weather.equals("中到大雨")) {
            return R.mipmap.biz_plugin_weather_zhongyu;
        } else if (weather.equals("雷阵雨")) {
            return R.mipmap.biz_plugin_weather_leizhenyu;
        } else if (weather.equals("阵雨") || weather.equals("阵雨转多云")) {
            return R.mipmap.biz_plugin_weather_zhenyu;
        } else if (weather.equals("暴雪")) {
            return R.mipmap.biz_plugin_weather_baoxue;
        } else if (weather.equals("暴雨")) {
            return R.mipmap.biz_plugin_weather_baoyu;
        } else if (weather.equals("大暴雨")) {
            return R.mipmap.biz_plugin_weather_dabaoyu;
        } else if (weather.equals("大雪")) {
            return R.mipmap.biz_plugin_weather_daxue;
        } else if (weather.equals("大雨") || weather.equals("大雨转中雨")) {
            return R.mipmap.biz_plugin_weather_dayu;
        } else if (weather.equals("雷阵雨冰雹")) {
            return R.mipmap.biz_plugin_weather_leizhenyubingbao;
        } else if (weather.equals("晴")) {
            return R.mipmap.biz_plugin_weather_qing;
        } else if (weather.equals("沙尘暴")) {
            return R.mipmap.biz_plugin_weather_shachenbao;
        } else if (weather.equals("特大暴雨")) {
            return R.mipmap.biz_plugin_weather_tedabaoyu;
        } else if (weather.equals("雾") || weather.equals("雾霾")) {
            return R.mipmap.biz_plugin_weather_wu;
        } else if (weather.equals("小雪")) {
            return R.mipmap.biz_plugin_weather_xiaoxue;
        } else if (weather.equals("小雨")) {
            return R.mipmap.biz_plugin_weather_xiaoyu;
        } else if (weather.equals("阴")) {
            return R.mipmap.biz_plugin_weather_yin;
        } else if (weather.equals("雨夹雪")) {
            return R.mipmap.biz_plugin_weather_yujiaxue;
        } else if (weather.equals("阵雪")) {
            return R.mipmap.biz_plugin_weather_zhenxue;
        } else if (weather.equals("中雪")) {
            return R.mipmap.biz_plugin_weather_zhongxue;
        } else {
            return R.mipmap.biz_plugin_weather_duoyun;
        }
    }

}


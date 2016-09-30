package com.example.mynews.Weather.presenter;

import android.app.ListFragment;
import android.content.Context;
import android.text.TextUtils;

import com.example.mynews.Utils.ToolUtils;
import com.example.mynews.Weather.model.IWeatherModel;
import com.example.mynews.Weather.model.WeatherModelImpl;
import com.example.mynews.Weather.view.IWeatherView;
import com.example.mynews.beans.WeatherBean;

import java.util.List;

/**
 * Created by frank on 2016/9/16.
 */
public class WeatherPresenterImpl implements IWeatherPresenter {

    private IWeatherModel iWeatherModel;
    private IWeatherView iWeatherView;
    private Context context;
    public WeatherPresenterImpl(Context context,IWeatherView weatherView){
        this.context=context;
        this.iWeatherView=weatherView;
        iWeatherModel=new WeatherModelImpl();
    }
    @Override
    public void loadWeatherData() {
        iWeatherView.showProgress();
        if(ToolUtils.isNetworkAvailable(context)){
            iWeatherView.hideProgress();
            iWeatherView.showErrorToast("网络连接错误");
            return;
        }

        iWeatherModel.loadLocation(context, new IWeatherModel.OnLoadLocationListener() {
            @Override
            public void onSuccess(String city) {
                iWeatherView.setCity(city);
                iWeatherModel.loadWeather(city, new IWeatherModel.OnLoadWeatherListener() {
                    @Override
                    public void onSuccess(List<WeatherBean> list) {
                        loadWeatherSuccess(list);
                    }

                    @Override
                    public void onFailed(String msg, Exception e) {
                        loadWeatherFailed();
                    }
                });
            }

            @Override
            public void onFailed(String msg) {
                iWeatherView.showErrorToast("定位失败");
                iWeatherView.setCity("武汉");
                iWeatherModel.loadWeather("武汉", new IWeatherModel.OnLoadWeatherListener() {
                    @Override
                    public void onSuccess(List<WeatherBean> list) {
                        loadWeatherSuccess(list);

                    }

                    @Override
                    public void onFailed(String msg, Exception e) {
                        loadWeatherFailed();
                    }
                });
            }
        });
    }
    public void loadWeatherSuccess(List<WeatherBean> list){
        if(list!=null&&list.size()>0){
            WeatherBean weather=list.remove(0);
            iWeatherView.setToday(weather.getDate());
            iWeatherView.setTemperature(weather.getTempretrue());
            iWeatherView.setWind(weather.getWind());
            iWeatherView.setWeather(weather.getWeather());
            iWeatherView.setWeatherImage(weather.getImgRes());
        }
        iWeatherView.setWeatherData(list);
        iWeatherView.hideProgress();
        iWeatherView.showWeatherLayout();

    }
    public void loadWeatherFailed(){
        iWeatherView.hideProgress();
        iWeatherView.showErrorToast("获取天气数据失败");
    }
}

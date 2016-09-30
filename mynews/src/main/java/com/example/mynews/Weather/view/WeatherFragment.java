package com.example.mynews.Weather.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.Weather.model.IWeatherModel;
import com.example.mynews.Weather.presenter.IWeatherPresenter;
import com.example.mynews.Weather.presenter.WeatherPresenterImpl;
import com.example.mynews.beans.WeatherBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2016/9/16.
 */
public class WeatherFragment extends Fragment implements IWeatherView {
    private IWeatherPresenter iWeatherPresenter;
    private TextView mTodayTV;
    private ImageView mTodayWeatherImage;
    private TextView mTodayTemperatureTV;
    private TextView mTodayWindTV;
    private TextView mTodayWeatherTV;
    private TextView mCityTV;
    private ProgressBar mProgressBar;
    private LinearLayout mWeatherLayout;
    private LinearLayout mWeatherContentLayout;
    private FrameLayout mRootLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iWeatherPresenter=new WeatherPresenterImpl(getActivity().getApplication(),this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_weather,null);
        mTodayTV = (TextView) view.findViewById(R.id.tv_day);
        mTodayWeatherImage = (ImageView) view.findViewById(R.id.weather_image);
        mTodayTemperatureTV = (TextView) view.findViewById(R.id.weather_temp);
        mTodayWindTV = (TextView) view.findViewById(R.id.weather_wind);
        mTodayWeatherTV = (TextView) view.findViewById(R.id.weather_weather);
        mCityTV = (TextView)view.findViewById(R.id.weather_city);
        mProgressBar = (ProgressBar) view.findViewById(R.id.weather_pb);
        mWeatherLayout = (LinearLayout) view.findViewById(R.id.ll_weather);
        mWeatherContentLayout = (LinearLayout) view.findViewById(R.id.weather_content);
        mRootLayout = (FrameLayout) view.findViewById(R.id.frame_weather);
        iWeatherPresenter.loadWeatherData();

        return view;

    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeatherLayout() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCity(String city) {
        mCityTV.setText(city);
    }

    @Override
    public void setToday(String data) {
        mTodayTV.setText(data);
    }

    @Override
    public void setTemperature(String temperature) {
        mTodayTemperatureTV.setText(temperature);
    }

    @Override
    public void setWind(String wind) {
        mTodayWindTV.setText(wind);
    }

    @Override
    public void setWeather(String weather) {
        mTodayWeatherTV.setText(weather);
    }

    @Override
    public void setWeatherImage(int res) {
        mTodayWeatherImage.setImageResource(res);
    }

    @Override
    public void setWeatherData(List<WeatherBean> lists) {
        List<View> list=new ArrayList<>();
        for(WeatherBean weatherBean:lists){
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.item_weather,null);
            TextView dateTV = (TextView) view.findViewById(R.id.tv_day);
            ImageView todayWeatherImage = (ImageView) view.findViewById(R.id.iv_weather);
            TextView todayTemperatureTV = (TextView) view.findViewById(R.id.tv_temp);
            TextView todayWindTV = (TextView) view.findViewById(R.id.tv_wind);
            TextView todayWeatherTV = (TextView) view.findViewById(R.id.tv_weather);

            dateTV.setText(weatherBean.getWeek());
            todayTemperatureTV.setText(weatherBean.getTempretrue());
            todayWindTV.setText(weatherBean.getWind());
            todayWeatherTV.setText(weatherBean.getWeather());
            todayWeatherImage.setImageResource(weatherBean.getImgRes());
            mWeatherContentLayout.addView(view);
            list.add(view);
        }


    }

    @Override
    public void showErrorToast(String msg) {
        Snackbar.make(getActivity().findViewById(R.id.drawer_layout),msg,Snackbar.LENGTH_SHORT).show();
    }
}

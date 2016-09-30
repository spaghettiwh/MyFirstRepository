package com.example.mynews.main.presenter;

import com.example.mynews.R;
import com.example.mynews.main.view.IMainView;

/**
 * Created by frank on 2016/9/14.
 */
public class MainPresenterImpl implements IMainPresenter {

    private IMainView iMainView;
    public MainPresenterImpl(IMainView mainView){
        this.iMainView=mainView;
    }
    @Override
    public void switchNavigation(int id) {
        switch (id){
            case R.id.navigation_item_about:
                iMainView.switchToAbout();
                break;
            case R.id.navigation_item_images:
                iMainView.switchToImage();
                break;
            case R.id.navigation_item_news:
                iMainView.switchToNews();
                break;
            case R.id.navigation_item_weather:
                iMainView.switchToWeather();
                break;
        }
    }
}

package com.example.mynews.News.view;

import com.example.mynews.beans.NewsBean;

import java.util.List;

/**
 * Created by frank on 2016/9/14.
 */
public interface INewsView {
    void addNews(List<NewsBean> newsBeen);
    void showProgress();
    void hideProgress();
    void showFailMsg();
}

package com.example.mynews.News.model;

/**
 * Created by frank on 2016/9/14.
 */
public interface INewsModel {
    void loadNews(String url, int type, NewsModelImpl.OnLoadNewsListListener loadNewsListListener);
    void loadNewsDetail(String docid, NewsModelImpl.OnLoadNewsDetailListener loadNewsDetailListener);
}

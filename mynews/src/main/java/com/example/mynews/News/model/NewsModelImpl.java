package com.example.mynews.News.model;

import com.example.mynews.Constant.Urls;
import com.example.mynews.News.view.NewsFragment;
import com.example.mynews.Utils.NewsJsonUtil;
import com.example.mynews.Utils.OkHttpUtil;
import com.example.mynews.beans.NewsBean;
import com.example.mynews.beans.NewsDetailBean;

import java.util.List;

/**
 * Created by frank on 2016/9/14.
 */
public class NewsModelImpl implements INewsModel{


    @Override
    public void loadNews(String url, final int type, final OnLoadNewsListListener loadNewsListListener) {
        OkHttpUtil.ResultCallback<String> callback=new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<NewsBean> list=NewsJsonUtil.readJsonNewsBeans(response,getId(type));
                loadNewsListListener.onSuccess(list);
            }

            @Override
            public void onFailure(Exception e) {
                loadNewsListListener.onFailed("loadNewsList error",e);
            }
        };
        OkHttpUtil.get(url,callback);
    }

    @Override
    public void loadNewsDetail(final String docid, final OnLoadNewsDetailListener loadNewsDetailListener) {

        String url=getDetailUrl(docid);
        OkHttpUtil.ResultCallback<String> callback=new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailBean newsDetailBean=NewsJsonUtil.readJsonNewsDetailBeans(response,docid);
                loadNewsDetailListener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                loadNewsDetailListener.onFailed("loadNewsDetail error",e);
            }
        };
        OkHttpUtil.get(url,callback);
    }

    private String getId(int type){

        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = Urls.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_SPORT:
                id = Urls.FOOTERBALL_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = Urls.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKE:
                id = Urls.JOKE_ID;
                break;
            default:
                id = Urls.TOP_ID;
                break;
        }
        return id;
    }
    private String getDetailUrl(String docid){
        StringBuffer sb=new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docid).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }
    public interface OnLoadNewsListListener{
        void onSuccess(List<NewsBean> newsBeen);
        void onFailed(String msg,Exception e);
    }
    public interface OnLoadNewsDetailListener{
        void onSuccess(NewsDetailBean newsDetailBean);
        void onFailed(String msg,Exception e);
    }

}

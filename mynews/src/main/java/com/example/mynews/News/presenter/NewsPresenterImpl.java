package com.example.mynews.News.presenter;

import com.example.mynews.Constant.Urls;
import com.example.mynews.News.model.INewsModel;
import com.example.mynews.News.model.NewsModelImpl;
import com.example.mynews.News.view.INewsView;
import com.example.mynews.News.view.NewsFragment;
import com.example.mynews.Utils.LogUtil;
import com.example.mynews.beans.NewsBean;

import java.util.List;

/**
 * Created by frank on 2016/9/14.
 */
public class NewsPresenterImpl implements INewsPresenter,NewsModelImpl.OnLoadNewsListListener {
    private static final String TAG="NewsPresenterImpl";
    private INewsModel iNewsModel;
    private INewsView iNewsView;

    public NewsPresenterImpl(INewsView newsView){
        this.iNewsView=newsView;
        iNewsModel =new NewsModelImpl();

    }
    @Override
    public void LoadNews(int page, int type) {
       String url=getUrl(type,page);
        LogUtil.d(TAG, url);
        if(page==0){
            iNewsView.showProgress();
        }
        iNewsModel.loadNews(url,type,this);
    }

    private String getUrl(int type,int pageIndex) {
        StringBuffer sb=new StringBuffer();
        switch (type){
            case NewsFragment.NEWS_TYPE_TOP:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
            case NewsFragment.NEWS_TYPE_SPORT:
                sb.append(Urls.COMMON_URL).append(Urls.FOOTERBALL_ID);
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                sb.append(Urls.COMMON_URL).append(Urls.CAR_ID);
                break;
            case NewsFragment.NEWS_TYPE_JOKE:
                sb.append(Urls.COMMON_URL).append(Urls.JOKE_ID);
                break;
            default:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
        }
        sb.append("/").append(pageIndex).append(Urls.END_URL);
        return sb.toString();
    }

    @Override
    public void onSuccess(List<NewsBean> newsBeen) {
        iNewsView.hideProgress();
        iNewsView.addNews(newsBeen);
    }

    @Override
    public void onFailed(String msg, Exception e) {
        iNewsView.hideProgress();
        iNewsView.showFailMsg();
    }
}

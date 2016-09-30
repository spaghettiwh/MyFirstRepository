package com.example.mynews.News.presenter;

import android.content.Context;

import com.example.mynews.News.model.INewsModel;
import com.example.mynews.News.model.NewsModelImpl;
import com.example.mynews.News.view.INewsDetailView;
import com.example.mynews.News.view.INewsView;
import com.example.mynews.beans.NewsDetailBean;

/**
 * Created by frank on 2016/9/14.
 */
public class NewsDetailPresenterImpl implements INewsDetailPresenter,NewsModelImpl.OnLoadNewsDetailListener {
   private INewsDetailView iNewsDetailView;
    private INewsModel iNewsModel;
    private Context mContext;
    public NewsDetailPresenterImpl(Context context,INewsDetailView newsView){
       this.mContext=context;
        this.iNewsDetailView=newsView;
        iNewsModel=new NewsModelImpl();
    }



    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        iNewsDetailView.hideProgress();
        if(newsDetailBean!=null){
            iNewsDetailView.showNewsDetailContent(newsDetailBean.getBody());
        }
    }

    @Override
    public void onFailed(String msg, Exception e) {
        iNewsDetailView.hideProgress();

    }

    @Override
    public void loadDetailNews(String docid) {
        iNewsModel.loadNewsDetail(docid,this);
        iNewsDetailView.showProgress();
    }
}

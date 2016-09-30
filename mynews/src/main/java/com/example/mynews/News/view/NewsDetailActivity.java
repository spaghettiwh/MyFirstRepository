package com.example.mynews.News.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mynews.News.presenter.INewsDetailPresenter;
import com.example.mynews.News.presenter.NewsDetailPresenterImpl;
import com.example.mynews.R;
import com.example.mynews.Utils.ImageLoaderUtil;
import com.example.mynews.beans.NewsBean;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by frank on 2016/9/15.
 */
public class NewsDetailActivity extends AppCompatActivity implements INewsDetailView {

    private ProgressBar mProgressBar;
    private HtmlTextView mHtmlTextView;
    private ImageView mImageView;
    private Toolbar mToolbar;
    private INewsDetailPresenter mINewsDetailPresenter;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private NewsBean mNewsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        mINewsDetailPresenter=new NewsDetailPresenterImpl(getApplicationContext(),this);
        mNewsBean= (NewsBean) getIntent().getSerializableExtra("news");
        Log.e("mNewsBean","mNewsBean---"+mNewsBean);

        mProgressBar= (ProgressBar) findViewById(R.id.newsdetail_progressbar);
        Log.e("mProgressBar","mProgressBar---"+mProgressBar);
        mHtmlTextView= (HtmlTextView) findViewById(R.id.htNewsContent);
        mImageView= (ImageView) findViewById(R.id.iv_newsdetail_image);
        mToolbar= (Toolbar) findViewById(R.id.newsdetail_toolbar);
        Log.e("mToolbar","mToolbar---"+mToolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        mCollapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(mNewsBean.getTitle());
        ImageLoaderUtil.display(getApplicationContext(),mNewsBean.getImgsrc(),mImageView);
        mINewsDetailPresenter.loadDetailNews(mNewsBean.getDocid());
    }



    @Override
    public void showNewsDetailContent(String content) {
        mHtmlTextView.setHtmlFromString(content,new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}

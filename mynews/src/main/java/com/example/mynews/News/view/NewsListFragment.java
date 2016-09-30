package com.example.mynews.News.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynews.Constant.Urls;
import com.example.mynews.News.NewsAdapter;
import com.example.mynews.News.presenter.INewsPresenter;
import com.example.mynews.News.presenter.NewsPresenterImpl;
import com.example.mynews.R;
import com.example.mynews.Utils.LogUtil;
import com.example.mynews.beans.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2016/9/14.
 */
public class NewsListFragment extends Fragment implements INewsView, SwipeRefreshLayout.OnRefreshListener {
    private INewsPresenter iNewsPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<NewsBean> newsBeen;
    private NewsAdapter mAdapter;
    private int type=NewsFragment.NEWS_TYPE_TOP;
    private int pageIndex=0;

    public static Fragment newInstance(int type){
        NewsListFragment fragment=new NewsListFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iNewsPresenter = new NewsPresenterImpl(this);
        type=getArguments().getInt("type");

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_news_list);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recylerView_news_list);
        mRecyclerView.setHasFixedSize(true);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter=new NewsAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(itemClickListener);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(onScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        onRefresh();
        return view;
    }

    private RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
        int lastVisibleItem;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+1==mAdapter.getItemCount()
                    && mAdapter.isShowFooter()){
                iNewsPresenter.LoadNews(pageIndex+ Urls.PAZE_SIZE,type);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
        }
    };
    @Override
    public void addNews(List<NewsBean> news) {
        mAdapter.isShowFooter(true);
        if(newsBeen==null){
            newsBeen=new ArrayList<NewsBean>();
        }
        newsBeen.addAll(news);
        if(pageIndex==0){
            mAdapter.setData(newsBeen);
        }else {
            if(news==null || news.size()==0){
                mAdapter.isShowFooter(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex+=Urls.PAZE_SIZE;
    }

    private NewsAdapter.OnItemClickListener itemClickListener=new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            NewsBean news=mAdapter.getItem(position);
            Intent intent=new Intent(getActivity(),NewsDetailActivity.class);
            intent.putExtra("news",news);
            View transitionView=view.findViewById(R.id.iv_news);
            ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(), transitionView,getString(R.string.transition_news_img));
            ActivityCompat.startActivity(getActivity(),intent,activityOptionsCompat.toBundle());
            LogUtil.e("startactivity","startactivity--"+intent);
        }
    };
    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showFailMsg() {
        if(pageIndex==0){
            mAdapter.isShowFooter(false);
            mAdapter.notifyDataSetChanged();
        }
        View view=getActivity()==null?mRecyclerView.getRootView():getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view,"加载数据失败",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        pageIndex=0;
        if(newsBeen!=null){
            newsBeen.clear();
        }
        iNewsPresenter.LoadNews(pageIndex,type);
    }
}

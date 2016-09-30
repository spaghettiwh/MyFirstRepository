package com.example.mynews.Image.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynews.Image.ImageAdapter;
import com.example.mynews.Image.presenter.IImagePresenter;
import com.example.mynews.Image.presenter.ImagePresenterImpl;
import com.example.mynews.R;
import com.example.mynews.beans.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2016/9/12.
 */
public class ImageFragment extends Fragment implements IImageView,SwipeRefreshLayout.OnRefreshListener {
    private IImagePresenter iImagePresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager manager;
    private List<ImageBean> images;
    private ImageAdapter imageAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iImagePresenter=new ImagePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_image,null);
        mSwipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView= (RecyclerView) view.findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true);

        manager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        imageAdapter=new ImageAdapter(getActivity().getApplicationContext());
        mRecyclerView.setAdapter(imageAdapter);
        mRecyclerView.addOnScrollListener(onScrollListener);

        onRefresh();
        return view;
    }
    private RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+1==imageAdapter.getItemCount()){
                Snackbar.make(getActivity().findViewById(R.id.drawer_layout),"加载更多",Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem=manager.findLastVisibleItemPosition();
        }
    };
    @Override
    public void addImage(List<ImageBean> imageBeen) {
        if(images==null){
            images=new ArrayList<ImageBean>();
        }
        images.addAll(imageBeen);
        imageAdapter.setData(images);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        View view=getActivity()==null ? mRecyclerView.getRootView():getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view,"加载失败",Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onRefresh() {
        if(images!=null){
            images.clear();
        }
        iImagePresenter.loadImageList();
    }
}

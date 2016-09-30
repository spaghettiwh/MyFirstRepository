package com.example.mynews.Image.view;

import com.example.mynews.beans.ImageBean;

import java.util.List;

/**
 * Created by frank on 2016/9/12.
 */
public interface IImageView {
    void addImage(List<ImageBean> imageBeen);
    void showProgress();
    void hideProgress();
    void showLoadFailMsg();
}

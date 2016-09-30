package com.example.mynews.Image.presenter;


import com.example.mynews.Image.model.IImageModel;
import com.example.mynews.Image.model.ImageModelImpl;
import com.example.mynews.Image.view.IImageView;
import com.example.mynews.beans.ImageBean;

import java.util.List;

/**
 * Created by frank on 2016/9/12.
 */
public class ImagePresenterImpl implements IImagePresenter,ImageModelImpl.OnLoadImageListListener {
    private IImageModel iImageModel;
    private IImageView iImageView;
    public ImagePresenterImpl(IImageView iImageView){
        this.iImageView=iImageView;
        iImageModel=new ImageModelImpl();
    }

    @Override
    public void loadImageList() {
        iImageModel.LoadImageList(this);
        iImageView.showProgress();
    }

    @Override
    public void onSuccess(List<ImageBean> images) {
        iImageView.hideProgress();
        iImageView.addImage(images);
    }

    @Override
    public void onFailed(String msg, Exception e) {
        iImageView.hideProgress();
        iImageView.showLoadFailMsg();
    }
}

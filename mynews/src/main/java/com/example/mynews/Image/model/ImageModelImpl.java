package com.example.mynews.Image.model;

import com.example.mynews.Constant.Urls;
import com.example.mynews.Utils.ImageJsonUtil;
import com.example.mynews.Utils.JsonUtil;
import com.example.mynews.Utils.OkHttpUtil;
import com.example.mynews.beans.ImageBean;

import java.util.List;

/**
 * Created by frank on 2016/9/12.
 */
public class ImageModelImpl implements IImageModel{


    @Override
    public void LoadImageList(final OnLoadImageListListener listener) {
        String url= Urls.IMAGES_URL;
        OkHttpUtil.ResultCallback<String> callback=new OkHttpUtil.ResultCallback<String>() {

            @Override
            public void onSuccess(String response) {
                List<ImageBean> images=ImageJsonUtil.readJsonImageBeans(response);
                listener.onSuccess(images);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailed("error",e);
            }
        };
        OkHttpUtil.get(url,callback);
    }

    public interface OnLoadImageListListener{
        void onSuccess(List<ImageBean> imageBeen);
        void onFailed(String msg,Exception e);
    }
}

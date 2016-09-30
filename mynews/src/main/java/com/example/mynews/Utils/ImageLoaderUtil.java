package com.example.mynews.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mynews.R;

/**
 * Created by frank on 2016/9/12.
 */
public class ImageLoaderUtil {

    public static void display(Context context,String url, ImageView imageView,int placeholder,int error){
        if(imageView==null){
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(placeholder).error(error).crossFade().into(imageView);
    }

    public static void display(Context context,String url,ImageView imageView){
        if(imageView==null){
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(R.mipmap.ic_image_loading).
                error(R.mipmap.ic_image_loadfail).crossFade().into(imageView);
    }
}

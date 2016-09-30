package com.example.mynews.beans;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by frank on 2016/9/12.
 */
public class ImageBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private String title;
    private String thumburl;
    private String sourceurl;
    private int height;
    private int weight;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbUrl() {
        return thumburl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumburl = thumbUrl;
    }

    public String getSourceUrl() {
        return sourceurl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceurl = sourceUrl;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

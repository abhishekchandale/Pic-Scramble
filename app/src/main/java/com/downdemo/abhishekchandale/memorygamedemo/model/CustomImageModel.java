package com.downdemo.abhishekchandale.memorygamedemo.model;

import com.downdemo.abhishekchandale.memorygamedemo.util.CommonUtil;

/**
 * Created by user on 6/16/2016.
 */

public class CustomImageModel {
    private String title;
    private String imageurl;
    private boolean isSelected=false;

    public CustomImageModel(String mTitle, String mImageUrl) {
        title = mTitle;
        imageurl = mImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}

package com.downdemo.abhishekchandale.memorygamedemo.model;

import com.google.gson.annotations.Expose;

/**
 * Created by user on 6/15/2016.
 */

public class ImageUrlModel {
    @Expose
    private String title;
    @Expose
    private ImageMedia media;

    public ImageMedia getMedia() {
        return media;
    }

    public void setMedia(ImageMedia media) {
        this.media = media;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

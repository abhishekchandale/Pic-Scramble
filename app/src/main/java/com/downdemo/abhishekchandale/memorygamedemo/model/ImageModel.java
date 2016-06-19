package com.downdemo.abhishekchandale.memorygamedemo.model;

import com.google.gson.annotations.Expose;
import java.util.List;

/**
 * Created by Abhishek on 6/15/2016.
 */

public class ImageModel {
    @Expose
    private String title;
    @Expose
    private String link;
    @Expose
    private String description;
    @Expose
    private String modified;
    @Expose
    private String generator;
    @Expose
    private List<ImageUrlModel> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }


    public List<ImageUrlModel> getItems() {
        return items;
    }

    public void setItems(List<ImageUrlModel> items) {
        this.items = items;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }
}

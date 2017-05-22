package com.tefah.bakingapp.pojo;

import java.io.Serializable;

/**
 * POJO class to hold a recipe step
 */

public class Step implements Serializable {

    private int id;
    private String shortDescription, description, videoUrl;

    public Step(int id, String shortDescription, String description, String videoUrl){
        this.id                 = id;
        this.shortDescription   = shortDescription;
        this.description        = description;
        this.videoUrl           = videoUrl;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}

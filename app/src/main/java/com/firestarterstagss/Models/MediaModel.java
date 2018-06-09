package com.firestarterstagss.Models;

/**
 * Created by HeartBeatMotion on 17/02/2018.
 */
public class MediaModel {
    String imageUrl;
    String imageId;
    String code;

    String byUser;
   String ranking;
    String videoLink;
    String currentLikes;
    String shortcode;

    public MediaModel(String imageUrl, String imageId, String currentLikes, String code, String byUser, String raking, String videoLink) {
        this.imageUrl = imageUrl;
        this.imageId = imageId;
        this.videoLink = videoLink;
        this.currentLikes = currentLikes;
        this.code = code;
        this.byUser = byUser;
        this.ranking = raking;
    }

    public MediaModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getByUser() {
        return byUser;
    }

    public String getRaking() {
        return ranking;
    }
    public String getVideoLink() {
        return videoLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageId() {
        return imageId;
    }
    public String getCode() {
        return code;
    }
    public String getCurrentLikes() {
        return currentLikes;
    }
}

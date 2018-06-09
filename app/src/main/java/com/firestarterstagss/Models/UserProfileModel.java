package com.firestarterstagss.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileModel {

    @SerializedName("follower_count")
    @Expose
    private Integer followerCount;
    @SerializedName("pk")
    @Expose
    private Long pk;

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_pic_url")
    @Expose
    private String profilePicUrl;

    private String cookie;
    private String csrf_token;


    String pub;
    String IMEI;
    String userID;
    String edge_array;
    String referral_code;

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public UserProfileModel() {
    }

    public UserProfileModel(Long pk, String username, String profilePicUrl, String cookie, String csrf, String IMEI, String userID,String edge_array) {
        this.pk = pk;
        this.username = username;
        this.profilePicUrl = profilePicUrl;
        this.cookie = cookie;
        this.csrf_token = csrf;
        this.IMEI = IMEI;
        this.userID = userID;
        this.edge_array = edge_array;
    }

    public UserProfileModel(Integer followerCount, Long pk, String username, String profilePicUrl, String edge_array) {
        this.followerCount = followerCount;
        this.pk = pk;
        this.username = username;
        this.profilePicUrl = profilePicUrl;
        this.edge_array = edge_array;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }
    void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public String getEdge_array() {
        return edge_array;
    }

    public void setEdge_array(String edge_array) {
        this.edge_array = edge_array;
    }

    public Long getPk() {
        return pk;
    }
    public String getCsrf() {
        return csrf_token;
    }

    public String getCsrf_token() {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) {
        this.csrf_token = csrf_token;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setCsrf(String csrf_token) {
        this.csrf_token = csrf_token;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

}

package com.firestarterstagss.Models;

public class Get20Model {
    String table_id,id,user_id,media_id,media_url,type,image,username;

    public Get20Model(String table_id,String id, String user_id, String media_id, String media_url, String type, String image, String username) {
        this.table_id = table_id;
        this.id = id;
        this.user_id = user_id;
        this.media_id = media_id;
        this.media_url = media_url;
        this.type = type;
        this.image = image;
        this.username = username;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

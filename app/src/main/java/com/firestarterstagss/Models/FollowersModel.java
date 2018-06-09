package com.firestarterstagss.Models;

public class FollowersModel {

    String id,coin,wanted;

    public FollowersModel(String id, String coin, String wanted) {
        this.id = id;
        this.coin = coin;
        this.wanted = wanted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getWanted() {
        return wanted;
    }

    public void setWanted(String wanted) {
        this.wanted = wanted;
    }
}

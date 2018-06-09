package com.firestarterstagss.Models;

public class SocialModel {

    String id,desc,insta,fb,twi;

    public SocialModel(String id, String desc, String insta, String fb, String twi) {
        this.id = id;
        this.desc = desc;
        this.insta = insta;
        this.fb = fb;
        this.twi = twi;
    }

    public SocialModel(String desc) {
        this.desc = desc;
    }

    public SocialModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getTwi() {
        return twi;
    }

    public void setTwi(String twi) {
        this.twi = twi;
    }
}

package com.firestarterstagss.Models;

/**
 * Created by PAWAN on 08-11-2016.
 */
public class NavigationPozo {

    String name,id;
    int image;

    public NavigationPozo() {

    }

    public NavigationPozo(String name, String id, int image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

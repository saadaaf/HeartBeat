package com.firestarterstagss.Models;

import android.graphics.Bitmap;

public class ImagesModel {
    String imageID, userID;
    Bitmap ImagePATH;

    public ImagesModel(Bitmap imagePATH) {
        ImagePATH = imagePATH;
    }

    public ImagesModel(String imageID, String userID, Bitmap imagePATH) {
        this.imageID = imageID;
        this.userID = userID;
        ImagePATH = imagePATH;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Bitmap getImagePATH() {
        return ImagePATH;
    }

    public void setImagePATH(Bitmap imagePATH) {
        ImagePATH = imagePATH;
    }
}

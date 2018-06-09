package com.firestarterstagss.Utils;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class HideActionBar {

    public HideActionBar(AppCompatActivity activity){
        activity.overridePendingTransition(0,0);
        activity. requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.hide();

    }



}

package com.firestarterstagss.Utils;

import android.app.Application;

import com.firestarterstagss.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        //....
    }
}

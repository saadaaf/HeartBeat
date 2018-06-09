package com.firestarterstagss.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class SharedPref {

    public  void AddSPData (Context act,String key,String value){
        act.getSharedPreferences("DATA", act.MODE_PRIVATE).edit().putString(key,value).commit();
    }

    public  SharedPreferences getSP(AppCompatActivity act){
        return  act.getSharedPreferences("DATA", act.MODE_PRIVATE);
    }

    public  String getSPData(Context act, String key){
        return  act.getSharedPreferences("DATA", act.MODE_PRIVATE).getString(key,"");
    }

    public  void clearSPData(AppCompatActivity act){
        act.getSharedPreferences("DATA", act.MODE_PRIVATE).edit().clear().commit();
    }


}

package com.firestarterstagss.Utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class MyIntent {

    public MyIntent(AppCompatActivity context, Class clas){
        Intent intent = new Intent(context,clas);
        context.startActivity(intent);
        context.finish();
    }

}


package com.firestarterstagss.Activitys;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.firestarterstagss.R;
import com.firestarterstagss.Utils.HideActionBar;
import com.firestarterstagss.Utils.MyImage;
import com.firestarterstagss.Utils.MyIntent;
import com.firestarterstagss.Utils.SharedPref;

public class GetStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new HideActionBar(GetStart.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start);
        ImageView imageView = findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().
                decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));

        new SharedPref().AddSPData(GetStart.this,"ad", "0");

//        String id = new SharedPref().getSPData(this,"id");
//        String ad = new SharedPref().getSPData(this,"ad");
//        if (!id.isEmpty() || !id.equals("")){
//            new MyIntent(GetStart.this,HomeActivity.class);
//        }



    }




    public void onClick(View view){
        String getProfileID = new SharedPref().getSPData(GetStart.this,"id");

        if (view == findViewById(R.id.getStart)){
            if (getProfileID.isEmpty()){
                new MyIntent(GetStart.this,AddAccount.class);
            }else {
                new MyIntent(GetStart.this,HomeActivity.class);
            }
        }
    }
}

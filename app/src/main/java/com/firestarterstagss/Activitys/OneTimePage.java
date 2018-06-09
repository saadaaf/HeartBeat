package com.firestarterstagss.Activitys;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.HideActionBar;
import com.firestarterstagss.Utils.MyImage;
import com.firestarterstagss.Utils.MyIntent;
import com.firestarterstagss.Utils.SetAnimation;
import com.firestarterstagss.Utils.SharedPref;

import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OneTimePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new HideActionBar(OneTimePage.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_page);
        TextView beat = findViewById(R.id.beat);

        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/OpenSans-Regular.ttf");
        beat.setTypeface(face);

        new SharedPref().AddSPData(OneTimePage.this,"ad", "0");

        ImageView imageView = findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().
                decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));

        SetAnimation.ButtonAnim(this,findViewById(R.id.popular));
        SetAnimation.ButtonAnim(this,findViewById(R.id.entertainment));
        SetAnimation.ButtonAnim(this,findViewById(R.id.nature));
        SetAnimation.ButtonAnim(this,findViewById(R.id.social));

//        boolean isinstall= isAppInstalled("com.instagram.android");
//        if (isinstall){
//            new MyIntent(OneTimePage.this, GetStart.class);
//        }
        hideInstapage();

    }

    private void hideInstapage() {
                                    ;
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET,
                HeartBeatConfig.hideinstapage, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response != null) {
                        String data=response.getString("data");
                        Log.d( "hideinstadata====>",data);
                        if (data.equals("0")){
                            new MyIntent(OneTimePage.this, GetStart.class);
                        }

//                        JSONObject jsonObject1 = response.getJSONObject("data").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media");
//                        count = jsonObject1.getInt("count");
//
//                        after = jsonObject1.getJSONObject("page_info").getString("end_cursor");

//                        try {
//                            list.clear();
//                            adapter.notifyDataSetChanged();
//                        } catch (Exception e) {
//
//                        }


                        //   InstagramPhotoPicker.startPhotoPickerForResult(this, CLIENT_ID, REDIRECT_URI, REQUEST_CODE_INSTAGRAM_PICKER);


//                        JSONArray jsonArray = jsonObject1.getJSONArray("edges");
//                        if (jsonArray.length() > 0) {
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                if (jsonArray.getJSONObject(i).getJSONObject("node").getBoolean("is_video") == false) {
//                                    String me = "" + new SharedPref().getSPData(activity, "id");
//
//                                    MediaModel item = new MediaModel(
//                                            jsonArray.getJSONObject(i).getJSONObject("node").get("thumbnail_src").toString(),
//                                            jsonArray.getJSONObject(i).getJSONObject("node").get("id").toString(),
//                                            jsonArray.getJSONObject(i).getJSONObject("node").getJSONObject("edge_media_preview_like").getString("count"),
//                                            jsonArray.getJSONObject(i).getJSONObject("node").get("shortcode").toString(), me, "", "");

//                                    if (!list.contains(item)){
//                                        list.add(item);
//                                    }


//                                }
//                            }
//                        }
//                        else {
//                            Toast.makeText(OneTimePage.this, "Either you don't have any media or your account is private", Toast.LENGTH_LONG).show();
//
//
//                        }
                        //   loading.setVisibility(View.GONE);
//                        adapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
        jsonObject.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(OneTimePage.this).add(jsonObject);

            }





    public void onClick(View view){
        if (view == findViewById(R.id.popular)){
           //new MyIntent(OneTimePage.this, GetStart.class);
        } else if (view == findViewById(R.id.entertainment)){

          //  new MyIntent(OneTimePage.this, GetStart.class);
        } else if (view == findViewById(R.id.nature)){

          //  new MyIntent(OneTimePage.this, GetStart.class);
        } else if (view == findViewById(R.id.social)){

            new MyIntent(OneTimePage.this, SocialPage.class);
        }
    }

    private boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}

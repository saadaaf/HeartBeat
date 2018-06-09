package com.firestarterstagss.Activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firestarterstagss.Activitys.Navigation.BaseActivity;
import com.firestarterstagss.Adapters.ViewPagerAdapter;
import com.firestarterstagss.CallData;
import com.firestarterstagss.Fragments.GetFollows;
import com.firestarterstagss.Fragments.GetLikes;
import com.firestarterstagss.Interfaces.LikeCallback;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.HideActionBar;
import com.firestarterstagss.Utils.MyImage;
import com.firestarterstagss.Utils.SharedPref;
import com.firestarterstagss.Utils.TabFontsChange;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends BaseActivity implements LikeCallback {

    String user_id;
    CallData callData;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new HideActionBar(HomeActivity.this);
        super.onCreate(savedInstanceState);
        try {
            findView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findView() throws Exception {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        mDrawer.addView(contentView, 0);

        callData = new CallData();

        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        user_id = sp.getString("user_id", "");
        String cookie = sp.getString("cookie", "");
        String username = sp.getString("username", "");

        callData.CallProfile(this, username, cookie);

        TextView home_username = findViewById(R.id.home_username);
        home_username.setText(username);

        ImageView imageView = findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        String ad = new SharedPref().getSPData(this, "ad");
        if (!ad.equals("1")) {
            GetAd();
        }
        GetCoins();

        new TabFontsChange().changeTabsFont(tabLayout, HomeActivity.this);
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.navi_button)) {
            if (mDrawer.isDrawerOpen(Gravity.LEFT)) {
                mDrawer.closeDrawer(Gravity.LEFT);
            } else {
                mDrawer.openDrawer(Gravity.LEFT);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GetLikes(), "LIKES");
        adapter.addFragment(new GetFollows(), "SUBSCRIBE");

        viewPager.setAdapter(adapter);
    }

    public void GetCoins() {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.getCoins, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("", "coin url " + HeartBeatConfig.getCoins);
                Log.e("", "onResponse: coin" + response);
                try {
                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");
                    if (satus.equals("200")) {
                        TextView tv = findViewById(R.id.money);
                        tv.setText(object.getString("data") + "$");
//                        Toast.makeText(HomeActivity.this, object.getString("data"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HeartBeatConfig().errorHanling(getApplicationContext(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("imei", new SharedPref().getSPData(getApplicationContext(), "IMEI"));
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }


    public void GetAd() {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.getAd, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
//                Toast.makeText(HomeActivity.this, "coins", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");
                    if (satus.equals("200")) {

//                        String ad = object.getString("data");
                        JSONObject dataWithImageVisible = object.getJSONObject("data");
                        String ad = dataWithImageVisible.getString("ad_image");
                        Log.d("image link=======>",ad);
                        String adVisibleValue = dataWithImageVisible.getString("is_banner_visible");
                        Log.d("advisible vlaue=======>",adVisibleValue);
                        if (adVisibleValue.equals("1")) {
                            final Dialog dialog = new Dialog(HomeActivity.this);
                            dialog.setContentView(R.layout.ad);
                            dialog.setCancelable(false);
                            ImageView image = dialog.findViewById(R.id.image);
                            ImageView close = dialog.findViewById(R.id.close);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    new SharedPref().AddSPData(HomeActivity.this, "ad", "1");
                                }
                            });

                            Log.e("dada", ad);
                            Picasso.get().load(ad).placeholder(R.drawable.images).error(R.drawable.images).into(image);
                            dialog.show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HeartBeatConfig().errorHanling(getApplicationContext(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("imei", new SharedPref().getSPData(getApplicationContext(), "IMEI"));
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(HomeActivity.this).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }


    @Override
    public void LikeCall() {
//        Toast.makeText(this, "xfv;knfd", Toast.LENGTH_SHORT).show();
        try {
            GetCoins();
        } catch (Exception e) {
            Log.e("Error dlkcb", ":::" + e);
            e.printStackTrace();
        }
    }
}

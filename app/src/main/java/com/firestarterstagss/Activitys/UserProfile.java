package com.firestarterstagss.Activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import com.firestarterstagss.Fragments.MyFollowers;
import com.firestarterstagss.Fragments.MyLikes;
import com.firestarterstagss.Fragments.MyView;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.HideActionBar;
import com.firestarterstagss.Utils.MyImage;
import com.firestarterstagss.Utils.MyIntent;
import com.firestarterstagss.Utils.SharedPref;
import com.firestarterstagss.Utils.TabFontsChange;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserProfile extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new HideActionBar(UserProfile.this);
        super.onCreate(savedInstanceState);
        findView();
    }


    private void findView(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_user_profile, null, false);
        mDrawer.addView(contentView, 0);

        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
       // user_id = sp.getString("user_id", "");
        String username = sp.getString("username", "");

        TextView home_username = findViewById(R.id.home_username);
        home_username.setText(username);

        ImageView imageView = findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().
                decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        try {
            GetCoins();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new TabFontsChange(). changeTabsFont(tabLayout,UserProfile.this);
    }

    public void onClick(View view){
        if (view == findViewById(R.id.navi_button)){
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

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyLikes(UserProfile.this), "Get Likes");
        adapter.addFragment(new MyView(UserProfile.this), "Get Views");
        adapter.addFragment(new MyFollowers(UserProfile.this), "Get Fans");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new MyIntent(UserProfile.this, HomeActivity.class);
    }

    public void GetCoins() throws Exception{

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.getCoins, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");
                    if (satus.equals("200")){
                        TextView tv =  findViewById(R.id.money);
                        tv.setText(object.getString("data")+"$");
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("imei", new SharedPref().getSPData(getApplicationContext(),"IMEI"));
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }

}

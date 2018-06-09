package com.firestarterstagss.Activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.firestarterstagss.Adapters.FollowersAdapter;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.Models.FollowersModel;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.HideActionBar;
import com.firestarterstagss.Utils.MyImage;
import com.firestarterstagss.Utils.MyIntent;
import com.firestarterstagss.Utils.SharedPref;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubmitForLike extends BaseActivity {

    ImageView user_image;
    private RecyclerView grid;
    private ArrayList<FollowersModel> flist = new ArrayList<>();
    private FollowersAdapter adapter;
    private String user_id, ImageId, ImageUrl, type,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new HideActionBar(SubmitForLike.this);
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_submit_for_like);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_submit_for_like, null, false);
        mDrawer.addView(contentView, 0);

        ImageView user_image = findViewById(R.id.user_image);
        ImageView imageView = findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().
                decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));

        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        image = sp.getString("Image", "");
        ImageId = sp.getString("ImageCode", "");
        ImageUrl = sp.getString("ImageUrl", "");
//        type = sp.getString("type", "");
//

        type = sp.getString("type", "");
        String username = sp.getString("username", "");

        Picasso.get().load(image).into(user_image);


        TextView home_username = findViewById(R.id.home_username);
        home_username.setText(username);

        try {
            SubmiteLikeCoins();
        } catch (Exception e) {
            e.printStackTrace();
        }

        grid = findViewById(R.id.grid);

        adapter = new FollowersAdapter(SubmitForLike.this, flist);
        grid.setLayoutManager(new GridLayoutManager(this, 1));
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setAdapter(adapter);

        try {
            GetCoins();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onBackPressed() {
        super.onBackPressed();
        new MyIntent(SubmitForLike.this, HomeActivity.class);
    }

    public void SubmiteLikeCoins() throws Exception {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.getAllLikeFollowCoins, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");
                    if (satus.equals("200")) {

                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            flist.add(new FollowersModel(array.getJSONObject(i).getString("id"), array.getJSONObject(i).getString("coin"), array.getJSONObject(i).getString("wanted")));
                            adapter.notifyDataSetChanged();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HeartBeatConfig().errorHanling(SubmitForLike.this, error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", new SharedPref().getSPData(SubmitForLike.this, "user_id"));
                params.put("type", type);
                Log.e("params ", params + "");
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(SubmitForLike.this).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }

    void DIA() {
        final Dialog dialog = new Dialog(SubmitForLike.this);
        dialog.setContentView(R.layout.confirm);
        dialog.setCancelable(false);
        Button yes = dialog.findViewById(R.id.yes);
        Button close = dialog.findViewById(R.id.close);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void GetCoins() throws Exception {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.getCoins, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");
                    if (satus.equals("200")) {
                        TextView tv = findViewById(R.id.money);
                        tv.setText(object.getString("data") + "$");
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


}

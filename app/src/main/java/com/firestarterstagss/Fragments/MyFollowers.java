package com.firestarterstagss.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firestarterstagss.Adapters.FollowersAdapter;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.Models.FollowersModel;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyFollowers extends Fragment {

    private RecyclerView grid;
    private ArrayList<FollowersModel> flist = new ArrayList<>();
    private FollowersAdapter adapter;
    private AppCompatActivity activity;

    public MyFollowers() {
    }

    @SuppressLint("ValidFragment")
    public MyFollowers(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new SharedPref().AddSPData(activity,"type", "2");


        try {
            GetFollowersCoins();
        } catch (Exception e) {
            e.printStackTrace();
        }

        View v = inflater.inflate(R.layout.fragment_my_followers, null);
        grid = v.findViewById(R.id.grid);

        adapter = new FollowersAdapter(activity, flist);
        grid.setLayoutManager(new GridLayoutManager(getContext(), 1));
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setAdapter(adapter);

        return v;
    }




    public void GetFollowersCoins() throws Exception{

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.getAllLikeFollowCoins, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");
                    if (satus.equals("200")){

                        try{
                            flist.clear();
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){

                        }

                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0;i<array.length();i++){
                            flist.add(new FollowersModel(array.getJSONObject(i).getString("id"),array.getJSONObject(i).getString("coin"),array.getJSONObject(i).getString("wanted")));
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
                new HeartBeatConfig().errorHanling(getActivity(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", new SharedPref().getSPData(getActivity(),"user_id"));
                params.put("type", "2");
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }


}

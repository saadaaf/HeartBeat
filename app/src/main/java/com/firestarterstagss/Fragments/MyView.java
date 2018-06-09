package com.firestarterstagss.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firestarterstagss.Adapters.ImageAdapter;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.Models.MediaModel;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyView extends Fragment implements AbsListView.OnScrollListener {

    List<MediaModel> list = new ArrayList<>();
    int count = 0;
    boolean userScrolled = false;
    String after = "", cookies;
    String requestTag = "EasyLikesFragment";
    MediaModel item;
    //  OrderLovesAdapter adapter;
    int myLastVisiblePos;
    private GridView likesgrid;
    private ImageAdapter adapters;
    private AppCompatActivity activity;

    public MyView() {
    }

    @SuppressLint("ValidFragment")
    public MyView(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String id = new SharedPref().getSPData(getActivity().getApplication(), "id");
        String username = new SharedPref().getSPData(getActivity().getApplication(), "username");
        String edge_array = new SharedPref().getSPData(getActivity().getApplication(), "edge_array");
        cookies = new SharedPref().getSPData(getActivity().getApplication(), "edge_array");



        View v = inflater.inflate(R.layout.fragment_my_likes, null);
        likesgrid = v.findViewById(R.id.likesgrid);

        adapters = new ImageAdapter(list, activity, after, "3");
        myLastVisiblePos = likesgrid.getFirstVisiblePosition();
        likesgrid.setNumColumns(2);
        likesgrid.setAdapter(adapters);
        likesgrid.setOnScrollListener(this);

        LoadImage(edge_array);

        return v;
    }

    void LoadImage(String edge_array) {

        try {
            JSONObject jsonObject1 = new JSONObject(edge_array);
            //  JSONObject jsonObject1 = response.getJSONObject("data").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media");
            count = jsonObject1.getInt("count");

            if (jsonObject1.getJSONObject("page_info").getBoolean("has_next_page") == true) {
                after = jsonObject1.getJSONObject("page_info").getString("end_cursor");
            } else {
                after = "";
            }

            try {
                list.clear();
                adapters.notifyDataSetChanged();
            } catch (Exception e) {

            }

            JSONArray jsonArray = jsonObject1.getJSONArray("edges");
            String me = "" + new SharedPref().getSPData(activity, "id");
            for (int i = 0; i < jsonArray.length(); i++) {

                if (jsonArray.getJSONObject(i).getJSONObject("node").getString("is_video").equals("true")) {
                    MediaModel item = new MediaModel(
                            jsonArray.getJSONObject(i).getJSONObject("node").get("thumbnail_src").toString(),
                            jsonArray.getJSONObject(i).getJSONObject("node").get("id").toString(),
                            jsonArray.getJSONObject(i).getJSONObject("node").get("video_view_count").toString(),
                            jsonArray.getJSONObject(i).getJSONObject("node").get("shortcode").toString(), me, "", "");

                    list.add(item);

                    adapters.notifyDataSetChanged();
                }


            }
            //   loading.setVisibility(View.GONE);
            //  adapter.notifyDataSetChanged();


        } catch (Exception e) {
            Log.e("video error", ">>> " + e + "");
            e.printStackTrace();
        }

    }


    private void LoadImages(String api) throws AuthFailureError {
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET,
                api, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response != null) {

                        JSONObject jsonObject1 = response.getJSONObject("data").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media");
                        count = jsonObject1.getInt("count");

                        after = jsonObject1.getJSONObject("page_info").getString("end_cursor");


                        JSONArray jsonArray = jsonObject1.getJSONArray("edges");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (jsonArray.getJSONObject(i).getJSONObject("node").getString("is_video").equals("true")) {
                                    // count = count + 1;
                                    String me = "" + new SharedPref().getSPData(activity, "id");

                                    MediaModel item = new MediaModel(
                                            jsonArray.getJSONObject(i).getJSONObject("node").get("thumbnail_src").toString(),
                                            jsonArray.getJSONObject(i).getJSONObject("node").get("id").toString(),
                                            jsonArray.getJSONObject(i).getJSONObject("node").get("video_view_count").toString(),
                                            jsonArray.getJSONObject(i).getJSONObject("node").get("shortcode").toString(), me, "", "");
                                    if (!list.contains(item)) {
                                        list.add(item);
                                    }
                                    adapters.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), "Either you don't have any media or your account is private", Toast.LENGTH_LONG).show();
                        }
                        //   loading.setVisibility(View.GONE);
                        //   adapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {
                    Log.e("Error json error", e + "");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                new HeartBeatConfig().errorHanling(getActivity(),error);

//                // As of f605da3 the following should work
//                NetworkResponse response = error.networkResponse;
//                if (error instanceof ServerError && response != null) {
//                    try {
//                        String res = new String(response.data,
//                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                    } catch (UnsupportedEncodingException e1) {
//
//                        e1.printStackTrace();
//                    } catch (Exception e2) {
//
//                        e2.printStackTrace();
//                    }
//                }

            }
        }

        );

        jsonObject.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(jsonObject);
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            userScrolled = true;
            if (after != "" && list.size() < count) {
                String url = HeartBeatConfig.LIKEAPI + 12 + "&id=" + new SharedPref().getSPData(activity, "id") + "&after=" + after;
                try {
                    LoadImages(url);
                } catch (AuthFailureError authFailureError) {
                    authFailureError.printStackTrace();
                }
                // new GetMedia(url).execute();
            }
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (userScrolled
                && totalItemCount == count && after == "") {

            userScrolled = false;
        }


    }
}

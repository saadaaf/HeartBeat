package com.firestarterstagss;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.Utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class CallData {


    public void CallProfile(final Context context, final String username, final String cookie1) {

        StringRequest strReq = new StringRequest(Request.Method.GET,
                HeartBeatConfig.LoginURL + username, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    Log.e("url ", "::" + HeartBeatConfig.LoginURL + username);

                    final String finalResult = response;

                    //  Log.e("finalResult ","::"+finalResult);

                    Document doc = null;
                    String shared = null;
                    doc = Jsoup.parse(finalResult);
                    Elements scriptElements = doc.getElementsByTag("script");

                    for (Element element : scriptElements) {
                        for (DataNode node : element.dataNodes()) {

                            if (node.toString().contains("window._sharedData")) {
                                shared = node.toString();
                                shared = shared.substring(shared.indexOf("=") + 1);
                                try {
                                    JSONObject jsonObject = new JSONObject(shared);
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("entry_data");
                                    JSONArray jsonArray = jsonObject1.getJSONArray("ProfilePage");
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                                    JSONObject jsonObjectNew = jsonObject2.getJSONObject("graphql");

                                    JSONObject jsonObject3 = jsonObjectNew.getJSONObject("user");
                                    JSONObject jsonObject4 = jsonObject3.getJSONObject("edge_followed_by");
                                    int follower_count = Integer.parseInt(jsonObject4.get("count").toString());
                                    String is_private = jsonObject3.get("is_private").toString();
                                    String username = jsonObject3.get("username").toString();
                                    JSONObject edge_owner_to_timeline_media = jsonObject3.getJSONObject("edge_owner_to_timeline_media");
                                    JSONArray edge_array = edge_owner_to_timeline_media.getJSONArray("edges");

                                    String profile_pic_url = jsonObject3.get("profile_pic_url").toString();
                                    String csrf_token = jsonObject.getJSONObject("config").get("csrf_token").toString();
                                    Long id = Long.parseLong(jsonObject3.get("id").toString());
//                                    UserProfileModel userProfile = new UserProfileModel(follower_count, id, username, profile_pic_url, edge_owner_to_timeline_media + "");
//                                    userProfile.setPub(is_private);
//                                    userProfile.setCsrf(csrf_token);
//                                    userProfile.setPk(id);
//                                    userProfile.setEdge_array(edge_owner_to_timeline_media + "");

                                    new SharedPref().AddSPData(context,"id", id+"");
                                    new SharedPref().AddSPData(context,"username", username);
                                    new SharedPref().AddSPData(context,"cookie", cookie1);
                                    new SharedPref().AddSPData(context,"csrf", csrf_token);
                                    new SharedPref().AddSPData(context,"profile_pic_url", profile_pic_url);
                               //     new SharedPref().AddSPData(AddAccount.this, "profile_pic_url", userProfile.getProfilePicUrl());
                                    new SharedPref().AddSPData(context,"edge_array", edge_owner_to_timeline_media+"");


                                } catch (JSONException e) {
                                    Log.e("CallProfile error", e + "");
                                }
                            }

                        }


                    }

                } catch (Exception e) {
                    Log.e("CallProfile 2 error", e + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HeartBeatConfig().errorHanling(context, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", cookie1);
                params.put("x-requested-with", "XMLHttpRequest");
                params.put("referer", HeartBeatConfig.LoginURL + username);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                return params;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(strReq);

        //  Global.getInstance().addToRequestQueue(strReq, reqTag);
    }


}

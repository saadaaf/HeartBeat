package com.firestarterstagss.Fragments;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firestarterstagss.Interfaces.LikeCallback;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.Models.Get20Model;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.SetAnimation;
import com.firestarterstagss.Utils.SharedPref;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GetLikes extends Fragment {
    View v;
    String user_id;
    int page = 0;
    int item = 0;
    ArrayList<Get20Model> glist = new ArrayList<>();
    ImageView user_image;
    ScheduledExecutorService exec;
    ProgressDialog pd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_getlikes, null);
        user_image = v.findViewById(R.id.user_image);


//        user_image.setImageBitmap(new MyImage().
//                decodeSampledBitmapFromResource(getResources(), R.drawable.pawan, 300, 300));

        SharedPreferences sp = getActivity().getSharedPreferences("DATA", getActivity().MODE_PRIVATE);
        user_id = sp.getString("user_id", "");

        SetAnimation.ShakeAnim(getActivity(), user_image);

        v.findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getActivity(), "dfsd", Toast.LENGTH_SHORT).show();
                if (!glist.isEmpty()) {
                    ++item;

                    if (item < glist.size()) {
                        Picasso.get().load(glist.get(item).getImage()).error(R.drawable.images).into(user_image);

                        Log.e("image ", item +
                                ":::: " + glist.get(item).getMedia_url());
                    } else {
                        ++page;
                        GetOthersIMages();
                    }


                }
                SetAnimation.ButtonAnim(getActivity(), v.findViewById(R.id.skip));
            }
        });
        v.findViewById(R.id.play_loop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetAnimation.ButtonAnim(getActivity(), v.findViewById(R.id.play_loop));
            }
        });
        v.findViewById(R.id.likethis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetAnimation.ButtonAnim(getActivity(), v.findViewById(R.id.likethis));
                try {
//                    SubmiteLike();
                    likeWeb();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(v.findViewById(R.id.stop_loop),
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(300);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();


        v.findViewById(R.id.play_loop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Auto Mode On", Toast.LENGTH_SHORT).show();
                SetAnimation.ButtonAnim(getActivity(), v.findViewById(R.id.play_loop));
                v.findViewById(R.id.play_loop).setVisibility(View.GONE);
                v.findViewById(R.id.stop_loop).setVisibility(View.VISIBLE);
                PlayLoop();
            }
        });

        v.findViewById(R.id.stop_loop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Auto Mode Off", Toast.LENGTH_SHORT).show();
                SetAnimation.ButtonAnim(getActivity(), v.findViewById(R.id.stop_loop));
                v.findViewById(R.id.play_loop).setVisibility(View.VISIBLE);
                v.findViewById(R.id.stop_loop).setVisibility(View.GONE);
                StopLoop();
            }
        });


        v.findViewById(R.id.likethis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(getActivity());
                pd.setCancelable(false);
                pd.setMessage("Please wait...");
                pd.show();
                SetAnimation.ButtonAnim(getActivity(), v.findViewById(R.id.likethis));
                try {
//                    SubmiteLike();
                    likeWeb();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        GetOthersIMages();

        return v;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            StopLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void PlayLoop() {
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
//                    SubmiteLike();
                    likeWeb();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 3, TimeUnit.SECONDS);

    }

    void StopLoop() {
        try {
            if (exec.isShutdown() || exec.isTerminated()) {
                exec.shutdown();
            }
        } catch (Exception e) {

        }
    }


    public void GetOthersIMages() {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.getLikesFollows, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    Log.e("response ", "::" + response);
                    Log.e("url ", "::" + HeartBeatConfig.getLikesFollows);

                    final String finalResult = response;


                    JSONObject jsonObject = new JSONObject(finalResult);
                    String status_code = jsonObject.getString("status_code");

                    if (status_code.equals("200")) {
                        JSONArray data = jsonObject.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            glist.add(new Get20Model(
                                    data.getJSONObject(i).getString("id"),
                                    data.getJSONObject(i).getString("insta_id"),
                                    data.getJSONObject(i).getString("user_id"),
                                    data.getJSONObject(i).getString("media_id"),
                                    data.getJSONObject(i).getString("media_code"),
                                    data.getJSONObject(i).getString("type"), data.getJSONObject(i).getString("image"), data.getJSONObject(i).getString("username")));

                            Picasso.get().load(data.getJSONObject(0).getString("image")).error(R.drawable.images).into(user_image);
                        }
                    }
                } catch (Exception e) {
                    Log.e("CallProfile 2 error", e + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HeartBeatConfig().errorHanling(getActivity(), error);
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("insta_id", ""+new SharedPref().getSPData(getActivity(),"id"));
                params.put("type", "1");
                params.put("page", page + "");
                Log.e("params", params + "");
                return params;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(strReq);

        //  Global.getInstance().addToRequestQueue(strReq, reqTag);
    }


    private void likeWeb() {


        final String url = HeartBeatConfig.likeURL + glist.get(item).getMedia_url() + "/like/";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new com.android.volley.Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e("print url ", ":::" + url);
                Log.e("response like ", "::" + response);
                try {
                    if (response != null) {

                        //    addToAction("200", isAuto);

                        SubmiteLike();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse networkResponse = error.networkResponse;

                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);

                    if (jsonError.toLowerCase().contains("please wait")) {
                        // Print Error!
                        Toast.makeText(getActivity(), "Going too fast,wait a few minutes before you try again", Toast.LENGTH_LONG).show();

                    } else if (jsonError.toLowerCase().contains("checkpoint")) {

                        Toast.makeText(getActivity(), "You are temporarily blocked,please relogin again to verify your account", Toast.LENGTH_LONG).show();


                    } else if (jsonError.toLowerCase().contains("temporarily blocked")) {

                        Toast.makeText(getActivity(), "You are temporarily blocked,please wait a few minutes", Toast.LENGTH_LONG).show();


                    } else {
                        // addToAction(String.valueOf(networkResponse.statusCode), isAuto);
                        try {
                            SubmiteLike();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

            }
        })


        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", new SharedPref().getSPData(getActivity(), "cookie"));
                params.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
                params.put("content-length", "0");
                params.put("content-type", "application/x-www-form-urlencoded");
                params.put("origin", "https://www.instagram.com");
                params.put("x-instagram-ajax", "1");
                params.put("x-requested-with", "XMLHttpRequest");
                params.put("referer", "https://www.instagram.com/p/" + glist.get(item).getMedia_id() + "/");
                params.put("x-csrftoken", new SharedPref().getSPData(getActivity(), "csrf"));
                Log.e("print params ", ":::" + params);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };


        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(strReq);


    }


    public void SubmiteLike() throws Exception {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.addLikeFollow, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("response like", response);
                try {
                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");
                    if (satus.equals("200")) {
                        ++item;
                        if (item <= glist.size()-1) {
                            Picasso.get().load(glist.get(item).getImage()).error(R.drawable.images).into(user_image);
                            Log.e("image ", item +
                                    ":::: " + glist.get(item).getImage());
                        } else {
                            ++page;
                            GetOthersIMages();
                        }
                    }
                    ((LikeCallback) getActivity()).LikeCall();
                } catch (JSONException e) {
                    Log.e("Error like",":::"+e);
                    e.printStackTrace();
                }

                try {
                    pd.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((LikeCallback) getActivity()).LikeCall();
                new HeartBeatConfig().errorHanling(getActivity(), error);
                try {
                    pd.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("to", glist.get(item).getUser_id());
                params.put("by", user_id);
                params.put("type", "1");
                params.put("tbl_media_id", glist.get(item).getTable_id());
                params.put("insta_id", ""+new SharedPref().getSPData(getActivity(),"id"));
                Log.e("prawm", params + "");
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }


}

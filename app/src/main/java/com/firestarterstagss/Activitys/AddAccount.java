package com.firestarterstagss.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firestarterstagss.Database.ProductController;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.Models.HeartBeatUsers;
import com.firestarterstagss.Models.UserProfileModel;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.HideActionBar;
import com.firestarterstagss.Utils.MyImage;
import com.firestarterstagss.Utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.TourGuide;

public class AddAccount extends AppCompatActivity {

    WebView mWebView;
    RelativeLayout loading, loading1, bottom;
    TextView tick;
    String cookies;
    String device;
   // String deviceIMEI;

    String url = "https://www.instagram.com/accounts/login";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"MissingPermission", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new HideActionBar(AddAccount.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        ImageView imageView = findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().
                decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));

//        c15249f1b0837686
//        c15249f1b0837686RGI7VO9SWCIN4LLZ

        device = Settings.Secure.getString(this.getContentResolver(), Settings
                .Secure.ANDROID_ID) + Build.SERIAL;

        String androidID = Settings.Secure.getString(this.getContentResolver(), Settings
                .Secure.ANDROID_ID);

        Log.e("device", device);
        //  Log.e("imei",imei);
        Log.e("androidID", androidID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().removeSessionCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            if (cookieManager != null) {
                cookieManager.removeAllCookie();
            }
            CookieSyncManager.getInstance().sync();
        }

        findView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void findView() {
        mWebView = (WebView) findViewById(R.id.webView1);
        tick = (TextView) findViewById(R.id.tick_button);

        loading = (RelativeLayout) findViewById(R.id.loadingPanel);
        loading.setVisibility(View.INVISIBLE);
        loading1 = (RelativeLayout) findViewById(R.id.loadingPanel1);
        bottom = (RelativeLayout) findViewById(R.id.top);
        bottom.setVisibility(View.GONE);

        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
//        mWebView.clearCache(true);
//        mWebView.clearHistory();

        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cookies = CookieManager.getInstance().getCookie(url);
                //   cookies = new SharedPref().getSPData(AddAccount.this,"id");
                if (cookies.contains("ds_user_id")) {

                    loading.setVisibility(View.VISIBLE);
                    tick.setEnabled(false);
                    CallLogin(cookies);
                } else {
                    Toast.makeText(AddAccount.this, "Please login and wait until page loading finishes", Toast.LENGTH_LONG).show();
                }
            }
        });
        mWebView.setWebViewClient(new MyWebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                view.loadUrl("javascript:console.log(document.body.getElementsByTagName('pre')[0].innerHTML);");

            }

            @Override
            public void onPageFinished(WebView view, String url) {

                bottom.setVisibility(View.VISIBLE);
                loading1.setVisibility(View.INVISIBLE);
                URL url1 = null;
                try {
                    url1 = new URL(url);
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                cookies = CookieManager.getInstance().getCookie(url);
                //   cookies = new SharedPref().getSPData(AddAccount.this,"id");
                //   Toast.makeText(AddAccount.this, cookies + "", Toast.LENGTH_SHORT).show();
                if (cookies.contains("ds_user_id")) {

                    TourGuide.init(AddAccount.this).with(TourGuide.Technique.CLICK)
                            .setPointer(new Pointer())
                           // .setToolTip(new ToolTip().setTitle("Success").setDescription("Click To Add Account..."))
                            .setOverlay(new Overlay())
                            .playOn(bottom);

                    bottom.setVisibility(View.VISIBLE);
                    tick.setVisibility(View.VISIBLE);

                }

            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.requestFocusFromTouch();
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.loadUrl(url);

//        insertDummyContactWrapper();

    }

//    String getIMEI() {
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//        }
//        deviceIMEI = telephonyManager.getDeviceId();
//        //  Toast.makeText(this, deviceIMEI+"", Toast.LENGTH_SHORT).show();
//
//
//        return telephonyManager.getDeviceId();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void insertDummyContactWrapper() {
//        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
//        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
//                    101);
//            return;
//        }
//        getIMEI();
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case 101:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission Granted
//                    getIMEI();
//                } else {
//                    // Permission Denied
//                    Toast.makeText(AddAccount.this, "Permission Denied", Toast.LENGTH_SHORT)
//                            .show();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

    public void CallLogin(final String cookie) {
        StringRequest strReq = new StringRequest(Request.Method.GET, HeartBeatConfig.LoginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final String finalResult = response;
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
                                    JSONObject jsonObject1 = new JSONObject(shared);
                                    JSONObject jsonObject2 = jsonObject1.getJSONObject("config");

                                    if (jsonObject2.has("viewer") && jsonObject2.get("viewer") != null && !jsonObject2.get("viewer").toString().equalsIgnoreCase("null")) {
                                        JSONObject jsonObject3 = jsonObject2.getJSONObject("viewer");

                                        String username = jsonObject3.get("username").toString();
                                        //  Toast.makeText(AddAccount.this, "::"+username, Toast.LENGTH_SHORT).show();

                                        CallProfile(cookie, username);
                                    } else {
                                        Toast.makeText(AddAccount.this, "There was some problem with your request,login again", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    Log.e("CallLogin error", e + "");
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HeartBeatConfig().errorHanling(AddAccount.this, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", cookie);
                params.put("x-requested-with", "XMLHttpRequest");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(AddAccount.this).add(strReq);

        //  Global.getInstance().addToRequestQueue(strReq, reqTag);
    }

    public void CallProfile(final String cookie1, final String username) {

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

//                                    Toast.makeText(AddAccount.this, edge_array.length()+"", Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(AddAccount.this, edge_array+"", Toast.LENGTH_SHORT).show();

                                    Log.e("edge_array", ">>>>>>>" + edge_array);

                                    String profile_pic_url = jsonObject3.get("profile_pic_url").toString();
                                    String csrf_token = jsonObject.getJSONObject("config").get("csrf_token").toString();
                                    Long id = Long.parseLong(jsonObject3.get("id").toString());
                                    UserProfileModel userProfile = new UserProfileModel(follower_count, id, username, profile_pic_url, edge_owner_to_timeline_media + "");
                                    userProfile.setPub(is_private);
                                    userProfile.setCsrf(csrf_token);
                                    userProfile.setCookie(cookie1);
                                    userProfile.setPk(id);
                                    userProfile.setProfilePicUrl(profile_pic_url);
                                    userProfile.setEdge_array(edge_owner_to_timeline_media + "");
                                    WebLogin(userProfile);

                                    //    Log.e("user datatatsa", "::"+username);

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
                new HeartBeatConfig().errorHanling(AddAccount.this, error);
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
        Volley.newRequestQueue(AddAccount.this).add(strReq);

        //  Global.getInstance().addToRequestQueue(strReq, reqTag);
    }

    private void WebLogin(UserProfileModel userProfile) throws Exception {
        // ((Global) getApplication()).setCurrentUserProfile(userProfile);
        HeartBeatUsers u = new HeartBeatUsers();
        u.setId(userProfile.getPk());
        u.setImageUrl(userProfile.getProfilePicUrl());
        u.setUsername(userProfile.getUsername());
        u.setCookie(userProfile.getCookie());
        u.setCsrf(userProfile.getCsrf());


//        Boolean m = true;
//        if (heartBeatSession.getHeartBeatUsersDao().loadAll().size()!=0) {
//            for (HeartBeatUsers x : heartBeatSession.getHeartBeatUsersDao().loadAll()) {
//                if (u.equals(x)) {
//                    m = false;
//                }
//            }
//        }
//
//        if (m)
//            heartBeatSession.getHeartBeatUsersDao().insertOrReplace(u);
        if (new SharedPref().getSP(AddAccount.this).contains("username")) {
            new SharedPref().clearSPData(AddAccount.this);
        }

        new SharedPref().AddSPData(AddAccount.this, "id", userProfile.getPk() + "");
        new SharedPref().AddSPData(AddAccount.this, "username", userProfile.getUsername());
        new SharedPref().AddSPData(AddAccount.this, "cookie", userProfile.getCookie());
        new SharedPref().AddSPData(AddAccount.this, "csrf", userProfile.getCsrf());
        new SharedPref().AddSPData(AddAccount.this, "profilepic", userProfile.getProfilePicUrl());
        new SharedPref().AddSPData(AddAccount.this, "IMEI", device);
        new SharedPref().AddSPData(AddAccount.this, "edge_array", userProfile.getEdge_array());
        new SharedPref().AddSPData(AddAccount.this, "profile_pic_url", userProfile.getProfilePicUrl());

//        Intent intent = new Intent(AddAccount.this, HomeActivity.class);
//        startActivity(intent);
//        finish();


        callAddUserApi(userProfile.getPk().toString(), userProfile.getUsername(), userProfile);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);

            }
        });

    }

    public void callAddUserApi(final String userId, final String userName, final UserProfileModel userProfile) throws Exception {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.addAccount, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    Log.e("response>>>",response);

                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");
                    if (satus.equals("200")) {
                        JSONObject jsonObject = object.getJSONObject("data");

                        UserProfileModel um = new UserProfileModel(userProfile.getPk(), userProfile.getUsername(), userProfile.getProfilePicUrl(),
                                userProfile.getCookie(), userProfile.getCsrf(), device, jsonObject.getString("id"), userProfile.getEdge_array());

                        boolean b = new ProductController(AddAccount.this).addUsre(um);

                        if (b) {
                            new SharedPref().AddSPData(AddAccount.this, "user_id", jsonObject.getString("id"));
                            new SharedPref().AddSPData(AddAccount.this, "referral_code", jsonObject.getString("referral_code"));
                            Intent intent = new Intent(AddAccount.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddAccount.this, "Account Already Added", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HeartBeatConfig().errorHanling(AddAccount.this, error);
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
              //  params.put("userid", userId);
                params.put("insta_id", userId);
                params.put("imei", device);
                params.put("device", device);
                params.put("username", userName);
                Log.e("params",params+"");
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(AddAccount.this).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }

    //    String url = "https://www.google.com";
    public class MyWebViewClient extends WebViewClient {
    }


}

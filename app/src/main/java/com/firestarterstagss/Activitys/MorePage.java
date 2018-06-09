package com.firestarterstagss.Activitys;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
import com.firestarterstagss.Activitys.Navigation.BaseActivity;
import com.firestarterstagss.Database.ProductController;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.HideActionBar;
import com.firestarterstagss.Utils.MyImage;
import com.firestarterstagss.Utils.MyIntent;
import com.firestarterstagss.Utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MorePage extends BaseActivity {
    TextView Referel;
    RelativeLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new HideActionBar(MorePage.this);
        super.onCreate(savedInstanceState);
        findView();
    }

    private void findView() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_more_page, null, false);
        mDrawer.addView(contentView, 0);

        TextView beat = findViewById(R.id.t1);
        Referel = findViewById(R.id.Referel);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/OpenSans-Regular.ttf");
        beat.setTypeface(face);
        try {
            Referel.setText(new SharedPref().getSPData(this, "referral_code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Referel.setTypeface(face);

        logout = findViewById(R.id.logout);

        TextView home_username = findViewById(R.id.home_username);
        home_username.setText("Setting");

        ImageView imageView = findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().
                decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));


        try {
            GetCoins();
        } catch (Exception e) {
            e.printStackTrace();
        }


        findViewById(R.id.email_sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                subsribeEmail();
                Toast.makeText(MorePage.this,"Coming soon",Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ProductController(MorePage.this).clearData();
                new SharedPref().clearSPData(MorePage.this);
                new MyIntent(MorePage.this, GetStart.class);
            }
        });

        findViewById(R.id.contactus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                String subject = getResources().getString(R.string.app_name);
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT, "User Code is: " + Referel.getText().toString());
                i.setData(Uri.fromParts("mailto", "my.apphelpdesk1@gmail.com", null));
                startActivity(i);

            }
        });

        findViewById(R.id.rateus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + getPackageName())));
                }
            }
        });

        findViewById(R.id.ref).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    subsribeRefral();
                } catch (ActivityNotFoundException e) {
                }
            }
        });

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new MyIntent(MorePage.this, HomeActivity.class);
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

    public void subsribeEmail() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        LayoutInflater inflater = getLayoutInflater();
        View referLayout = inflater.inflate(R.layout.sub_layout, null);

        ImageView imageView = referLayout.findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));


        builder.setView(referLayout);
        ImageView close_email_sub = (ImageView) referLayout.findViewById(R.id.close_email_sub);
        RelativeLayout submit_email_sub = (RelativeLayout) referLayout.findViewById(R.id.submit_email_sub);
        TextView desc = (TextView) referLayout.findViewById(R.id.desc_email_sub);
        final EditText inputtext_email_sub = (EditText) referLayout.findViewById(R.id.inputtext_email_sub);

        desc.setText("Get notification for more discounts and our new recommended related apps \n\n" + " 50" + " free coins on email subscription  and only after you verify your email");

        //builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final AlertDialog dialog = builder.show();
        close_email_sub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        submit_email_sub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (inputtext_email_sub.getText().toString().equalsIgnoreCase("")) {

                    Toast.makeText(MorePage.this, "Input your email", Toast.LENGTH_SHORT).show();

                } else {
                    //   verifyEmail(inputtext_email_sub.getText().toString());

                }
            }
        });

    }


    public void subsribeRefral() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        LayoutInflater inflater = getLayoutInflater();
        View referLayout = inflater.inflate(R.layout.referel_lay, null);

        ImageView imageView = referLayout.findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));


        builder.setView(referLayout);
        ImageView close_email_sub = (ImageView) referLayout.findViewById(R.id.close_email_sub);
        RelativeLayout submit_email_sub = (RelativeLayout) referLayout.findViewById(R.id.submit_email_sub);

        final EditText inputtext_email_sub = (EditText) referLayout.findViewById(R.id.inputtext_email_sub);

        //builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final AlertDialog dialog = builder.show();
        close_email_sub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        submit_email_sub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (inputtext_email_sub.getText().toString().equalsIgnoreCase("")) {

                    Toast.makeText(MorePage.this, "Input your code", Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        SubmitReferal(inputtext_email_sub.getText().toString());
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //   verifyEmail(inputtext_email_sub.getText().toString());

                }
            }
        });

    }

    public void SubmitReferal(final String code) throws Exception {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.addReferralCode, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Error",response);

                try {
                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");
                    if (satus.equals("200")) {

                       if (object.getString("data").equals("true")){
                           GetCoins();
                       }else {
//                           Toast.makeText(MorePage.this, object.getString("data"), Toast.LENGTH_SHORT).show();
                       }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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
                params.put("code", code);
                params.put("user_id", new SharedPref().getSPData(getApplicationContext(), "user_id"));

                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }
}

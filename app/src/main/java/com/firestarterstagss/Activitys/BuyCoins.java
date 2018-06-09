package com.firestarterstagss.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firestarterstagss.Activitys.Navigation.BaseActivity;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.Models.CoinsModel;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.HideActionBar;
import com.firestarterstagss.Utils.MyImage;
import com.firestarterstagss.Utils.MyIntent;
import com.firestarterstagss.Utils.SharedPref;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BuyCoins extends BaseActivity {
    private RecyclerView grid;
    private ArrayList<CoinsModel> clist = new ArrayList<>();
    private CoinsNewAdapter adapter;
    private AppCompatActivity activity;

    private TextView home_username;
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)

            .clientId("AWz8cGh5JZF-1NZOnewPTFphRw2rKvroFTv1lqucM9md1Tdp-tybVyZ69MwaKO7WW03qOSlJxQAfLOk0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new HideActionBar(BuyCoins.this);
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_buy_coins, null, false);
        mDrawer.addView(contentView, 0);
        startService();
        TextView home_username = findViewById(R.id.home_username);
        home_username.setText("Buy Coins");

        ImageView imageView = findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().
                decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));

        grid = findViewById(R.id.grid);
        clist.add(new CoinsModel("500", "$ 0.99"));
        clist.add(new CoinsModel("3500", "$ 4.99"));
        clist.add(new CoinsModel("15000", "$ 14.99"));
        clist.add(new CoinsModel("32000", "$ 29.99"));
        clist.add(new CoinsModel("75000", "$ 59.99"));
        clist.add(new CoinsModel("100000", "$ 74.99"));
        clist.add(new CoinsModel("150000", "$ 99.99"));


        adapter = new CoinsNewAdapter(activity, clist);
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
        new MyIntent(BuyCoins.this, HomeActivity.class);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

void startService(){
    Intent intent = new Intent(this, PayPalService.class);

    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

    startService(intent);
}
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    Log.i("paymentKey", confirm.getPayment().toJSONObject().toString(4));
                    GetpaymentInfo(confirm.getPayment().toJSONObject().toString(4));
                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
    public class CoinsNewAdapter extends RecyclerView.Adapter<CoinsNewAdapter.MyViewHolder> {
        private ArrayList<CoinsModel> plist = new ArrayList<>();
        AppCompatActivity ctx;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private final LinearLayout rowIndexLinearList;
            private final TextView verifiactionLogoWithTextView;
            TextView coins,amount;
            RelativeLayout r1;

            public MyViewHolder(View view) {
                super(view);
                amount = (TextView) view.findViewById(R.id.amount);
                coins = (TextView) view.findViewById(R.id.coins);
                verifiactionLogoWithTextView = (TextView) view.findViewById(R.id.verification_Text_logo);
                rowIndexLinearList=(LinearLayout)view.findViewById(R.id.row_list_linearLayout);
                r1 = view.findViewById(R.id.r1);
            }
        }

        public CoinsNewAdapter(AppCompatActivity ctx, ArrayList<CoinsModel> plist) {
            this.plist = plist;
            this.ctx = ctx;
        }

        @Override
        public CoinsNewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.coinsbase, parent, false);
            return new CoinsNewAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final CoinsNewAdapter.MyViewHolder holder, final int position) {
            CoinsModel data = plist.get(position);
            holder.amount.setText(data.getAmount());
            holder.coins.setText(data.getNumbarOFCoins());
            boolean value=false;
            if (data.getNumbarOFCoins().equals("15000")){
                holder.rowIndexLinearList.setBackgroundColor(Color.parseColor("#69b2ab"));
                holder.verifiactionLogoWithTextView.setVisibility(View.VISIBLE);
                holder.verifiactionLogoWithTextView.setText("Popular");
                value=true;
            }
            if (data.getNumbarOFCoins().equals("150000")){
                holder.rowIndexLinearList.setBackgroundColor(Color.parseColor("#adac62"));
                holder.verifiactionLogoWithTextView.setVisibility(View.VISIBLE);
                holder.verifiactionLogoWithTextView.setText("Best value");
                value=true;
            }

holder.r1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String amount=null;
        if(position==0) {
            amount="0.99";
        }else if(position==1) {
            amount="4.99";
        }else if(position==2) {
            amount="14.99";
        }else if(position==3) {
            amount="29.99";
        }else if(position==4) {
            amount="59.99";
        }else if(position==5) {
            amount="74.99";
        }else if(position==6) {
            amount="99.99";
        }
        if(amount!=null) {
            PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), "USD", "Papural coins",
                    PayPalPayment.PAYMENT_INTENT_SALE);

            Intent intent = new Intent(BuyCoins.this, PaymentActivity.class);
            // send the same configuration for restart resiliency
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(intent, 0);
        }

    }
});

        }

        @Override
        public int getItemCount() {
            return plist.size();
        }

    }
    public void GetpaymentInfo(String paymentId) throws Exception{

        StringRequest strReq = new StringRequest(Request.Method.GET,
                HeartBeatConfig.paymentInfno+paymentId, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String PaymentId = object.getString("id");
                    String date=object.getString("update_time");
                    JSONArray discription=new JSONArray(object.getString("transactions"));
                    JSONObject disObj=discription.getJSONObject(0) ;
                    JSONObject anmntObj=disObj.getJSONObject("amount");
                    String amount=anmntObj.getString("total");
                    try {
                        savePaymentInfo(PaymentId,amount,date);
                    } catch (Exception e) {
                        e.printStackTrace();
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer EJQ54JQ_hFA1sQ6VTA1zkXA1MmExH-x_0kvb_yVi5hXtBFoZ5-9FKRgz-2HgoOjjktTZDz-JFPlYNJsr");
                return params;
            }
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("imei", new SharedPref().getSPData(getApplicationContext(),"IMEI"));
//                return params;
//            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }
    public void savePaymentInfo(final String paymentId, final String amount, final String dateTime) throws Exception{

        StringRequest strReq = new StringRequest(Request.Method.GET,
                HeartBeatConfig.addPayment, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    GetCoins();
//                    String satus = object.getString("id");
//                    String date=object.getString("update_time");
//                    JSONArray discription=new JSONArray(object.getString("transactions"));
//                    JSONObject disObj=discription.getJSONObject(0) ;
//                    JSONObject anmntObj=disObj.getJSONObject("amount");
//                    String amount=anmntObj.getString("total");

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("imei", new SharedPref().getSPData(getApplicationContext(),"IMEI"));
                params.put("insta_id",new SharedPref().getSPData(getApplicationContext(),"id"));
                params.put("transaction_id",paymentId);
                params.put("payment_id",paymentId);
                params.put("data&time",dateTime);
                params.put("amount",amount);

                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }
}

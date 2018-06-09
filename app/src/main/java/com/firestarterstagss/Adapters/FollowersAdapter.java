package com.firestarterstagss.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firestarterstagss.Activitys.HomeActivity;
import com.firestarterstagss.Intgerfaces.HeartBeatConfig;
import com.firestarterstagss.Models.FollowersModel;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.MyIntent;
import com.firestarterstagss.Utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.MyViewHolder> {
    AppCompatActivity ctx;
    long coins;
    private ArrayList<FollowersModel> plist = new ArrayList<>();
    private String user_id, ImageId, ImageUrl, type, ImageCode, image;

    public FollowersAdapter(AppCompatActivity ctx, ArrayList<FollowersModel> plist) {
        this.plist = plist;
        this.ctx = ctx;
    }

    @Override
    public FollowersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.followersbase, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FollowersAdapter.MyViewHolder holder, final int position) {
        FollowersModel data = plist.get(position);

        holder.count.setText(data.getWanted());
        holder.coins.setText(data.getCoin());

        SharedPreferences sp = ctx.getSharedPreferences("DATA", ctx.MODE_PRIVATE);

        type = sp.getString("type", "");


        if (type.equals("1")){
            holder.icons.setImageResource(R.drawable.if_heart_299063);
        }else if (type.equals("2")){
            holder.icons.setImageResource(R.drawable.persons_icon);
        }else if (type.equals("3")){
            holder.icons.setImageResource(R.drawable.play_button2);
        }

        try {
            GetCoins();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);

        user_id = new SharedPref().getSPData(ctx, "user_id");
        ImageId = new SharedPref().getSPData(ctx, "ImageId");
        ImageUrl = new SharedPref().getSPData(ctx, "ImageUrl");
        ImageCode = new SharedPref().getSPData(ctx, "ImageCode");
        type = new SharedPref().getSPData(ctx, "type");


        if (type.equals("2")) {
            image = new SharedPref().getSPData(ctx, "profile_pic_url");
        } else {
            image = new SharedPref().getSPData(ctx, "Image");
        }

//
//        params.put("media_id", new SharedPref().getSPData(ctx,"username"));
//        params.put("type", type);
//        params.put("media_url", "https://www.instagram.com/"+ new SharedPref().getSPData(ctx,"username"));
//

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FollowersModel data = plist.get(position);
                if (type.equals("2")) {
                    DIA(data.getId(), "Do you want " + data.getWanted() + " Followers");
                    ImageId = new SharedPref().getSPData(ctx, "username");
                    ImageUrl = "https://www.instagram.com/" + new SharedPref().getSPData(ctx, "username");
                } else if (type.equals("1")) {
                    ImageId = new SharedPref().getSPData(ctx, "ImageId");
                    ImageUrl = new SharedPref().getSPData(ctx, "ImageUrl");
                    DIA(data.getId(), "Do you want to add likes?");
                } else {
                    ImageId = new SharedPref().getSPData(ctx, "ImageId");
                    ImageUrl = new SharedPref().getSPData(ctx, "ImageUrl");
                    DIA(data.getId(), "Do you want to add views?");
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return plist.size();
    }

    void DIA(final String id, String msg) {
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.confirm);
        dialog.setCancelable(false);
        TextView text = dialog.findViewById(R.id.text);
        Button yes = dialog.findViewById(R.id.yes);
        Button close = dialog.findViewById(R.id.close);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        text.setText(msg);

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

                if (coins <= 0) {
                    Toast.makeText(ctx, "you dont have enough coins", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        SubMitLikesFollow(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        dialog.show();
    }

    public void SubMitLikesFollow(final String id) throws Exception {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.addMediaPromotion, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Promote ", response);
                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");

                    if (satus.equals("200")) {

                        String data = object.getString("data");
                        if (!data.equals("1")) {
                            if (type.equals("1")) {
                                Toast.makeText(ctx, "Likes are on the way", Toast.LENGTH_SHORT).show();
                            } else if (type.equals("2")) {
                                Toast.makeText(ctx, "Fans are on the way", Toast.LENGTH_SHORT).show();
                            } else if (type.equals("3")) {
                                Toast.makeText(ctx, "Views are on the way", Toast.LENGTH_SHORT).show();
                            }
                            new MyIntent(ctx, HomeActivity.class);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HeartBeatConfig().errorHanling(ctx, error);
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", "" + new SharedPref().getSPData(ctx, "user_id"));
                params.put("insta_id", "" + new SharedPref().getSPData(ctx, "id"));
                params.put("media_id", ImageId);
                params.put("image", image);
                params.put("type", type);
                params.put("media_url", ImageUrl);
                //  params.put("image", ImageUrl);
                params.put("media_code", ImageCode);
                params.put("promoted_by", new SharedPref().getSPData(ctx, "user_id"));
                params.put("wanted_id", id);
                Log.e("getLikes params", params + "");
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(ctx).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }

    public void GetCoins() throws Exception {
        final ProgressDialog pd = new ProgressDialog(ctx);
        pd.setMessage("Please wait..");
        pd.setCancelable(false);
        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HeartBeatConfig.getCoins, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String satus = object.getString("status_code");
                    if (satus.equals("200")) {

                        coins = Long.parseLong(object.getString("data"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                new HeartBeatConfig().errorHanling(ctx, error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("imei", new SharedPref().getSPData(ctx, "IMEI"));
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(ctx).add(strReq);

        // Global.getInstance().addToRequestQueue(strReq, reqTag);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout click;
        TextView coins, count;
        ImageView icons;

        public MyViewHolder(View view) {
            super(view);
            count = (TextView) view.findViewById(R.id.count);
            coins = (TextView) view.findViewById(R.id.coins);
            click = view.findViewById(R.id.click);
            icons = view.findViewById(R.id.icons);
        }
    }

}

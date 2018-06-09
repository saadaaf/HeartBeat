package com.firestarterstagss.Intgerfaces;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class HeartBeatConfig {

    public static String LoginURL ="https://www.instagram.com/";
    public static String BaseURL ="http://firestartersapp.com/api/";
//    public static String LIKEAPI = "https://www.instagram.com/graphql/query?query_id=17880160963012870&first=";
    public static String LIKEAPI = "https://www.instagram.com/graphql/query?query_id=17888483320059182&first=";

    public static String hideinstapage= "http://firestartersapp.com/api/getSettings";
    public  static String paymentInfno=/*"https://api.paypal.com/v1/payments/payment/"*/ "https://api.sandbox.paypal.com/v1/payments/payment/";
    public static String addAccount = BaseURL+"login/";
    public static String addPayment=BaseURL+"addTransaction";
    public static String getCoins = BaseURL+"getCoins";
    public static String getAllLikeFollowCoins = BaseURL+"getAllLikeFollowCoins";
    public static String addMediaPromotion = BaseURL+"addMediaPromotion";
//    public static String getAd = BaseURL+"getAd";
    public static String getAd ="http://followers-and-leaders.com/api/getAd";
    public static String getLikesFollows = BaseURL+"getLikesFollows";
    public static String addLikeFollow = BaseURL+"addLikeFollow";
    public static String    addReferralCode = BaseURL+"addReferralCode";
    public static String likeURL="https://www.instagram.com/web/likes/";
    public static String followURL="https://www.instagram.com/web/friendships/";
    public void    errorHanling(Context context, VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(context, "Check your network connection!", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            //Toast.makeText(context, "Server Error!", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            //Toast.makeText(context, "AuthFailureError!", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            //Toast.makeText(context, "Parse Error!", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(context, "No Connection Error!", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            // Toast.makeText(context, "Timeout Error!", Toast.LENGTH_SHORT).show();
        }
    }
}

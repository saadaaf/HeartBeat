package com.firestarterstagss.Activitys.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firestarterstagss.Activitys.AddAccount2;
import com.firestarterstagss.Activitys.BuyCoins;
import com.firestarterstagss.Activitys.HomeActivity;
import com.firestarterstagss.Activitys.MorePage;
import com.firestarterstagss.Activitys.UserProfile;
import com.firestarterstagss.CallData;
import com.firestarterstagss.Database.ProductController;
import com.firestarterstagss.Models.NavigationPozo;
import com.firestarterstagss.Models.UserProfileModel;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.MyIntent;
import com.firestarterstagss.Utils.SharedPref;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout mDrawer;
    ArrayList<NavigationPozo> drawerListItems = new ArrayList<>();
    List<UserProfileModel> usersListItems = new ArrayList<>();
    MyAdapterBASE adap;
    MyAdapterUser adapuser;
    ListView mDrawerList;
    ListView left_users;
    String loginstatus, user_id;
    TextView logout;
    ActionBar ab;
    Context ctx;
    TextView a_account;
    CircularImageView userIMage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.mydrawer);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        left_users = (ListView) findViewById(R.id.left_users);
        a_account = findViewById(R.id.a_account);
        userIMage = findViewById(R.id.userIMage);

        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        user_id = sp.getString("user_id", "");
        String username = sp.getString("username", "");
        String profilepic = sp.getString("profilepic", "");
        String cookie = sp.getString("cookie", "");


        new CallData().CallProfile(this, username, cookie);


        TextView name = (TextView) findViewById(R.id.name);
        TextView address = (TextView) findViewById(R.id.id);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ctx = this;

        Log.e("Image ",":"+profilepic);

        Picasso.get().load(profilepic).into(userIMage);
        name.setText(username);
        address.setVisibility(View.GONE);
//        address.setText("@pawansoni");

        drawerListItems.add(new NavigationPozo("Earn Coins", "earn_coin", R.drawable.earn_coin_icon));
        drawerListItems.add(new NavigationPozo("Promote", "promote", R.drawable.promote));
        drawerListItems.add(new NavigationPozo("Buy Coins", "buy_coins", R.drawable.buy_coins));
        drawerListItems.add(new NavigationPozo("More", "more", R.drawable.more));


        adap = new MyAdapterBASE();
        mDrawerList.setAdapter(adap);
        adap.notifyDataSetChanged();


        usersListItems = new ProductController(this).getAllProduct();

        adapuser= new MyAdapterUser();
        left_users.setAdapter(adapuser);

        left_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserProfileModel userProfile = usersListItems.get(i);
                new SharedPref().AddSPData(BaseActivity.this,"id", userProfile.getPk()+"");
                new SharedPref().AddSPData(BaseActivity.this,"username", userProfile.getUsername());
                new SharedPref().AddSPData(BaseActivity.this,"cookie", userProfile.getCookie());
                new SharedPref().AddSPData(BaseActivity.this,"csrf", userProfile.getCsrf());
                new SharedPref().AddSPData(BaseActivity.this,"profilepic", userProfile.getProfilePicUrl());
                new SharedPref().AddSPData(BaseActivity.this,"IMEI", userProfile.getIMEI());
                new SharedPref().AddSPData(BaseActivity.this,"edge_array", userProfile.getEdge_array());

                new MyIntent(BaseActivity.this, HomeActivity.class);
            }
        });


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavigationPozo name = drawerListItems.get(position);
                String nav = name.getId();
                if (nav.equals("earn_coin")) {
                    new MyIntent(BaseActivity.this, HomeActivity.class);
                } else if (nav.equals("promote")) {
                    new MyIntent(BaseActivity.this, UserProfile.class);
                } else if (nav.equals("buy_coins")) {
                    new MyIntent(BaseActivity.this, BuyCoins.class);
                } else if (nav.equals("more")) {
                    new MyIntent(BaseActivity.this, MorePage.class);
                }

            }
        });

        a_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyIntent(BaseActivity.this, AddAccount2.class);
            }
        });
    }

    class MyAdapterBASE extends BaseAdapter {

        @Override
        public int getCount() {
            return drawerListItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.navigationbase, null);
            TextView tv = (TextView) v.findViewById(R.id.textView5);
            ImageView img = (ImageView) v.findViewById(R.id.dimg);

//            Typeface type = Typeface.createFromAsset(ctx.getAssets(), "fonts/Comfortaa Regular.ttf");
//            tv.setTypeface(type);

            NavigationPozo ss = drawerListItems.get(position);


            tv.setText(ss.getName());
            img.setImageResource(ss.getImage());
            return v;
        }
    }

    void ratess() {
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + ctx.getPackageName()));
        startActivity(rateIntent);

        Log.e("Packagename = ", Uri.parse("market://details?id=" + ctx.getPackageName()) + "");
    }

    void share() {
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, Uri.parse("market://details?id=" + ctx.getPackageName())+"" );
//        sendIntent.setType("text/plain");
//        startActivity(sendIntent);
//

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=" + ctx.getPackageName());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    class MyAdapterUser extends BaseAdapter {

        @Override
        public int getCount() {
            return usersListItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.navigationbaseuser, null);
            TextView tv = (TextView) v.findViewById(R.id.textView5);
            ImageView img = (ImageView) v.findViewById(R.id.dimg);

//            Typeface type = Typeface.createFromAsset(ctx.getAssets(), "fonts/Comfortaa Regular.ttf");
//            tv.setTypeface(type);

            UserProfileModel ss = usersListItems.get(position);


            tv.setText(ss.getUsername());
            Picasso.get().load(ss.getProfilePicUrl()).into(img);
            return v;
        }
    }

}

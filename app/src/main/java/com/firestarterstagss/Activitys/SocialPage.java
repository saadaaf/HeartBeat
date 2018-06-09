package com.firestarterstagss.Activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.firestarterstagss.Adapters.SocialAdapter;
import com.firestarterstagss.Models.SocialModel;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.HideActionBar;
import com.firestarterstagss.Utils.MyImage;
import com.firestarterstagss.Utils.MyIntent;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SocialPage extends AppCompatActivity {
    private RecyclerView grid;
    private ArrayList<SocialModel> slist = new ArrayList<>();
    private SocialAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new HideActionBar(SocialPage.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_page);


        ImageView imageView = findViewById(R.id.bgIMage);
        imageView.setImageBitmap(new MyImage().
                decodeSampledBitmapFromResource(getResources(), R.drawable.getstart_bg, 300, 300));

//        slist.add(new SocialModel(""));
//        slist.add(new SocialModel(""));
//        slist.add(new SocialModel(""));
//        slist.add(new SocialModel(""));
//        slist.add(new SocialModel(""));
//        slist.add(new SocialModel(""));
//        slist.add(new SocialModel(""));

        grid = findViewById(R.id.grid);

        adapter = new SocialAdapter(SocialPage.this, slist);
        grid.setLayoutManager(new GridLayoutManager(this, 1));
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setAdapter(adapter);
               ashuPrepaireDataModel();

    }

    private void ashuPrepaireDataModel() {
        SocialModel ashudummydatamodel = new SocialModel("#love #followback #instagramers" +
                " #envywear #tweegram #photooftheday #20likes #amazing #smile " +
                "#follow4follow #like4like #look #instalike #igers #picoftheday " +
                "#food #instadaily #instafollow #followme #girl #instagood #bestoftheday" +
                " #instacool #envywearco #follow #colorful #style #swag ");
        slist.add(ashudummydatamodel);
        ashudummydatamodel = new SocialModel("#photo #photos #pic #pics " +
                "#envywear #picture #pictures #snapshot #art #beautiful" +
                " #instagood #picoftheday #photooftheday #color #all_shots " +
                "#exposure #composition #focus #capture #moment #hdr #hdrspotters" +
                " #hdrstyles_gf #hdri " +
                "#hdroftheday #hdriphonegraphy #hdr_lovers #awesome_hdr ");
        slist.add(ashudummydatamodel);
        ashudummydatamodel = new SocialModel("#minimalism #minimalist #minimal #envywear " +
                "#minimalistic #minimalistics #minimalove #minimalobsession" +
                " #photooftheday #minimalninja #instaminim #minimalisbd #simple" +
                " #simplicity #keepitsimple #minimalplanet #love #instagood #minimalhunter #minimalista" +
                " #minimalismo #beautiful #art #lessismore #simpleandpure" +
                " #negativespace");
        slist.add(ashudummydatamodel);
        ashudummydatamodel = new SocialModel("#instagrammers #igers #instalove #instamood #instagood #followme #follow #comment #shoutout #iphoneography #androidography #filter #filters #hipster #contests #photo #instadaily #igaddict #envywear #photooftheday #pics #insta #picoftheday #bestoftheday #instadaily #instafamous #popularpic #popularphoto ");
        slist.add(ashudummydatamodel);
        ashudummydatamodel = new SocialModel("#hdr #hdriphoneographer #envywear #hdrspotters #hdrstyles_gf #hdri #hdroftheday #hdriphonegraphy #hdrepublic #hdr_lovers #awesome_hdr #instagood #hdrphotography #photooftheday #hdrimage #hdr_gallery #hdr_love #hdrfreak #hdrama #hdrart #hdrphoto #hdrfusion #hdrmania #hdrstyles #ihdr #str8hdr #hdr_edits ");
        slist.add(ashudummydatamodel);
        ashudummydatamodel = new SocialModel("#blackandwhite #bnw #monochrome #envywear #instablackandwhite #monoart #insta_bw #bnw_society #bw_lover #bw_photooftheday #photooftheday #bw #instagood #bw_society #bw_crew #bwwednesday #insta_pick_bw #bwstyles_gf #irox_bw #igersbnw #bwstyleoftheday #monotone #monochromatic#noir #fineart_photobw ");
        slist.add(ashudummydatamodel);
        ashudummydatamodel = new SocialModel("#abstract #art #abstractart #envywear #abstracters_anonymous #abstract_buff #abstraction #instagood #creative #artsy #beautiful #photooftheday #abstracto #stayabstract #instaabstract");
        slist.add(ashudummydatamodel);
        ashudummydatamodel = new SocialModel(" #fashion #style #stylish #love #envywear #envywear #cute #photooftheday #nails #hair #beauty #beautiful #instagood #pretty #swag #pink #girl #eyes #design #model #dress #shoes #heels #styles #outfit #purse #jewelry #shopping");
        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#smile ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#follow4follow ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#like4like ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel(" #look ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#instalike ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#food ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#instadaily ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel(" #instafollow");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#followme ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#girl ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#instagood ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#bestoftheday ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#instacool ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#envywearco ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#follow ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#colorful ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#style ");
//        slist.add(ashudummydatamodel);
//        ashudummydatamodel = new SocialModel("#swag ");
//        slist.add(ashudummydatamodel);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new MyIntent(SocialPage.this, OneTimePage.class);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

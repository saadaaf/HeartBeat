package com.firestarterstagss.Adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firestarterstagss.Activitys.SubmitForLike;
import com.firestarterstagss.Models.MediaModel;
import com.firestarterstagss.R;
import com.firestarterstagss.Utils.MyIntent;
import com.firestarterstagss.Utils.SharedPref;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageAdapter extends BaseAdapter {
    List<MediaModel> list;

    AppCompatActivity context;
    String max_id;
    int pas = 16;
    String type;

    public ImageAdapter(List<MediaModel> list, AppCompatActivity context , String max_id, String type) {
        this.list = list;
        this.context = context;
        this.max_id  =max_id;
        this.type  =type;
    }



    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (context == null)
            return null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.imagebase, parent , false);
        final MediaModel item  = (MediaModel) getItem(position);
        final ImageView image = (ImageView) rowView.findViewById(R.id.image);
        final ImageView imageDil = (ImageView) rowView.findViewById(R.id.like_row_icon);

        TextView likeCount = (TextView) rowView.findViewById(R.id.like_getmedia_likeno);
        Picasso.get().load(item.getImageUrl()).into(image);
        if (item.getCurrentLikes().toString().equals("0")){
            likeCount.setVisibility(View.GONE);
            imageDil.setVisibility(View.GONE);
        }
        likeCount.setText(item.getCurrentLikes().toString());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 MediaModel item  = list.get(position);
                new SharedPref().AddSPData(context,"Image", item.getImageUrl()+"");
                new SharedPref().AddSPData(context,"ImageId", item.getCode()+"");
                new SharedPref().AddSPData(context,"ImageCode", item.getImageId()+"");
                new SharedPref().AddSPData(context,"ImageUrl", "https://www.instagram.com/p/"+item.getCode()+"");
                new SharedPref().AddSPData(context,"type", type);

                new MyIntent(context, SubmitForLike.class);

            }
        });


        return rowView;

    }


}

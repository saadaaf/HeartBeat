package com.firestarterstagss.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firestarterstagss.Interfaces.AshuItemClickListener;
import com.firestarterstagss.Models.SocialModel;
import com.firestarterstagss.R;

import java.util.ArrayList;


public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.MyViewHolder> {
    private ArrayList<SocialModel> plist = new ArrayList<>();
    AppCompatActivity ctx;
    private AshuItemClickListener clickListener;
    private ClipboardManager cbm;
    private ClipData cd;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final ImageView copyTextImageView;
       public TextView tagTextView;
        TextView coins,amount;
       private AshuItemClickListener clickListener;

        public MyViewHolder(View view) {
            super(view);
            amount = (TextView) view.findViewById(R.id.amount);
            coins = (TextView) view.findViewById(R.id.coins);
            tagTextView = (TextView) view.findViewById(R.id.tag_container_textView);
            copyTextImageView=(ImageView)view.findViewById(R.id.copy_socailbase_image_view);


            copyTextImageView.setOnClickListener(this);
            copyTextImageView.setOnLongClickListener(this);
        }
        void setClickListener(AshuItemClickListener ashuItemClickListener){
            this.clickListener=ashuItemClickListener;
        }


        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition(), false);

        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view,getAdapterPosition(),true);
            return true;
        }
    }

    public SocialAdapter(AppCompatActivity ctx, ArrayList<SocialModel> plist) {
        this.plist = plist;
        this.ctx = ctx;
    }

    @Override
    public SocialAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.socialbase, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SocialAdapter.MyViewHolder holder, final int position) {
        SocialModel data = plist.get(position);
        holder.tagTextView.setText(data.getDesc());
        holder.setClickListener(new AshuItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                SocialModel data= plist.get(position);
                cbm=(ClipboardManager)view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                String copyText ;
                copyText=holder.tagTextView.getText().toString();
                cd=ClipData.newPlainText("text",copyText);
                cbm.setPrimaryClip(cd);
//                Toast.makeText(ctx,copyText,Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return plist.size();
    }

}


package com.firestarterstagss.Adapters;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firestarterstagss.Models.CoinsModel;
import com.firestarterstagss.R;

import java.util.ArrayList;


public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.MyViewHolder> {
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

    public CoinsAdapter(AppCompatActivity ctx, ArrayList<CoinsModel> plist) {
        this.plist = plist;
        this.ctx = ctx;
    }

    @Override
    public CoinsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coinsbase, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CoinsAdapter.MyViewHolder holder, final int position) {
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



    }

    @Override
    public int getItemCount() {
        return plist.size();
    }

}

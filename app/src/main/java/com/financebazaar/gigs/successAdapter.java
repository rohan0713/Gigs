package com.financebazaar.gigs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class successAdapter extends RecyclerView.Adapter<successAdapter.successViewholder> {

    List<SuccessItem> list;

    public successAdapter(List<SuccessItem> list){
        this.list = list;
    }

    @NonNull
    @Override
    public successViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_, parent, false);
        return new successViewholder(view);
    }

    @Override
    public void onBindViewHolder(successViewholder holder, int position) {

        holder.Bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class successViewholder extends RecyclerView.ViewHolder{

        public successViewholder(@NonNull View itemView) {
            super(itemView);
        }

        public void Bind(SuccessItem successItem) {

            TextView money = itemView.findViewById(R.id.money_trans);
            TextView  gig = itemView.findViewById(R.id.pay);
            TextView time = itemView.findViewById(R.id.money_trans_time);
            ImageView i = itemView.findViewById(R.id.payBy);

            if(successItem.getImage().length() != 0) {
                Picasso.get().load(successItem.getImage()).placeholder(R.mipmap.app_logo).into(i);
            }else{
                i.setBackgroundResource(R.mipmap.app_logo);
            }
            gig.setText(successItem.getCampaignname()+"\n" + successItem.getStatus());
            money.setText("₹ " + successItem.getAmount());
            time.setText(successItem.getDatetime());

        }
    }
}

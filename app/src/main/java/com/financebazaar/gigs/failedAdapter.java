package com.financebazaar.gigs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class failedAdapter extends RecyclerView.Adapter<failedAdapter.failedViewholder> {

    List<FailedItem> list;

    public failedAdapter(List<FailedItem> list){
        this.list = list;
    }


    @NonNull
    @Override
    public failedViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_, parent, false);
        return new failedViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull failedViewholder holder, int position) {

        holder.Bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class failedViewholder extends RecyclerView.ViewHolder {
        public failedViewholder(@NonNull View itemView) {
            super(itemView);
        }

        public void Bind(FailedItem failedItem) {

            TextView money = itemView.findViewById(R.id.money_trans);
            TextView  gig = itemView.findViewById(R.id.pay);
            TextView time = itemView.findViewById(R.id.paytm);
            time.setVisibility(View.VISIBLE);
            ImageView i = itemView.findViewById(R.id.payBy);
            ImageView im = itemView.findViewById(R.id.info);
            im.setVisibility(View.VISIBLE);

            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Invalid paytm number", Toast.LENGTH_SHORT).show();
                }
            });

            if(failedItem.getImage().length() != 0) {
                Picasso.get().load(failedItem.getImage()).placeholder(R.mipmap.app_logo).into(i);
            }else{
                i.setBackgroundResource(R.mipmap.app_logo);
            }
            gig.setText(failedItem.getCampaignname() + "\n" + "On Hold");
//            money.setText("₹" + failedItem.getEarnpay());
//            time.setText(failedItem.getDatetime());
        }
    }
}

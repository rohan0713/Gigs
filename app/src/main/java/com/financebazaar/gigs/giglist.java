package com.financebazaar.gigs;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import club.cred.synth.views.SynthButton;

public class giglist extends RecyclerView.Adapter<giglist.gigViewholder> {

    List<ResponseItem> list;
    static String name,url, number;

    public giglist(List<ResponseItem> list, String name, String url, String number) {
    this.list = list;
    this.name = name;
    this.url = url;
    this.number = number;
    }

    @NonNull
    @Override
    public gigViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gigs_recycler, parent, false);
        return new gigViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull gigViewholder holder, int position) {

        holder.Bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class gigViewholder extends RecyclerView.ViewHolder{

        public gigViewholder(@NonNull View itemView) {
            super(itemView);
        }

        public void Bind(ResponseItem gigs_detail) {

            ImageView i = itemView.findViewById(R.id.gig_image);
            TextView t1 = itemView.findViewById(R.id.gig_name);
            TextView t2 = itemView.findViewById(R.id.status_update);
            SynthButton t3 = itemView.findViewById(R.id.gigs_visit);

            if(gigs_detail.getImage().length() != 0) {
                Picasso.get().load(gigs_detail.getImage()).placeholder(R.mipmap.app_logo).into(i);
            }else{
                i.setBackgroundResource(R.mipmap.app_logo);
            }

            t1.setText(gigs_detail.getCampaignname());
            t2.setText("Earn ₹" + gigs_detail.getEarnpay());
            t3.setText("visit offer");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), gigs_details_screen.class);
                    i.putExtra("id", gigs_detail.getCampaignId());
                    i.putExtra("title", gigs_detail.getCampaignname());
                    i.putExtra("earn", gigs_detail.getEarnpay());
                    i.putExtra("refer", gigs_detail.getReferpay());
                    i.putExtra("steps", gigs_detail.getSteps());
                    i.putExtra("terms", gigs_detail.getTC());
                    i.putExtra("name", name);
                    i.putExtra("url", url);
                    i.putExtra("image", gigs_detail.getImage());
                    i.putExtra("number", number);
                    view.getContext().startActivity(i);
                }
            });

        }
    }
}

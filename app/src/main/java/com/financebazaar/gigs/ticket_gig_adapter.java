package com.financebazaar.gigs;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import club.cred.synth.views.SynthButton;

public class ticket_gig_adapter extends RecyclerView.Adapter<ticket_gig_adapter.ticketViewHolder> {

    static View view;
    static int count = 0;
    List<ResponseItem> list = new ArrayList<>();
    static String url, name, number;

    public ticket_gig_adapter(List<ResponseItem> list, String name, String url, String number){
        this.list = list;
        this.url = url;
        this.name = name;
        this.number = number;
    }

    @NonNull
    @Override
    public ticketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_gigs, parent, false);
        return new ticketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ticketViewHolder holder, int position) {

        holder.Bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ticketViewHolder extends RecyclerView.ViewHolder {
        public ticketViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void Bind(ResponseItem responseItem) {

            ImageView gig_image = itemView.findViewById(R.id.gig_image);
            ImageView status_image = itemView.findViewById(R.id.status_image);
            SynthButton visit = itemView.findViewById(R.id.gigs_visit);
            TextView status = itemView.findViewById(R.id.status_update);

            if(responseItem.getStatus().equalsIgnoreCase("1")){
                status_image.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.status_color));
                status.setText("Status : Done");
                status.setTextColor(view.getResources().getColor(R.color.status_color));
            }else {
                status.setText("Status : Pending");
                status_image.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.black));
            }
            visit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), gigs_details_screen.class);
                    i.putExtra("title", responseItem.getCampaignname());
                    i.putExtra("refer", responseItem.getReferpay());
                    i.putExtra("earn", responseItem.getEarnpay());
                    i.putExtra("steps", responseItem.getSteps());
                    i.putExtra("terms", responseItem.getTC());
                    i.putExtra("id", responseItem.getCampaignId());
                    i.putExtra("url", url);
                    i.putExtra("name", name);
                    i.putExtra("image", responseItem.getImage());
                    i.putExtra("number", number);
                    view.getContext().startActivity(i);
                }
            });
            TextView name = itemView.findViewById(R.id.gig_name);

            if(responseItem.getImage().length() != 0) {
                Picasso.get().load(responseItem.getImage()).placeholder(R.mipmap.app_logo).into(gig_image);
            }else{
                gig_image.setBackgroundResource(R.mipmap.app_logo);
            }
            name.setText(responseItem.getCampaignname());
        }
    }
}

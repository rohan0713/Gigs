package com.financebazaar.gigs;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import club.cred.synth.views.SynthImageButton;

public class gigs_adapter extends RecyclerView.Adapter<gigs_adapter.gigsViewHolder> {

    List<ResponseItem> list = new ArrayList<>();
    static String url;
    static String name;
    static String number;

    public gigs_adapter(List<ResponseItem> list, String url, String name, String number){
        this.list = list;
        this.url = url;
        this.name = name;
        this.number = number;
    }

    @NonNull
    @Override
    public gigsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gigs_item, parent, false);
        return new gigsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(gigsViewHolder holder, int position) {

        holder.Bind(list.get(position));
        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class gigsViewHolder extends RecyclerView.ViewHolder{

        public gigsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void Bind(ResponseItem o){

            SynthImageButton price= itemView.findViewById(R.id.offer);
            if(o.getImage().length()!=0) {
                Picasso.get().load(o.getImage()).placeholder(R.mipmap.app_logo).into(price);
            }else {
                price.setBackgroundResource(R.mipmap.app_logo);
            }
            TextView c = itemView.findViewById(R.id.camapign_name);
            String str = o.getCampaignname();
            if(str.length() > 12){
                String[] s = str.split(" ");
                StringBuilder stringBuilder = new StringBuilder();
                for(String st : s){
                    stringBuilder.append(st + "\n");
                }
                c.setText(stringBuilder);
            }else {
                c.setText(o.getCampaignname() + "\n");
            }
            TextView task = itemView.findViewById(R.id.gigs_earn);
            task.setText("Earn ₹" + o.getEarnpay());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(view.getContext(), gigs_details_screen.class);
                    i.putExtra("title", o.getCampaignname());
                    i.putExtra("refer", o.getReferpay());
                    i.putExtra("earn", o.getEarnpay());
                    i.putExtra("steps", o.getSteps());
                    i.putExtra("terms", o.getTC());
                    i.putExtra("id", o.getCampaignId());
                    i.putExtra("url", url);
                    i.putExtra("name", name);
                    i.putExtra("image", o.getImage());
                    i.putExtra("number", number);
                    view.getContext().startActivity(i);
                }
            });
}
    }
}

package com.example.gigs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import club.cred.synth.views.SynthButton;
import club.cred.synth.views.SynthImageButton;

public class gigs_adapter extends RecyclerView.Adapter<gigs_adapter.gigsViewHolder> {

    List<ResponseItem> list = new ArrayList<>();
    List<offers> list1 = new ArrayList<>();

    public gigs_adapter(List<offers> list){
        this.list1 = list;
    }

    @NonNull
    @Override
    public gigsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gigs_item, parent, false);
        return new gigsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(gigsViewHolder holder, int position) {

        holder.Bind(list1.get(position));
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    static class gigsViewHolder extends RecyclerView.ViewHolder{

        public gigsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void Bind(offers o){

            SynthImageButton price= itemView.findViewById(R.id.offer);
            price.setBackgroundResource(o.image);
            TextView task = itemView.findViewById(R.id.gigs_earn);
            task.setText(o.price);
            SynthButton button = itemView.findViewById(R.id.visit_offer);
            button.setText(o.task);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), gigs_details_screen.class);
                    view.getContext().startActivity(i);
                }
            });
}
    }
}

package com.example.gigs;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import club.cred.synth.views.SynthButton;

public class giglist extends RecyclerView.Adapter<giglist.gigViewholder> {
    List<gigs_detail> list;
    public giglist(List<gigs_detail> list) {
    this.list = list;
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

        public void Bind(gigs_detail gigs_detail) {

            ImageView i = itemView.findViewById(R.id.gig_image);
            TextView t1 = itemView.findViewById(R.id.gig_name);
            TextView t2 = itemView.findViewById(R.id.gig_detail);
            SynthButton t3 = itemView.findViewById(R.id.gigs_visit);

            i.setBackgroundResource(gigs_detail.image);
            t1.setText(gigs_detail.gig_name);
            t2.setText(gigs_detail.details);
            t3.setText(gigs_detail.offer);

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

package com.financebazaar.gigs;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ticket_adapter extends RecyclerView.Adapter<ticket_adapter.tViewHolder> {

    List<tickets> ticket = new ArrayList<>();
    static String tname, url, number;

    public ticket_adapter(List<tickets> ticket, String name, String url, String number) {
        this.ticket = ticket;
        this.tname = name;
        this.url = url;
        this.number = number;
    }

    @NonNull
    @Override
    public tViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list, parent, false);
        return new tViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tViewHolder holder, int position) {

        holder.Bind(ticket.get(position));
    }

    @Override
    public int getItemCount() {
        return ticket.size();
    }

    public static class tViewHolder extends RecyclerView.ViewHolder {
        public tViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void Bind(tickets ticket_class) {

            TextView name = itemView.findViewById(R.id.fest_name);
            TextView date = itemView.findViewById(R.id.date);
            TextView time = itemView.findViewById(R.id.time);
            TextView location = itemView.findViewById(R.id.location);
            TextView status = itemView.findViewById(R.id.Rstatus);
            TextView note = itemView.findViewById(R.id.ticket_day);
            ImageView fest = itemView.findViewById(R.id.fest_image);

            name.setText(ticket_class.getTitle());
            date.setText(ticket_class.getDate());
            time.setText(ticket_class.getTime());
            location.setText(ticket_class.getLocation());
            note.setText(ticket_class.getShort_note());
//            if (ticket_class.getStatus().equalsIgnoreCase("Pending")) {
//                status.setText("Pending");
//                status.setTextColor(itemView.getResources().getColor(R.color.copper_text_color_30));
//            } else {
//                status.setText("Booked");
//            }

            String flag = String.valueOf(userHelperClass.get(itemView.getContext(), ticket_class.getTicket_id(), "false"));

            if (flag.equalsIgnoreCase("true")) {
                status.setText("Booked");
                status.setTextColor(itemView.getResources().getColor(R.color.status_color));
            } else {
                status.setText("Pending");
                status.setTextColor(itemView.getResources().getColor(R.color.copper_text_color_30));
            }

            if(ticket_class.getImage().length() != 0) {
                Picasso.get()
                        .load("https://campaigndesigner.tech/finance-bazaar/uploads/" + ticket_class.getImage().replaceAll(" ", "%20"))
                        .placeholder(R.mipmap.load)
                        .into(fest);
            }else{
                fest.setBackgroundResource(R.mipmap.app_logo);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), gig_offers.class);
                    i.putExtra("name", tname);
                    i.putExtra("url", url);
                    i.putExtra("number", number);
                    i.putExtra("ticket", ticket_class.getTicket_id());
                    itemView.getContext().startActivity(i);
                }
            });


        }
    }
}

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

public class pendingAdapter extends RecyclerView.Adapter<pendingAdapter.pendingView> {

    List<PendingItem> list;

    public pendingAdapter(List<PendingItem> list){
        this.list = list;
    }

    @NonNull
    @Override
    public pendingView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_, parent, false);
        return new pendingView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pendingView holder, int position) {

        holder.Bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class pendingView extends RecyclerView.ViewHolder {
        public pendingView(@NonNull View itemView) {
            super(itemView);
        }

        public void Bind(PendingItem pendingItem) {

            TextView money = itemView.findViewById(R.id.money_trans);
            TextView  gig = itemView.findViewById(R.id.pay);
            TextView time = itemView.findViewById(R.id.money_trans_time);

            ImageView i = itemView.findViewById(R.id.payBy);

            if(pendingItem.getImage().length() != 0) {
                Picasso.get().load(pendingItem.getImage()).placeholder(R.mipmap.app_logo).into(i);
            }else{
                i.setBackgroundResource(R.mipmap.app_logo);
            }
            gig.setText(pendingItem.getCampaignname() + "\n" + "In Progress");
            money.setText("₹" + pendingItem.getEarnId());
//            time.setText(pendingItem.getcampaign_id());
        }
    }
}

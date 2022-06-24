package com.financebazaar.gigs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import club.cred.synth.views.SynthButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class gig_offers extends AppCompatActivity {

    RecyclerView recyclerView;
    SpinKitView progressBar;
    SynthButton ticket, query;
    int count = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_offers);
        recyclerView = findViewById(R.id.g_rView);
        ImageView b = findViewById(R.id.profile);
        ImageView q = findViewById(R.id.query);
        swipeRefreshLayout = findViewById(R.id.swipe);
        progressBar = findViewById(R.id.progress_bar);
        ticket = findViewById(R.id.get_ticket);
        query = findViewById(R.id.queries);
        progressBar.animate();

        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(gig_offers.this);
                View v = getLayoutInflater().inflate(R.layout.todo_info, null);
                alert.setView(v);
                AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(getResources().getColor(R.color.synth_color))
                );
                alertDialog.show();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ticketStatus();

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(gig_offers.this, query_activity.class);
                startActivity(i);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ticketStatus();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("ticket");
                String flag = String.valueOf(userHelperClass.get(gig_offers.this, id, "false"));

                Log.d("hell", id);
                Log.d("hell", flag);

                if(flag.equalsIgnoreCase("true")){
                    userHelperClass.put(gig_offers.this, id, "true");
                    ticket.setEnabled(false);
                    Toast.makeText(gig_offers.this, "Ticket generated", Toast.LENGTH_SHORT).show();
                }else{
                    userHelperClass.put(gig_offers.this, id, "false");
                    Toast.makeText(gig_offers.this, "Complete all the gigs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getGigsList(){

        Call<List<ResponseItem>> gigsCall = Retrofit.getServices().getOffersInfo();
        gigsCall.enqueue(new Callback<List<ResponseItem>>() {
            @Override
            public void onResponse(Call<List<ResponseItem>> call, Response<List<ResponseItem>> response) {

                progressBar.setVisibility(View.GONE);
                List<ResponseItem> gigs = response.body();
                recyclerView.setAdapter(new ticket_gig_adapter(gigs, getIntent().getStringExtra("name"),
                        getIntent().getStringExtra("url"),
                        getIntent().getStringExtra("number")));
                ticket.setVisibility(View.VISIBLE);
                query.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<ResponseItem>> call, Throwable t) {

                Toast.makeText(gig_offers.this, "connection failed.." , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ticketStatus(){

        String number = String.valueOf(userHelperClass.get(gig_offers.this,
                "number", "number"));
        String token = String.valueOf(userHelperClass.get(gig_offers.this, "token", "token"));
        String id = getIntent().getStringExtra("ticket");

        Call<List<ResponseItem>> listCall = Retrofit.getServices().getTicketStatus(number, token, id);
        listCall.enqueue(new Callback<List<ResponseItem>>() {
            @Override
            public void onResponse(Call<List<ResponseItem>> call, Response<List<ResponseItem>> response) {

                progressBar.setVisibility(View.GONE);
                List<ResponseItem> gigs = response.body();
                for(ResponseItem r : gigs){
                    if(r.getStatus().equalsIgnoreCase("1")){
                        count++;
                    }
                }
                Log.d("hell", String.valueOf(count));
                if(count == gigs.size()){
                    userHelperClass.put(gig_offers.this, id, "true");
                }else{
                    userHelperClass.put(gig_offers.this, id, "false");
                }

                recyclerView.setAdapter(new ticket_gig_adapter(gigs, getIntent().getStringExtra("name"),
                        getIntent().getStringExtra("url"),
                        getIntent().getStringExtra("number")));
                ticket.setVisibility(View.VISIBLE);
                query.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<ResponseItem>> call, Throwable t) {

            }
        });

    }

}
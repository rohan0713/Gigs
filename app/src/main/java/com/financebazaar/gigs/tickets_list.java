package com.financebazaar.gigs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import club.cred.synth.views.ElevatedView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tickets_list extends AppCompatActivity {

    String number, url, name;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);
        ElevatedView tc = findViewById(R.id.ticket_view);
        ImageView b = findViewById(R.id.profile);
        swipeRefreshLayout = findViewById(R.id.swipe);
        spinKitView = findViewById(R.id.progress_bar);
        spinKitView.animate();
        recyclerView = findViewById(R.id.trView);
        name = getIntent().getStringExtra("name");
        url = getIntent().getStringExtra("url");
        number = getIntent().getStringExtra("number");

        List<ticket_class> list = new ArrayList<>();
        getTicketsList();
        recyclerView.setLayoutManager(new LinearLayoutManager(tickets_list.this, LinearLayoutManager.HORIZONTAL, false));
        final int radius = getResources().getDimensionPixelSize(R.dimen.radius);
        final int dotsHeight = getResources().getDimensionPixelSize(R.dimen.dots_height);
        final int color = ContextCompat.getColor(tickets_list.this, R.color.copper_text_color);
        recyclerView.addItemDecoration(new DotsIndicatorDecoration(radius, radius * 4, dotsHeight, color, color));
        new PagerSnapHelper().attachToRecyclerView(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTicketsList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getTicketsList() {

        Call<List<tickets>> ticketsCall = Retrofit.getServices().getTickets();
        ticketsCall.enqueue(new Callback<List<tickets>>() {
            @Override
            public void onResponse(Call<List<tickets>> call, Response<List<tickets>> response) {

                List<tickets> list = response.body();
                for (tickets t : list) {
                    String ticket_id = String.valueOf(t.getTicket_id());
                    Log.d("hell", t.getTicket_id());
                    if (!userHelperClass.contains(tickets_list.this, ticket_id)) {
                        userHelperClass.put(tickets_list.this, ticket_id, "false");
                    }
                }
                recyclerView.setAdapter(new ticket_adapter(list, name, url, number));
                spinKitView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<tickets>> call, Throwable t) {

            }
        });

    }
}
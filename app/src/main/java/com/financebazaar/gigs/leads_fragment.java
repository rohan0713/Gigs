package com.financebazaar.gigs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class leads_fragment extends Fragment {

    String number, url, name, code;
    RecyclerView recyclerView;
    SpinKitView spinKitView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_leads_fragment, container, false);

        spinKitView = view.findViewById(R.id.progress_bar);
        SwipeRefreshLayout swipeRefreshLayout;
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        number = String.valueOf(userHelperClass.get(view.getContext(), "number", "number"));
        url = String.valueOf(userHelperClass.get(view.getContext(), "url", "url"));
        name = String.valueOf(userHelperClass.get(view.getContext(), "name", "name"));
        code = String.valueOf(userHelperClass.get(view.getContext(), "code", "code"));

        recyclerView = view.findViewById(R.id.trView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        getTicketsList();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTicketsList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

//        final int radius = getResources().getDimensionPixelSize(R.dimen.radius);
//        final int dotsHeight = getResources().getDimensionPixelSize(R.dimen.dots_height);
//        final int color = ContextCompat.getColor(view.getContext(), R.color.copper_text_color);
//        recyclerView.addItemDecoration(new DotsIndicatorDecoration(radius, radius * 4, dotsHeight, color, color));
//        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        return view;
    }

    private void getTicketsList() {

        Call<List<tickets>> ticketsCall = Retrofit.getServices().getTickets();
        ticketsCall.enqueue(new Callback<List<tickets>>() {
            @Override
            public void onResponse(Call<List<tickets>> call, Response<List<tickets>> response) {

                spinKitView.setVisibility(View.GONE);
                List<tickets> list = response.body();
                for (tickets t : list) {
                    String ticket_id = String.valueOf(t.getTicket_id());
                    Log.d("hell", t.getTicket_id());
                    if (!userHelperClass.contains(view.getContext(), ticket_id)) {
                        userHelperClass.put(view.getContext(), ticket_id, "false");
                    }
                }
                recyclerView.setAdapter(new ticket_adapter(list, name, url, number));
            }

            @Override
            public void onFailure(Call<List<tickets>> call, Throwable t) {

                Toast.makeText(view.getContext(), "Please check your internet connection...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
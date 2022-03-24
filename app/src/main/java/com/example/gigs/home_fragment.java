package com.example.gigs;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home_fragment extends Fragment {


    RecyclerView recyclerView;
    View view;
    DrawerLayout drawerLayout;
    NavigationView navigationView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);
        ImageView profile = view.findViewById(R.id.profile);
        ImageView n = view.findViewById(R.id.notify_image);

        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new leads_fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
            }
        });

        recyclerView = view.findViewById(R.id.gigs);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),
                2));
        List<ResponseItem> list = new ArrayList<>();
        List<offers> list1 = new ArrayList<>();
        list1.add(new offers(R.drawable.insta_logo, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.wp_logo, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.wp_logo, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.twitter, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.insta_logo, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.twitter, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.wp_logo, "Cloud Dms", "Visit Offer"));
        recyclerView.setAdapter(new gigs_adapter(list1));
        recyclerView.setHasFixedSize(true);
//        getGigsList();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        return view;
    }


    private void getGigsList(){

        Call<List<ResponseItem>> gigsCall = Retrofit.getServices().getOffersInfo();
        gigsCall.enqueue(new Callback<List<ResponseItem>>() {
            @Override
            public void onResponse(Call<List<ResponseItem>> call, Response<List<ResponseItem>> response) {

                List<ResponseItem> gigs = response.body();
//                recyclerView.setAdapter(new gigs_adapter(gigs));
            }

            @Override
            public void onFailure(Call<List<ResponseItem>> call, Throwable t) {

                Toast.makeText(getActivity(), "connection failed.." , Toast.LENGTH_LONG).show();
            }
        });
    }
}
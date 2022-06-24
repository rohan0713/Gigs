package com.financebazaar.gigs;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import club.cred.synth.views.SynthButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class money_fragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    moneyPageAdapter pageAdapter;
    TextView balance, gig_completed;
    ProgressBar progressBar1, progressBar2;

    String number,url,name,code, token;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money_fragment, container, false);
        progressBar1 = view.findViewById(R.id.progress_bar1);
        progressBar2 = view.findViewById(R.id.progress_bar2);
        progressBar1.animate();
        progressBar2.animate();
        BottomNavigationView bn = ((home_screen)requireActivity()).findViewById(R.id.nav_menu);
        bn.getMenu().getItem(3).setIconTintList(ColorStateList.valueOf(Color.BLACK));
        number = String.valueOf(com.financebazaar.gigs.userHelperClass.get(view.getContext(), "number", "number"));
        token = String.valueOf(userHelperClass.get(view.getContext(), "token", "token"));
        name = String.valueOf(com.financebazaar.gigs.userHelperClass.get(view.getContext(), "name", "name"));
        code = String.valueOf(com.financebazaar.gigs.userHelperClass.get(view.getContext(), "code", "code"));
        url = String.valueOf(com.financebazaar.gigs.userHelperClass.get(view.getContext(), "url", "url"));

        tabLayout = view.findViewById(R.id.tabLayout);
        balance = view.findViewById(R.id.balance);
        gig_completed = view.findViewById(R.id.gigs_completed);
        viewPager = view.findViewById(R.id.viewPager);
        getGigsCompleted();

        SynthButton share = view.findViewById(R.id.share_button_money);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), user_profile.class);
                startActivity(i);
            }
        });

        pageAdapter = new moneyPageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());

                        if(tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2){
                            pageAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return view;
    }

    private void getGigsCompleted() {

        Call<Status> call = Retrofit.getServices().getSuccessItem(number, token);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

                progressBar1.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);
                assert response.body() != null;
                List<SuccessItem> list = response.body().getSuccess();
                gig_completed.setText(String.valueOf(list.size()));
                int a = 0;
                for(SuccessItem s : list){
                    a = a + Integer.parseInt(s.getAmount());
                }
                balance.setText("₹ " + String.valueOf(a));

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

}
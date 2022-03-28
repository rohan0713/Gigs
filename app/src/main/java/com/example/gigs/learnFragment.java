package com.example.gigs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import club.cred.synth.views.ElevatedView;

public class learnFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        ElevatedView se = view.findViewById(R.id.sNe);
        ElevatedView ky = view.findViewById(R.id.kyc);
        ElevatedView t = view.findViewById(R.id.team);
        ElevatedView er = view.findViewById(R.id.er);

        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new home_fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
            }
        });

        ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), kyc_Screen.class);
                startActivity(i);
            }
        });

        er.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new money_fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
            }
        });

        return view;
    }
}
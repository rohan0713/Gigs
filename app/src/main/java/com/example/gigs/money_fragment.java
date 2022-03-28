package com.example.gigs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class money_fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money_fragment, container, false);
        TextView p = view.findViewById(R.id.payout);
        TextView r = view.findViewById(R.id.referrals);
        TextView m = view.findViewById(R.id.money);

        if(savedInstanceState == null){
            p.setTextColor(getResources().getColor(R.color.copper_text_color));
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                    new payoutFragment()).commit();
        }

        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.setTextColor(getResources().getColor(R.color.copper_text_color));
//                r.setTextColor(getResources().getColor(R.color.white));
//                m.setTextColor(getResources().getColor(R.color.white));
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                        new payoutFragment()).commit();
            }
        });

//        r.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                r.setTextColor(getResources().getColor(R.color.copper_text_color));
//                p.setTextColor(getResources().getColor(R.color.white));
//                m.setTextColor(getResources().getColor(R.color.white));
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
//                        new payoutFragment()).commit();
//            }
//        });
//
//        m.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                m.setTextColor(getResources().getColor(R.color.copper_text_color));
//                r.setTextColor(getResources().getColor(R.color.white));
//                p.setTextColor(getResources().getColor(R.color.white));
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
//                        new payoutFragment()).commit();
//            }
//        });

        return view;
    }
}
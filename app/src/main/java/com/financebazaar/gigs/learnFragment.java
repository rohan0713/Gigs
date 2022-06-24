package com.financebazaar.gigs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import club.cred.synth.views.ElevatedView;
import club.cred.synth.views.SynthButton;

public class learnFragment extends Fragment {

    String number, url, name, code;
    static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_learn, container, false);

        number = String.valueOf(com.financebazaar.gigs.userHelperClass.get(view.getContext(), "number", "number"));
        name = String.valueOf(com.financebazaar.gigs.userHelperClass.get(view.getContext(), "name", "name"));
        code = String.valueOf(com.financebazaar.gigs.userHelperClass.get(view.getContext(), "code", "code"));
        url = String.valueOf(com.financebazaar.gigs.userHelperClass.get(view.getContext(), "url", "url"));

        ElevatedView se = view.findViewById(R.id.sNe);
        ElevatedView ky = view.findViewById(R.id.kyc);
        ElevatedView contact = view.findViewById(R.id.contact);
        ElevatedView er = view.findViewById(R.id.er);
        SynthButton faq = view.findViewById(R.id.top_faq);
        SynthButton about = view.findViewById(R.id.about_us);
        SynthButton rating = view.findViewById(R.id.rating);

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.financebazaar.gigs"));
                startActivity(intent);

            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), about_info.class);
                i.putExtra("info", "You can contact us by writing an email at info@campaigndesigner.tech We will definitely resolve all your queries!");
                i.putExtra("info_name", "Contact Us");
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.bottom_to_top, R.anim.top_to_bottom);
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), infoActivity.class);
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.bottom_to_top, R.anim.top_to_bottom);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), about_info.class);
                i.putExtra("info", "We are an enthusiastic team who come from all over India and believe that everyone should have multiple sources of income. To keep our aim high and to give equal earning opportunity to all, disregarded of their education or stereotypes, we bring Finance Bazaar, one of many apps that will bring Indians closer to financial independence.");
                i.putExtra("info_name", about.getText());
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.bottom_to_top, R.anim.top_to_bottom);

            }
        });

        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BottomNavigationView bn = requireActivity().findViewById(R.id.nav_menu);
//                bn.setSelectedItemId(R.id.home);
//                Fragment fragment = new home_fragment();
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        fragment).commit();

                Intent intent = new Intent(view.getContext(), user_account.class);
                startActivity(intent);
            }
        });

        ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = String.valueOf(userHelperClass.get(view.getContext(), "adhar", "adhar"));
                String p = String.valueOf(userHelperClass.get(view.getContext(), "pan", "pan"));
                Intent i = new Intent(view.getContext(), kyc_Screen.class);
                i.putExtra("adhar", a);
                i.putExtra("pan", p);
                startActivity(i);
            }
        });

        er.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                BottomNavigationView bn = requireActivity().findViewById(R.id.nav_menu);
                bn.setSelectedItemId(R.id.money);
                Fragment fragment = new money_fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
            }
        });

        return view;
    }


}
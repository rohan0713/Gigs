package com.financebazaar.gigs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import club.cred.synth.views.SynthButton;

public class home_screen extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    public static BottomNavigationView bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        bn = findViewById(R.id.nav_menu);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        bn.setBackground(null);
        bn.getMenu().getItem(2).setEnabled(false);
//
//        Bundle bundle = new Bundle();
//        bundle.putString("number", getIntent().getStringExtra("number"));
////        bundle.putString("url", getIntent().getStringExtra("url"));
//        bundle.putString("name", getIntent().getStringExtra("name"));
//        bundle.putString("code", getIntent().getStringExtra("code"));
        Fragment fragment = new home_fragment();
//        fragment.setArguments(bundle);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
        }

        bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                if (item.getItemId() == R.id.home) {
                    fragment = new home_fragment();
                } else if (item.getItemId() == R.id.ticket) {
                    fragment = new leads_fragment();

                } else if (item.getItemId() == R.id.money) {
                    fragment = new money_fragment();

                } else if (item.getItemId() == R.id.learn) {
                    fragment = new learnFragment();

                }

                assert fragment != null;
//                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment)
                        .commit();
                return true;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(home_screen.this);
                View v = getLayoutInflater().inflate(R.layout.gigs_type, null);
                alert.setView(v);
                AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT)
                );
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                SynthButton demat = v.findViewById(R.id.demat_button);
                SynthButton savings = v.findViewById(R.id.savings_button);
                SynthButton crypto = v.findViewById(R.id.credit_button);
                SynthButton gaming = v.findViewById(R.id.itr_button);

                Window window = alertDialog.getWindow();

                alertDialog.show();
                alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.BOTTOM);

                demat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(home_screen.this, all_gigs.class);
                        i.putExtra("title", "Demat");
                        alertDialog.dismiss();
                        startActivity(i);
                    }
                });

                savings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(home_screen.this, all_gigs.class);
                        i.putExtra("title", "Savings");
                        alertDialog.dismiss();
                        startActivity(i);
                    }
                });

                crypto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(home_screen.this, all_gigs.class);
                        i.putExtra("title", "Crypto");
                        alertDialog.dismiss();
                        startActivity(i);
                    }
                });

                gaming.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(home_screen.this, all_gigs.class);
                        i.putExtra("title", "Gaming");
                        alertDialog.dismiss();
                        startActivity(i);
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {

        if(bn.getSelectedItemId() == R.id.home) {
            super.onBackPressed();
        }else{
            bn.setSelectedItemId(R.id.home);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        userHelperClass.remove(home_screen.this, "second");
        userHelperClass.remove(home_screen.this, "list");
    }

    @Override
    protected void onDestroy() {
        userHelperClass.remove(home_screen.this, "second");
        userHelperClass.remove(home_screen.this, "list");
        super.onDestroy();
    }

}
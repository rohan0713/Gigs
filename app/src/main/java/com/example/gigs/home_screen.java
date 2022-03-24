package com.example.gigs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        BottomNavigationView bn = findViewById(R.id.nav_menu);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        bn.setBackground(null);
        bn.getMenu().getItem(2).setEnabled(false);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new home_fragment()).commit();
        }

        bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                if(item.getItemId() == R.id.home){
                    fragment = new home_fragment();
                }else if(item.getItemId() == R.id.leads){
                    fragment = new leads_fragment();

                }else if(item.getItemId() == R.id.money){
                    fragment = new money_fragment();

                }else if(item.getItemId() == R.id.learn){
                    fragment = new learnFragment();

                }

                assert fragment !=null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
                return true;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(home_screen.this, R.style.MyAlertDialogTheme);
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
                SynthButton credit = v.findViewById(R.id.credit_button);
                SynthButton tax = v.findViewById(R.id.itr_button);

                Window window = alertDialog.getWindow();
//                WindowManager.LayoutParams lp = window.getAttributes();
//                lp.x = 100;
//                lp.y = 200;
//                window.setAttributes(lp);

                alertDialog.show();
                alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.BOTTOM);

                demat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(home_screen.this, all_gigs.class);
                        alertDialog.dismiss();
                        startActivity(i);
                    }
                });

            }
        });
    }
}
package com.financebazaar.gigs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import club.cred.synth.views.ElevatedView;

public class infoActivity extends AppCompatActivity {

    boolean first = false;
    boolean second = false;
    boolean third = false;
    boolean fourth = false;
    boolean fifth = false;
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_to_top, R.anim.top_to_bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ImageView back = findViewById(R.id.back_pressed);
        ElevatedView f = findViewById(R.id.fElevated);
        ElevatedView s = findViewById(R.id.sElevated);
        ElevatedView t = findViewById(R.id.tElevated);
        ElevatedView fo = findViewById(R.id.foElevated);
        ElevatedView fi = findViewById(R.id.fiElevated);

        TextView t1 = findViewById(R.id.fQuestion);
        TextView t2 = findViewById(R.id.sQuestion);
        TextView t3 = findViewById(R.id.tQuestion);
        TextView t4 = findViewById(R.id.foQuestion);
        TextView t5 = findViewById(R.id.fiQuestion);

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (first) {
                    t1.setVisibility(View.GONE);
                    first = false;
                } else {
                    t1.setVisibility(View.VISIBLE);
                    first = true;
                }
            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (second) {
                    t2.setVisibility(View.GONE);
                    second = false;
                } else {
                    t2.setVisibility(View.VISIBLE);
                    second = true;
                }
            }
        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (third) {
                    t3.setVisibility(View.GONE);
                    third = false;
                } else {
                    t3.setVisibility(View.VISIBLE);
                    third = true;
                }
            }
        });

        fo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fourth) {
                    t4.setVisibility(View.GONE);
                    fourth = false;
                } else {
                    t4.setVisibility(View.VISIBLE);
                    fourth = true;
                }
            }
        });

        fi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fifth) {
                    t5.setVisibility(View.GONE);
                    fifth = false;
                } else {
                    t5.setVisibility(View.VISIBLE);
                    fifth = true;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.bottom_to_top, R.anim.top_to_bottom);
            }
        });


    }
}
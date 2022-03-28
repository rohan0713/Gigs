package com.example.gigs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class all_gigs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_gigs);
        RecyclerView recyclerView = findViewById(R.id.rv_gigs);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<gigs_detail> list = new ArrayList<>();
        list.add(new gigs_detail(R.drawable.facebook, "Jiffy demat account", "Free demat", "Earn 450rs"));
        list.add(new gigs_detail(R.drawable.wp_logo, "Jiffy demat account", "Free demat", "Earn 450rs"));
        list.add(new gigs_detail(R.drawable.twitter, "Jiffy demat account", "Free demat", "Earn 450rs"));
        list.add(new gigs_detail(R.drawable.facebook, "Jiffy demat account", "Free demat", "Earn 450rs"));
        list.add(new gigs_detail(R.drawable.insta_logo, "Jiffy demat account", "Free demat", "Earn 450rs"));
        list.add(new gigs_detail(R.drawable.facebook, "Jiffy demat account", "Free demat", "Earn 450rs"));
        list.add(new gigs_detail(R.drawable.facebook, "Jiffy demat account", "Free demat", "Earn 450rs"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new giglist(list));
    }
}
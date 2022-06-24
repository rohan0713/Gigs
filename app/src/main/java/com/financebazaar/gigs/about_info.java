package com.financebazaar.gigs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class about_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_info);
        TextView title = findViewById(R.id.info);
        TextView info = findViewById(R.id.information);
        ImageView back = findViewById(R.id.back_pressed);
        title.setText(getIntent().getStringExtra("info_name"));
        info.setText(getIntent().getStringExtra("info"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.bottom_to_top, R.anim.top_to_bottom);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_to_top, R.anim.top_to_bottom);
    }
}
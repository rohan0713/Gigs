package com.financebazaar.gigs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import club.cred.synth.views.SynthButton;

public class query_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        SynthButton button = findViewById(R.id.query_submit);
        ImageView b = findViewById(R.id.back);
        EditText feed = findViewById(R.id.feedback);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(feed.getText().toString().length() != 0){
                    Toast.makeText(query_activity.this, "Query Submitted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(query_activity.this, "No Query Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
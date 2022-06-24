package com.financebazaar.gigs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class all_gigs extends AppCompatActivity {

    String name,url,number;
    RecyclerView recyclerView;
    TextView s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_gigs);
        recyclerView = findViewById(R.id.rv_gigs);
        ImageView back = findViewById(R.id.back);
        TextView title = findViewById(R.id.gigs_title);
        s = findViewById(R.id.status);

        title.setText(getIntent().getStringExtra("title"));
        name = String.valueOf(userHelperClass.get(all_gigs.this, "name", "name"));
        url = String.valueOf(userHelperClass.get(all_gigs.this, "url", "url"));
        number = String.valueOf(userHelperClass.get(all_gigs.this, " number", "number"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        getGigsList();
    }

    private void getGigsList(){

        Call<List<ResponseItem>> gigsCall = Retrofit.getServices().getOffersInfo();
        gigsCall.enqueue(new Callback<List<ResponseItem>>() {
            @Override
            public void onResponse(Call<List<ResponseItem>> call, Response<List<ResponseItem>> response) {

                List<ResponseItem> gigs = response.body();
                List<ResponseItem> list = new ArrayList<>();
                for(ResponseItem r : gigs){
                    if(r.getType().equalsIgnoreCase(getIntent().getStringExtra("title"))){
                        list.add(r);
                    }
                }
                if(list.size() == 0){
                   s.setText("Nothing to show");
                }else{
                    s.setVisibility(View.GONE);
                }
                recyclerView.setAdapter(new giglist(list,name,url,number));
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<ResponseItem>> call, Throwable t) {

                Toast.makeText(all_gigs.this, "connection failed.." , Toast.LENGTH_LONG).show();
            }
        });
    }
}
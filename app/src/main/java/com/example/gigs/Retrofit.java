package com.example.gigs;

import java.util.List;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class Retrofit {

    public static String url = "https://campaigndesigner.tech/";
    public static services services = null;

    public static services getServices(){

        if(services == null){

            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            services = retrofit.create(services.class);
        }
        return services;
    }

    public interface services {

        @GET("campaign.api.php")
        Call<List<ResponseItem>> getOffersInfo();
    }
}

package com.financebazaar.gigs;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public class Retrofit {

    public static String url = "https://campaigndesigner.tech/finance-bazaar/";
    public static services services = null;

    public static services getServices(){

        if(services == null){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            services = retrofit.create(services.class);
        }
        return services;
    }

    public interface services {

        @GET("campaign.api.php")
        Call<List<ResponseItem>> getOffersInfo();

        @GET("status.api.php")
        Call<Status> getSuccessItem(@Query("no") String number, @Query("token") String  token);

        @GET("get_user_details.api.php")
        Call<List<users>> getUserDetails(@Query("no") String number, @Query("token") String token);

        @GET("authenticate_user.api.php")
        Call<userResponse> authenticateUser(@Query("no") String number);

        @FormUrlEncoded
        @POST("register.api.php")
        Call<userResponse> uploadUser(@Field("user_code") String code,
                                      @Field("name") String name,
                                      @Field("phone_no") String number,
                                      @Field("address") String address,
                                      @Field("gender") String gender,
                                      @Field("dob") String dob,
                                      @Field("image") String image,
                                      @Field("referral_code") String refer
        );

        @Multipart
        @POST("image_update.api.php")
        Call<ArrayList<Object>> updateImage(@Query("no") String number, @Part MultipartBody.Part file);


        @Multipart
        @POST("image_upload.api.php")
        Call<ArrayList<Object>> uploadImage(@Part MultipartBody.Part image);

        @FormUrlEncoded
        @POST("update_user_data.php")
        Call<userResponse> updateUser(@Field("token") String token,
                                      @Field("name") String name,
                                      @Field("no") String number,
                                      @Field("address") String address,
                                      @Field("gender") String gender,
                                      @Field("dob") String dob,
                                      @Field("aadhar_no") String aadhar_no,
                                      @Field("pan_no") String pan_no,
                                      @Field("email") String email
        );

        @GET("ticket.api.php")
        Call<List<tickets>> getTickets();

        @GET("ticket_status.api.php")
        Call<List<ResponseItem>> getTicketStatus(@Query("no") String number,
                                                 @Query("token") String token,
                                                 @Query("ticket_id") String id);
    }
}

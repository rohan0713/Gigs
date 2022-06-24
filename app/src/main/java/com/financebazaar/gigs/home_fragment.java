package com.financebazaar.gigs;


import android.Manifest;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import club.cred.synth.views.ElevatedView;
import club.cred.synth.views.SynthButton;
import club.cred.synth.views.SynthImageButton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home_fragment extends Fragment {


    private static final int IMAGE_PICK = 1000;
    private static final int RESULT_OK = -1;
    RecyclerView recyclerView;
    View view;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private static final int RESULT_LOAD_IMAGE = 1001;
    View header;
    ImageView buttonLoadImage;
    SpinKitView progressBar, progressBar1, progressBar2;
    String number, url, name, code, token;
    TextView balance, gig_completed;
    StringBuilder sb;
    String image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        number = String.valueOf(userHelperClass.get(view.getContext(), "number", "number"));
        token = String.valueOf(userHelperClass.get(view.getContext(), "token", "token"));
        String nn = String.valueOf(userHelperClass.get(view.getContext(), "name", "user"));
        String[] nu = nn.split(" ");
        name = nu[0];
        code = String.valueOf(userHelperClass.get(view.getContext(), "code", "code"));

        progressBar1 = view.findViewById(R.id.progress_bar1);
        progressBar2 = view.findViewById(R.id.progress_bar2);
        progressBar1.animate();
        progressBar2.animate();

        TextView username = view.findViewById(R.id.username);
        sb = new StringBuilder(name);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        username.setText(sb.toString());

        SynthButton share = view.findViewById(R.id.share_button_home);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), user_profile.class);
                startActivity(i);
            }
        });

        balance = view.findViewById(R.id.balance);
        gig_completed = view.findViewById(R.id.gigs_completed);

        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);
        SynthImageButton profile = view.findViewById(R.id.profile);
        SynthImageButton n = view.findViewById(R.id.notify_image);

        progressBar = view.findViewById(R.id.recycle_progress);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.animate();

        SynthButton telegram = view.findViewById(R.id.telegram);
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/FinanceBazaarGigs"));
                startActivity(intent);
            }
        });

        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), user_account.class);
                startActivity(i);
            }
        });

        recyclerView = view.findViewById(R.id.gigs);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                LinearLayoutManager.VERTICAL));

        List<ResponseItem> ls = new ArrayList<>();

        if (userHelperClass.contains(view.getContext(), "second")) {
            if (userHelperClass.contains(view.getContext(), "list")) {
                String value = String.valueOf(userHelperClass.get(view.getContext(), "list", "list"));
                Gson gson = new Gson();

                Type type = new TypeToken<List<ResponseItem>>() {
                }.getType();
                ls = gson.fromJson(value, type);
                if (ls == null) {
                    getGigsList();
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(new gigs_adapter(ls, url, sb.toString(), number));
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setHasFixedSize(true);
                }
            } else {
                getGigsList();
            }

            if (userHelperClass.contains(view.getContext(), "balance")) {
                progressBar1.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);
                String bal = String.valueOf(userHelperClass.get(view.getContext(), "balance", "0"));
                balance.setText("₹ " + bal);

                String g = String.valueOf(userHelperClass.get(view.getContext(), "gigs", 0));
                gig_completed.setText(g);
            } else {
                getGigsCompleted();
            }
        } else {
            getGigsCompleted();
            getGigsList();
        }

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        header = navigationView.getHeaderView(0);

        buttonLoadImage = header.findViewById(R.id.display_pic);
        image = String.valueOf(userHelperClass.get(view.getContext(), "image", "image"));
        TextView Uname = header.findViewById(R.id.username);
        Uname.setText(sb.toString());
        TextView codeR = header.findViewById(R.id.code);
        codeR.setText("Code: " + code);

        Picasso.get()
                .load("https://campaigndesigner.tech/finance-bazaar/uploads/" + image.replaceAll(" ", "%20"))
                .placeholder(R.mipmap.blankprofilepicture)
                .into(buttonLoadImage);

        ElevatedView logout = header.findViewById(R.id.logOut);
        ElevatedView kyc = header.findViewById(R.id.kyc_page);
        ElevatedView earning = header.findViewById(R.id.earning_page);
        ElevatedView shareNearn = header.findViewById(R.id.share_earn);
        SynthButton sh = header.findViewById(R.id.share_button);
        SynthButton faq = header.findViewById(R.id.faq);
        SynthButton about = header.findViewById(R.id.about_us);

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

        sh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), user_profile.class);
                startActivity(intent);
            }
        });

        kyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String a = String.valueOf(userHelperClass.get(view.getContext(), "adhar", "adhar"));
                String p = String.valueOf(userHelperClass.get(view.getContext(), "pan", "pan"));
                Intent i = new Intent(view.getContext(), kyc_Screen.class);
                i.putExtra("adhar", a);
                i.putExtra("pan", p);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        earning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bn = requireActivity().findViewById(R.id.nav_menu);
                bn.setSelectedItemId(R.id.money);
                Fragment fragment = new money_fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
            }
        });

        shareNearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), tickets_list.class);
                startActivity(i);
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userHelperClass.clear(view.getContext());

                Intent i = new Intent(view.getContext(), login_page.class);
                startActivity(i);
                requireActivity().finish();
            }
        });

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, RESULT_LOAD_IMAGE);
                    } else {
                        pick();
                    }
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        return view;
    }

    private void getGigsCompleted() {

        Call<Status> call = Retrofit.getServices().getSuccessItem(number, token);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

                progressBar1.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);
                assert response.body() != null;
                List<SuccessItem> list = response.body().getSuccess();
                gig_completed.setText(String.valueOf(list.size()));
                int a = 0;
                for (SuccessItem s : list) {
                    a = a + Integer.parseInt(s.getAmount());
                }
                balance.setText("₹ " + String.valueOf(a));
                userHelperClass.put(view.getContext(), "balance", String.valueOf(a));
                userHelperClass.put(view.getContext(), "gigs", list.size());
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    private void pick() {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, IMAGE_PICK);

    }


    private void getGigsList() {

        Call<List<ResponseItem>> gigsCall = Retrofit.getServices().getOffersInfo();
        gigsCall.enqueue(new Callback<List<ResponseItem>>() {
            @Override
            public void onResponse(Call<List<ResponseItem>> call, Response<List<ResponseItem>> response) {

                List<ResponseItem> gigs = response.body();
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(new gigs_adapter(gigs, url, sb.toString(), number));
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setHasFixedSize(true);
                Gson gson = new Gson();
                String value = gson.toJson(gigs);
                userHelperClass.put(view.getContext(), "list", value);
                userHelperClass.put(view.getContext(), "second", "yes");
            }

            @Override
            public void onFailure(Call<List<ResponseItem>> call, Throwable t) {

                Toast.makeText(getActivity(), "connection failed..", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RESULT_LOAD_IMAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pick();
                } else {
                    Toast.makeText(view.getContext(), "Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK) {
            Uri uri = data.getData();
            buttonLoadImage.setImageURI(uri);
            getResponse(uri);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(view.getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void getResponse(Uri uri){

        File file = new File(getRealPathFromURI(uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filepart = MultipartBody.Part.createFormData("img", file.getName(), requestFile);

        String number = String.valueOf(userHelperClass.get(view.getContext(), "number", "number"));
        Call<ArrayList<Object>> call = Retrofit.getServices().updateImage(number, filepart);
        call.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, Response<ArrayList<Object>> response) {
                ArrayList<Object> list = response.body();
                assert list != null;

                if (list.get(1).equals(0)) {
                    Toast.makeText(view.getContext(), "Invalid Image", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("hell", String.valueOf(list.get(0)));
                    Log.d("hell", String.valueOf(list.get(1)));
                    image = String.valueOf(list.get(0));
                    userHelperClass.put(view.getContext(), "image", image);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Object>> call, Throwable t) {
                Log.d("hell", t.getMessage());
                Toast.makeText(view.getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
package com.example.gigs;

import static android.graphics.Color.TRANSPARENT;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import club.cred.synth.views.ElevatedView;
import club.cred.synth.views.SynthButton;
import club.cred.synth.views.SynthImageButton;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);
        ImageView profile = view.findViewById(R.id.profile);
        ImageView n = view.findViewById(R.id.notify_image);

        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new leads_fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
            }
        });

        recyclerView = view.findViewById(R.id.gigs);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),
                2));
        List<ResponseItem> list = new ArrayList<>();
        List<offers> list1 = new ArrayList<>();
        list1.add(new offers(R.drawable.insta_logo, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.wp_logo, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.wp_logo, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.twitter, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.insta_logo, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.twitter, "Cloud Dms", "Visit Offer"));
        list1.add(new offers(R.drawable.wp_logo, "Cloud Dms", "Visit Offer"));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new gigs_adapter(list1));
        recyclerView.setHasFixedSize(true);
//        getGigsList();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        header = navigationView.getHeaderView(0);

        ElevatedView logout = header.findViewById(R.id.logOut);
        ElevatedView kyc = header.findViewById(R.id.kyc_page);
        ElevatedView earning = header.findViewById(R.id.earning_page);
        ElevatedView shareNearn = header.findViewById(R.id.share_earn);
        SynthButton sh = header.findViewById(R.id.share_button);

        sh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), getImage.class);
                startActivity(intent);
            }
        });

        kyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), kyc_Screen.class);
                startActivity(i);
                requireActivity().finish();
            }
        });

        earning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new money_fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
            }
        });

        shareNearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), login_page.class);
                startActivity(i);
                requireActivity().finish();
            }
        });

        buttonLoadImage =  header.findViewById(R.id.display_pic);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, RESULT_LOAD_IMAGE);
                    }
                    else{
                        pick();
                    }
                }
//                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                home_fragment.this.startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        return view;
    }

    private void pick() {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, IMAGE_PICK);

    }


    private void getGigsList(){

        Call<List<ResponseItem>> gigsCall = Retrofit.getServices().getOffersInfo();
        gigsCall.enqueue(new Callback<List<ResponseItem>>() {
            @Override
            public void onResponse(Call<List<ResponseItem>> call, Response<List<ResponseItem>> response) {

                List<ResponseItem> gigs = response.body();
//                recyclerView.setAdapter(new gigs_adapter(gigs));
            }

            @Override
            public void onFailure(Call<List<ResponseItem>> call, Throwable t) {

                Toast.makeText(getActivity(), "connection failed.." , Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case RESULT_LOAD_IMAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pick();
                }else{
                    Toast.makeText(view.getContext(), "Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = view.getContext().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
////            ImageView imageView = header.findViewById(R.id.profile_picture);
//            buttonLoadImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//        }

//        if(resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE){
//            Uri uri = data.getData();
//            buttonLoadImage.setImageURI(uri);
//        }

        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK){
            buttonLoadImage.setImageURI(data.getData());

        }
    }
}
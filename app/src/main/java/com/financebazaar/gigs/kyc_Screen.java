package com.financebazaar.gigs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.santalu.maskedittext.MaskEditText;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import club.cred.synth.views.SynthButton;
import club.cred.synth.views.SynthImageButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class kyc_Screen extends AppCompatActivity {


    String adhar_number;
    String pan_number;
    SharedPreferences sharedPreferences;
    String adhaar, pann;
    SynthButton as, ps, pc, ac;
    TextInputLayout al, pl;
    EditText pe;
    MaskEditText ae;
    TextView an, pn, dn;
    boolean flag = false;
    String uname, unumber, uaddress, udob, ugender, ucode, image, urefer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_screen);
        SynthImageButton next = findViewById(R.id.next_to_home);

        TextView name = findViewById(R.id.aadhar_name);
        TextView address = findViewById(R.id.address);
        TextView dob = findViewById(R.id.dob);
        TextView panName = findViewById(R.id.pan_name);
        dn = findViewById(R.id.pan_date_of_birth);
        TextView infoT = findViewById(R.id.info_text);
        ImageView info = findViewById(R.id.info);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    infoT.setVisibility(View.GONE);
                    flag = false;
                } else {
                    infoT.setVisibility(View.VISIBLE);
                    flag = true;
                }
            }
        });

        name.setText(String.valueOf(userHelperClass.get(kyc_Screen.this, "name", "name")));
        address.setText(String.valueOf(userHelperClass.get(kyc_Screen.this, "address", "address")));
        dob.setText(String.valueOf(userHelperClass.get(kyc_Screen.this, "dob", "dob")));
        dn.setText(String.valueOf(userHelperClass.get(kyc_Screen.this, "dob", "dob")));
        panName.setText(String.valueOf(userHelperClass.get(kyc_Screen.this, "name", "name")));

        uname = String.valueOf(userHelperClass.get(kyc_Screen.this, "name", "name"));
        uaddress = String.valueOf(userHelperClass.get(kyc_Screen.this, "address", "address"));
        udob = String.valueOf(userHelperClass.get(kyc_Screen.this, "dob", "dob"));
        ugender = String.valueOf(userHelperClass.get(kyc_Screen.this, "gender", "gender"));
        unumber = String.valueOf(userHelperClass.get(kyc_Screen.this, "number", "number"));
        ucode = String.valueOf(userHelperClass.get(kyc_Screen.this, "code", "code"));
        image = String.valueOf(userHelperClass.get(kyc_Screen.this, "image", "image"));
        urefer = String.valueOf(userHelperClass.get(kyc_Screen.this, "referral", "referral"));

        as = findViewById(R.id.adhar_submit);
        ps = findViewById(R.id.pan_submit);
        pc = findViewById(R.id.pan_change);
        ac = findViewById(R.id.adhar_change);

        al = findViewById(R.id.aadhar_layout);
        pl = findViewById(R.id.panLayout);

        ae = findViewById(R.id.aadhar_number);
        pe = findViewById(R.id.panNumber);

        an = findViewById(R.id.a_number);
        pn = findViewById(R.id.p_number);

        if (getIntent().getStringExtra("adhar") != null) {
            adhar_number = getIntent().getStringExtra("adhar");
            adhaar = adhar_number;
            al.setVisibility(View.GONE);
            an.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            int count = 0;
            for (int i = 0; i < adhar_number.length(); i++) {
                sb.append(adhar_number.charAt(i));
                count++;
                if (count == 4 && i < adhar_number.length() - 1) {
                    sb.append("-");
                    count = 0;
                }
            }
            an.setText(sb.toString());
            ae.setText(sb.toString());
        }

        if (getIntent().getStringExtra("pan") != null) {
            pan_number = getIntent().getStringExtra("pan");
            pann = pan_number;
            pe.setText(pan_number);
            pl.setVisibility(View.GONE);
            pn.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            int count = 0;
            int j = 5;
            for (int i = 0; i < pan_number.length(); i++) {
                sb.append(pan_number.charAt(i));
                count++;
                if (count == j && i < pan_number.length() - 1) {
                    sb.append(" ");
                    count = 0;
                    j = 4;
                }
            }
            pn.setText(sb.toString());
        }


        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pn.setVisibility(View.GONE);
                pn.setText("");
                pe.setVisibility(View.VISIBLE);
                pl.setVisibility(View.VISIBLE);

            }
        });

        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                an.setVisibility(View.GONE);
                ae.setVisibility(View.VISIBLE);
                an.setText("");
                al.setVisibility(View.VISIBLE);
            }
        });

        as.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ae.getRawText().toString().length() < 12 || ae.getRawText().toString().length() > 12) {
                    al.setError("Enter a valid number");
                } else {
                    adhar_number = ae.getText().toString().trim();
                    adhaar = ae.getRawText().toString();
                    al.setVisibility(View.GONE);
                    an.setVisibility(View.VISIBLE);
                    an.setText(adhar_number);
                }
            }
        });

        ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pe.getText().toString().length() < 10 || pe.getText().toString().length() > 10) {
                    pl.setError("Enter a valid number");
                } else {
                    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                    pan_number = pe.getText().toString().trim();
                    Matcher matcher = pattern.matcher(pan_number);
                    if (matcher.matches()) {
                        Log.i("Matching", "Yes");
                        pann = pe.getText().toString();
                        pl.setVisibility(View.GONE);
                        StringBuilder sb = new StringBuilder();
                        int count = 0;
                        int j = 5;
                        for (int i = 0; i < pan_number.length(); i++) {
                            sb.append(pan_number.charAt(i));
                            count++;
                            if (count == j && i < pan_number.length() - 1) {
                                sb.append(" ");
                                count = 0;
                                j = 4;
                            }
                        }
                        pn.setVisibility(View.VISIBLE);
                        pn.setText(sb.toString());
                    }else{
                        Toast.makeText(kyc_Screen.this, "Invalid PAN Number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        next.setOnClickListener(view -> {

            if (userHelperClass.contains(kyc_Screen.this, "kvisited")) {
                update();
            } else {
                upload();
            }
        });
    }

    private void update() {

        if (an.getText().length() == 0 && pn.getText().length() == 0) {
            Toast.makeText(kyc_Screen.this, "Please fill your details", Toast.LENGTH_LONG).show();
        } else if (an.length() == 0) {
            Toast.makeText(kyc_Screen.this, "Enter your aadhar number", Toast.LENGTH_LONG).show();
        } else if (pn.length() == 0) {
            Toast.makeText(kyc_Screen.this, "Enter your PAN number", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(kyc_Screen.this, "Please wait for few seconds...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(kyc_Screen.this, home_screen.class);
            String aadhar_no = String.valueOf(userHelperClass.get(kyc_Screen.this, "adhar", "adhar"));
            String pan_no = String.valueOf(userHelperClass.get(kyc_Screen.this, "pan", "pan"));
            String token = String.valueOf(userHelperClass.get(kyc_Screen.this, "token", "token"));
            String uemail = String.valueOf(userHelperClass.get(kyc_Screen.this, "email", "email"));

            Log.d("hell", token);
            Log.d("hell", uname);
            Log.d("hell", unumber);
            Log.d("hell", uaddress);
            Log.d("hell", ugender);
            Log.d("hell", udob);
            Log.d("hell", aadhar_no);
            Log.d("hell", adhaar);
            Log.d("hell", pann);
            Log.d("hell", pan_no);
            Log.d("hell", uemail);

            Call<userResponse> call = Retrofit.getServices().updateUser(token, uname, unumber, uaddress,
                    ugender, udob, adhaar, pann, uemail);
            call.enqueue(new Callback<userResponse>() {
                @Override
                public void onResponse(Call<userResponse> call, Response<userResponse> response) {
                    Log.d("hell", response.body().toString());
//                    Toast.makeText(kyc_Screen.this, "success", Toast.LENGTH_SHORT).show();
                    userHelperClass.put(kyc_Screen.this, "adhar", adhaar);
                    userHelperClass.put(kyc_Screen.this, "pan", pann);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onFailure(Call<userResponse> call, Throwable t) {
                    Log.d("hell", t.getMessage());
                    Toast.makeText(kyc_Screen.this, "Data upload failed, Try Again", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void upload() {

        if (an.getText().length() == 0 && pn.getText().length() == 0) {
            Toast.makeText(kyc_Screen.this, "Please fill your details", Toast.LENGTH_LONG).show();
        } else if (an.length() == 0) {
            Toast.makeText(kyc_Screen.this, "Enter your aadhar number", Toast.LENGTH_LONG).show();
        } else if (pn.length() == 0) {
            Toast.makeText(kyc_Screen.this, "Enter your PAN number", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(kyc_Screen.this, home_screen.class);

            Toast.makeText(kyc_Screen.this, "Please wait for few seconds...", Toast.LENGTH_SHORT).show();
            Call<userResponse> userPost = Retrofit.getServices().uploadUser(ucode, uname, unumber, uaddress, ugender, udob, image, urefer);
            userPost.enqueue(new Callback<userResponse>() {
                @Override
                public void onResponse(Call<userResponse> call, Response<userResponse> response) {

                    assert response.body() != null;
                    userResponse u = response.body();
                    userHelperClass.put(kyc_Screen.this, "token", String.valueOf(u.token));
                    userHelperClass.put(kyc_Screen.this, "kvisited", true);
                    Log.d("rest", String.valueOf(response.body().token));
//                    Toast.makeText(kyc_Screen.this, "Upload success", Toast.LENGTH_SHORT).show();
                    userHelperClass.put(kyc_Screen.this, "adhar", adhaar);
                    userHelperClass.put(kyc_Screen.this, "pan", pann);
                    update();
                }

                @Override
                public void onFailure(Call<userResponse> call, Throwable t) {
                    Log.d("hell", t.getMessage());
                    Toast.makeText(kyc_Screen.this, "Upload failed, Try Again", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

}
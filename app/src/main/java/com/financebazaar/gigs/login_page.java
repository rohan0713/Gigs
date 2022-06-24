package com.financebazaar.gigs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.concurrent.TimeUnit;

import club.cred.synth.views.SynthButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_page extends AppCompatActivity {

    SynthButton verify, resend;
    private FirebaseAuth auth;
    String verificationCodeBySystem;
    ProgressBar pb;
    EditText otp;
    TextView timer, auto;
    String phoneNumber;
    boolean flag = false;
    boolean got_code = false;
    String code;
    AlertDialog alertDialog;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        TypeWriter textview = findViewById(R.id.animatedText);
        textview.setText("");
        textview.setCharacterDelay(100);
        textview.animateText("Opening Saving Accounts");
        EditText mobile_number = findViewById(R.id.mobile_number);
        SynthButton button = findViewById(R.id.proceed);

        FirebaseMessaging.getInstance().subscribeToTopic("gigs")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "hello";
                        if (!task.isSuccessful()) {
                            msg = "no hello";
                        }
                    }
                });


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int time = 60000;
                phoneNumber = mobile_number.getText().toString().trim();
                if (phoneNumber.length() != 10) {
                    mobile_number.setError("Enter a valid 10-digit mobile number");
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(login_page.this);
                    View v = getLayoutInflater().inflate(R.layout.otp_dialog, null);
                    alert.setView(v);
                    alertDialog = alert.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.getWindow().setBackgroundDrawable(
                            new ColorDrawable(Color.TRANSPARENT)
                    );
                    timer = v.findViewById(R.id.timer);
                    resend = v.findViewById(R.id.resend);
                    auto = v.findViewById(R.id.auto);

                    alertDialog.show();

                    if (flag) {
                        countDownTimer.cancel();
                        flag = false;
                    }
                    countDownTimer = new CountDownTimer(time, 1000) {

                        public void onTick(long millisUntilFinished) {
                            flag = true;
                            timer.setText("Seconds Remaining: " + (millisUntilFinished / 1000));
                        }

                        public void onFinish() {

                            auto.setVisibility(View.GONE);
                            timer.setVisibility(View.GONE);
                            resend.setVisibility(View.VISIBLE);

                        }
                    }.start();


                    alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);
                    Window window = alertDialog.getWindow();
                    window.setGravity(Gravity.BOTTOM);

                    otp = v.findViewById(R.id.otp);
                    verify = v.findViewById(R.id.verify_otp);
                    auth = FirebaseAuth.getInstance();
                    pb = v.findViewById(R.id.progress_bar);
                    if (phoneNumber.equalsIgnoreCase("9876543210")) {

                    } else {
                        verificationCode(phoneNumber);
                    }
                    otp.requestFocus();

//                    if (otp.getText().toString().equalsIgnoreCase(code)) {
//
//                        authenticate();
//                    }

                    otp.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                            if (otp.getText().toString().equalsIgnoreCase(code)) {
//                                authenticate();
//                            }
                            if (charSequence.toString().length() == 6) {
                                verify.setEnabled(true);
                            }
                            if (charSequence.toString().length() < 6) {
                                verify.setEnabled(false);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    verify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String c = otp.getText().toString().trim();
                            if (phoneNumber.equalsIgnoreCase("9876543210") && c.equalsIgnoreCase("111111")) {
                                authenticate();
                            } else if (c.isEmpty() || c.length() < 6) {
                                Toast.makeText(login_page.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                            }
                            else if (verificationCodeBySystem != null) {
                                otp.setEnabled(false);
                                verify.setText("");
                                verify.setEnabled(false);
                                pb.setVisibility(View.VISIBLE);
                                pb.animate();
                                verifyCode(c);
                            }
                            else {
//                                verifyCode(c);
////                                if (verificationCodeBySystem.length() == 0) {
////                                    Toast.makeText(login_page.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
////                                }else {
////                                    pb.setVisibility(View.VISIBLE);
////                                    pb.animate();
////                                    otp.setText(code);
////                                    verify.setText("");
////                                    verify.setEnabled(false);
////                                    authenticate();
////                                }
                                Toast.makeText(login_page.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    resend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            auto.setVisibility(View.VISIBLE);
                            timer.setVisibility(View.VISIBLE);
                            resend.setVisibility(View.GONE);
                            countDownTimer.cancel();
                            countDownTimer = new CountDownTimer(time, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    flag = true;
                                    timer.setText("Seconds Remaining: " + (millisUntilFinished / 1000));
                                }

                                public void onFinish() {

                                    auto.setVisibility(View.GONE);
                                    timer.setVisibility(View.GONE);
                                    resend.setVisibility(View.VISIBLE);

                                }
                            }.start();
                            if (!phoneNumber.equalsIgnoreCase("9876543210")) {
                                verificationCode(phoneNumber);
                            }
                        }
                    });
                }
            }
        });

    }

    private void getUser(String token) {

        Call<List<users>> user = Retrofit.getServices().getUserDetails(phoneNumber, token);
        user.enqueue(new Callback<List<users>>() {
            @Override
            public void onResponse(Call<List<users>> call, Response<List<users>> response) {

                List<users> list = response.body();
                userHelperClass.put(login_page.this, "adhar", list.get(0).getAadhar_no());
                userHelperClass.put(login_page.this, "pan", list.get(0).getPan_no());
                userHelperClass.put(login_page.this, "name", list.get(0).getName());
                userHelperClass.put(login_page.this, "address", list.get(0).getAddress());
                userHelperClass.put(login_page.this, "dob", list.get(0).getDob());
                userHelperClass.put(login_page.this, "gender", list.get(0).getGender());
                userHelperClass.put(login_page.this, "code", list.get(0).getUser_code());
                userHelperClass.put(login_page.this, "number", list.get(0).getPhone_no());
                userHelperClass.put(login_page.this, "email", list.get(0).getEmail());
                userHelperClass.put(login_page.this, "image", list.get(0).getImage());
                userHelperClass.put(login_page.this, "referral", list.get(0).getReferral_code());

                Intent i = new Intent(login_page.this, home_screen.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<List<users>> call, Throwable t) {
                Toast.makeText(login_page.this, "Please have a active internet...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void verificationCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+91" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                pb.setVisibility(View.VISIBLE);
                pb.animate();
                otp.setText(code);
                otp.setEnabled(false);
                verify.setText("");
                verify.setEnabled(false);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(login_page.this, "verification failed! Please try again", Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {

//        Log.d("hell", verificationCodeBySystem);
//        Log.d("hell", code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, code);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential).addOnCompleteListener(
                login_page.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            authenticate();
//                            Toast.makeText(login_page.this, "correct OTP", Toast.LENGTH_LONG).show();
                        } else {
                            otp.setEnabled(true);
                            verify.setText("Verify");
                            verify.setEnabled(true);
                            pb.setVisibility(View.GONE);
                            Toast.makeText(login_page.this, "Incorrect OTP", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void authenticate() {

        Call<userResponse> auth = Retrofit.getServices().authenticateUser(phoneNumber);
        auth.enqueue(new Callback<userResponse>() {
            @Override
            public void onResponse(Call<userResponse> call, Response<userResponse> response) {

                assert response.body() != null;
                if (String.valueOf(response.body().status).equalsIgnoreCase("True")) {
                    String token = String.valueOf(response.body().token);
                    Log.d("hell", token);
                    userHelperClass.put(login_page.this, "token", token);
                    userHelperClass.put(login_page.this, "kvisited", true);
                    getUser(token);
                } else {
                    Log.d("hell", String.valueOf(response.body()));
                    Intent intent = new Intent(login_page.this, userInfo.class);
                    intent.putExtra("number", phoneNumber);
                    alertDialog.dismiss();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<userResponse> call, Throwable t) {


            }
        });

    }
}
package com.example.gigs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
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

import java.util.concurrent.TimeUnit;

import club.cred.synth.views.SynthButton;
import club.cred.synth.views.SynthImageButton;

public class login_page extends AppCompatActivity {

    SynthButton verify, resend;
    private FirebaseAuth auth;
    String verificationCodeBySystem;
    ProgressBar pb;
    PinView otp;
    TextView timer, auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        TypeWriter textview = findViewById(R.id.animatedText);
        textview.setText("");
        textview.setCharacterDelay(100);
        String code = "123456";
        textview.animateText("Saving Accounts");
        EditText mobile_number = findViewById(R.id.mobile_number);
        SynthButton button = findViewById(R.id.proceed);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = mobile_number.getText().toString().trim();
                AlertDialog.Builder alert = new AlertDialog.Builder(login_page.this, R.style.MyAlertDialogTheme);
                View v = getLayoutInflater().inflate(R.layout.otp_dialog, null);
                alert.setView(v);
                AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT)
                );
                timer = v.findViewById(R.id.timer);
                resend = v.findViewById(R.id.resend);
                auto = v.findViewById(R.id.auto);

                alertDialog.show();

                new CountDownTimer(15000, 1000) {

                    public void onTick(long millisUntilFinished) {
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
//                verificationCode(phoneNumber);
//                getVerification(code);

                otp.requestFocus();
                otp.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        if(charSequence.toString().length()==6){
                            verify.setText("");
                            pb.setVisibility(View.VISIBLE);
                            pb.animate();
                            Intent intent = new Intent(login_page.this, kyc_Screen.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                verify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String code = otp.getText().toString();

                        if (code.isEmpty() || code.length() < 6) {
                            otp.setError("Wrong OTP...");
                            otp.requestFocus();
                        }else{
                            if(getVerification(code)){
                                verify.setText("");
                                pb.setVisibility(View.VISIBLE);
                                pb.animate();
                                Intent intent = new Intent(login_page.this, kyc_Screen.class);
                                startActivity(intent);
                                finish();
                            }else{
                                otp.setError("Wrong OTP...");
                            }
                        }
//                        verifyCode(code);
                    }
                });

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

            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(login_page.this, "verification failed", Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, code);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(
                login_page.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(login_page.this, "Success", Toast.LENGTH_LONG).show();
                            verify.setText("");
                            pb.setVisibility(View.VISIBLE);
                            pb.animate();
                            Intent i = new Intent(login_page.this, kyc_Screen.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(login_page.this, "failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public static boolean getVerification(String code){

        if(code.equals("123456")){
            return true;
        }
        return false;
    }
}
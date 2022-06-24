package com.financebazaar.gigs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import club.cred.synth.views.SynthButton;

public class gigs_details_screen extends AppCompatActivity {

    CardView ben, km, ht, faq;
    TextView title, offer, name;
    ImageView i;
    boolean flag = false;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gigs_details_screen);
        i = findViewById(R.id.back_pressed);
        ben = findViewById(R.id.benefits);
        km = findViewById(R.id.know_more);
        ht = findViewById(R.id.how_to);
        faq = findViewById(R.id.faq);
        SynthButton share = findViewById(R.id.share_others);
        SynthButton do_it = findViewById(R.id.do_it_yourself);
        title = findViewById(R.id.gig_title);
        offer = findViewById(R.id.gig_offer);
        name = findViewById(R.id.user_name);
        TextView steps = findViewById(R.id.steps);
        TextView terms = findViewById(R.id.terms_c);
        TextView money = findViewById(R.id.money);
        TextView gig = findViewById(R.id.gigName);

        gig.setText(getIntent().getStringExtra("title"));

        money.setText("Earn ₹" + getIntent().getStringExtra("earn"));
        share.setText("Share to others &\nEarn ₹" + getIntent().getStringExtra("refer"));

        do_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("id");
//                Intent i = new Intent(gigs_details_screen.this, doItActivity.class);
//                i.putExtra("id", id);
//                startActivity(i);
                AlertDialog.Builder alert = new AlertDialog.Builder(gigs_details_screen.this);
                View v = getLayoutInflater().inflate(R.layout.paytm_dialog, null);
                alert.setView(v);
                AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(getResources().getColor(R.color.synth_color))
                );
                EditText paytm_number = v.findViewById(R.id.paytm_number);
                TextView ignore = v.findViewById(R.id.ignore);
                SynthButton submit = v.findViewById(R.id.number_submit);

                alertDialog.show();
                alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                Window window = alertDialog.getWindow();
                window.setGravity(Gravity.BOTTOM);

                ignore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String number = String.valueOf(userHelperClass.get(gigs_details_screen.this,
                                "number", "number"));
                        String url = "https://campaigndesigner.tech/fb-earn.php?campaign=" + id + "&paytmno="+number;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        alertDialog.dismiss();

                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String number = paytm_number.getText().toString().trim();
                        if(number.length() != 10){
                            Toast.makeText(gigs_details_screen.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                        }else{
                            String url = "https://campaigndesigner.tech/fb-earn.php?campaign=" + id + "&paytmno="+number;
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
        relativeLayout = findViewById(R.id.share_layout);
        String str = getIntent().getStringExtra("steps");
        String[] st = str.split("\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : st){
            stringBuilder.append(s + "\n\n");
        }
        steps.setText(stringBuilder);

        String str1 = getIntent().getStringExtra("terms");
        String[] stt = str1.split("\n");
        StringBuilder stringBuilder1 = new StringBuilder();
        for (String s : stt){
            stringBuilder1.append(s + "\n\n");
        }
        terms.setText(stringBuilder1);


        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                km.setCardBackgroundColor(getResources().getColor(R.color.copper_text_color));
                ben.setCardBackgroundColor(getResources().getColor(R.color.white));
                ht.setCardBackgroundColor(getResources().getColor(R.color.white));
                faq.setCardBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        ben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ben.setCardBackgroundColor(getResources().getColor(R.color.copper_text_color));
                km.setCardBackgroundColor(getResources().getColor(R.color.white));
                ht.setCardBackgroundColor(getResources().getColor(R.color.white));
                faq.setCardBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        ht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ht.setCardBackgroundColor(getResources().getColor(R.color.copper_text_color));
                ben.setCardBackgroundColor(getResources().getColor(R.color.white));
                km.setCardBackgroundColor(getResources().getColor(R.color.white));
                faq.setCardBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faq.setCardBackgroundColor(getResources().getColor(R.color.copper_text_color));
                ben.setCardBackgroundColor(getResources().getColor(R.color.white));
                ht.setCardBackgroundColor(getResources().getColor(R.color.white));
                km.setCardBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(gigs_details_screen.this, getImage.class);
                i.putExtra("title", getIntent().getStringExtra("title"));
                i.putExtra("id", getIntent().getStringExtra("id"));
                i.putExtra("earn", getIntent().getStringExtra("earn"));
                i.putExtra("refer", getIntent().getStringExtra("refer"));
                i.putExtra("url", getIntent().getStringExtra("url"));
                i.putExtra("name", getIntent().getStringExtra("name"));
                i.putExtra("image", getIntent().getStringExtra("image"));
                i.putExtra("number", getIntent().getStringExtra("number"));
                startActivity(i);
            }
        });
    }
}
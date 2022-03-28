package com.example.gigs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

import club.cred.synth.views.SynthButton;

public class gigs_details_screen extends AppCompatActivity {

    CardView ben, km, ht, faq;
    TextView title, offer, name;
    ImageView i;
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
        title = findViewById(R.id.gig_title);
        offer = findViewById(R.id.gig_offer);
        name = findViewById(R.id.user_name);

        relativeLayout = findViewById(R.id.share_layout);

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

//                relativeLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//                relativeLayout.layout(0, 0, relativeLayout.getMeasuredWidth(), relativeLayout.getMeasuredHeight());
//
//                relativeLayout.setDrawingCacheEnabled(true);
//                relativeLayout.buildDrawingCache();
//                Bitmap bitmap = relativeLayout.getDrawingCache();
//                RelativeLayout relativeLayout = getImage.relativeLayout;
//                Bitmap bitmap = sharei(view);
//                Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(),
//                        Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                relativeLayout.draw(canvas);
//                Bitmap decodedByte = BitmapFactory.decodeResource(getResources(), R.drawable.twitter);
//                Uri imageToShare = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Share app", null));   // in case of fragment use [context].getContentResolver()
//                String shareMessage = "Hi! I have completed this gig and won 100rs. Please refer the link provided below\n\n https://campaigndesigner.tech/home \n\n";
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("image/*");
//                share.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                share.putExtra(Intent.EXTRA_STREAM, imageToShare);
//                startActivity(Intent.createChooser(share, "Share via"));
//               new getImage();
                Intent i = new Intent(gigs_details_screen.this, getImage.class);
                startActivity(i);
            }
        });
    }

//   private Bitmap sharei(View view) {
//
//        Bitmap b = (new getImage()).sendBitmap();
//        return b;
//
//   }
}
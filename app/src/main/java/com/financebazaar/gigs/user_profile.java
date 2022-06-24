package com.financebazaar.gigs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class user_profile extends AppCompatActivity {

    String name, number, url, codes, image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        RelativeLayout rl = findViewById(R.id.relative);
        TextView code = findViewById(R.id.user_code);

        number = String.valueOf(userHelperClass.get(user_profile.this, "number", "number"));
        url = String.valueOf(userHelperClass.get(user_profile.this, "url", "url"));
        name = String.valueOf(userHelperClass.get(user_profile.this, "name", "name"));
        codes = String.valueOf(userHelperClass.get(user_profile.this, "code", "code"));
        image = String.valueOf(userHelperClass.get(user_profile.this, "image", "image"));
        code.setText("Referral Code : " + codes);

        ShapeableImageView imageView = findViewById(R.id.user_pic);
        imageView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        TextView user = findViewById(R.id.user_name);
        user.setText(name);

        TextView n = findViewById(R.id.user_number);
        n.setText("+91-" + number);

        Picasso.get().load("https://campaigndesigner.tech/finance-bazaar/uploads/" + image.replaceAll(" ", "%20"))
                .placeholder(R.mipmap.blankprofilepicture).into(imageView);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b = screenShot(view);
                sendBitmap(b);
                finish();
            }
        });
    }

    private void sendBitmap(Bitmap bitmap) {

        Uri imageToShare = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Share_app" + Calendar.getInstance().getTime(), null));   // in case of fragment use [context].getContentResolver()
        String shareMessage = name + " is the top earner and referrer at Finance Bazaar!\nDownload the app and earn real money today.\nJoin with his referral code - " + codes + "\n\nlink to download the app\n\nhttps://play.google.com/store/apps/details?id=com.financebazaar.gigs";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_TEXT, shareMessage);
        share.putExtra(Intent.EXTRA_STREAM, imageToShare);
        startActivity(Intent.createChooser(share, "Share via"));
    }

    private Bitmap screenShot(View view) {

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
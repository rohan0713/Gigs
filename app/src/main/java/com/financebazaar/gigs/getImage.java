package com.financebazaar.gigs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getImage extends AppCompatActivity {

    public static TextView title, user_name, offer, money_total, total_gigs;
    public static RelativeLayout rl;
    SpinKitView spinKitView;
    String number, token, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image);
        ImageView image = findViewById(R.id.gigs_image);
        if(getIntent().getStringExtra("image").length() != 0) {
            Picasso.get().load(getIntent().getStringExtra("image")).placeholder(R.mipmap.app_logo).into(image);
        }else{
            Picasso.get().load(R.mipmap.app_logo).placeholder(R.mipmap.app_logo).into(image);
        }
        title = findViewById(R.id.gig_title);
        spinKitView = findViewById(R.id.progress_bar);

        user_name = findViewById(R.id.user_name);
        user_name.setText(getIntent().getStringExtra("name"));

        offer = findViewById(R.id.gig_offer);
        rl = findViewById(R.id.share_layout);

        number = String.valueOf(com.financebazaar.gigs.userHelperClass.get(getImage.this, "number", "number"));
        token = String.valueOf(userHelperClass.get(getImage.this, "token", "token"));
        code = String.valueOf(userHelperClass.get(getImage.this, "code", "code"));
        ShapeableImageView imageView = findViewById(R.id.user_image);
        String ig = String.valueOf(userHelperClass.get(getImage.this, "image", "image"));
        Picasso.get().load("https://campaigndesigner.tech/finance-bazaar/uploads/" + ig.replaceAll(" ", "%20"))
                .placeholder(R.mipmap.blankprofilepicture)
                .into(imageView);

        TextView earn = findViewById(R.id.gigs_earn);
        TextView refer = findViewById(R.id.gigs_refer);
        earn.setText("₹ " + getIntent().getStringExtra("earn"));
        refer.setText("₹ " + getIntent().getStringExtra("refer"));

        money_total = findViewById(R.id.total_money);
        total_gigs = findViewById(R.id.user_gigs);

        title.setText(getIntent().getStringExtra("title"));
        offer.setText("Share and \nearn ₹ " + getIntent().getStringExtra("refer") + " paytm cash");
        getGigsCompleted();
        Toast.makeText(this, "Tap on the screen to share", Toast.LENGTH_LONG).show();
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b = screenShot(view);
                sendBitmap(b);
                finish();
            }
        });

    }

    private void getGigsCompleted() {

        Call<Status> call = Retrofit.getServices().getSuccessItem(number, token);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                spinKitView.setVisibility(View.GONE);
                assert response.body() != null;
                List<SuccessItem> list = response.body().getSuccess();
                total_gigs.setText("No. of Gigs Completed: " + String.valueOf(list.size()));
                int a = 0;
                for(SuccessItem s : list){
                    a = a + Integer.parseInt(s.getAmount());
                }
                money_total.setText("Total money earned till now: ₹ " + String.valueOf(a));

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }


    public void sendBitmap(Bitmap bitmap) {

        String id = getIntent().getStringExtra("id");
        String earn = getIntent().getStringExtra("earn");
        Uri imageToShare = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Share app", null));   // in case of fragment use [context].getContentResolver()
        String shareMessage = "Hi Friends!!\nI have completed this gig and earned ₹" + earn + "\nI use this app to earn daily.\nDo this gig to earn ₹" + earn + " now!\n\nhttps://campaigndesigner.tech/earn?campaign=" + id + "&refer=" + id + getIntent().getStringExtra("number") + "\n\n";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_TEXT, shareMessage);
        share.putExtra(Intent.EXTRA_STREAM, imageToShare);
        startActivity(Intent.createChooser(share, "Share via"));
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
package com.example.gigs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class getImage extends AppCompatActivity {

    public static TextView title, user_name, offer;
    public static RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image);
        title = findViewById(R.id.gig_title);
        user_name = findViewById(R.id.user_name);
        offer = findViewById(R.id.gig_offer);
        rl = findViewById(R.id.share_layout);
        Toast.makeText(this, "Tap on screen to share", Toast.LENGTH_LONG).show();

//        title.setText("savings");
//        offer.setText("900");
//        user_name.setText("rohan");


//       Bitmap bitmap = Bitmap.createBitmap(rl.getWidth(), rl.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(bitmap);
//        rl.draw(c);
//        i.setImageBitmap(b);

//        sendBitmap();
//        takeScreenshot();
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b = screenShot(view);
                sendBitmap(b);
                finish();
            }
        });

    }

    public void sendBitmap(Bitmap bitmap) {
//        rl.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        rl.layout(0, 0, rl.getMeasuredWidth(), rl.getMeasuredHeight());
//
//        rl.setDrawingCacheEnabled(true);
//        rl.buildDrawingCache();
//        Bitmap bitmap = rl.getDrawingCache();
//        i.setImageBitmap(bitmap);

        Uri imageToShare = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Share app", null));   // in case of fragment use [context].getContentResolver()
        String shareMessage = "Hi! I have completed this gig and won 100rs. Please refer the link provided below\n\n https://campaigndesigner.tech/home \n\n";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_TEXT, shareMessage);
        share.putExtra(Intent.EXTRA_STREAM, imageToShare);
        startActivity(Intent.createChooser(share, "Share via"));
    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
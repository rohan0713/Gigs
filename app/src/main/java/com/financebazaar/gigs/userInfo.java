package com.financebazaar.gigs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import club.cred.synth.views.SynthButton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userInfo extends AppCompatActivity {

    String name;
    String number;
    String address;
    String date_of_birth;
    String gender;
    String image = "";
    String referral;

    private static final int RESULT_LOAD_IMAGE = 1001;
    private static final int IMAGE_PICK = 1000;
    private static final int RESULT_OK = -1;
    Uri imageUri;
    ShapeableImageView imageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Spinner spinner = findViewById(R.id.spinner);
        SynthButton submit = findViewById(R.id.user_submit);
        EditText user_name = findViewById(R.id.username);
        EditText user_address = findViewById(R.id.user_address);
        EditText dob = findViewById(R.id.user_dob);
        EditText refer = findViewById(R.id.referral_code);

        FloatingActionButton cam = findViewById(R.id.imageSave);
        imageView = findViewById(R.id.user_pic);
        progressBar = findViewById(R.id.progress_bar);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, RESULT_LOAD_IMAGE);
                    } else {
                        pick();
                    }
                }

            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        userInfo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "-" + month + "-" + day;
                        dob.setText(date);
                    }
                }, year, month, day
                );
                datePickerDialog.show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = null;
                if (spinner != null && spinner.getSelectedItem() != null) {
                    s = (String) spinner.getSelectedItem();
                }
                name = user_name.getText().toString();
                address = user_address.getText().toString();
                date_of_birth = dob.getText().toString();
                number = getIntent().getStringExtra("number");
                referral = refer.getText().toString();

                if(referral.length() == 0){
                    referral = "";
                }

                if(!s.equalsIgnoreCase("Male") && !s.equalsIgnoreCase("female") && !s.equalsIgnoreCase("others")
                && name.length() == 0 && address.length() == 0 && date_of_birth.length() == 0){
                    Toast.makeText(userInfo.this, "Please fill all your details", Toast.LENGTH_LONG).show();
                }else if (!s.equalsIgnoreCase("Male") && !s.equalsIgnoreCase("female") && !s.equalsIgnoreCase("others")) {
                    Toast.makeText(userInfo.this, "Please enter your gender details", Toast.LENGTH_LONG).show();
                } else if (name.length() == 0) {
                    Toast.makeText(userInfo.this, "Please enter your name", Toast.LENGTH_LONG).show();
                } else if (address.length() == 0) {
                    Toast.makeText(userInfo.this, "Please enter your address", Toast.LENGTH_LONG).show();
                } else if (date_of_birth.length() == 0) {
                    Toast.makeText(userInfo.this, "Please enter your date of birth", Toast.LENGTH_LONG).show();
                }
                else {
                    submit.setText("");
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.animate();
                    uploadData();
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.spinner_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                gender = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void uploadData() {

        String code = name.substring(0, 3) + number.substring(4, 7) + address.charAt(1) + gender.charAt(0) + date_of_birth.charAt(2);
        Intent i = new Intent(userInfo.this, kyc_Screen.class);

        userHelperClass.put(userInfo.this, "name", name);
        userHelperClass.put(userInfo.this, "address", address);
        userHelperClass.put(userInfo.this, "dob", date_of_birth);
        userHelperClass.put(userInfo.this, "gender", gender);
        userHelperClass.put(userInfo.this, "number", number);
        userHelperClass.put(userInfo.this, "code", code);
        userHelperClass.put(userInfo.this, "image", image);
        userHelperClass.put(userInfo.this, "referral", referral);
        startActivity(i);
    }

    private void pick() {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, IMAGE_PICK);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pick();
                } else {
                    Toast.makeText(userInfo.this, "Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            getResponse(imageUri);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
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

        Call<ArrayList<Object>> call = Retrofit.getServices().uploadImage(filepart);
        call.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, Response<ArrayList<Object>> response) {

                ArrayList<Object> list = response.body();
                assert list != null;
                if(list.get(1).equals(0)){
                    Toast.makeText(userInfo.this, "Invalid image format", Toast.LENGTH_SHORT).show();
                    Log.d("hell", String.valueOf(list.get(0)));
                    Log.d("hell", String.valueOf(list.get(1)));
                }else{
                   image = String.valueOf(list.get(0));
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Object>> call, Throwable t) {
                Log.d("hell", t.getMessage());
                Toast.makeText(userInfo.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
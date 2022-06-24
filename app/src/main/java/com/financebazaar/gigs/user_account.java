package com.financebazaar.gigs;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import club.cred.synth.views.SynthButton;
import club.cred.synth.views.SynthImageButton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class user_account extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1001;
    private static final int IMAGE_PICK = 1000;
    private static final int RESULT_OK = -1;
    ShapeableImageView imageView;

    EditText name, address, dob, email;
    Spinner spinner;
    SynthButton update;
    SynthImageButton edit;
    String img, gender, token, aadhar_no, pan_no, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        FloatingActionButton image = findViewById(R.id.imageSave);

        SynthImageButton imageV = findViewById(R.id.exit);
        imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        name = findViewById(R.id.username);
        address = findViewById(R.id.user_address);
        spinner = findViewById(R.id.gender);
        dob = findViewById(R.id.user_dob);
        email = findViewById(R.id.email);
        update = findViewById(R.id.user_submit);
        edit = findViewById(R.id.edit);

        name.setText(String.valueOf(userHelperClass.get(user_account.this, "name", "name")));
        address.setText(String.valueOf(userHelperClass.get(user_account.this, "address", "address")));
        dob.setText(String.valueOf(userHelperClass.get(user_account.this, "dob", "dob")));
        email.setText(String.valueOf(userHelperClass.get(user_account.this, "email", "email")));
        token = String.valueOf(userHelperClass.get(user_account.this, "token", "token"));
        aadhar_no = String.valueOf(userHelperClass.get(user_account.this, "adhar", "adhar"));
        pan_no = String.valueOf(userHelperClass.get(user_account.this, "pan", "pan"));
        number = String.valueOf(userHelperClass.get(user_account.this, "number", "number"));
        String g = String.valueOf(userHelperClass.get(user_account.this, "gender", "gender"));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update.setEnabled(true);
                name.setEnabled(true);
                name.getBackground().mutate().setColorFilter(getResources().getColor(R.color.copper_text_color),
                        PorterDuff.Mode.SRC_ATOP);
                address.setEnabled(true);
                address.getBackground().mutate().setColorFilter(getResources().getColor(R.color.copper_text_color),
                        PorterDuff.Mode.SRC_ATOP);
                dob.setEnabled(true);
                dob.getBackground().mutate().setColorFilter(getResources().getColor(R.color.copper_text_color),
                        PorterDuff.Mode.SRC_ATOP);
                spinner.setEnabled(true);
                spinner.setBackgroundResource(R.drawable.spinner_background2);
                email.setEnabled(true);
                email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.copper_text_color),
                        PorterDuff.Mode.SRC_ATOP);
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        user_account.this, new DatePickerDialog.OnDateSetListener() {
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

        img = String.valueOf(userHelperClass.get(user_account.this,
                "image", "image"));

        Log.d("hell", img);
        String url = "https://campaigndesigner.tech/finance-bazaar/uploads/" + img.replace(" ", "%20");
        Log.d("hell", url);
        imageView = findViewById(R.id.user_pic);
        Picasso.get().load(url)
                .placeholder(R.mipmap.blankprofilepicture)
                .into(imageView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.spinner_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setEnabled(false);

        if (g.equalsIgnoreCase("male")) {
            spinner.setSelection(1);
        } else if (g.equalsIgnoreCase("female")) {
            spinner.setSelection(2);
        } else if (g.equalsIgnoreCase("Others")) {
            spinner.setSelection(3);
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                gender = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = name.getText().toString();
                String uaddress = address.getText().toString();
                String udob = dob.getText().toString();
                String s = null;
                if (spinner != null && spinner.getSelectedItem() != null) {
                    s = (String) spinner.getSelectedItem();
                }
                String uemail = email.getText().toString();

                if(!s.equalsIgnoreCase("Male") && !s.equalsIgnoreCase("female") && !s.equalsIgnoreCase("others")
                        && uname.length() == 0 && uaddress.length() == 0 && udob.length() == 0 && uemail.length() == 0){
                    Toast.makeText(user_account.this, "Please fill all your details", Toast.LENGTH_LONG).show();
                }else if (!s.equalsIgnoreCase("Male") && !s.equalsIgnoreCase("female") && !s.equalsIgnoreCase("others")) {
                    Toast.makeText(user_account.this, "Please enter your gender details", Toast.LENGTH_LONG).show();
                } else if (uname.length() == 0) {
                    Toast.makeText(user_account.this, "Please enter your name", Toast.LENGTH_LONG).show();
                } else if (uaddress.length() == 0) {
                    Toast.makeText(user_account.this, "Please enter your address", Toast.LENGTH_LONG).show();
                } else if (udob.length() == 0) {
                    Toast.makeText(user_account.this, "Please enter your date of birth", Toast.LENGTH_LONG).show();
                }
                else if (uemail.length() == 0) {
                    Toast.makeText(user_account.this, "Please enter your email id", Toast.LENGTH_LONG).show();
                }
                else {
                    userHelperClass.put(user_account.this, "name", uname);
                    userHelperClass.put(user_account.this, "dob", udob);
                    userHelperClass.put(user_account.this, "address", uaddress);
                    userHelperClass.put(user_account.this, "gender", gender);
                    userHelperClass.put(user_account.this, "email", uemail);
                    updateData(uname, uaddress, udob, gender, uemail);
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, RESULT_LOAD_IMAGE);
                    } else {
                        getImageupload();
                    }
                }
            }
        });
    }

    private void updateData(String uname, String uaddress, String udob, String gender, String uemail) {

        Call<userResponse> call = Retrofit.getServices().updateUser(token, uname, number, uaddress,
                gender, udob, aadhar_no, pan_no, uemail);
        call.enqueue(new Callback<userResponse>() {
            @Override
            public void onResponse(Call<userResponse> call, Response<userResponse> response) {
                Log.d("hell", response.body().toString());
                Toast.makeText(user_account.this, "Profile updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<userResponse> call, Throwable t) {
                Log.d("hell", t.getMessage());
                Toast.makeText(user_account.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getImageupload() {
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
                    getImageupload();
                } else {
                    Toast.makeText(user_account.this, "Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK) {
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            getResponse(uri);
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

    public void getResponse(Uri uri) {

        File file = new File(getRealPathFromURI(uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filepart = MultipartBody.Part.createFormData("img", file.getName(), requestFile);

        String number = String.valueOf(userHelperClass.get(user_account.this, "number", "number"));
        Call<ArrayList<Object>> call = Retrofit.getServices().updateImage(number, filepart);
        call.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, Response<ArrayList<Object>> response) {
                ArrayList<Object> list = response.body();
                assert list != null;
                if (list.get(1).equals(0)) {
                    Toast.makeText(user_account.this, "Invalid Image", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("hell", String.valueOf(list.get(0)));
                    Log.d("hell", String.valueOf(list.get(1)));
                    String ig = list.get(0).toString();
                    userHelperClass.put(user_account.this, "image", ig);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Object>> call, Throwable t) {
                Log.d("hell", t.getMessage());
                Toast.makeText(user_account.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
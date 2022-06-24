package com.financebazaar.gigs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class doItActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_it);
        WebView browser = findViewById(R.id.webview);
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);
        browser.setWebChromeClient(new WebChromeClient());
        String id = getIntent().getStringExtra("id");
        String number = String.valueOf(userHelperClass.get(doItActivity.this, "number", "number"));
        String url = "https://campaigndesigner.tech/earn?campaign=" + id + "&refer="+id + number;
        browser.loadUrl(url);
    }
}
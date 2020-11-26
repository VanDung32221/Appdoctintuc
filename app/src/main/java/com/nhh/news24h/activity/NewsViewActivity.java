package com.nhh.news24h.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nhh.news24h.R;

public class NewsViewActivity extends AppCompatActivity {
    private WebView wvNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);
        wvNews = findViewById(R.id.wv_news);
        Intent intent = getIntent();

        String link = intent.getStringExtra("link");

        wvNews.loadUrl(link);
        wvNews.setWebViewClient(new WebViewClient());

    }
}

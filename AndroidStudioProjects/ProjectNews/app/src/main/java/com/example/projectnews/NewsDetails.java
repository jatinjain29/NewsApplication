package com.example.projectnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class NewsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        WebView webView=findViewById(R.id.display);
        Intent intent=getIntent();
        int position=intent.getIntExtra("position",-1);
        int size=MainActivity.urls.size();

        if(position!=-1 && position>=0 && position<size){
          String url= String.valueOf(MainActivity.urls.get(position));
          webView.getSettings().setJavaScriptEnabled(true);

            webView.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
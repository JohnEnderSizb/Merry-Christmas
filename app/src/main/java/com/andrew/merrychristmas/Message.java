package com.andrew.merrychristmas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Message extends AppCompatActivity {
    WebView webView;
    MediaPlayer jingles;
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_message);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        webView = (WebView) findViewById(R.id.thewv);

        WebSettings webSetting = webView.getSettings();


        sharedpreferences = getSharedPreferences("merryx", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        //webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);

        webView.addJavascriptInterface(this, "JSReceiver");

        webView.loadUrl("file:///android_asset/index.html");
    }

    @JavascriptInterface
    public void continueToMain() {
        startActivity(new Intent(Message.this, MainActivity.class));
        finish();
    }

    @JavascriptInterface
    public void skippy() {
        editor.putString("skippy", "yes");
        editor.commit();
        startActivity(new Intent(Message.this, MainActivity.class));
        finish();
    }

    private void stopPlayer() {
        if (jingles != null) {
            jingles.release();
            jingles = null;
            //Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jingles = MediaPlayer.create(this, R.raw.ho_ho_ho);
        jingles.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPlayer();
    }
}

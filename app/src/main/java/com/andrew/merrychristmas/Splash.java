package com.andrew.merrychristmas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

public class Splash extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    String skippy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);


        sharedpreferences = getSharedPreferences("merryx", Context.MODE_PRIVATE);

        skippy = sharedpreferences.getString("skippy", "no");



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE))
            { WebView.setWebContentsDebuggingEnabled(true); }
        }


        new Thread(new Runnable() {
            public void run() {

                doSomeWork();

                if(skippy.equals("yes")) {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }
                else if(skippy.equals("no")) {
                    startActivity(new Intent(Splash.this, Message.class));
                    finish();
                }





            }
            //---do some long running work here---
            private void doSomeWork()
            {
                try {
                    //---simulate doing some work---
                    Thread.sleep(1500);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

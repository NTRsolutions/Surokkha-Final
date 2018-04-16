package com.example.user.surokkha.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.surokkha.R;
import com.example.user.surokkha.classes.SharedPrefManager;

public class Splash extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                if (!SharedPrefManager.getmInstance(getApplicationContext()).isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(Splash.this, HomeActivity.class));
                    finish();
                }
            }
        }, secondsDelayed * 1000);
    }
}
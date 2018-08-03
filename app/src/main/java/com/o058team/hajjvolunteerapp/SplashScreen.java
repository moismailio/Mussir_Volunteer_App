package com.o058team.hajjvolunteerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startLandingActivity();
            }
        },3000);

    }

    public void startLandingActivity(){
        Intent intent = new Intent(this,LandingPageActivty.class);
        startActivity(intent);
    }

}

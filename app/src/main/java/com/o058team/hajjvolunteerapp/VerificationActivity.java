package com.o058team.hajjvolunteerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Mekawy on 03/08/2018 AD.
 */

public class VerificationActivity extends AppCompatActivity {

    LinearLayout llContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        llContainer=findViewById(R.id.llContainer);
        llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileScreen();
            }
        });
    }

    public void openProfileScreen(){
        Intent in= new Intent(this,ProfileScreenActivtiy.class);
        startActivity(in);
    }

}

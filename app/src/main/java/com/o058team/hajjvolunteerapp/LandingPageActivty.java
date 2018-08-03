package com.o058team.hajjvolunteerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class LandingPageActivty extends AppCompatActivity {


    @BindView(R.id.btnRegisration)
    Button btnRegisration;

    @BindView(R.id.tvEmailInput)
    EditText tvEmailInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegisration)
    public void onRegistrationClicked(){
        Intent intent = new Intent(this,BasicRegistrationActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.btnLogin)
    public void onMainLoginClicked(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("type",tvEmailInput.getText().toString().contains("v")?"v":"a");
        startActivity(intent);
    }



}

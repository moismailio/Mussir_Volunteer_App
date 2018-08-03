package com.o058team.hajjvolunteerapp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class BasicRegistrationActivity extends AppCompatActivity{

    MaterialDialog basicRegistrationDialog;
    Spinner spinnerServicesType;

    LayoutInflater layoutInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_basic);
        ButterKnife.bind(this);
        layoutInflater = LayoutInflater.from(this);
        spinnerServicesType = findViewById(R.id.spinnerServicesType);
        spinnerServicesType.setAdapter(new SpinnerAdapter() {
            @Override
            public View getDropDownView(int i, View view, ViewGroup viewGroup) {
                return getSysView(i,view,viewGroup);
            }

            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return getSysView(i,view,viewGroup);
            }

            @Override
            public int getItemViewType(int i) {
                return 1;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }


            public View getSysView(int pos,View view, ViewGroup viewGroup){
                if (view==null){
                    view = layoutInflater.inflate(R.layout.viewholder_services_type,viewGroup,false);
                    return  view;
                }
                return view;
            }
        });

    }

    public int getServiceTypeIcon(int pos,View view, ViewGroup viewGroup){
        if (pos==1)
            return R.drawable.doctor;
        else
            return R.drawable.mostaqbil;
    }

    @OnClick(R.id.btnNext)
    public void onNextClicked(){
        basicRegistrationDialog = new MaterialDialog.Builder(this).title("Basic Registration").content("Registering your basic info").cancelable(false).progress(true,100).build();
        basicRegistrationDialog.show();
        showWaitingPeriod();
    }
    public void showWaitingPeriod(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                basicRegistrationDialog.dismiss();
                startDetailedRegistration();
            }
        },3000);
    }


    public void startDetailedRegistration(){
        Intent intent = new Intent(this,AdvancedRegistrationActivity.class);
        startActivity(intent);


    }

}

package com.o058team.hajjvolunteerapp;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.o058team.App.database;

public class MainActivity extends AppCompatActivity {

    MaterialDialog newNotificationDialog;

    Button btnNext;

    @BindView(R.id.ivBg)
    ImageView ivBg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startFirebaseConnection();

        String type = getIntent().getStringExtra("type");
        if (type.equalsIgnoreCase("v")){
            ivBg.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.main_v));
        }
        else if (type.equalsIgnoreCase("a")){
            ivBg.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.main_a));

        }
        validateLocationPermission();
    }


    public void validateLocationPermission(){
        if (    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void startFirebaseConnection(){
        DatabaseReference myRef = database.getReference("help_requests");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ServiceRequest serviceRequest = new Gson().fromJson(dataSnapshot.getValue().toString(),ServiceRequest.class);
                handleCreatedOrUpdatedRequest(dataSnapshot.getKey(),serviceRequest);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ServiceRequest serviceRequest = new Gson().fromJson(dataSnapshot.getValue().toString(),ServiceRequest.class);
                handleCreatedOrUpdatedRequest(dataSnapshot.getKey(),serviceRequest);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void handleCreatedOrUpdatedRequest(final String key , final ServiceRequest serviceRequest){
        DatabaseReference configReference = database.getReference("config");
        configReference.child("active_diameter").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    String diameter = dataSnapshot.getValue().toString();
                    if (serviceRequest.getStatus().equalsIgnoreCase("waiting")){
                        // change status if nearby
                        showNewHelpNotification(key);
                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showNewHelpNotification(final String key){
        newNotificationDialog = new MaterialDialog.Builder(this).customView(R.layout.dialog_new_notification,false).build();
        newNotificationDialog.show();
        newNotificationDialog.getCustomView().findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNotificationDialog.dismiss();
                startDetailedNotificationActivity(key);
            }
        });
        playNotificationSound();
    }

    public void startDetailedNotificationActivity(String key){
        Intent intent = new Intent(this,NotificationDetailedScreen.class);
        intent.putExtra("selected_key",key);
        startActivity(intent);
    }

    public void playNotificationSound(){
        try {
            Uri ring_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ring_uri);
            r.play();
        } catch (Exception e) {
            // Error playing sound
        }
    }

    @OnClick(R.id.ivProfile)
    public void onProfileIconClicked(){
        Intent intent = new Intent(this,ProfileScreenActivtiy.class);
        startActivity(intent);
    }

    @OnClick(R.id.ivTeam)
    public void onTeamClicked(){
        Intent intent = new Intent(this,MyTeamActivity.class);
        startActivity(intent);
    }








}

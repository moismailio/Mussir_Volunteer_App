package com.o058team.hajjvolunteerapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.o058team.App.database;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class NotificationDetailedScreen extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    GoogleMap googleMap;

    String mKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_notification);
        ButterKnife.bind(this);
        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentData));
        mapFragment.getMapAsync(this);
        mKey= getIntent().getStringExtra("selected_key");
    }


    @Override
    public void onMapReady(GoogleMap gm) {
        if (gm != null) {
            googleMap = gm;
            configureBlueDot();
            zoomToMyCurrentLocation();
        }
    }
    public void configureBlueDot() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
    }

    Location currentLocation;

    public void zoomToMyCurrentLocation() {
        if (googleMap != null) {
            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    if (location != null) {
                        googleMap.moveCamera(CameraUpdateFactory.
                                newLatLngZoom(new LatLng(location.getLatitude(),
                                        location.getLongitude()), 10.0f));
                        googleMap.setOnMyLocationChangeListener(null);
                        currentLocation = location;
                        addmarker();
                    }
                }
            });

        }
    }

    public void addmarker(){
        MarkerOptions markerOptions = new MarkerOptions();

        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title("location of user");
        googleMap.addMarker(markerOptions);
    }

    MaterialDialog infoDialog;

    @OnClick(R.id.btnProceed)
    public void showProceedActionMessage(){
        changeStatusOfObjectInstantly(mKey,"processing");
        infoDialog = new MaterialDialog.Builder(this).title("Task Proceed").content("Good Job , May Allah help you").positiveText("Amin").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                infoDialog.dismiss();
            }
        }).build();
        infoDialog.show();
    }

    @OnClick(R.id.btnCompleted)
    public void showTaskCompletedActionMessage(){
        changeStatusOfObjectInstantly(mKey,"done");
        infoDialog = new MaterialDialog.Builder(this).title("Task Completed").content("Well done , God bless You").positiveText("Amin").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                infoDialog.dismiss();
            }
        }).build();
        infoDialog.show();
    }


    public void changeStatusOfObjectInstantly(String key , String status){
        DatabaseReference databaseReference = database.getReference("help_requests");
        databaseReference.child(key).child("status").setValue(status);
    }

}

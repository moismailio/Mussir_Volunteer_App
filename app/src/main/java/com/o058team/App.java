package com.o058team;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class App extends Application {

    public static FirebaseDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
    }
}

package com.app.nailappointment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.utils.NavBarActivities;
import com.app.nailappointment.utils.NavigationHelper;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuthenticationViewmodel fireAuthViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fireAuthViewmodel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);

        NavigationHelper navHelper = new NavigationHelper(this, fireAuthViewmodel, NavBarActivities.MAIN);
        navHelper.setupNavigationListener();
    }
}
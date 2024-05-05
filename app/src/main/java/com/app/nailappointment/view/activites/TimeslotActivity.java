package com.app.nailappointment.view.activites;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.app.nailappointment.R;
import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.utils.NavBarActivities;
import com.app.nailappointment.utils.NavigationHelper;

public class TimeslotActivity extends AppCompatActivity {

    private FirebaseAuthenticationViewmodel firebaseAuthViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeslot);

        firebaseAuthViewModel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);

        NavigationHelper navHelper = new NavigationHelper(this, firebaseAuthViewModel, NavBarActivities.DATE);
        navHelper.setupNavigationListener();
    }
}
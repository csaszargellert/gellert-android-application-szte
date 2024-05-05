package com.app.nailappointment.view.activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.nailappointment.R;
import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.room.viewmodel.AppointmentViewModel;
import com.app.nailappointment.utils.NavBarActivities;
import com.app.nailappointment.utils.NavigationHelper;

public class DateActivity extends AppCompatActivity {

    private FirebaseAuthenticationViewmodel firebaseAuthViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        firebaseAuthViewmodel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);

        NavigationHelper navHelper = new NavigationHelper(this, firebaseAuthViewmodel, NavBarActivities.DATE);
        navHelper.setupNavigationListener();
    }
}
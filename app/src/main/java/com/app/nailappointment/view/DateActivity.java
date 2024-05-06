package com.app.nailappointment.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.nailappointment.R;
import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.utils.NavBarActivities;
import com.app.nailappointment.utils.NavigationHelper;
import com.app.nailappointment.utils.model.Appointment;
import com.app.nailappointment.view.timeslots.TimeslotActivity;

public class DateActivity extends AppCompatActivity {

    private CalendarView calendarView;

    private Appointment selectedDate;

    private FirebaseAuthenticationViewmodel firebaseAuthViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        firebaseAuthViewmodel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);

        NavigationHelper navHelper = new NavigationHelper(this, firebaseAuthViewmodel, NavBarActivities.DATE);
        navHelper.setupNavigationListener();

        calendarView = findViewById(R.id.calendarView);
    }

    @Override
    public void onStart() {
        super.onStart();
        selectedDate = null;
        calendarView.setOnDateChangeListener(this::onDateSelected);
    }

    private void onDateSelected(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        selectedDate = new Appointment(
                year,
                month + 1,
                dayOfMonth
        );

        Intent appointmentIntent = new Intent(getApplicationContext(), TimeslotActivity.class);
        appointmentIntent.putExtra("selectedYear", selectedDate.getYear());
        appointmentIntent.putExtra("selectedMonth", selectedDate.getMonth());
        appointmentIntent.putExtra("selectedDayOfMonth", selectedDate.getDateOfMonth());
        startActivity(appointmentIntent);
    }
}
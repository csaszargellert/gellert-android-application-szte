package com.app.nailappointment.view.appointments;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nailappointment.R;
import com.app.nailappointment.firebase.viewmodel.FirebaseAppointmentViewmodel;
import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.room.viewmodel.AppointmentViewModel;
import com.app.nailappointment.utils.NavBarActivities;
import com.app.nailappointment.utils.NavigationHelper;
import com.app.nailappointment.utils.model.Appointment;

import java.util.List;
import java.util.Objects;

public class MyAppointmentsActivity extends AppCompatActivity {

    private AppointmentViewModel appointmentViewmodel;

    private FirebaseAppointmentViewmodel firebaseAppointmentViewmodel;

    private FirebaseAuthenticationViewmodel firebaseAuthViewmodel;

    private RecyclerView appointmentRecyclerView;

    private TextView noAppointmentsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        this.noAppointmentsTextView = findViewById(R.id.noAppointments);

        firebaseAuthViewmodel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);
        firebaseAppointmentViewmodel = new ViewModelProvider(this)
                .get(FirebaseAppointmentViewmodel.class);
        appointmentViewmodel = new ViewModelProvider(this)
                .get(AppointmentViewModel.class);

        NavigationHelper navHelper = new NavigationHelper(this, firebaseAuthViewmodel, NavBarActivities.NONE);
        navHelper.setupNavigationListener();

        this.appointmentRecyclerView = findViewById(R.id.dateRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        appointmentRecyclerView.setLayoutManager(layoutManager);

        MyAppointmentsAdapter adapter = new MyAppointmentsAdapter(firebaseAppointmentViewmodel, appointmentViewmodel);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down_animation);
        appointmentRecyclerView.setLayoutAnimation(animation);

        appointmentRecyclerView.setAdapter(adapter);

        String userId = firebaseAuthViewmodel.getUserId().getValue();
        appointmentViewmodel.getUserAppointments(userId)
                .observe(this, new Observer<List<Appointment>>() {
                    @Override
                    public void onChanged(List<Appointment> appointments) {
                        if (Objects.isNull(appointments)) {
                            firebaseAppointmentViewmodel.findAppointmentsByUser(userId, appointmentViewmodel);
                        } else if (appointments.isEmpty()) {
                            noAppointmentsTextView.setVisibility(View.VISIBLE);
                        } else {
                            noAppointmentsTextView.setVisibility(View.INVISIBLE);
                        }
                        adapter.setData(appointments);
                    }
                });
    }
}
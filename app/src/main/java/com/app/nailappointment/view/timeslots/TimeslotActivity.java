package com.app.nailappointment.view.timeslots;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nailappointment.R;
import com.app.nailappointment.firebase.viewmodel.FirebaseAppointmentViewmodel;
import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.room.viewmodel.AppointmentViewModel;
import com.app.nailappointment.utils.DateTimeHelper;
import com.app.nailappointment.utils.NavBarActivities;
import com.app.nailappointment.utils.NavigationHelper;
import com.app.nailappointment.utils.model.Appointment;
import com.app.nailappointment.view.timeslots.Timeslot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TimeslotActivity extends AppCompatActivity {

    private Appointment newAppointment;

    private FirebaseAuthenticationViewmodel firebaseAuthViewModel;

    private FirebaseAppointmentViewmodel firebaseAppointmentViewmodel;

    private AppointmentViewModel appointmentViewmodel;

    private RecyclerView timeslotRecyclerView;

    private Button onBookButton;

    private Button onBackButton;

    private TextView selectedDateTextView;

    private final ArrayList<Timeslot> timeslotArrayList = new ArrayList<>(
            Arrays.asList(
                    new Timeslot(8, 9),
                    new Timeslot(9, 10),
                    new Timeslot(10, 11),
                    new Timeslot(11, 12),
                    new Timeslot(12, 13),
                    new Timeslot(13, 14),
                    new Timeslot(14, 15),
                    new Timeslot(15, 16)
            )
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeslot);
        Date today = new Date();

        Intent intent = getIntent();
        int year = intent.getIntExtra("selectedYear", today.getYear());
        int month = intent.getIntExtra("selectedMonth", today.getMonth());
        int dayOfMonth = intent.getIntExtra("selectedDayOfMonth", today.getDate());
        newAppointment = new Appointment(year, month, dayOfMonth);

        onBackButton = findViewById(R.id.goBackButton);
        timeslotRecyclerView = findViewById(R.id.appointments);
        onBookButton = findViewById(R.id.bookNowButton);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);

        firebaseAuthViewModel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);
        appointmentViewmodel = new ViewModelProvider(this)
                .get(AppointmentViewModel.class);
        firebaseAppointmentViewmodel = new ViewModelProvider(this)
                .get(FirebaseAppointmentViewmodel.class);


        NavigationHelper navHelper = new NavigationHelper(this, firebaseAuthViewModel, NavBarActivities.DATE);
        navHelper.setupNavigationListener();

        TimeslotAdapter adapter = new TimeslotAdapter(
                timeslotArrayList,
                newAppointment
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        timeslotRecyclerView.setLayoutManager(layoutManager);
        timeslotRecyclerView.setAdapter(adapter);

        appointmentViewmodel.getAllAppointmentsByDate(newAppointment)
                .observe(this, new Observer<List<Appointment>>() {
                    @Override
                    public void onChanged(List<Appointment> appointments) {
                        if (Objects.isNull(appointments)) {
                            firebaseAppointmentViewmodel.findAppointmentsByDate(newAppointment, appointmentViewmodel);
                        } else {
                            List<Timeslot> excludedTimeslots = excludeAlreadyBookedTimeslots(appointments);
                            adapter.setTimeslots(excludedTimeslots);
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        onBackButton.setOnClickListener(buttonView -> finish());
        onBookButton.setOnClickListener(this::onBook);

        selectedDateTextView.setText(DateTimeHelper.formatDate(newAppointment.getYear(),
                newAppointment.getMonth(),
                newAppointment.getDateOfMonth())
        );
    }

    private void onBook(View view) {
        if (newAppointment.getStartHour() < 8) {
            Toast.makeText(this, "Select a timeslot", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (!firebaseAuthViewModel.getIsUserLoggedIn().getValue()) {
            Toast.makeText(this, "Please log in", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        String userId = firebaseAuthViewModel.getUserId().getValue();
        newAppointment.setUserId(userId);

        firebaseAppointmentViewmodel.createAppointment(newAppointment, appointmentViewmodel);
    }

    private List<Timeslot> excludeAlreadyBookedTimeslots(List<Appointment> appointments) {
        Set<Integer> indices = appointments.stream()
                .map(Appointment::getIndex)
                .collect(Collectors.toSet());

        List<Timeslot> filteredArrayList = new ArrayList<>();

        for (int i = 0; i < timeslotArrayList.size(); i++) {
            if (!indices.contains(i)) {
                filteredArrayList.add(timeslotArrayList.get(i));
            }
        }

        return filteredArrayList;
    }
}
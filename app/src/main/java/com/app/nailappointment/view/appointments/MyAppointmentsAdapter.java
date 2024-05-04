package com.app.nailappointment.view.appointments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nailappointment.R;
import com.app.nailappointment.db.DateEntity;
import com.app.nailappointment.utils.DateTimeHelper;
import com.app.nailappointment.viewmodel.AppointmentViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class MyAppointmentsAdapter extends FirestoreRecyclerAdapter<DateEntity, MyAppointmentsAdapter.AppointmentHolder> {

    private AppointmentViewModel appointmentViewModel;

    public MyAppointmentsAdapter(@NonNull FirestoreRecyclerOptions<DateEntity> options, AppointmentViewModel viewModel) {
        super(options);
        this.appointmentViewModel = viewModel;
    }

    @Override
    protected void onBindViewHolder(@NonNull AppointmentHolder appointmentHolder,
                                    int i,
                                    @NonNull DateEntity currentDate) {

        String date = DateTimeHelper.formatDate(currentDate.getYear(),
                currentDate.getMonth(),
                currentDate.getDayOfMonth()
        );

        currentDate.getTimeslotList()
                .forEach(timeslot -> {
                    appointmentHolder.dateTextView.setText(date);
                    appointmentHolder.timeTextView.setText(timeslot.toString());
                    appointmentHolder.deleteButton
                            .setOnClickListener(v -> {
                                appointmentViewModel.deleteAppointment(currentDate.getId());
                            });
                });
    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dateView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.appointment, parent, false);

        return new AppointmentHolder(dateView);
    }

    public static class AppointmentHolder extends RecyclerView.ViewHolder {

        public TextView dateTextView;

        public TextView timeTextView;

        public Button deleteButton;

        public AppointmentHolder(@NonNull View view) {
            super(view);
            this.dateTextView = view.findViewById(R.id.dateTextView);
            this.timeTextView = view.findViewById(R.id.timeTextView);
            this.deleteButton = view.findViewById(R.id.deleteButton);
        }
    }
}

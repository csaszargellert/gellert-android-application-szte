package com.app.nailappointment.view.appointments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nailappointment.R;
import com.app.nailappointment.firebase.viewmodel.FirebaseAppointmentViewmodel;
import com.app.nailappointment.room.viewmodel.AppointmentViewModel;
import com.app.nailappointment.utils.DateTimeHelper;
import com.app.nailappointment.utils.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class MyAppointmentsAdapter extends RecyclerView.Adapter<MyAppointmentsAdapter.ViewHolder> {

    private List<Appointment> mAppointments;

    private FirebaseAppointmentViewmodel firebaseAppointmentViewmodel;

    private AppointmentViewModel appointmentViewModel;

    public MyAppointmentsAdapter(FirebaseAppointmentViewmodel firebaseAppointmentViewmodel, AppointmentViewModel appointmentViewModel) {
        this.firebaseAppointmentViewmodel = firebaseAppointmentViewmodel;
        this.appointmentViewModel = appointmentViewModel;
        mAppointments = new ArrayList<>();
    }

    public void setData(List<Appointment> appointments) {
        this.mAppointments = appointments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.appointment, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = mAppointments.get(position);

        String dateText = DateTimeHelper.formatDate(appointment.getYear(),
                appointment.getMonth(),
                appointment.getDateOfMonth()
        );
        holder.dateTextView.setText(dateText);

        String timeText = DateTimeHelper.formatTime(appointment.getStartHour(),
                appointment.getEndHour());
        holder.timeTextView.setText(timeText);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAppointmentViewmodel.deleteAppointment(appointment, appointmentViewModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Button deleteButton;

        private TextView dateTextView;

        private TextView timeTextView;

        public ViewHolder(@NonNull View view) {
            super(view);

            this.deleteButton = view.findViewById(R.id.deleteButton);
            this.dateTextView = view.findViewById(R.id.dateTextView);
            this.timeTextView = view.findViewById(R.id.timeTextView);
        }
    }
}

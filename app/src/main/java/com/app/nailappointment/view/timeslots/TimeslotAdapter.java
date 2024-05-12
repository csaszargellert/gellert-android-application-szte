package com.app.nailappointment.view.timeslots;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nailappointment.R;
import com.app.nailappointment.utils.model.Appointment;

import java.util.List;

public class TimeslotAdapter extends RecyclerView.Adapter<TimeslotAdapter.ViewHolder> {

    private List<Timeslot> timeslots;

    private Appointment appointment;

    private int position;

    public TimeslotAdapter(List<Timeslot> timeslots, Appointment appointment) {
        this.appointment = appointment;
        this.timeslots = timeslots;
    }

    public void setTimeslots(List<Timeslot> listOfTimeslots) {
        this.timeslots = listOfTimeslots;
        this.position = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimeslotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.timeslot, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int selectedPosition) {
        Timeslot currentTimeslot = timeslots.get(selectedPosition);

        holder.timeTextView.setText(currentTimeslot.toString());

        holder.radioButton.setChecked(position == selectedPosition);

        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appointment.setIndex(selectedPosition);
                    appointment.setStartHour(currentTimeslot.getStartHour());
                    appointment.setEndHour(currentTimeslot.getEndHour());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeslots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView timeTextView;

        private RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.timeTextView = itemView.findViewById(R.id.time);
            this.radioButton = itemView.findViewById(R.id.radioButton);
        }
    }
}
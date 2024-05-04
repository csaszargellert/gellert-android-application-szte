package com.app.nailappointment.view.timeslots;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.nailappointment.R;
import com.app.nailappointment.db.DateEntity;
import com.app.nailappointment.db.Timeslot;

import java.util.List;

public class TimeslotAdapter extends ArrayAdapter<Timeslot> {

    private List<Timeslot> timeslots;

    private DateEntity selectedDate;

    public TimeslotAdapter(@NonNull Context context, @NonNull List<Timeslot> objects, DateEntity selectedDate) {
        super(context, 0, objects);
        this.timeslots = objects;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View timeslotView = convertView;

        if(timeslotView == null)
            timeslotView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeslot, parent,false);

        Timeslot currentTimeslot = timeslots.get(position);

        TextView timeTextView = timeslotView.findViewById(R.id.time);
        timeTextView.setText(currentTimeslot.toString());

        CheckBox checkBox = timeslotView.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedDate.addTimeslot(currentTimeslot);
                } else {
                    selectedDate.removeTimeslot(currentTimeslot);
                }
            }
        });

        return timeslotView;
    }
}

package com.app.nailappointment.view.timeslots;

import androidx.annotation.NonNull;

import com.app.nailappointment.utils.DateTimeHelper;

public class Timeslot {

    private int startHour;

    private int endHour;

    public Timeslot(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    @NonNull
    @Override
    public String toString() {
        return DateTimeHelper.formatTime(getStartHour(), getEndHour());
    }
}

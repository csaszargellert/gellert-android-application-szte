package com.app.nailappointment.db;

import androidx.annotation.NonNull;

import com.app.nailappointment.utils.DateTimeHelper;

public class Timeslot {

    private int startHour;

    private int endHour;

    private int index;

    public Timeslot() {}

    public Timeslot(int startHour, int endHour, int index) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.index = index;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getIndex() {
        return index;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @NonNull
    @Override
    public String toString() {
        String start = DateTimeHelper.formatTime(getStartHour());
        String end = DateTimeHelper.formatTime(getEndHour());

        return start + " - " + end;
    }
}

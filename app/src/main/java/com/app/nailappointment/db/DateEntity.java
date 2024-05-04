package com.app.nailappointment.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DateEntity implements Serializable {

    private String id;

    private int year;

    private int month;

    private int dayOfMonth;

    private String userId;

    private List<Timeslot> timeslotList = new ArrayList<>();

    public DateEntity() {}

    public DateEntity(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public void addTimeslot(Timeslot timeslot) {
        if (Objects.isNull(timeslotList)) {
            return;
        }

        timeslotList.add(timeslot);
    }

    public void removeTimeslot(Timeslot timeslot) {
        if (Objects.isNull(timeslotList)) {
            return;
        }

        timeslotList.remove(timeslot);
    }

    public List<Timeslot> getTimeslotList() {
        return timeslotList;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }
    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }


    public String getId() {
        return id;
    }

    public void setId(String dateId) {
        this.id = dateId;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}

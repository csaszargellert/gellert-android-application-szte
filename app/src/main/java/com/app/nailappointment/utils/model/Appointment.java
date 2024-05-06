package com.app.nailappointment.utils.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "appointments")
public class Appointment {

    @NonNull
    @PrimaryKey
    private String id;

    private String userId;

    private int year;

    private int month;

    private int dateOfMonth;

    private int startHour;

    private int endHour;

    private int index;

    public Appointment() {
    }

    public Appointment(int year, int month, int dateOfMonth) {
        this.year = year;
        this.month = month;
        this.dateOfMonth = dateOfMonth;
    }

    public Appointment(@NonNull String id, String userId, int year, int month, int dateOfMonth, int startHour, int endHour, int index) {
        this.id = id;
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.dateOfMonth = dateOfMonth;
        this.startHour = startHour;
        this.endHour = endHour;
        this.index = index;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String appointmentId) {
        this.id = appointmentId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getDateOfMonth() {
        return dateOfMonth;
    }

    public void setDateOfMonth(int dateOfMonth) {
        this.dateOfMonth = dateOfMonth;
    }

}

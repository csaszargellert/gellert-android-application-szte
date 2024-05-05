package com.app.nailappointment.utils.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.app.nailappointment.db.Timeslot;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "appointments")
public class Appointment {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String appointmentId;

    private String userId;

    private int year;

    private int month;

    private int dateOfMonth;

    private List<Timeslot> appointmentSlots = new ArrayList<>();

    public Appointment() {}

    public Appointment(int year, int month, int dateOfMonth) {
        this.year = year;
        this.month = month;
        this.dateOfMonth = dateOfMonth;
    }

    @NonNull
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(@NonNull String appointmentId) {
        this.appointmentId = appointmentId;
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

    public List<Timeslot> getAppointmentSlots() {
        return appointmentSlots;
    }

    public void setAppointmentSlots(List<Timeslot> appointmentSlots) {
        this.appointmentSlots = appointmentSlots;
    }

    public void addAppointmentSlot(Timeslot timeslot) {
        this.appointmentSlots.add(timeslot);
    }

    public static class Timeslot {
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
    }

}

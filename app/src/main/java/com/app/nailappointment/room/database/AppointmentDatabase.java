package com.app.nailappointment.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.nailappointment.room.dao.AppointmentDao;
import com.app.nailappointment.utils.model.Appointment;

@Database(entities = {Appointment.class}, exportSchema = false, version = 1)
public abstract class AppointmentDatabase extends RoomDatabase {

    private static AppointmentDatabase mInstance;

    public abstract AppointmentDao appointmentDao();

    public static synchronized  AppointmentDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room
                    .databaseBuilder(context.getApplicationContext(), AppointmentDatabase.class, "appointment_database")
                    .build();
        }

        return mInstance;
    }
}

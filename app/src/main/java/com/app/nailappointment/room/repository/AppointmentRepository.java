package com.app.nailappointment.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.app.nailappointment.room.dao.AppointmentDao;
import com.app.nailappointment.room.database.AppointmentDatabase;
import com.app.nailappointment.utils.model.Appointment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppointmentRepository {

    private AppointmentDao appointmentDao;

    private Executor deleteExecutor = Executors.newSingleThreadExecutor();

    private Executor insertExecutor = Executors.newSingleThreadExecutor();


    public AppointmentRepository(Application application) {
        AppointmentDatabase appointmentDatabase = AppointmentDatabase.getInstance(application);
        this.appointmentDao = appointmentDatabase.appointmentDao();
    }

    public void deleteAppointment(Appointment appointment) {
        deleteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                appointmentDao.delete(appointment);
            }
        });
    }

    public void createAppointment(Appointment appointment) {
        insertExecutor.execute(new Runnable() {
            @Override
            public void run() {
                appointmentDao.create(appointment);
            }
        });
    }

    public LiveData<List<Appointment>> getAppointmentsByUserId(String userId) {
        return appointmentDao.getAllByUserId(userId);
    }

    public LiveData<List<Appointment>> getAllAppointments(int year, int month, int day) {
        return appointmentDao.getAllByDate(year, month, day);
    }
}


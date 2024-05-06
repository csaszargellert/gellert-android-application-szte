package com.app.nailappointment.room.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.nailappointment.room.repository.AppointmentRepository;
import com.app.nailappointment.utils.model.Appointment;

import java.util.List;

public class AppointmentViewModel extends AndroidViewModel {

    private AppointmentRepository appointmentRepository;

    public AppointmentViewModel(@NonNull Application application) {
        super(application);
        this.appointmentRepository = new AppointmentRepository(application);
    }

    public void createAppointment(Appointment appointment) {
        appointmentRepository.createAppointment(appointment);
    }

    public void deleteAppointment(Appointment appointment) {
        appointmentRepository.deleteAppointment(appointment);
    }

    public LiveData<List<Appointment>> getUserAppointments(String userId) {
        return appointmentRepository.getAppointmentsByUserId(userId);
    }

    public LiveData<List<Appointment>> getAllAppointmentsByDate(Appointment appointment) {
        return appointmentRepository.getAllAppointments(appointment.getYear(),
                appointment.getMonth(),
                appointment.getDateOfMonth()
        );
    }
}

package com.app.nailappointment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.nailappointment.db.DateEntity;
import com.app.nailappointment.repository.AppointmentRepository;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class AppointmentViewModel extends AndroidViewModel {

    private AppointmentRepository appointmentRepository;

//    private MutableLiveData<List<DateEntity>> userAppointments;

    public AppointmentViewModel(@NonNull Application application) {
        super(application);

        this.appointmentRepository = new AppointmentRepository(application);
//        this.userAppointments = appointmentRepository.getUserDates();
    }

//    public MutableLiveData<List<DateEntity>> getUserAppointments() {
//        return userAppointments;
//    }

    public void saveAppointment(DateEntity selectedDate) {
        appointmentRepository.save(selectedDate);
    }

    public FirestoreRecyclerOptions<DateEntity> findUserAppointments(String userId) {
        return appointmentRepository.findDatesByUserId(userId);
    }

    public void deleteAppointment(String appointmentId) {
        appointmentRepository.findByIdAndDelete(appointmentId);
    }

    public void getTimeslotsByDate(DateEntity selectedDate) {
        appointmentRepository.findByDate(selectedDate);
    }
}

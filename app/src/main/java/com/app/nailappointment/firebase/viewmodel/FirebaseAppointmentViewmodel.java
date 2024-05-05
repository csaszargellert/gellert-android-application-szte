package com.app.nailappointment.firebase.viewmodel;

import androidx.lifecycle.ViewModel;

import com.app.nailappointment.firebase.repository.FirebaseAppointmentRepo;
import com.app.nailappointment.room.viewmodel.AppointmentViewModel;
import com.app.nailappointment.utils.model.Appointment;

public class FirebaseAppointmentViewmodel extends ViewModel {

    private FirebaseAppointmentRepo firebaseAppRepo;

   public FirebaseAppointmentViewmodel() {
       this.firebaseAppRepo = new FirebaseAppointmentRepo();
   }

   public void createAppointment(Appointment appointment, AppointmentViewModel appViewmodel) {
       firebaseAppRepo.save(appointment, appViewmodel);
   }

   public void deleteAppointment(Appointment appointment, AppointmentViewModel appViewmodel) {
       firebaseAppRepo.findByIdAndDelete(appointment, appViewmodel);
   }

   public void findAppointmentsByDate(Appointment appointment, AppointmentViewModel appViewmodel) {
       firebaseAppRepo.findByDate(appointment, appViewmodel);
   }

   public void findAppointmentsByUser(String userId, AppointmentViewModel appViewmodel) {
       firebaseAppRepo.findDatesByUserId(userId, appViewmodel);
   }
}

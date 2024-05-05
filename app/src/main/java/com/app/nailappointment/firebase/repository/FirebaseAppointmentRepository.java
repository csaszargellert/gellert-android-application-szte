package com.app.nailappointment.firebase.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.nailappointment.room.viewmodel.AppointmentViewModel;
import com.app.nailappointment.utils.CollectionPaths;
import com.app.nailappointment.utils.model.Appointment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseAppointmentRepository {

    private final CollectionReference mDates;

    public FirebaseAppointmentRepository() {
        this.mDates = FirebaseFirestore.getInstance()
                .collection(CollectionPaths.DATES.getName());
    }

    //    public FirestoreRecyclerOptions<DateEntity> findDatesByUserId(String userId) {
//        Query query = mDates.whereEqualTo("userId", userId);
//
//        return new FirestoreRecyclerOptions
//                .Builder<DateEntity>()
//                .setQuery(query, DateEntity.class)
//                .build();
//    }
    public void findDatesByUserId(String userId, AppointmentViewModel appointmentViewmodel) {
        mDates.whereEqualTo("userId", userId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("FirebaseAppointmentRepository", error.getMessage());
                            return;
                        }

                        for (DocumentSnapshot doc : value.getDocuments()) {
                            Appointment appointment = doc.toObject(Appointment.class);
                            appointmentViewmodel.createAppointment(appointment);
                        }
                    }
                });
    }

    public void findByDate(Appointment appointment, AppointmentViewModel appointmentViewmodel) {
        mDates.whereEqualTo("year", appointment.getYear())
                .whereEqualTo("month", appointment.getMonth())
                .whereEqualTo("dayOfMonth", appointment.getDateOfMonth())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("FirebaseAppointmentRepository", error.getMessage());
                            return;
                        }

                        for (DocumentSnapshot doc : value.getDocuments()) {
                            Appointment appointment = doc.toObject(Appointment.class);
                            appointmentViewmodel.createAppointment(appointment);
                        }
                    }
                });
    }

    public void findByIdAndDelete(Appointment appointment, AppointmentViewModel appointmentViewmodel) {
        mDates.document(appointment.getAppointmentId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (task.isComplete()) {
                                appointmentViewmodel.deleteAppointment(appointment);
                            }
                        } else {
                            Log.e("FirebaseAppointmentRepository", task.getException().getMessage());
                        }
                    };
                });
    }

    public void save(Appointment appointment, AppointmentViewModel appointmentViewmodel) {
        DocumentReference newDate = mDates.document();
        String dateId = newDate.getId();
        appointment.setAppointmentId(dateId);

        newDate.set(appointment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        appointmentViewmodel.createAppointment(appointment);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseAppointmentRepository", e.getMessage());
                    }
                });
    }
}

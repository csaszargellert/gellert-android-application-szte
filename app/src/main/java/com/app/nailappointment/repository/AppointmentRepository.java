package com.app.nailappointment.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.app.nailappointment.db.DateEntity;
import com.app.nailappointment.utils.CollectionPaths;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentRepository {

    private Application application;

    private final CollectionReference mDates;

    public AppointmentRepository(Application application) {
        this.application = application;
        this.mDates = FirebaseFirestore.getInstance()
                .collection(CollectionPaths.DATES.getName());
    }

    public FirestoreRecyclerOptions<DateEntity> findDatesByUserId(String userId) {
        Query query = mDates.whereEqualTo("userId", userId);

        return new FirestoreRecyclerOptions
                .Builder<DateEntity>()
                .setQuery(query, DateEntity.class)
                .build();
    }

    public void findByDate(DateEntity selectedDate) {
        mDates.whereEqualTo("year", selectedDate.getYear())
                .whereEqualTo("month", selectedDate.getMonth())
                .whereEqualTo("dayOfMonth", selectedDate.getDayOfMonth())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(application, error.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                        }
                    }
                });
    }

    public void findByIdAndDelete(String dateId) {
        mDates.document(dateId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(application, "Date had been deleted", Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception error) {
                        Toast.makeText(application, error.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    public void save(DateEntity selectedDate) {
        DocumentReference newDate = mDates.document();
        String dateId = newDate.getId();
        selectedDate.setId(dateId);

                newDate
                .set(selectedDate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(application, "Appointment has been saved", Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(application, e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}

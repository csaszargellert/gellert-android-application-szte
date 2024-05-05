package com.app.nailappointment.firebase.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.app.nailappointment.firebase.viewmodel.FirebaseUserViewmodel;
import com.app.nailappointment.room.viewmodel.UserRoomViewModel;
import com.app.nailappointment.utils.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthenticationRepo {

//    private final Application application;

    private final FirebaseAuth mAuth;

    private MutableLiveData<Boolean> isUserLoggedInMutableLiveData;

    private MutableLiveData<String> currentUserId;

    public MutableLiveData<String> getCurrentUserId() {
        return currentUserId;
    }

    public MutableLiveData<Boolean> getIsUserLoggedInMutableLiveData() {
        return isUserLoggedInMutableLiveData;
    }

    public FirebaseAuthenticationRepo() {
        this.mAuth = FirebaseAuth.getInstance();
        isUserLoggedInMutableLiveData = new MutableLiveData<>();
        currentUserId = new MutableLiveData<>();

        if (mAuth.getCurrentUser() != null) {
            isUserLoggedInMutableLiveData.setValue(true);
            currentUserId.setValue(mAuth.getCurrentUser().getUid());
        } else {
            isUserLoggedInMutableLiveData.setValue(false);
            currentUserId.setValue(null);
        }
    }

    public void register(User user, FirebaseUserViewmodel firebaseUserViewmodel, UserRoomViewModel userRoomViewModel) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(authResult -> {
                    String currentlySavedUserId = authResult.getUser().getUid();
                    user.setId(currentlySavedUserId);
                    firebaseUserViewmodel.createUser(user, userRoomViewModel);
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("AUTH", e.getMessage());
                    }
                });
    }

    public void login(User user, FirebaseUserViewmodel firebaseUserViewmodel, UserRoomViewModel userRoomViewModel) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(authResult -> {
                    String currentlySignedInUserid = authResult.getUser().getUid();

                    firebaseUserViewmodel.findUserById(currentlySignedInUserid, userRoomViewModel);
                    isUserLoggedInMutableLiveData.setValue(true);
                    currentUserId.setValue(currentlySignedInUserid);
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("AUTH", e.getMessage());
                    }
                });
    }

    public void logout() {
        mAuth.signOut();
        isUserLoggedInMutableLiveData.setValue(false);
        currentUserId.setValue(null);
    }

    public void updateEmail(User user, FirebaseUserViewmodel firebaseUserViewmodel, UserRoomViewModel userRoomViewModel) {
        mAuth.getCurrentUser()
                .updateEmail(user.getEmail())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        firebaseUserViewmodel.updateUser(user, userRoomViewModel);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("AUTH", e.getMessage());
                    }
                });
    }
}

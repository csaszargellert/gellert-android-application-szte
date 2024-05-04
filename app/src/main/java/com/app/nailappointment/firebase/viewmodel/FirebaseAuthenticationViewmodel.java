package com.app.nailappointment.firebase.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.nailappointment.firebase.repository.FirebaseAuthenticationRepo;
import com.app.nailappointment.room.viewmodel.UserRoomViewModel;
import com.app.nailappointment.utils.model.User;

public class FirebaseAuthenticationViewmodel extends ViewModel {

    private FirebaseAuthenticationRepo firebaseAuthRepo;

    private MutableLiveData<Boolean> isUserLoggedIn;

    private MutableLiveData<String> userId;

    public MutableLiveData<String> getUserId() {
        return userId;
    }

    public MutableLiveData<Boolean> getIsUserLoggedIn() {
        return isUserLoggedIn;
    }

    public FirebaseAuthenticationViewmodel() {
        this.firebaseAuthRepo = new FirebaseAuthenticationRepo();
        this.isUserLoggedIn = firebaseAuthRepo.getIsUserLoggedInMutableLiveData();
        this.userId = firebaseAuthRepo.getCurrentUserId();
    }

    public void registerUser(User user, FirebaseUserViewmodel firebaseUserViewmodel, UserRoomViewModel userRoomViewModel) {
        firebaseAuthRepo.register(user, firebaseUserViewmodel, userRoomViewModel);
    }

    public void loginUser(User user, FirebaseUserViewmodel firebaseUserViewmodel, UserRoomViewModel userRoomViewModel) {
        firebaseAuthRepo.login(user, firebaseUserViewmodel, userRoomViewModel);
    }

    public void logoutUser() {
        firebaseAuthRepo.logout();
    }

    public void updateUser(User user, FirebaseUserViewmodel firebaseUserViewmodel, UserRoomViewModel userRoomViewModel) {
        firebaseAuthRepo.updateEmail(user, firebaseUserViewmodel, userRoomViewModel);
    }
}

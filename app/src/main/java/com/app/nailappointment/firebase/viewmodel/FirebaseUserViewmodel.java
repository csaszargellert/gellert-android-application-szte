package com.app.nailappointment.firebase.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.app.nailappointment.firebase.repository.FirebaseUserRepo;
import com.app.nailappointment.room.viewmodel.UserRoomViewModel;
import com.app.nailappointment.utils.model.User;

public class FirebaseUserViewmodel extends ViewModel {

    private FirebaseUserRepo firebaseUserRepo;

    public FirebaseUserViewmodel() {
        this.firebaseUserRepo = new FirebaseUserRepo();
    }

    public void createUser(User user, UserRoomViewModel userRoomViewModel) {
        firebaseUserRepo.addUser(user, userRoomViewModel);
    }

    public void findUserById(String id, UserRoomViewModel userRoomViewModel) {
        firebaseUserRepo.findById(id, userRoomViewModel);
    }

    public void updateUser(User user, UserRoomViewModel userRoomViewModel) {
        firebaseUserRepo.updateUser(user, userRoomViewModel);
    }
}

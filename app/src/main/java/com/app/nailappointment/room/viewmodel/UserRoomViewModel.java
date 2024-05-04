package com.app.nailappointment.room.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.nailappointment.room.repository.UserRoomRepo;
import com.app.nailappointment.utils.model.User;

public class UserRoomViewModel extends AndroidViewModel {

    private UserRoomRepo userRepo;

    public UserRoomViewModel(@NonNull Application application) {
        super(application);
        this.userRepo = new UserRoomRepo(application);
    }

    public void createUser(User user) {
        userRepo.createUser(user);
    }

    public void updateUser(User user) {
        userRepo.updateUser(user);
    }

    public LiveData<User> getCurrentUser(String id) {
        return userRepo.getCurrentUser(id);
    }
}

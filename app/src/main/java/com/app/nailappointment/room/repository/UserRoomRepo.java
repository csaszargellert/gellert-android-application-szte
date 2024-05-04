package com.app.nailappointment.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.app.nailappointment.room.dao.UserDao;
import com.app.nailappointment.room.database.UserDatabase;
import com.app.nailappointment.utils.model.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserRoomRepo {

    private UserDao userDao;

    private Executor executor = Executors.newSingleThreadExecutor();

    public UserRoomRepo(Application application) {
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        this.userDao = userDatabase.userDao();
    }

    public void createUser(User user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insertUser(user);
            }
        });
    }

    public void updateUser(User user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.updateUser(user);
            }
        });
    }

    public LiveData<User> getCurrentUser(String id) {
        return userDao.getCurrentUser(id);
    }
}

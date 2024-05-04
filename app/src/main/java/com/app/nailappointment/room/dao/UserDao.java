package com.app.nailappointment.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import com.app.nailappointment.utils.model.User;

@Dao
public interface UserDao {

    @Upsert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<User> getCurrentUser(String id);

}

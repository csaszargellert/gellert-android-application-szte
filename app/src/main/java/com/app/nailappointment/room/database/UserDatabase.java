package com.app.nailappointment.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.nailappointment.room.dao.UserDao;
import com.app.nailappointment.utils.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase mInstance;

    public abstract UserDao userDao();

    public  static synchronized UserDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room
                    .databaseBuilder(context.getApplicationContext(), UserDatabase.class, "user_database")
                    .build();
        }

        return mInstance;
    }
}

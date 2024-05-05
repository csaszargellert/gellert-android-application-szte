package com.app.nailappointment.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.app.nailappointment.utils.model.Appointment;

import java.util.List;

@Dao
public interface AppointmentDao {

    @Query("SELECT * FROM appointments WHERE userId =:userId")
    LiveData<List<Appointment>> getAllByUserId(String userId);

    @Query("SELECT * FROM appointments WHERE year =:year AND month =:month AND dateOfMonth =:dateOfMonth")
    LiveData<List<Appointment>> getAllByDate(String year, String month, String dateOfMonth);

    @Delete
    void delete(Appointment appointment);

    @Insert
    void create(Appointment appointment);

}

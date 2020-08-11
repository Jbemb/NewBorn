package com.example.newborn.sleep.dal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newborn.sleep.bo.Sleep;

import java.util.Date;
import java.util.List;

@Dao
public interface SleepDao {

    @Insert
    void insertSleep (Sleep sleep);

    @Query("SELECT * FROM Sleep WHERE baby = :baby")
    LiveData<List<Sleep>> getSleepByBaby(String baby);

    @Query("SELECT * FROM Sleep WHERE (baby = :baby) AND (startTime BETWEEN :dayStart AND :dayEnd)")
    List<Sleep> getSleepByBabyByDate(String baby, Date dayStart, Date dayEnd);

    @Query("SELECT * FROM Sleep WHERE baby = :baby ORDER BY startTime DESC LIMIT 1 ")
    Sleep getLastSleepByBaby(String baby);

    @Update
    void updateSleep (Sleep sleep);

    @Delete
    void deleteSleep (Sleep sleep);
}

package com.example.newborn.dal.change;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newborn.model.change.Change;

import java.util.Date;
import java.util.List;

@Dao
public interface ChangeDao {

    @Insert
    void insertChange (Change change);

    @Query("SELECT * FROM Change WHERE baby = :baby")
    LiveData<List<Change>> getChangeByBaby(String baby);

    @Query("SELECT * FROM Change WHERE (baby = :baby) AND (changeTime BETWEEN :dayStart AND :dayEnd)")
    List<Change> getChangeByBabyByDate(String baby, Date dayStart, Date dayEnd);

    @Query("SELECT * FROM Change WHERE baby = :baby ORDER BY changeTime DESC LIMIT 1 ")
    Change getLastChangeByBaby(String baby);

    @Update
    void updateChange (Change change);

    @Delete
    void deleteChange (Change change);
}

package com.example.newborn.meal.dal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newborn.meal.bo.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface MealDao {

    @Insert
    void insertMeal (Meal meal);

    @Query("SELECT * FROM Meal WHERE baby = :baby")
    LiveData<List<Meal>> getMealByBaby(String baby);

    @Query("SELECT * FROM Meal WHERE baby = :baby AND time = :date")
    List<Meal> getMealByBabyByDate(String baby, LocalDateTime date);

    @Query("SELECT * FROM Meal WHERE baby = :baby ORDER BY id DESC LIMIT 1 ")
    Meal getLastMealByBaby(String baby);

    @Update
    void updateMeal (Meal meal);

    @Delete
    void deleteMeal (Meal meal);
}

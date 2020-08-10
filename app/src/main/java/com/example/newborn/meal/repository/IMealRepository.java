package com.example.newborn.meal.repository;

import androidx.lifecycle.LiveData;

import com.example.newborn.meal.bo.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface IMealRepository {


    LiveData<List<Meal>> getObserverMealByBaby();
    LiveData<List<Meal>> getObserverMealByBabyByDate();
    LiveData<Meal> getObserverLastMealByBaby();

    void getMealByBabyByDate (String baby, LocalDateTime date);

    void getLastMealByBaby (String baby);

    void insertMeal (Meal meal);

    void updateMeal (Meal meal);

    void deleteMeal (Meal meal);
}

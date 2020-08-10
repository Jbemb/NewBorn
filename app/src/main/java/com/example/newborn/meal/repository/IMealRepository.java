package com.example.newborn.meal.repository;

import androidx.lifecycle.LiveData;

import com.example.newborn.meal.bo.Meal;

import java.util.List;

public interface IMealRepository {


    LiveData<List<Meal>> getObserverMealByBaby();
    List<Meal> getObserverMealByBabyByDate();

    Meal getLastMealByBaby(String baby);

    void insertMeal (Meal meal);

    void updateMeal (Meal meal);

    void deleteMeal (Meal meal);
}

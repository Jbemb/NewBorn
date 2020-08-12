package com.example.newborn.meal.repository;

import androidx.lifecycle.LiveData;

import com.example.newborn.meal.bo.Meal;

import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Amandine
 * Interface for Meal entity related methods implemented by the MealRepository Class
 */
public interface IMealRepository {


    LiveData<List<Meal>> getObserverMealByBaby();
    LiveData<List<Meal>> getObserverMealByBabyByDate();
    LiveData<Meal> getObserverLastMealByBaby();

    void getMealByBabyByDate (String baby, Date dayStart, Date dayEnd);

    void getLastMealByBaby (String baby);

    void insertMeal (Meal meal);

    void updateMeal (Meal meal);

    void deleteMeal (Meal meal);
}

package com.example.newborn.meal.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.repository.IMealRepository;
import com.example.newborn.meal.repository.MealDbRepository;

import java.util.Date;
import java.util.List;

public class MealViewModel extends AndroidViewModel {
    IMealRepository mealRepo;

    public MealViewModel(@NonNull Application application) {
        super(application);
        mealRepo = new MealDbRepository(application);
    }

    LiveData<List<Meal>> getObserverMealByBaby() {
        return mealRepo.getObserverMealByBaby();
    }

    LiveData<List<Meal>> getObserverMealByBabyByDate() {
        return mealRepo.getObserverMealByBabyByDate();
    }

    LiveData<Meal> getObserverLastMealByBaby() {
        return mealRepo.getObserverLastMealByBaby();
    }

    void getMealByBabyByDate(String baby, Date date) {
        mealRepo.getMealByBabyByDate(baby,date);
    }

    void getLastMealByBaby(String baby) {
        mealRepo.getLastMealByBaby(baby);
    }

    void insertMeal(Meal meal) {
        mealRepo.insertMeal(meal);
    }

    void updateMeal(Meal meal) {
        mealRepo.updateMeal(meal);
    }

    void deleteMeal(Meal meal) {
        mealRepo.deleteMeal(meal);
    }
}

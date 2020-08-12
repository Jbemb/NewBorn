package com.example.newborn.view_model.meal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newborn.model.meal.Meal;
import com.example.newborn.repository.meal.IMealRepository;
import com.example.newborn.repository.meal.MealDbRepository;

import java.util.Date;
import java.util.List;

public class MealViewModel extends AndroidViewModel {
    IMealRepository mealRepo;

    public MealViewModel(@NonNull Application application) {
        super(application);
        mealRepo = new MealDbRepository(application);
    }

    public LiveData<List<Meal>> getObserverMealByBaby() {
        return mealRepo.getObserverMealByBaby();
    }

    public LiveData<List<Meal>> getObserverMealByBabyByDate() {
        return mealRepo.getObserverMealByBabyByDate();
    }

    public LiveData<Meal> getObserverLastMealByBaby() {
        return mealRepo.getObserverLastMealByBaby();
    }

    public void getMealByBabyByDate(String baby, Date dayStart, Date dayEnd) {
        mealRepo.getMealByBabyByDate(baby,dayStart, dayEnd);
    }

    public void getLastMealByBaby(String baby) {
        mealRepo.getLastMealByBaby(baby);
    }

    public void insertMeal(Meal meal) {
        mealRepo.insertMeal(meal);
    }

    public void updateMeal(Meal meal) {
        mealRepo.updateMeal(meal);
    }

    public void deleteMeal(Meal meal) {
        mealRepo.deleteMeal(meal);
    }
}

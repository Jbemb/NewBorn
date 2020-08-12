package com.example.newborn.repository.meal;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newborn.repository.DoubleParamAsync;
import com.example.newborn.appdatabase.AppDatabase;
import com.example.newborn.model.meal.Meal;
import com.example.newborn.dal.meal.MealDao;

import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Amandine
 * Class defining the methods to work with the entity Meal from the database
 */
public class MealDbRepository implements IMealRepository {
    LiveData<List<Meal>> observerMealByBaby = null;
    MutableLiveData<List<Meal>> observerMealByBabyByDate = null;
    MutableLiveData<Meal> observerLastMealByBaby = null;

    MealDao dao = null;
    //TODO: change to real names in the baby file
    String baby = "Zachary";

    public MealDbRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        dao = database.getMealDao();

        //TODO: change to real names in the baby file
        observerMealByBaby = dao.getMealByBaby(baby);

        observerMealByBabyByDate = new MutableLiveData<>();
        observerLastMealByBaby = new MutableLiveData<>();
    }


    @Override
    public LiveData<List<Meal>> getObserverMealByBaby() {
        return observerMealByBaby;
    }

    @Override
    public LiveData<List<Meal>> getObserverMealByBabyByDate() {
        return observerMealByBabyByDate;
    }

    @Override
    public LiveData<Meal> getObserverLastMealByBaby() {
        return observerLastMealByBaby;
    }

    @Override
    public void getMealByBabyByDate(String baby, Date dayStart, Date dayEnd) {
        DoubleParamAsync babyDateParams = new DoubleParamAsync(baby, dayStart, dayEnd);

        new AsyncTask<DoubleParamAsync,Void,List<Meal>>() {

            @Override
            protected List<Meal> doInBackground(DoubleParamAsync... babyDateParams) {
                String babyPara = babyDateParams[0].baby;
                Date dayStartPara = babyDateParams[0].dayStart;
                Date dayEndPara = babyDateParams[0].dayEnd;

                return dao.getMealByBabyByDate(babyPara, dayStartPara, dayEndPara);
            }

            @Override
            protected void onPostExecute(List<Meal> meal) {
                super.onPostExecute(meal);
                observerMealByBabyByDate.setValue(meal);
            }
        }.execute(babyDateParams);
    }

    @Override
    public void getLastMealByBaby(String baby) {
        new AsyncTask<String,Void,Meal>(){

            @Override
            protected Meal doInBackground(String... strings) {
                return dao.getLastMealByBaby(strings[0]);
            }

            @Override
            protected void onPostExecute(Meal meal) {
                super.onPostExecute(meal);
                observerLastMealByBaby.setValue(meal);
            }
        }.execute(baby);
    }

    @Override
    public void insertMeal(final Meal meal) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertMeal(meal);
            }
        });
    }

    @Override
    public void updateMeal(final Meal meal) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateMeal(meal);
            }
        });
    }

    @Override
    public void deleteMeal(final Meal meal) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteMeal(meal);
            }
        });
    }
}

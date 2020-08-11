package com.example.newborn.sleep.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newborn.appRepository.DoubleParamAsync;
import com.example.newborn.appdatabase.AppDatabase;
import com.example.newborn.change.bo.Change;
import com.example.newborn.change.dal.ChangeDao;
import com.example.newborn.sleep.bo.Sleep;
import com.example.newborn.sleep.dal.SleepDao;

import java.util.Date;
import java.util.List;

public class SleepDbRepository implements ISleepRepository {

    LiveData<List<Sleep>> observerSleepByBaby = null;
    MutableLiveData<List<Sleep>> observerSleepByBabyByDate = null;
    MutableLiveData<Sleep> observerLastSleepByBaby = null;

    SleepDao dao = null;
    //TODO: change to real names in the baby file
    String baby = "Zachary";

    public SleepDbRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        dao = database.getSleepDao();

        //TODO: change to real names in the baby file
        observerSleepByBaby = dao.getSleepByBaby(baby);

        observerSleepByBabyByDate = new MutableLiveData<>();
        observerLastSleepByBaby = new MutableLiveData<>();
    }

    @Override
    public LiveData<List<Sleep>> getObserverSleepByBaby() {
        return observerSleepByBaby;
    }

    @Override
    public LiveData<List<Sleep>> getObserverSleepByBabyByDate() {
        return observerSleepByBabyByDate;
    }

    @Override
    public LiveData<Sleep> getObserverLastSleepByBaby() {
        return observerLastSleepByBaby;
    }

    @Override
    public void getSleepByBabyByDate(String baby, Date dayStart, Date dayEnd) {
        DoubleParamAsync babyDateParams = new DoubleParamAsync(baby, dayStart, dayEnd);

        new AsyncTask<DoubleParamAsync,Void,List<Sleep>>() {

            @Override
            protected List<Sleep> doInBackground(DoubleParamAsync... babyDateParams) {
                String babyPara = babyDateParams[0].baby;
                Date dayStartPara = babyDateParams[0].dayStart;
                Date dayEndPara = babyDateParams[0].dayEnd;

                return dao.getSleepByBabyByDate(babyPara, dayStartPara, dayEndPara);
            }

            @Override
            protected void onPostExecute(List<Sleep> sleep) {
                super.onPostExecute(sleep);
                observerSleepByBabyByDate.setValue(sleep);
            }
        }.execute(babyDateParams);
    }

    @Override
    public void getLastSleepByBaby(String baby) {
        new AsyncTask<String,Void,Sleep>(){

            @Override
            protected Sleep doInBackground(String... strings) {
                return dao.getLastSleepByBaby(strings[0]);
            }

            @Override
            protected void onPostExecute(Sleep sleep) {
                super.onPostExecute(sleep);
                observerLastSleepByBaby.setValue(sleep);
            }
        }.execute(baby);
    }

    @Override
    public void insertSleep(final Sleep sleep) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertSleep(sleep);
            }
        });
    }

    @Override
    public void updateSleep(final Sleep sleep) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateSleep(sleep);
            }
        });
    }

    @Override
    public void deleteSleep(final Sleep sleep) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteSleep(sleep);
            }
        });
    }
}

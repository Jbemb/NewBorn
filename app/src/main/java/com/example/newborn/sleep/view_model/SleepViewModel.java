package com.example.newborn.sleep.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newborn.sleep.bo.Sleep;
import com.example.newborn.sleep.repository.ISleepRepository;
import com.example.newborn.sleep.repository.SleepDbRepository;

import java.util.Date;
import java.util.List;

public class SleepViewModel extends AndroidViewModel {
    ISleepRepository sleepRepo;

    //constructor
    public SleepViewModel(@NonNull Application application) {
        super(application);
        sleepRepo = new SleepDbRepository(application);
    }

    public LiveData<List<Sleep>> getObserverSleepByBaby() {
        return sleepRepo.getObserverSleepByBaby();
    }

    public LiveData<List<Sleep>> getObserverSleepByBabyByDate() {
        return sleepRepo.getObserverSleepByBabyByDate();
    }

    public LiveData<Sleep> getObserverLastSleepByBaby() {
        return sleepRepo.getObserverLastSleepByBaby();
    }

    public void getSleepByBabyByDate(String baby, Date dayStart, Date dayEnd) {
        sleepRepo.getSleepByBabyByDate(baby, dayStart, dayEnd);
    }

    public void getLastSleepByBaby(String baby) {
        sleepRepo.getLastSleepByBaby(baby);
    }

    public void insertSleep(final Sleep sleep) {
       sleepRepo.insertSleep(sleep);
    }

    public void updateSleep(final Sleep sleep) {
        sleepRepo.updateSleep(sleep);
    }

    public void deleteSleep(final Sleep sleep) {
        sleepRepo.deleteSleep(sleep);
    }
}

package com.example.newborn.sleep.repository;

import androidx.lifecycle.LiveData;

import com.example.newborn.sleep.bo.Sleep;

import java.time.LocalDateTime;
import java.util.List;

public interface ISleepRepository {

    LiveData<List<Sleep>> getObserverSleepByBaby();
    LiveData<List<Sleep>> getObserverSleepByBabyByDate();
    LiveData<Sleep> getObserverLastSleepByBaby();

    void getSleepByBabyByDate (String baby, LocalDateTime date);

    void getLastSleepByBaby (String baby);

    void insertSleep (Sleep sleep);

    void updateSleep (Sleep sleep);

    void deleteSleep (Sleep sleep);
}

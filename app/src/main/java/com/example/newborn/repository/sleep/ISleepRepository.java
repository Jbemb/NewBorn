package com.example.newborn.repository.sleep;

import androidx.lifecycle.LiveData;

import com.example.newborn.model.sleep.Sleep;

import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Amandine
 * Interface for Sleep entity related methods implemented by the SleepRepository Class
 */
public interface ISleepRepository {

    LiveData<List<Sleep>> getObserverSleepByBaby();
    LiveData<List<Sleep>> getObserverSleepByBabyByDate();
    LiveData<Sleep> getObserverLastSleepByBaby();

    void getSleepByBabyByDate (String baby, Date dayStart, Date dayEnd);

    void getLastSleepByBaby (String baby);

    void insertSleep (Sleep sleep);

    void updateSleep (Sleep sleep);

    void deleteSleep (Sleep sleep);
}

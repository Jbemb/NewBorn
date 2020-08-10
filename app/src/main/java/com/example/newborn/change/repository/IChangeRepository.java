package com.example.newborn.change.repository;

import androidx.lifecycle.LiveData;

import com.example.newborn.change.bo.Change;

import java.util.Date;
import java.util.List;

public interface IChangeRepository {

    LiveData<List<Change>> getObserverChangeByBaby();
    LiveData<List<Change>> getObserverChangeByBabyByDate();
    LiveData<Change> getObserverLastChangeByBaby();

    void getChangeByBabyByDate (String baby, Date date);

    void getLastChangeByBaby (String baby);

    void insertChange (Change change);

    void updateChange (Change change);

    void deleteChange (Change change);
}

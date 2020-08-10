package com.example.newborn.change.repository;

import androidx.lifecycle.LiveData;

import com.example.newborn.change.bo.Change;

import java.util.List;

public interface IChangeRepository {

    LiveData<List<Change>> getObserverChangeByBaby();
    List<Change> getObserverChangeByBabyByDate();

    Change getLastChangeByBaby(String baby);

    void insertChange (Change change);

    void updateChange (Change change);

    void deleteChange (Change change);
}

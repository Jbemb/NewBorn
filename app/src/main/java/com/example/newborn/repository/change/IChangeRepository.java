package com.example.newborn.repository.change;

import androidx.lifecycle.LiveData;

import com.example.newborn.model.change.Change;

import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Amandine
 * Interface for Change entity related methods implemented by the ChangeRepository Class
 */
public interface IChangeRepository {

    LiveData<List<Change>> getObserverChangeByBaby();
    LiveData<List<Change>> getObserverChangeByBabyByDate();
    LiveData<Change> getObserverLastChangeByBaby();

    void getChangeByBabyByDate (String baby, Date dayStart, Date dayEnd);

    void getLastChangeByBaby (String baby);

    void insertChange (Change change);

    void updateChange (Change change);

    void deleteChange (Change change);
}

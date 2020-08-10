package com.example.newborn.change.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newborn.change.bo.Change;
import com.example.newborn.change.repository.ChangeDbRepository;
import com.example.newborn.change.repository.IChangeRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ChangeViewModel extends AndroidViewModel {
    IChangeRepository changeRepo;

    public ChangeViewModel(@NonNull Application application) {
        super(application);
        changeRepo = new ChangeDbRepository(application);
    }

    LiveData<List<Change>> getObserverChangeByBaby() {
        return changeRepo.getObserverChangeByBaby();
    }

    LiveData<List<Change>> getObserverChangeByBabyByDate() {
        return changeRepo.getObserverChangeByBabyByDate();
    }

    LiveData<Change> getObserverLastChangeByBaby() {
        return changeRepo.getObserverLastChangeByBaby();
    }

    void getChangeByBabyByDate(String baby, LocalDateTime date) {
        changeRepo.getChangeByBabyByDate(baby, date);
    }

    void getLastChangeByBaby(String baby) {
        changeRepo.getLastChangeByBaby(baby);
    }

    void insertChange(Change change) {
        changeRepo.insertChange(change);
    }

    void updateChange(Change change) {
        changeRepo.updateChange(change);
    }

    void deleteChange(Change change) {
        changeRepo.deleteChange(change);
    }
}

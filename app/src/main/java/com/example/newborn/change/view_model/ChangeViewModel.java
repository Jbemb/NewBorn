package com.example.newborn.change.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newborn.change.bo.Change;
import com.example.newborn.change.repository.ChangeDbRepository;
import com.example.newborn.change.repository.IChangeRepository;

import java.util.Date;
import java.util.List;

public class ChangeViewModel extends AndroidViewModel {
    IChangeRepository changeRepo;

    public ChangeViewModel(@NonNull Application application) {
        super(application);
        changeRepo = new ChangeDbRepository(application);
    }

    public LiveData<List<Change>> getObserverChangeByBaby() {
        return changeRepo.getObserverChangeByBaby();
    }

    public LiveData<List<Change>> getObserverChangeByBabyByDate() {
        return changeRepo.getObserverChangeByBabyByDate();
    }

    public LiveData<Change> getObserverLastChangeByBaby() {
        return changeRepo.getObserverLastChangeByBaby();
    }

    public void getChangeByBabyByDate(String baby, Date dayStart, Date dayEnd) {
        changeRepo.getChangeByBabyByDate(baby, dayStart, dayEnd);
    }

    public void getLastChangeByBaby(String baby) {
        changeRepo.getLastChangeByBaby(baby);
    }

    public void insertChange(Change change) {
        changeRepo.insertChange(change);
    }

    public void updateChange(Change change) {
        changeRepo.updateChange(change);
    }

    public void deleteChange(Change change) {
        changeRepo.deleteChange(change);
    }
}

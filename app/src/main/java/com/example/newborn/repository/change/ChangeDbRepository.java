package com.example.newborn.change.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newborn.appRepository.DoubleParamAsync;
import com.example.newborn.appdatabase.AppDatabase;
import com.example.newborn.change.bo.Change;
import com.example.newborn.dal.change.ChangeDao;

import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Amandine
 * Class defining the methods to work with the entity Change from the database
 */
public class ChangeDbRepository implements IChangeRepository{

    LiveData<List<Change>> observerChangeByBaby = null;
    MutableLiveData<List<Change>> observerChangeByBabyByDate = null;
    MutableLiveData<Change> observerLastChangeByBaby = null;

    ChangeDao dao = null;
    //TODO: change to real names in the baby file
    String baby = "Zachary";

    public ChangeDbRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        dao = database.getChangeDao();

        //TODO: change to real names in the baby file
        observerChangeByBaby = dao.getChangeByBaby(baby);

        observerChangeByBabyByDate = new MutableLiveData<>();
        observerLastChangeByBaby = new MutableLiveData<>();
    }

    @Override
    public LiveData<List<Change>> getObserverChangeByBaby() {
        return observerChangeByBaby;
    }

    @Override
    public LiveData<List<Change>> getObserverChangeByBabyByDate() {
        return observerChangeByBabyByDate;
    }

    @Override
    public LiveData<Change> getObserverLastChangeByBaby() {
        return observerLastChangeByBaby;
    }

    @Override
    public void getChangeByBabyByDate(String baby, Date dayStart, Date dayEnd) {
        DoubleParamAsync babyDateParams = new DoubleParamAsync(baby, dayStart, dayEnd);

        new AsyncTask<DoubleParamAsync,Void,List<Change>>() {

            @Override
            protected List<Change> doInBackground(DoubleParamAsync... babyDateParams) {
                String babyPara = babyDateParams[0].baby;
                Date dayStartPara = babyDateParams[0].dayStart;
                Date dayEndPara = babyDateParams[0].dayEnd;

                return dao.getChangeByBabyByDate(babyPara, dayStartPara, dayEndPara);
            }

            @Override
            protected void onPostExecute(List<Change> change) {
                super.onPostExecute(change);
                observerChangeByBabyByDate.setValue(change);
            }
        }.execute(babyDateParams);
    }

    @Override
    public void getLastChangeByBaby(String baby) {
        new AsyncTask<String,Void,Change>(){

            @Override
            protected Change doInBackground(String... strings) {
                return dao.getLastChangeByBaby(strings[0]);
            }

            @Override
            protected void onPostExecute(Change change) {
                super.onPostExecute(change);
                observerLastChangeByBaby.setValue(change);
            }
        }.execute(baby);
    }


    @Override
    public void insertChange(final Change change) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertChange(change);
            }
        });
    }

    @Override
    public void updateChange(final Change change) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateChange(change);
            }
        });
    }

    @Override
    public void deleteChange(final Change change) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteChange(change);
            }
        });
    }
}

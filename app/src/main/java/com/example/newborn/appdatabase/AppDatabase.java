package com.example.newborn.appdatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.newborn.change.bo.Change;
import com.example.newborn.change.dal.ChangeDao;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.dal.MealDao;
import com.example.newborn.sleep.bo.Sleep;
import com.example.newborn.sleep.dal.SleepDao;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Meal.class, Change.class, Sleep.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //singleton
    private static AppDatabase INSTANCE;

    //DAOs
    public abstract MealDao getMealDao();
    public abstract ChangeDao getChangeDao();
    public abstract SleepDao getSleepDao();

    public static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(1);
    /**
     * singleton
     */
    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            //creation - context (who access it) - this class info - database name
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "izzy_baby")
                    .addCallback(roomFixture)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    /**
     * Callback function for fixtures
     */
    private static Callback roomFixture = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new AsyncTask<AppDatabase,Void,Void>(){
                @Override
                protected Void doInBackground(AppDatabase... appDatabases) {
                    //basic variables
                    boolean random = true;
                    String name = "Zachary";
                    LocalDateTime date = LocalDateTime.now();
                    Random rand = new Random();
                    LocalDateTime ranDate = date.minusHours(rand.nextInt(36));
                    //random baby 1/5
                    int ran = rand.nextInt(5);
                    if(ran == 2){
                        name = "Armel";
                    }

                    //info change
                    ChangeDao changeDao = INSTANCE.getChangeDao();
                    int i;
                    for(i=0; i>70; i++){
                        ran = rand.nextInt(2);
                        if(ran == 0){
                            random = false;
                        }
                        Change change = new Change(name,ranDate,random);
                        changeDao.insertChange(change);
                    }
                    //make sure last entry for Zachary is the most recent
                    changeDao.insertChange(new Change("Zachary",date,true));

                    //info sleep
                    SleepDao sleepDao = INSTANCE.getSleepDao();
                    for(i=0; i>70; i++){
                        LocalDateTime startDate = ranDate;
                        LocalDateTime endDate = startDate.plusMinutes(rand.nextInt(360));
                        Sleep sleep = new Sleep(name, startDate, endDate);
                        sleepDao.insertSleep(sleep);
                    }
                    //make sure last sleep is recent and Zachary's
                    sleepDao.insertSleep(new Sleep("Zachary", date.minusHours(1), date.minusMinutes(30)));

                    //info meal
                    MealDao mealDao = INSTANCE.getMealDao();
                    for(i=0; i>70; i++){
                        //change breast
                        if(random == true){
                            random = false;
                        }else{
                            random = true;
                        }
                        Meal meal = new Meal(name, ranDate, 120, random);
                        mealDao.insertMeal(meal);
                    }
                    //make sure last entry for Zachary is the most recent
                    mealDao.insertMeal(new Meal("Zachary",date,120,random));

                    return null;
                }
            }.execute(INSTANCE);
        }
    };

}

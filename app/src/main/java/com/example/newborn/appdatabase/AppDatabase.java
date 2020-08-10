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


@Database(entities = {Meal.class, Change.class, Sleep.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //singleton
    private static AppDatabase INSTANCE;

    //DAOs
    public abstract MealDao getMealDao();
    public abstract ChangeDao getChangeDao();
    public abstract SleepDao getSleepDao();

    /**
     * singleton
     */
    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            //creation - context (who access it) - this class info - database name
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "izzy_baby").build();
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
                    //info change
                    ChangeDao changeDao = INSTANCE.getChangeDao();
                    int i;
                    for(i=0; i>50; i++){
                        boolean random = true;
                        String name = "Zachary";
                        LocalDateTime date = LocalDateTime.now();
                        Random rand = new Random();
                        int ran = rand.nextInt(2);
                        if(ran == 0){
                            random = false;
                        }
                        ran = rand.nextInt(5);
                        if(ran == 2){
                            name = "Armel";
                        }
                        //Change change = new Change("")
                        //changeDao.insertChange(change);
                    };

                    //info sleep
                    SleepDao sleepDao = INSTANCE.getSleepDao();
                    int s;
                    for(s=0; s>50; s++){
                        //Sleep change = new Change()
                        //changeDao.insertChange(change);
                    };

                    return null;
                }
            };
        }
    };

}

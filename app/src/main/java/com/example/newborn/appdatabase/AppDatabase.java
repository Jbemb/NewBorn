package com.example.newborn.appdatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.newborn.change.bo.Change;
import com.example.newborn.change.dal.ChangeDao;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.dal.MealDao;
import com.example.newborn.sleep.bo.Sleep;
import com.example.newborn.sleep.dal.SleepDao;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.namespace.QName;

enum TestEventName {
    CHANGE,
    MEAL,
    SLEEP
}

@Database(entities = {Meal.class, Change.class, Sleep.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
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
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "izzy_baby9.db")
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
                    Random rand = new Random();
                    Calendar calendar = Calendar.getInstance();
                    Date dateNow = new Date();
                    Date eventDate;
                    int nbTestDays = 10;
                    int maxSleepDuration = 6;
                    String breast[] = {"right","left",null};

                    String names[] = {
                            "Zachary",
                            "Armel"
                    };

                    calendar.setTime(dateNow);
                    calendar.add(Calendar.DAY_OF_YEAR, -nbTestDays);

                    for (eventDate = calendar.getTime();
                         eventDate.before(dateNow);
                         calendar.add(Calendar.HOUR_OF_DAY, rand.nextInt(3)+1),
                                 eventDate = calendar.getTime())
                    {
                        TestEventName e = TestEventName
                                .values()[new Random().nextInt(TestEventName.values().length)];

                        String randName = names[rand.nextInt(names.length)];
                        boolean randBool = rand.nextInt(2)==1;
                        String randBreast = breast[rand.nextInt(breast.length)];

                        switch (e) {
                            case CHANGE:
                            {
                                ChangeDao changeDao = INSTANCE.getChangeDao();
                                Change change =
                                        new Change(
                                                randName,
                                                eventDate,
                                                randBool
                                        );
                                changeDao.insertChange(change);
                            }
                            break;
                            case MEAL:
                            {
                                MealDao mealDao = INSTANCE.getMealDao();
                                Meal meal = new Meal(randName, eventDate, 120, randBreast);
                                mealDao.insertMeal(meal);
                            }
                            break;
                            case SLEEP:
                            {
                                int sleepDuration;
                                sleepDuration = rand.nextInt(maxSleepDuration);
                                Date dateEndSleep = (Date)eventDate.clone();
                                Calendar tempCalendar = Calendar.getInstance();
                                tempCalendar.setTime(dateEndSleep);
                                tempCalendar.add(Calendar.HOUR_OF_DAY, sleepDuration);
                                dateEndSleep = tempCalendar.getTime();

                                SleepDao sleepDao = INSTANCE.getSleepDao();
                                Sleep sleep = new Sleep(randName, eventDate, dateEndSleep);
                                sleepDao.insertSleep(sleep);
                            }
                            break;
                        };

                    }

                    return null;
                }
            }.execute(INSTANCE);
        }
    };

}

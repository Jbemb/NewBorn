package com.example.newborn.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.newborn.R;
import com.example.newborn.activity.lists.ChangeListActivity;
import com.example.newborn.activity.lists.MealListActivity;
import com.example.newborn.activity.lists.SleepListActivity;
import com.example.newborn.activity.lists.adapters.MealAdapter;
import com.example.newborn.change.bo.Change;
import com.example.newborn.change.view_model.ChangeViewModel;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.view_model.MealViewModel;
import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SummaryDayActivity extends AppCompatActivity {
    private Date dayStart = null;
    private Date dayEnd  = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_day);
        Stetho.initializeWithDefaults(this);

        //TODO get the date from the calendar
        TextView tvDay = findViewById(R.id.tv_day);
        final TextView tvMealSummary = findViewById(R.id.tv_meal_summary);

        String tvDateString = "10/08/2020";
        tvDay.setText(tvDateString);

        //Create the starting of the day and its ending
        Calendar c = Calendar.getInstance();
        c.set(2020, 7,7,0,0,0);
        dayStart = c.getTime();
        c.add(Calendar.HOUR_OF_DAY, 23);
        c.add(Calendar.MINUTE, 59);
        dayEnd = c.getTime();

        MealViewModel cvm = new ViewModelProvider(this).get(MealViewModel.class);
        cvm.getMealByBabyByDate("Zachary", dayStart, dayEnd);//TODO mettre les dates Passer les dates en intent qu'on peut récupérer dans le list activity
        cvm.getObserverMealByBabyByDate().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                int listSize = meals.size();
                tvMealSummary.setText(String.format("x %d", listSize));
            }
        });



    }

    public void onClickDetailsMeal(View view) {
        Intent intentDay = new Intent(this, MealListActivity.class);
        intentDay.putExtra("dayStart", dayStart);
        intentDay.putExtra("dayEnd", dayEnd);
        startActivity(intentDay);
    }

    public void onClickDetailsChange(View view) {
        Intent intentDay = new Intent(this, ChangeListActivity.class);
        intentDay.putExtra("dayStart",  dayStart);
        intentDay.putExtra("dayEnd", dayEnd);
        startActivity(intentDay);
    }

    public void onClickDetailsSleep(View view) {
        Intent intentDay = new Intent(this, SleepListActivity.class);
        intentDay.putExtra("dayStart", dayStart);
        intentDay.putExtra("dayEnd", dayEnd);
        startActivity(intentDay);
    }
}
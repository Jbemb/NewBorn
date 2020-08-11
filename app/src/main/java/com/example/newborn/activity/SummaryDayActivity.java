package com.example.newborn.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newborn.R;
import com.example.newborn.activity.lists.ChangeListActivity;
import com.example.newborn.activity.lists.MealListActivity;
import com.example.newborn.activity.lists.SleepListActivity;
import com.example.newborn.activity.lists.adapters.MealAdapter;
import com.example.newborn.change.bo.Change;
import com.example.newborn.change.view_model.ChangeViewModel;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.view_model.MealViewModel;
import com.example.newborn.sleep.bo.Sleep;
import com.example.newborn.sleep.view_model.SleepViewModel;
import com.facebook.stetho.Stetho;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SummaryDayActivity extends AppCompatActivity {
    private Date dayStart = null;
    private Date dayEnd  = null;
    private Calendar cal = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_day);
        Stetho.initializeWithDefaults(this);

        //TODO get the date from the calendar
        TextView tvDay = findViewById(R.id.tv_day);
        final TextView tvMealSummary = findViewById(R.id.tv_meal_summary);
        final TextView tvChangeSummary = findViewById(R.id.tv_change_summary);
        final TextView tvSleepSummary = findViewById(R.id.tv_sleep_summary);

        //Create the starting of the day and its ending
        Intent intentSummary = getIntent();
        dayStart = (Date)intentSummary.getSerializableExtra("dayStart");
        dayEnd = (Date)intentSummary.getSerializableExtra("dayEnd");

        if (dayStart==null && dayEnd==null) {
            cal = Calendar.getInstance();
            this.resetToMidnight(cal);
            dayStart = cal.getTime();
            cal.add(Calendar.HOUR_OF_DAY, 23);
            cal.add(Calendar.MINUTE, 59);
            dayEnd = cal.getTime();
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String tvDateString = dateFormat.format(dayStart);
        tvDay.setText(tvDateString);

        MealViewModel mvm = new ViewModelProvider(this).get(MealViewModel.class);
        mvm.getMealByBabyByDate("Zachary", dayStart, dayEnd);//TODO mettre les dates Passer les dates en intent qu'on peut récupérer dans le list activity
        mvm.getObserverMealByBabyByDate().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                int listSize = meals.size();
                tvMealSummary.setText(String.format("x %d", listSize));
            }
        });

        ChangeViewModel cvm = new ViewModelProvider(this).get(ChangeViewModel.class);
        cvm.getChangeByBabyByDate("Zachary", dayStart, dayEnd);//TODO mettre les dates Passer les dates en intent qu'on peut récupérer dans le list activity
        cvm.getObserverChangeByBabyByDate().observe(this, new Observer<List<Change>>() {
            @Override
            public void onChanged(List<Change> changes) {
                int listSize = changes.size();
                tvChangeSummary.setText(String.format("x %d", listSize));
            }
        });

        SleepViewModel svm = new ViewModelProvider(this).get(SleepViewModel.class);
        svm.getSleepByBabyByDate("Zachary", dayStart, dayEnd);//TODO mettre les dates Passer les dates en intent qu'on peut récupérer dans le list activity
        svm.getObserverSleepByBabyByDate().observe(this, new Observer<List<Sleep>>() {
            @Override
            public void onChanged(List<Sleep> sleeps) {
                int listSize = sleeps.size();
                tvSleepSummary.setText(String.format("x %d", listSize));
            }
        });

        //Set the calendar to enable the user to choose the day
        final ImageButton tvCalendar = findViewById(R.id.ib_calendar);
        tvCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(tvCalendar);
            }
        });
    }

    private void showDateDialog(ImageButton tvCalendar) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                SummaryDayActivity.resetToMidnight(cal);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dayStart = cal.getTime();
                cal.add(Calendar.HOUR_OF_DAY, 23);
                cal.add(Calendar.MINUTE, 59);
                dayEnd = cal.getTime();

                Intent intentSummary = new Intent(SummaryDayActivity.this, SummaryDayActivity.class);
                intentSummary.putExtra("dayStart", dayStart);
                intentSummary.putExtra("dayEnd", dayEnd);

                startActivity(intentSummary);
            }
        };
        new DatePickerDialog(SummaryDayActivity.this,dateSetListener,cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
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

    public static void resetToMidnight(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
}
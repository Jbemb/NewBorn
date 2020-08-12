package com.example.newborn.activity.global;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.newborn.R;
import com.example.newborn.activity.change.ChangeListActivity;
import com.example.newborn.activity.meal.MealListActivity;
import com.example.newborn.activity.sleep.SleepListActivity;
import com.example.newborn.model.change.Change;
import com.example.newborn.view_model.change.ChangeViewModel;
import com.example.newborn.model.meal.Meal;
import com.example.newborn.view_model.meal.MealViewModel;
import com.example.newborn.model.sleep.Sleep;
import com.example.newborn.view_model.sleep.SleepViewModel;
import com.facebook.stetho.Stetho;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Amandine
 * This class allows to display the summary of the activities for a certain day
 */
public class SummaryDayActivity extends AppCompatActivity {

    private Date dayStart = null;
    private Date dayEnd  = null;
    private Calendar cal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_day);
        Stetho.initializeWithDefaults(this);

        //Initiate the layout variables
        final TextView tvDay = findViewById(R.id.tv_day);
        final TextView tvMealSummary = findViewById(R.id.tv_meal_summary);
        final TextView tvChangeSummary = findViewById(R.id.tv_change_summary);
        final TextView tvSleepSummary = findViewById(R.id.tv_sleep_summary);

        //DAY START AND DAY END
        //Get the date parameters sent by other activities or the calendar
        Intent intentSummary = getIntent();
        dayStart = (Date)intentSummary.getSerializableExtra("dayStart");
        dayEnd = (Date)intentSummary.getSerializableExtra("dayEnd");

        //If no date parameters were sent by other activities or the calendar
        //the default date is today
        if (dayStart==null && dayEnd==null) {
            cal = Calendar.getInstance();
            this.resetToMidnight(cal);
            dayStart = cal.getTime();
            cal.add(Calendar.HOUR_OF_DAY, 23);
            cal.add(Calendar.MINUTE, 59);
            dayEnd = cal.getTime();
        }
        //Set the display of the date
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String tvDateString = dateFormat.format(dayStart);
        tvDay.setText(tvDateString);

        MealViewModel mvm = new ViewModelProvider(this).get(MealViewModel.class);
        mvm.getMealByBabyByDate("Zachary", dayStart, dayEnd);
        mvm.getObserverMealByBabyByDate().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                int listSize = meals.size();
                tvMealSummary.setText(String.format("x %d", listSize));
            }
        });

        ChangeViewModel cvm = new ViewModelProvider(this).get(ChangeViewModel.class);
        cvm.getChangeByBabyByDate("Zachary", dayStart, dayEnd);
        cvm.getObserverChangeByBabyByDate().observe(this, new Observer<List<Change>>() {
            @Override
            public void onChanged(List<Change> changes) {
                int listSize = changes.size();
                tvChangeSummary.setText(String.format("x %d", listSize));
            }
        });

        SleepViewModel svm = new ViewModelProvider(this).get(SleepViewModel.class);
        svm.getSleepByBabyByDate("Zachary", dayStart, dayEnd);
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

    /**
     * Function allowing to use a calendar the pick the date
     * The picked date has the time corresponding to the actual time
     * To allow the user to set the time one wants, the time of the picked
     * date is reset by the static function SummaryDayActivity.resetToMidnight
     * @param tvCalendar
     */
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

    /**
     * Function to go to the list of meals
     * @param view
     */
    public void onClickDetailsMeal(View view) {
        Intent intentDay = new Intent(this, MealListActivity.class);
        intentDay.putExtra("dayStart", dayStart);
        intentDay.putExtra("dayEnd", dayEnd);
        startActivity(intentDay);
    }

    /**
     * Function to go to the list of changes
     * @param view
     */
    public void onClickDetailsChange(View view) {
        Intent intentDay = new Intent(this, ChangeListActivity.class);
        intentDay.putExtra("dayStart",  dayStart);
        intentDay.putExtra("dayEnd", dayEnd);
        startActivity(intentDay);
    }

    /**
     * Function to go to the list of sleeps
     * @param view
     */
    public void onClickDetailsSleep(View view) {
        Intent intentDay = new Intent(this, SleepListActivity.class);
        intentDay.putExtra("dayStart", dayStart);
        intentDay.putExtra("dayEnd", dayEnd);
        startActivity(intentDay);
    }

    /**
     * Function to reset the time at midnight
     * @param cal
     */
    public static void resetToMidnight(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
}
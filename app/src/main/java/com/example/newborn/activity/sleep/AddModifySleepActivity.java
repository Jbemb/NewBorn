package com.example.newborn.activity.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.newborn.R;
import com.example.newborn.activity.global.SummaryDayActivity;
import com.example.newborn.model.sleep.Sleep;
import com.example.newborn.view_model.sleep.SleepViewModel;
import com.facebook.stetho.Stetho;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created and implemented by Amandine
 * This class allows to add or modify a meal
 */
public class AddModifySleepActivity extends AppCompatActivity {
    //Variables related to the layout
    private EditText etDateStart;
    private EditText etTimeStart;
    private EditText etDateEnd;
    private EditText etTimeEnd;
    private TextView tvTitle;
    //Variables needed for the Object Meal
    private Date dateStartToSave;
    private Date dateEndToSave;
    private int hoursStart;
    private int minutesStart;
    private int hoursEnd;
    private int minutesEnd;

    private Sleep sleepToModify = null;
    private SleepViewModel svm = null;

    //Format to convert dates to strings
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_sleep);

        Stetho.initializeWithDefaults(this);

        //set babyName
        final TextView tvBabyName = findViewById(R.id.tv_baby);
        tvBabyName.setText("Zachary");

        etDateStart = findViewById(R.id.et_date_add_sleep);
        etTimeStart = findViewById(R.id.et_time_add_sleep);
        etDateEnd = findViewById(R.id.et_dateend_add_sleep);
        etTimeEnd = findViewById(R.id.et_timeend_add_sleep);
        tvTitle = findViewById(R.id.et_title_add_sleep);

        svm = new ViewModelProvider(this).get(SleepViewModel.class);

        //Get the object Sleep sent from the list of meals by clicking on edit button
        sleepToModify = getIntent().getParcelableExtra("modifySleep");

        //Check if there was an object sent from the list of sleeps
        if (sleepToModify != null) {
            //Display temporal data of the object Sleep
            Date datetimeStart = sleepToModify.getStartTime();
            etDateStart.setText(dateFormat.format(datetimeStart));
            etTimeStart.setText(timeFormat.format(datetimeStart));
            Date datetimeEnd = sleepToModify.getEndTime();
            etDateEnd.setText(dateFormat.format(datetimeEnd));
            etTimeEnd.setText(timeFormat.format(datetimeEnd));

            tvTitle.setText("Modification d'un dodo");
        } else {
            tvTitle.setText("Ajout d'un dodo");
            sleepToModify = new Sleep();
        }

        //Set the calendar to enable the user to choose the day of sleep start
        final ImageButton ibCalendar = findViewById(R.id.ib_calendar);
        ibCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateStartDialog(ibCalendar);
            }
        });

        //Set the calendar to enable the user to choose the day of sleep end
        final ImageButton ibCalendar2 = findViewById(R.id.ib_calendar2);
        ibCalendar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateEndDialog(ibCalendar2);
            }
        });

        //Set the clock to enable the user to choose the start time of sleep
        final ImageButton ibClock = findViewById(R.id.ib_clock);
        ibClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeStartDialog(ibClock);
            }
        });

        //Set the clock to enable the user to choose the end time of sleep
        final ImageButton ibClock2 = findViewById(R.id.ib_clock2);
        ibClock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeEndDialog(ibClock2);
            }
        });
    }

    /**
     * Function allowing to use a calendar the pick the date the sleep starts
     * The picked date has the time corresponding to the actual time
     * To allow the user to set the time one wants, the time of the picked
     * date is reset by the static function SummaryDayActivity.resetToMidnight
     * @param ibCalendar
     */
    private void showDateStartDialog(ImageButton ibCalendar) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                SummaryDayActivity.resetToMidnight(cal);
                dateStartToSave = cal.getTime();

                etDateStart.setText(dateFormat.format(dateStartToSave));
            }
        };
        new DatePickerDialog(AddModifySleepActivity.this,dateSetListener,cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Function allowing to use a clock the pick the time the sleep starts
     * @param ibClock
     */
    private void showTimeStartDialog(ImageButton ibClock) {
        final Calendar cal = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker TimePicker, int hour, int minute) {
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);

                etTimeStart.setText(timeFormat.format(cal.getTime()));

            }


        };
        new TimePickerDialog(AddModifySleepActivity.this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),false).show();
    }


    /**
     * Function allowing to use a calendar the pick the date the sleep ends
     * The picked date has the time corresponding to the actual time
     * To allow the user to set the time one wants, the time of the picked
     * date is reset by the static function SummaryDayActivity.resetToMidnight
     * @param ibCalendar2
     */
    private void showDateEndDialog(ImageButton ibCalendar2) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                SummaryDayActivity.resetToMidnight(cal);
                dateEndToSave = cal.getTime();

                etDateEnd.setText(dateFormat.format(dateEndToSave));
            }
        };
        new DatePickerDialog(AddModifySleepActivity.this,dateSetListener,cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Function allowing to use a clock the pick the time the sleep ends
     * @param ibClock2
     */
    private void showTimeEndDialog(ImageButton ibClock2) {
        final Calendar cal = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker TimePicker, int hour, int minute) {
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);

                etTimeEnd.setText(timeFormat.format(cal.getTime()));

            }


        };
        new TimePickerDialog(AddModifySleepActivity.this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),false).show();
    }

    public void onClickSave(View view) {

        try {
            //START TIME
            //Get the input date from the input field (the time by default is the entry time)
            String dateToSaveStr = etDateStart.getText().toString();
            dateStartToSave = dateFormat.parse(dateToSaveStr);
            //Get the input time from the input field
            String timeToSaveStr = etTimeStart.getText().toString();
            Date timeStartToSave = timeFormat.parse(timeToSaveStr);
            //Set the input time to the input date
            Calendar cal = Calendar.getInstance();
            cal.setTime(timeStartToSave);
            hoursStart = cal.get(Calendar.HOUR_OF_DAY);
            minutesStart = cal.get(Calendar.MINUTE);
            cal.setTime(dateStartToSave);
            cal.set(Calendar.HOUR_OF_DAY, hoursStart);
            cal.set(Calendar.MINUTE, minutesStart);
            dateStartToSave = cal.getTime();

            //END TIME
            //Get the input date from the input field (the time by default is the entry time)
            dateToSaveStr = etDateEnd.getText().toString();
            dateEndToSave = dateFormat.parse(dateToSaveStr);
            //Get the input time from the input field
            timeToSaveStr = etTimeStart.getText().toString();
            Date timeEndToSave = timeFormat.parse(timeToSaveStr);
            //Set the input time to the input date
            cal.setTime(timeEndToSave);
            hoursEnd = cal.get(Calendar.HOUR_OF_DAY);
            minutesEnd = cal.get(Calendar.MINUTE);
            cal.setTime(dateEndToSave);
            cal.set(Calendar.HOUR_OF_DAY, hoursEnd);
            cal.set(Calendar.MINUTE, minutesEnd);
            dateEndToSave = cal.getTime();

            //Set the start date and the start time to save
            sleepToModify.setStartTime(dateStartToSave);
            //Set the end date and the ebd time to save
            sleepToModify.setEndTime(dateEndToSave);

            //Update the object  in the dbb if it exists or insert it in the dbb if not
            if (sleepToModify.getId() == 0) {
                //TODO get the baby's name
                sleepToModify = new Sleep("Zachary", dateStartToSave, dateEndToSave);
                svm.insertSleep(sleepToModify);
            } else {
                svm.updateSleep(sleepToModify);
            }
            Toast.makeText(this, "Sauvegardé", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SummaryDayActivity.class);
            startActivity(intent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
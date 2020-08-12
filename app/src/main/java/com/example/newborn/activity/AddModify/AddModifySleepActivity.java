package com.example.newborn.activity.AddModify;

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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.newborn.R;
import com.example.newborn.activity.SummaryDayActivity;
import com.example.newborn.change.bo.Change;
import com.example.newborn.change.view_model.ChangeViewModel;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.sleep.bo.Sleep;
import com.example.newborn.sleep.view_model.SleepViewModel;
import com.facebook.stetho.Stetho;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddModifySleepActivity extends AppCompatActivity {
    private EditText etDateStart;
    private EditText etTimeStart;
    private EditText etDateEnd;
    private EditText etTimeEnd;
    private EditText etTitle;

    private Sleep sleepToModify = null;
    private SleepViewModel svm = null;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private Date dateStartToSave;
    private Date dateEndToSave;
    private int hoursStart;
    private int minutesStart;
    private int hoursEnd;
    private int minutesEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_sleep);

        Stetho.initializeWithDefaults(this);

        etDateStart = findViewById(R.id.et_date_add_sleep);
        etTimeStart = findViewById(R.id.et_time_add_sleep);
        etDateEnd = findViewById(R.id.et_dateend_add_sleep);
        etTimeEnd = findViewById(R.id.et_timeend_add_sleep);
        etTitle = findViewById(R.id.et_title_add_sleep);

        svm = new ViewModelProvider(this).get(SleepViewModel.class);
        sleepToModify = getIntent().getParcelableExtra("modifySleep");

        if (sleepToModify != null) {
            Date datetimeStart = sleepToModify.getStartTime();
            etDateStart.setText(dateFormat.format(datetimeStart));
            etTimeStart.setText(timeFormat.format(datetimeStart));
            Date datetimeEnd = sleepToModify.getEndTime();
            etDateEnd.setText(dateFormat.format(datetimeEnd));
            etTimeEnd.setText(timeFormat.format(datetimeEnd));
            etTitle.setText("Modification d'un dodo");
        } else {
            etTitle.setText("Ajout d'un dodo");
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
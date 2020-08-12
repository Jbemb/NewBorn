package com.example.newborn.activity.AddModify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.newborn.R;
import com.example.newborn.activity.SummaryDayActivity;
import com.example.newborn.activity.lists.adapters.ChangeAdapter;
import com.example.newborn.change.bo.Change;
import com.example.newborn.change.view_model.ChangeViewModel;
import com.facebook.stetho.Stetho;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddModifyChangeActivity extends AppCompatActivity {

    private EditText etDate;
    private EditText etTime;
    private CheckBox cbSelle;

    private Change changeToModify = null;
    private ChangeViewModel cvm = null;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private Date dateToSave;
    private int hours;
    private int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_change);
        Stetho.initializeWithDefaults(this);

       etDate = findViewById(R.id.et_date_add_change);
       etTime = findViewById(R.id.et_time_add_change);
       cbSelle = findViewById(R.id.cb_selle_add_change);

        cvm = new ViewModelProvider(this).get(ChangeViewModel.class);
        changeToModify = getIntent().getParcelableExtra("modifyChange");

        if (changeToModify != null) {
            Date datetimeChange = changeToModify.getChangeTime();
            etDate.setText(dateFormat.format(datetimeChange));
            etTime.setText(timeFormat.format(datetimeChange));
            cbSelle.setChecked(changeToModify.getPoop());
        } else {
            changeToModify = new Change();
        }

        //Set the calendar to enable the user to choose the day
        final ImageButton ibCalendar = findViewById(R.id.ib_calendar);
        ibCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(ibCalendar);
            }
        });

        //Set the clock to enable the user to choose the time
        final ImageButton ibClock = findViewById(R.id.ib_clock);
        ibClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(ibClock);
            }
        });

    }

    private void showDateDialog(ImageButton ibCalendar) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                SummaryDayActivity.resetToMidnight(cal);
                dateToSave = cal.getTime();

                etDate.setText(dateFormat.format(dateToSave));

//                Intent intentSummary = new Intent(SummaryDayActivity.this, SummaryDayActivity.class);
//                intentSummary.putExtra("dayStart", dayStart);
//                intentSummary.putExtra("dayEnd", dayEnd);
//
//                startActivity(intentSummary);
            }
        };
        new DatePickerDialog(AddModifyChangeActivity.this,dateSetListener,cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog(ImageButton ibClock) {
        final Calendar cal = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker TimePicker, int hour, int minute) {
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);

                etTime.setText(timeFormat.format(cal.getTime()));

//                Intent intentSummary = new Intent(SummaryDayActivity.this, SummaryDayActivity.class);
//                intentSummary.putExtra("dayStart", dayStart);
//                intentSummary.putExtra("dayEnd", dayEnd);
//
//                startActivity(intentSummary);
            }


        };
        new TimePickerDialog(AddModifyChangeActivity.this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),false).show();
    }

    public void onClickSave(View view) {

        try {
            //Get the input date from the input field (the time by default is the entry time)
            String dateToSaveStr = etDate.getText().toString();
            dateToSave = dateFormat.parse(dateToSaveStr);
            //Get the input time from the input field
            String timeToSaveStr = etTime.getText().toString();
            Date timeToSave = timeFormat.parse(timeToSaveStr);
            //Set the input time to the input date
            Calendar cal = Calendar.getInstance();
            cal.setTime(timeToSave);
            hours = cal.get(Calendar.HOUR_OF_DAY);
            minutes = cal.get(Calendar.MINUTE);
            cal.setTime(dateToSave);
            cal.set(Calendar.HOUR_OF_DAY, hours);
            cal.set(Calendar.MINUTE, minutes);
            dateToSave = cal.getTime();

            //Set the date and the time to the change to save
            changeToModify.setChangeTime(dateToSave);
            //Set the selle
            changeToModify.setPoop(cbSelle.isChecked());

            if (changeToModify.getId() == 0) {
                //TODO get the baby's name
                changeToModify = new Change("Zachary", dateToSave, cbSelle.isChecked());
                cvm.insertChange(changeToModify);
            } else {
                cvm.updateChange(changeToModify);
            }
            finish();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
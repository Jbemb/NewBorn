package com.example.newborn.activity.change;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.newborn.R;
import com.example.newborn.activity.global.SummaryDayActivity;
import com.example.newborn.model.change.Change;
import com.example.newborn.view_model.change.ChangeViewModel;
import com.facebook.stetho.Stetho;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created and implemented by Amandine
 * This class allows to add or modify a change
 */
public class AddModifyChangeActivity extends AppCompatActivity {
    //Variables related to the layout
    private EditText etDate;
    private EditText etTime;
    private CheckBox cbSelle;
    private TextView tvTitle;
    //Variables needed for the Object Change
    private Date dateToSave;
    private int hours;
    private int minutes;

    private Change changeToModify = null;
    private ChangeViewModel cvm = null;

    //Format to convert dates to strings
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_change);
        Stetho.initializeWithDefaults(this);

        //Initiate the layout variables
        etDate = findViewById(R.id.et_date_add_change);
        etTime = findViewById(R.id.et_time_add_change);
        cbSelle = findViewById(R.id.cb_selle_add_change);
        tvTitle = findViewById(R.id.et_title_add_change);

        cvm = new ViewModelProvider(this).get(ChangeViewModel.class);

        //Get the object Change sent from the list of meals by clicking on edit button
        changeToModify = getIntent().getParcelableExtra("modifyChange");

        //Check if there was an object sent from the list of changes
        if (changeToModify != null) {
            //Display temporal data of the object Change
            Date datetimeChange = changeToModify.getChangeTime();
            etDate.setText(dateFormat.format(datetimeChange));
            etTime.setText(timeFormat.format(datetimeChange));
            //Display if the change include selle or not
            cbSelle.setChecked(changeToModify.getPoop());

            tvTitle.setText("Modification d'un change");
        } else {
            tvTitle.setText("Ajout d'un change");
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

    /**
     * Function allowing to use a calendar the pick the date
     * The picked date has the time corresponding to the actual time
     * To allow the user to set the time one wants, the time of the picked
     * date is reset by the static function SummaryDayActivity.resetToMidnight
     * @param ibCalendar
     */
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

            }
        };
        new DatePickerDialog(AddModifyChangeActivity.this,dateSetListener,cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Function allowing to use a clock the pick the time
     * @param ibClock
     */
    private void showTimeDialog(ImageButton ibClock) {
        final Calendar cal = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker TimePicker, int hour, int minute) {
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);

                etTime.setText(timeFormat.format(cal.getTime()));

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

            //Update the object in the dbb if it exists or insert it in the dbb if not
            if (changeToModify.getId() == 0) {
                //TODO get the baby's name
                changeToModify = new Change("Zachary", dateToSave, cbSelle.isChecked());
                cvm.insertChange(changeToModify);
            } else {
                cvm.updateChange(changeToModify);
            }
            Toast.makeText(this, "Sauvegardé", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SummaryDayActivity.class);
            startActivity(intent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
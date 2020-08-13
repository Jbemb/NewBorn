package com.example.newborn.activity.meal;

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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.newborn.R;
import com.example.newborn.activity.global.SummaryDayActivity;
import com.example.newborn.model.meal.Meal;
import com.example.newborn.view_model.meal.MealViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created and implemented by Amandine
 * This class allows to add or modify a meal
 */
public class AddModifyMealActivity extends AppCompatActivity {
    //Variables related to the layout
    private EditText etDate;
    private EditText etTime;
    private EditText etQuantity;
    private RadioButton rbLeftBreast;
    private RadioButton rbRightBreast;
    private TextView tvTitle;
    //Variables needed for the Object Meal
    private String breast;
    private int quantity;
    private Date dateToSave;

    private Meal mealToModify = null;
    private MealViewModel mvm = null;

    //Format to convert dates to strings
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    //Variables needed to set the time in the Object Meal
    private int hours;
    private int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_meal);

        //Initiate the layout variables
        etDate = findViewById(R.id.et_date_add_meal);
        etTime = findViewById(R.id.et_time_add_meal);
        etQuantity = findViewById(R.id.et_quantity_add_meal);
        rbLeftBreast = findViewById(R.id.rb_left);
        rbRightBreast = findViewById(R.id.rb_right);
        tvTitle = findViewById(R.id.et_title_add_meal);

        mvm = new ViewModelProvider(this).get(MealViewModel.class);

        //Get the object Meal sent from the list of meals by clicking on edit button
        mealToModify = getIntent().getParcelableExtra("modifyMeal");

        //Check if there was an object sent from the list of meals
        if (mealToModify != null) {
            //Display temporal data of the object Meal
            Date datetimeMeal = mealToModify.getTime();
            etDate.setText(dateFormat.format(datetimeMeal));
            etTime.setText(timeFormat.format(datetimeMeal));
            etQuantity.setText(""+mealToModify.getQuantity());
            tvTitle.setText("Modification d'un repas");
            //Display breastfeeding data of the object Meal
            breast = mealToModify.getBreast();
            boolean isLeftBreast = false;
            boolean isRightBreast = false;
            if (breast == "left") {
                isLeftBreast = true;
            } else if (breast == "right"){
                isRightBreast = true;
            }
            rbLeftBreast.setChecked(isLeftBreast);
            rbRightBreast.setChecked(isRightBreast);

        } else {
            tvTitle.setText("Ajout d'un repas");
            mealToModify = new Meal();
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
        new DatePickerDialog(AddModifyMealActivity.this,dateSetListener,cal.get(Calendar.YEAR),
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
        new TimePickerDialog(AddModifyMealActivity.this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),
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

            //Set the date and the time to the meal to save
            mealToModify.setTime(dateToSave);
            //Set the quantity
            quantity = Integer.valueOf(etQuantity.getText().toString());
            mealToModify.setQuantity(quantity);
            //Set the breast
            if (rbRightBreast==null && rbLeftBreast==null) {
                breast = "";
            } else if (rbLeftBreast.isChecked()) {
                breast = "left";
            } else if (rbRightBreast.isChecked()){
                breast = "right";
            }
            mealToModify.setBreast(breast);

            //Update the object  in the dbb if it exists or insert it in the dbb if not
            if (mealToModify.getId() == 0) {
                //TODO get the baby's name
                mealToModify = new Meal("Zachary", dateToSave, quantity, breast);
                mvm.insertMeal(mealToModify);
            } else {
                mvm.updateMeal(mealToModify);
            }
            Toast.makeText(this, "Sauvegardé", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SummaryDayActivity.class);
            startActivity(intent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
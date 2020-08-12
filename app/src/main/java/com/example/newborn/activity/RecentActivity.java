package com.example.newborn.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.newborn.R;
import com.example.newborn.change.bo.Change;
import com.example.newborn.change.view_model.ChangeViewModel;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.view_model.MealViewModel;
import com.example.newborn.sleep.bo.Sleep;
import com.example.newborn.sleep.view_model.SleepViewModel;
import com.facebook.stetho.Stetho;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecentActivity extends AppCompatActivity {
    //baby name to change later and other "parameters"
    private String baby = "Zachary";
    private boolean isBreastFeeding = true;
    private boolean isBottle = true;
    //timers


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        Stetho.initializeWithDefaults(this);
        final TextView tvMealTime = findViewById(R.id.tv_meal_time);
        final TextView tvChangeTime = findViewById(R.id.tv_change_time);
        final TextView tvSleepCount = findViewById(R.id.tv_sleep_count);
        //set day
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final TextView tvToday = findViewById(R.id.tv_today);
        tvToday.setText(dateFormat.format(date));

        //Meal
        final TextView tvMealQuantity = findViewById(R.id.tv_recent_meal_quantity);
        final TextView tvMealBoob = findViewById(R.id.tv_recent_boob);
        final EditText etQuantity = findViewById(R.id.et_meal_quantity);
        final RadioGroup rgBreast = findViewById(R.id.rg_breast);
        final RadioButton rbLeft = findViewById(R.id.rb_left);
        final RadioButton rbright = findViewById(R.id.rb_right);
        final ImageButton btnStartMeal = findViewById(R.id.btn_new_change);

        //countdown timer meal
       /*
        btnStartMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });

        */

        MealViewModel mvm = new ViewModelProvider(this).get(MealViewModel.class);
        mvm.getLastMealByBaby(baby);
        mvm.getObserverLastMealByBaby().observe(this, new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {

                if(meal.getQuantity()!=0){
                    tvMealQuantity.setVisibility(View.VISIBLE);
                    tvMealQuantity.setText("quantité: " + meal.getQuantity() + "ml");
                }
                if(meal.getBreast()!=null){
                    tvMealBoob.setVisibility(View.VISIBLE);
                    if(meal.getBreast().equals("left")){
                        tvMealBoob.setText("gauche");
                    }else{
                        tvMealBoob.setText("droit");
                    }
                }
                //add new
                if(isBottle){
                    etQuantity.setVisibility(View.VISIBLE);
                    etQuantity.setText(String.valueOf(meal.getQuantity()));
                }
                //add breast
                boolean hasBreast = true;
                if(meal.getBreast()== null){
                    hasBreast = false;
                }
                if(isBreastFeeding & hasBreast){
                    rgBreast.setVisibility(View.VISIBLE);
                    if(meal.getBreast().equals("left")){
                        rbLeft.setChecked(true);
                    }else{
                        rbright.setChecked(false);
                    }
                }
            }
        });

        //set couche
        final TextView tvselle = findViewById(R.id.tv_selle);

        ChangeViewModel cvm = new ViewModelProvider(this).get(ChangeViewModel.class);
        cvm.getLastChangeByBaby(baby);
        cvm.getObserverLastChangeByBaby().observe(this, new Observer<Change>() {
            @Override
            public void onChanged(Change change) {
                String selle = "non";
                if(change.getPoop()){
                    selle= "oui";
                }
                tvselle.setText("selle: " + selle);
            }
        });

        //set dodo
        final TextView tvSleepTime = findViewById(R.id.tv_sleep_time);
        final TextView tvSleepDuration = findViewById(R.id.tv_sleep_duration);

        SleepViewModel svm = new ViewModelProvider(this).get(SleepViewModel.class);
        svm.getLastSleepByBaby(baby);
        svm.getObserverLastSleepByBaby().observe(this, new Observer<Sleep>() {
            @Override
            public void onChanged(Sleep sleep) {
                //sleep time
                Date dateTimeSleepEnd = sleep.getEndTime();
                Date now = new Date();
                long timeSinceMilliseconds =  now.getTime() - dateTimeSleepEnd.getTime();
                long timeSince = timeSinceMilliseconds/1000/60;
                if (timeSince < 60) {
                    tvSleepTime.setText("Dernier dodo: " + timeSince + " min");
                } else {
                    long hours = timeSince/60;
                    long minutes = timeSince%60;
                    tvSleepTime.setText("Dernier dodo: "+ hours + " h " + minutes);
                }
                //Calculation of the duration of sleep
                Date dateTimeSleepStart = sleep.getStartTime();
                long durationMilliseconds = dateTimeSleepEnd.getTime() - dateTimeSleepStart.getTime();
                long duration = durationMilliseconds/1000/60;
                if (duration < 60) {
                    tvSleepDuration.setText("durée: " + duration + " min");
                } else {
                    long hours = duration/60;
                    long minutes = duration%60;
                    tvSleepDuration.setText("durée: " + hours + " h " + minutes);
                }
                //sleep count

            }
        });
    }

    public void onClickAddMeal(View view) {
    }

    public void onClickAddChange(View view) {
    }

    public void checkButton(View view) {
    }

    public void onClickNewSleep(View view) {
    }
}
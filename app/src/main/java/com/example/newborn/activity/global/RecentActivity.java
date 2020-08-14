package com.example.newborn.activity.global;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newborn.R;
import com.example.newborn.model.change.Change;
import com.example.newborn.repository.change.ChangeDbRepository;
import com.example.newborn.repository.change.IChangeRepository;
import com.example.newborn.view_model.change.ChangeViewModel;
import com.example.newborn.model.meal.Meal;
import com.example.newborn.repository.meal.IMealRepository;
import com.example.newborn.repository.meal.MealDbRepository;
import com.example.newborn.view_model.meal.MealViewModel;
import com.example.newborn.model.sleep.Sleep;
import com.example.newborn.repository.sleep.ISleepRepository;
import com.example.newborn.repository.sleep.SleepDbRepository;
import com.example.newborn.view_model.sleep.SleepViewModel;
import com.facebook.stetho.Stetho;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class RecentActivity extends AppCompatActivity {
    //baby name to change later and other "parameters"
    private String baby = "Zachary";
    private boolean isBreastFeeding = true;
    private String test = null;
    private boolean isBottle = true;
    //timer sleep
    private Chronometer sleepChrono;
    private boolean isSleeping = false;
    private Date newSleepStart;
    private long newSleepDuration;
    SharedPreferences preferences;
    //info for meal and radio button
    private RadioGroup rgBreast;
    private RadioButton rbBreast;
    //Repos
    private IMealRepository mealRepo;
    private IChangeRepository changeRepo;
    private ISleepRepository sleepRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        Stetho.initializeWithDefaults(this);

        //set babyName
        final TextView tvBabyName = findViewById(R.id.tv_baby);
        tvBabyName.setText("Zachary");

        //initialize Repos
        mealRepo = new MealDbRepository(this);
        changeRepo = new ChangeDbRepository(this);
        sleepRepo = new SleepDbRepository(this);
        //set day
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final TextView tvToday = findViewById(R.id.tv_today);
        tvToday.setText(dateFormat.format(date));
        //Meal
        final TextView tvMealTime = findViewById(R.id.tv_meal_time);
        final TextView tvMealQuantity = findViewById(R.id.tv_recent_meal_quantity);
        final TextView tvMealBoob = findViewById(R.id.tv_recent_boob);
        final EditText etQuantity = findViewById(R.id.et_meal_quantity);
        rgBreast = findViewById(R.id.rg_breast);
        final RadioButton rbLeft = findViewById(R.id.rb_left);
        final RadioButton rbright = findViewById(R.id.rb_right);
        //final ImageButton btnStartMeal = findViewById(R.id.btn_new_change);

        preferences = getSharedPreferences("chrono", MODE_PRIVATE);

        MealViewModel mvm = new ViewModelProvider(this).get(MealViewModel.class);
        mvm.getLastMealByBaby(baby);
        mvm.getObserverLastMealByBaby().observe(this, new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                //meal timer
                Date dateTimeMeal = meal.getTime();
                Date now = new Date();
                long timeSinceMilliseconds =  now.getTime() - dateTimeMeal.getTime();
                long timeSince = timeSinceMilliseconds/1000/60;
                if (timeSince < 60) {

                    tvMealTime.setText(timeSince +"min");
                } else {
                    long hours = timeSince/60;
                    long minutes = timeSince%60;
                    tvMealTime.setText( hours + "h " + minutes);
                }

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
        final TextView tvChangeTime = findViewById(R.id.tv_change_time);
        final TextView tvselle = findViewById(R.id.tv_selle);

        ChangeViewModel cvm = new ViewModelProvider(this).get(ChangeViewModel.class);
        cvm.getLastChangeByBaby(baby);
        cvm.getObserverLastChangeByBaby().observe(this, new Observer<Change>() {
            @Override
            public void onChanged(Change change) {
                //set time
                Date dateTimeChange = change.getChangeTime();
                Date now = new Date();
                long timeSinceMilliseconds =  now.getTime() - dateTimeChange.getTime();
                long timeSince = timeSinceMilliseconds/1000/60;
                if (timeSince < 60) {
                    tvChangeTime.setText(timeSince +"min");
                } else {
                    long hours = timeSince/60;
                    long minutes = timeSince%60;
                    tvChangeTime.setText(hours + "h " + minutes);
                }
                //set selle
                String selle = "non";
                if(change.getPoop()){
                    selle= "oui";
                }
                tvselle.setText("selle: " + selle);
            }
        });
        //set dodo
        sleepChrono = findViewById(R.id.chrono_sleep_count);
        final TextView tvSleepTime = findViewById(R.id.tv_sleep_time);
        final TextView tvSleepDuration = findViewById(R.id.tv_sleep_duration);
        final ImageButton btnStartSleep = findViewById(R.id.btn_start_sleep);
        final ImageButton btnStopSleep = findViewById(R.id.btn_stop_sleep);

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
                    tvSleepTime.setText(timeSince +"min");
                } else {
                    long hours = timeSince/60;
                    long minutes = timeSince%60;
                    tvSleepTime.setText(hours + "h " + minutes);
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
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        isSleeping = preferences.getBoolean("sleeping", false);
        if (isSleeping) {
            long startMillis = preferences.getLong("startTime", System.currentTimeMillis());
            //reset newSleepStart
            Date date = new Date(startMillis);
            newSleepStart = date;
            //calculate
            //now - start = temps ecoulé
            Date now = new Date();
            Long timeSince = now.getTime() - startMillis;
            //reset - start timer
            sleepChrono.setBase(SystemClock.elapsedRealtime() - timeSince);
            sleepChrono.start();
        }
    }

    public void onClickAddMeal(View view) {
        Date now = new Date();
        Meal newMeal = new Meal(baby, now);
        //check for breast
        int radioId = rgBreast.getCheckedRadioButtonId();
        if(radioId != -1){
            rbBreast = findViewById(radioId);
            String breast = (String) rbBreast.getText();
            if(breast.equals("Sein G")){
                breast = "left";
            }else{
                breast = "right";
            }
            newMeal.setBreast(breast);
        }
        EditText etQuantity = findViewById(R.id.et_meal_quantity);
        int quantity = Integer.valueOf(etQuantity.getText().toString());
        newMeal.setQuantity(quantity);
        mealRepo.insertMeal(newMeal);
        Toast.makeText(this, "ajouté", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, RecentActivity.class);
        this.startActivity(intent);
    }

    public void onClickAddChange(View view) {
        Change newChange = new Change();
        Date date = new Date();
        CheckBox checkBox = (CheckBox) findViewById(R.id.cb_selle);
        newChange.setBaby(baby);
        newChange.setPoop(checkBox.isChecked());
        newChange.setChangeTime(date);
        //insert
        changeRepo.insertChange(newChange);
        Toast.makeText(this, "ajouté", Toast.LENGTH_LONG).show();
        checkBox.setChecked(false);
        Intent intent = new Intent(this, RecentActivity.class);
        this.startActivity(intent);
    }

    public void startSleepTimer(View view) {
        if(!isSleeping){
            sleepChrono.setBase(SystemClock.elapsedRealtime());
            sleepChrono.start();
            isSleeping = true;
            newSleepStart= new Date();
            long startMillis = newSleepStart.getTime();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong("startTime", startMillis);
            editor.putBoolean("sleeping", isSleeping);
            editor.apply();
        }
    }

    public void stopSleepTimer(View view) {
        if(isSleeping){
            sleepChrono.stop();
            newSleepDuration = SystemClock.elapsedRealtime() - sleepChrono.getBase();
            isSleeping = false;
            //reset chrono to 0
            sleepChrono.setBase(SystemClock.elapsedRealtime());
            //store sleep
            //calculate the sleep end time
            long endTimeMilliseconds = newSleepStart.getTime() + newSleepDuration;
            Date endDate = new Date(endTimeMilliseconds);
            Sleep newSleep = new Sleep(baby, newSleepStart, endDate);
            sleepRepo.insertSleep(newSleep);
            Toast.makeText(this, "ajouté" , Toast.LENGTH_LONG).show();
            preferences.edit().putBoolean("sleeping",isSleeping)
                    .putString("startTime","0").apply();
            Intent intent = new Intent(this, RecentActivity.class);
            this.startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    //TODO links of the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_accueil:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_activites_recentes:
                intent = new Intent(this, RecentActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_bilan:
                intent = new Intent(this, SummaryDayActivity.class);
                startActivity(intent);
                return true;
//            case R.id.action_parametres:
//                intent = new Intent(this, ParameterActivity.class);
//                startActivity(intent);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
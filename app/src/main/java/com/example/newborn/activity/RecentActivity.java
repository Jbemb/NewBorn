package com.example.newborn.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.newborn.R;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.view_model.MealViewModel;
import com.facebook.stetho.Stetho;

import java.util.List;

public class RecentActivity extends AppCompatActivity {
    //baby name to change later and other "parameters"
    private String baby = "Zachary";
    private boolean isBreastFeeding = true;
    private boolean isBottle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        Stetho.initializeWithDefaults(this);

        //Meal
        final TextView tvMealTime = findViewById(R.id.tv_meal_time);
        final TextView tvMealQuantity = findViewById(R.id.tv_recent_meal_quantity);
        final TextView tvMealBoob = findViewById(R.id.tv_recent_boob);
        final EditText etQuantity = findViewById(R.id.et_meal_quantity);
        final RadioGroup rgBreast = findViewById(R.id.rg_breast);
        final RadioButton rbLeft = findViewById(R.id.rb_left);
        final RadioButton rbright = findViewById(R.id.rb_right);

        MealViewModel mvm = new ViewModelProvider(this).get(MealViewModel.class);
        mvm.getLastMealByBaby(baby);
        mvm.getObserverLastMealByBaby().observe(this, new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                //calculate time remaining and set in string TODO
                tvMealTime.setText("Derniere repas: variable h et variable mins");
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
                    etQuantity.setText(meal.getQuantity());
                }
                //add breast
                if(isBreastFeeding){
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

        //set dodo

    }

    public void onClickAddMeal(View view) {
    }

    public void onClickAddChange(View view) {
    }

    public void checkButton(View view) {
    }

    public void onClickEndSleep(View view) {
    }

    public void onClickStartSleep(View view) {
    }
}
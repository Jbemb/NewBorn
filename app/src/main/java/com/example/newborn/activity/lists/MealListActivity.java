package com.example.newborn.activity.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.newborn.R;
import com.example.newborn.activity.AddModify.AddModifyMealActivity;
import com.example.newborn.activity.SummaryDayActivity;
import com.example.newborn.activity.lists.adapters.ChangeAdapter;
import com.example.newborn.activity.lists.adapters.MealAdapter;
import com.example.newborn.change.bo.Change;
import com.example.newborn.change.view_model.ChangeViewModel;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.view_model.MealViewModel;
import com.facebook.stetho.Stetho;

import java.util.Date;
import java.util.List;

public class MealListActivity extends AppCompatActivity {

    private MealViewModel mealVM = null;
    ListView mealList =null;
    List<Meal> meals = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        Stetho.initializeWithDefaults(this);

        mealList = findViewById(R.id.lv_meallist);

        Intent intent = getIntent();

        Date dayStart = (Date)intent.getSerializableExtra("dayStart");
        Date dayEnd = (Date)intent.getSerializableExtra("dayEnd");


        MealViewModel cvm = new ViewModelProvider(this).get(MealViewModel.class);
        cvm.getMealByBabyByDate("Zachary", dayStart, dayEnd);//TODO mettre les dates Passer les dates en intent qu'on peut récupérer dans le list activity
        cvm.getObserverMealByBabyByDate().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                MealListActivity.this.meals = meals;
                mealList.setAdapter(new MealAdapter(MealListActivity.this,R.layout.style_list_meal,meals));    }
        });
    }

    public void OnClickAdd(View view) {
        Intent intent = new Intent(this, AddModifyMealActivity.class);
        startActivity(intent);
    }
    //TODO add back function
    public void OnClickBack(View view) {
        Intent intent = new Intent(this, SummaryDayActivity.class);
        startActivity(intent);
    }
}
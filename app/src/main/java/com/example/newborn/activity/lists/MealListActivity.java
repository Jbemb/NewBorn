package com.example.newborn.activity.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.newborn.R;
import com.example.newborn.activity.AddModify.AddModifyMealActivity;
import com.example.newborn.activity.lists.adapters.ChangeAdapter;
import com.example.newborn.activity.lists.adapters.MealAdapter;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.view_model.MealViewModel;

import java.util.List;

public class MealListActivity extends AppCompatActivity {

    private MealViewModel mealVM = null;
    ListView mealList =null;
    List<Meal> meals = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        mealList = findViewById(R.id.lv_meallist);
        mealVM = ViewModelProviders.of(this).get(MealViewModel.class);
        mealVM.getObserverMealByBaby().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                MealListActivity.this.meals = meals;
                mealList.setAdapter(new MealAdapter(MealListActivity.this,R.layout.style_list_meal,meals));    }
        });
    }

    public void OnClickAddMeal(View view) {
        Intent intent = new Intent(this, AddModifyMealActivity.class);
        startActivity(intent);
    }
    //TODO add back function
    public void OnClickBack(View view) {
    }
}
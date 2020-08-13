package com.example.newborn.activity.meal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.newborn.R;
import com.example.newborn.activity.global.MainActivity;
import com.example.newborn.activity.global.RecentActivity;
import com.example.newborn.activity.global.SummaryDayActivity;
import com.example.newborn.model.meal.Meal;
import com.example.newborn.view_model.meal.MealViewModel;
import com.facebook.stetho.Stetho;

import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Janet
 *
 */
public class MealListActivity extends AppCompatActivity {

    private MealViewModel mealVM = null;
    ListView mealList =null;
    List<Meal> meals = null;
    Date dayStart = null;
    Date dayEnd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meal);
        Stetho.initializeWithDefaults(this);

        //set babyName
        final TextView tvBabyName = findViewById(R.id.tv_baby);
        tvBabyName.setText("Zachary");

        mealList = findViewById(R.id.lv_meallist);

        Intent intent = getIntent();

        dayStart = (Date)intent.getSerializableExtra("dayStart");
        dayEnd = (Date)intent.getSerializableExtra("dayEnd");


        MealViewModel cvm = new ViewModelProvider(this).get(MealViewModel.class);
        cvm.getMealByBabyByDate("Zachary", dayStart, dayEnd);
        //TODO mettre les dates Passer les dates en intent qu'on peut récupérer dans le list activity
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

    public void OnClickBack(View view) {
        Intent intent = new Intent(this, SummaryDayActivity.class);
        intent.putExtra("dayStart", dayStart);
        intent.putExtra("dayEnd", dayEnd);
        startActivity(intent);
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
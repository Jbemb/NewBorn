package com.example.newborn.activity.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.newborn.R;
import com.example.newborn.activity.MainActivity;
import com.example.newborn.activity.lists.adapters.SleepAdapter;
import com.example.newborn.sleep.bo.Sleep;
import com.example.newborn.sleep.view_model.SleepViewModel;

import java.util.List;

public class SleepListActivity extends AppCompatActivity {

    private SleepViewModel sleepVM = null;
    ListView sleepList =null;
    List<Sleep> sleeps = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_list);

        sleepList = findViewById(R.id.lv_sleeplist);

        sleepVM = ViewModelProviders.of(this).get(SleepViewModel.class);

        sleepVM.getObserverSleepByBaby().observe(this, new Observer<List<Sleep>>() {
            @Override
            public void onChanged(List<Sleep> sleeps) {
                SleepListActivity.this.sleeps = sleeps;
                sleepList.setAdapter(new SleepAdapter(SleepListActivity.this,R.layout.style_list_sleep,sleeps));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    //TODO links of the menu
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent;
//        switch (item.getItemId()) {
//            case R.id.action_accueil:
//                intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.action_activites_recentes:
//                intent = new Intent(this, RecentActivitiesActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.action_bilan:
//                intent = new Intent(this, SummaryActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.action_parametres:
//                intent = new Intent(this, ParameterActivity.class);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
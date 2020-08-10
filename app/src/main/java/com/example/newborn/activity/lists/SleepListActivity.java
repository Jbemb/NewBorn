package com.example.newborn.activity.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.ListView;

import com.example.newborn.R;
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



}
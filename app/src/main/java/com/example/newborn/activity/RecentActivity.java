package com.example.newborn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.newborn.R;
import com.facebook.stetho.Stetho;

public class RecentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        Stetho.initializeWithDefaults(this);
    }

    public void onClickAddMeal(View view) {
    }

    public void onClickAddChange(View view) {
    }

    public void onClickAddSleep(View view) {
    }
}
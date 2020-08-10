package com.example.newborn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.newborn.R;
import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
    }
}
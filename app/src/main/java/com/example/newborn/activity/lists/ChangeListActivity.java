package com.example.newborn.activity.lists;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.newborn.R;
import com.facebook.stetho.Stetho;

public class ChangeListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_list);
        Stetho.initializeWithDefaults(this);
    }

    public void OnClickAddChange(View view) {
    }

    public void OnClickBack(View view) {
    }
}
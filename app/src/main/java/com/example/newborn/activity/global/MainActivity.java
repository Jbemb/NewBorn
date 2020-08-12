package com.example.newborn.activity.global;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.newborn.R;
import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
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
package com.example.newborn.activity.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.newborn.R;
import com.example.newborn.activity.SummaryDayActivity;
import com.example.newborn.activity.lists.adapters.ChangeAdapter;
import com.example.newborn.activity.AddModify.AddModifyChangeActivity;
import com.example.newborn.activity.lists.adapters.MealAdapter;
import com.example.newborn.change.bo.Change;
import com.example.newborn.change.repository.ChangeDbRepository;
import com.example.newborn.change.repository.IChangeRepository;
import com.example.newborn.change.view_model.ChangeViewModel;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.view_model.MealViewModel;

import java.util.Date;
import java.util.List;

public class ChangeListActivity extends AppCompatActivity {


    private ChangeViewModel changeVM = null;
    ListView changeList =null;
    List<Change> changes = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_list);
        changeList = findViewById(R.id.lv_changeList);

        Intent intent = getIntent();
        Date dayStart = (Date)intent.getSerializableExtra("dayStart");
        Date dayEnd = (Date)intent.getSerializableExtra("dayEnd");


        ChangeViewModel cvm = new ViewModelProvider(this).get(ChangeViewModel.class);
        cvm.getChangeByBabyByDate("Zachary", dayStart, dayEnd);//TODO mettre les dates Passer les dates en intent qu'on peut récupérer dans le list activity
        cvm.getObserverChangeByBabyByDate().observe(this, new Observer<List<Change>>() {
            @Override
            public void onChanged(List<Change> changes) {
                ChangeListActivity.this.changes = changes;
                changeList.setAdapter(new ChangeAdapter(ChangeListActivity.this,R.layout.style_list_change,changes));    }
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

    //TODO add intent to add activity
    public void OnClickAddChange(View view) {
        Intent intent = new Intent(this, AddModifyChangeActivity.class);
        startActivity(intent);
    }
    //TODO add intent to go back
    public void OnClickBack(View view) {
        Intent intent = new Intent(this, SummaryDayActivity.class);
        startActivity(intent);
    }
}
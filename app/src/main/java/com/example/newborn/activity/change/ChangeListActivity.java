package com.example.newborn.activity.change;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.newborn.R;
import com.example.newborn.activity.global.MainActivity;
import com.example.newborn.activity.global.RecentActivity;
import com.example.newborn.activity.global.SummaryDayActivity;
import com.example.newborn.model.change.Change;
import com.example.newborn.view_model.change.ChangeViewModel;

import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Janet
 *
 */
public class ChangeListActivity extends AppCompatActivity {

    private ChangeViewModel changeVM = null;
    ListView changeList =null;
    List<Change> changes = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_change);
        changeList = findViewById(R.id.lv_changeList);

        Intent intent = getIntent();
        Date dayStart = (Date)intent.getSerializableExtra("dayStart");
        Date dayEnd = (Date)intent.getSerializableExtra("dayEnd");


        ChangeViewModel cvm = new ViewModelProvider(this).get(ChangeViewModel.class);
        cvm.getChangeByBabyByDate("Zachary", dayStart, dayEnd);
        //TODO mettre les dates Passer les dates en intent qu'on peut récupérer dans le list activity
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


    public void OnClickAdd(View view) {
        Intent intent = new Intent(this, AddModifyChangeActivity.class);
        startActivity(intent);
    }

    public void OnClickBack(View view) {
        Intent intent = new Intent(this, SummaryDayActivity.class);
        startActivity(intent);
    }

}
package com.example.newborn.activity.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.newborn.R;
import com.example.newborn.activity.lists.adapters.ChangeAdapter;
import com.example.newborn.change.bo.Change;
import com.example.newborn.change.view_model.ChangeViewModel;

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
        changeVM = ViewModelProviders.of(this).get(ChangeViewModel.class);
        changeVM.getObserverChangeByBaby().observe(this, new Observer<List<Change>>() {
            @Override
            public void onChanged(List<Change> changes) {
            ChangeListActivity.this.changes = changes;
            changeList.setAdapter(new ChangeAdapter(ChangeListActivity.this,R.layout.style_list_change,changes));
            }
        });
    }

    public void OnClickAddChange(View view) {
    }

    public void OnClickBack(View view) {
    }
}
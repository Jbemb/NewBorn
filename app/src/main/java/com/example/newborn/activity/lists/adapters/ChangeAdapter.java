package com.example.newborn.activity.lists.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newborn.R;
import com.example.newborn.activity.AddModify.AddModifyChangeActivity;
import com.example.newborn.activity.AddModify.AddModifyMealActivity;
import com.example.newborn.activity.lists.ChangeListActivity;
import com.example.newborn.activity.lists.MealListActivity;
import com.example.newborn.change.bo.Change;
import com.example.newborn.change.repository.ChangeDbRepository;
import com.example.newborn.change.repository.IChangeRepository;
import com.example.newborn.meal.bo.Meal;
import com.example.newborn.meal.repository.IMealRepository;
import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ChangeAdapter extends ArrayAdapter<Change> {
    private IChangeRepository changeRepo = null;

    public ChangeAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        Stetho.initializeWithDefaults(context);
        changeRepo = new ChangeDbRepository(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.style_list_change, parent, false);
        }

        TextView tvDate = convertView.findViewById(R.id.tv_date);
        TextView tvTime = convertView.findViewById(R.id.tv_time);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_selle);

        Change change = getItem(position);

        Date dateTime = change.getChangeTime();
        //set date
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(dateTime);
        tvDate.setText(dateString);
        //set time
        String timeString = new SimpleDateFormat("HH:mm").format(dateTime);
        tvTime.setText(timeString);
        //set selle
        if (change.getPoop()){
            imageView.setVisibility(View.VISIBLE);
        }

        ImageButton delete = convertView.findViewById(R.id.ib_deleteChange);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Change change = getItem(position);
                changeRepo.deleteChange(change);
                Toast.makeText(getContext(), "Suprimé", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), ChangeListActivity.class);
                getContext().startActivity(intent);
            }
        });

        ImageButton modify = convertView.findViewById(R.id.ib_modifyChange);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Change change = getItem(position);
                Intent intent = new Intent(getContext(), AddModifyChangeActivity.class);
                intent.putExtra("modifyChange", change);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}

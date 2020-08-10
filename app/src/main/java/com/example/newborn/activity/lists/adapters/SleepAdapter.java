package com.example.newborn.activity.lists.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newborn.R;
import com.example.newborn.sleep.bo.Sleep;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class SleepAdapter extends ArrayAdapter<Sleep> {
    public SleepAdapter(@NonNull Context context, int resource, @NonNull List<Sleep> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.style_list_sleep, parent,false);
        }
        //Get the sleep item at the line position
        Sleep sleep = getItem(position);

        Date dateTimeSleepStart = sleep.getStartTime();

        //Set the date to be showed
        TextView tvDate = convertView.findViewById(R.id.tv_dateItem);
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(dateTimeSleepStart);
        tvDate.setText(dateString);

        //Set the time to be showed
        TextView tvTime = convertView.findViewById(R.id.tv_timeItem);
        String timeString = new SimpleDateFormat("HH:mm").format(dateTimeSleepStart);
        tvTime.setText(timeString);

        //Calculation of the duration of sleep
        Date dateTimeSleepEnd = sleep.getEndTime();
        long durationMilliseconds = dateTimeSleepEnd.getTime() - dateTimeSleepStart.getTime();
        long duration = durationMilliseconds/1000/60;

        TextView tvDuration = convertView.findViewById(R.id.tv_durationItem);

        if (duration < 60) {
            tvDuration.setText(duration + " min");
        } else {
            long hours = duration/60;
            long minutes = duration%60;
            tvDuration.setText(hours + " h " + minutes);
        }
        
        return convertView;
    }
}

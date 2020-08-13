package com.example.newborn.activity.sleep;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newborn.R;
import com.example.newborn.activity.change.AddModifyChangeActivity;
import com.example.newborn.activity.global.SummaryDayActivity;
import com.example.newborn.activity.meal.MealListActivity;
import com.example.newborn.activity.sleep.SleepListActivity;
import com.example.newborn.model.sleep.Sleep;
import com.example.newborn.repository.sleep.ISleepRepository;
import com.example.newborn.repository.sleep.SleepDbRepository;
import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Amandine
 * This class controls the list to be displayed
 */
public class SleepAdapter extends ArrayAdapter<Sleep> {
    private ISleepRepository sleepRepo = null;
    public SleepAdapter(@NonNull Context context, int resource, @NonNull List<Sleep> objects) {
        super(context, resource, objects);
        Stetho.initializeWithDefaults(context);
        sleepRepo = new SleepDbRepository(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.style_list_sleep, parent,false);
        }
        //Get the sleep item at the line position
        final Sleep sleep = getItem(position);

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

        //Set the way the duration is displayed depending on its value
        if (duration < 60) {
            tvDuration.setText(duration + " min");
        } else {
            long hours = duration/60;
            long minutes = duration%60;
            tvDuration.setText(hours + " h " + minutes);
        }

        //Action of the delete button
        ImageButton delete = convertView.findViewById(R.id.ib_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sleep sleep = getItem(position);
                sleepRepo.deleteSleep(sleep);

                Date dayStart = sleep.getStartTime();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dayStart);
                SummaryDayActivity.resetToMidnight(cal);
                dayStart=cal.getTime();
                Date dayEnd = null;
                dayEnd = SummaryDayActivity.setDayEnd(cal);
                Toast.makeText(getContext(), "Supprimé", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getContext(), SleepListActivity.class);
                intent.putExtra("dayStart", dayStart);
                intent.putExtra("dayEnd", dayEnd);
                getContext().startActivity(intent);
            }
        });

        //Action of the edit button
        ImageButton modify = convertView.findViewById(R.id.ib_modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sleep sleep = getItem(position);
                Intent intent = new Intent(getContext(), AddModifyChangeActivity.class);
                intent.putExtra("modifySleep", sleep);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}

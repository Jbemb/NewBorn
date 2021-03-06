package com.example.newborn.activity.change;

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
import com.example.newborn.activity.change.AddModifyChangeActivity;
import com.example.newborn.activity.change.ChangeListActivity;
import com.example.newborn.activity.global.SummaryDayActivity;
import com.example.newborn.model.change.Change;
import com.example.newborn.repository.change.ChangeDbRepository;
import com.example.newborn.repository.change.IChangeRepository;
import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Janet
 * This class controls the list to be displayed
 */
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

        TextView tvDate = convertView.findViewById(R.id.tv_dateItem);
        TextView tvTime = convertView.findViewById(R.id.tv_timeItem);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_selle);

        Change change = getItem(position);

        Date dateTime = change.getChangeTime();
        //set date
        final String dateString = new SimpleDateFormat("dd/MM/yyyy").format(dateTime);
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
                Toast.makeText(getContext(), "Supprimé", Toast.LENGTH_LONG).show();

                Date dayStart = change.getChangeTime();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dayStart);
                SummaryDayActivity.resetToMidnight(cal);
                dayStart=cal.getTime();
                Date dayEnd = null;
                dayEnd = SummaryDayActivity.setDayEnd(cal);

                Intent intent = new Intent(getContext(), ChangeListActivity.class);
                intent.putExtra("dayStart", dayStart);
                intent.putExtra("dayEnd", dayEnd);
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

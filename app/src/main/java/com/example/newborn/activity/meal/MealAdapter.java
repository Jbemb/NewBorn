package com.example.newborn.activity.meal;

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

import com.example.newborn.activity.global.SummaryDayActivity;
import com.example.newborn.activity.meal.AddModifyMealActivity;
import com.example.newborn.activity.meal.MealListActivity;
import com.example.newborn.model.meal.Meal;
import com.example.newborn.repository.meal.IMealRepository;
import com.example.newborn.repository.meal.MealDbRepository;
import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created and implemented by Janet
 * This class controls the list to be displayed
 */
public class MealAdapter extends ArrayAdapter<Meal> {

    private IMealRepository mealRepo = null;

    public MealAdapter(@NonNull Context context, int resource, @NonNull List<Meal> objects) {
        super(context, resource, objects);
        Stetho.initializeWithDefaults(context);
        mealRepo = new MealDbRepository(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.style_list_meal, parent, false);
        }

        TextView tvDate = convertView.findViewById(R.id.tv_dateItem);
        TextView tvTime = convertView.findViewById(R.id.tv_timeItem);
        TextView tvQuantity = convertView.findViewById(R.id.tv_quantityItem);
        TextView tvBreast = convertView.findViewById(R.id.tv_breastItem);
        Meal meal = getItem(position);

        Date dateTime = meal.getTime();
        //set date
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(dateTime);
        tvDate.setText(dateString);
        //set time
        String timeString = new SimpleDateFormat("HH:mm").format(dateTime);
        tvTime.setText(timeString);
        //set quantity
        if (meal.getQuantity() != 0){
            tvQuantity.setVisibility(View.VISIBLE);
            tvQuantity.setText(meal.getQuantity() + " ml");
        }
        //set boob
        if(meal.getBreast() != null){
            tvBreast.setVisibility(View.VISIBLE);
            if(meal.getBreast().equals("left")){
                tvBreast.setText("gauche");
            }else{
                tvBreast.setText("droit");
            }
        }

        ImageButton delete = convertView.findViewById(R.id.ib_deleteMeal);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meal meal = getItem(position);
                mealRepo.deleteMeal(meal);

                Date dayStart = meal.getTime();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dayStart);
                SummaryDayActivity.resetToMidnight(cal);
                dayStart=cal.getTime();
                Date dayEnd = null;
                dayEnd = SummaryDayActivity.setDayEnd(cal);
                Toast.makeText(getContext(), "Supprimé", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getContext(), MealListActivity.class);
                intent.putExtra("dayStart", dayStart);
                intent.putExtra("dayEnd", dayEnd);
                getContext().startActivity(intent);
            }
        });

        ImageButton modify = convertView.findViewById(R.id.ib_modifyMeal);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meal meal = getItem(position);
                Intent intent = new Intent(getContext(), AddModifyMealActivity.class);
                intent.putExtra("modifyMeal", meal);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}

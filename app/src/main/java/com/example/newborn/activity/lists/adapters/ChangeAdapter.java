package com.example.newborn.activity.lists.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newborn.R;
import com.example.newborn.change.bo.Change;

import java.util.List;

public class ChangeAdapter extends ArrayAdapter<Change> {
    public ChangeAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.style_list_change, parent, false);
        }
        //TODO add info by line
        TextView tvDate = convertView.findViewById(R.id.tv_date);
        TextView tvTime = convertView.findViewById(R.id.tv_time);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_selle);

        Change change = getItem(position);

        
        if (change.getPoop()){
            imageView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}

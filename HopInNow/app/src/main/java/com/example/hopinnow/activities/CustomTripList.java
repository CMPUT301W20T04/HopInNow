package com.example.hopinnow.activities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Trip;

import java.util.ArrayList;

/**
 * Author: Qianxi Li
 * Version: 1.0.0
 * customize the trip history list
 */
public class CustomTripList extends ArrayAdapter<Trip> {
    private ArrayList<Trip> trips; // store all the trips in an array list
    private Context context;

    /**
     * constructor for CustomTripList
     * @param context
     * @param trips
     */
    public CustomTripList(Context context, ArrayList<Trip> trips){
        super(context,0,trips);
        this.trips = trips;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_trip_list_each_record,parent,false);

        }
        // get the single trip from the trip data list and set texts for single view.
        Trip trip = trips.get(position);
        TextView fromText = view.findViewById(R.id.fromText);
        TextView toText = view.findViewById(R.id.ToText);
        TextView dateText = view.findViewById(R.id.DateText);
        fromText.setText(String.format(trip.getPickUpLocName()));
        toText.setText(String.format(trip.getDropOffLocName()));
        dateText.setText(trip.getPickUpDateTime().toString());
        return view;
    }
}

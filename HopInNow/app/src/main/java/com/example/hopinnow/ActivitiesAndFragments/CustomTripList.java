package com.example.hopinnow.ActivitiesAndFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Trip;

import java.util.ArrayList;

public class CustomTripList extends ArrayAdapter<Trip> {
    private ArrayList<Trip> trips;
    private Context context;
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
        Trip trip = trips.get(position);
        TextView fromText = view.findViewById(R.id.fromText);
        TextView toText = view.findViewById(R.id.ToText);
        TextView dateText = view.findViewById(R.id.DateText);
        fromText.setText("from: "+trip.getPickUpLoc().toString());
        toText.setText("to: "+trip.getDropOffLoc().toString());
        dateText.setText("date: "+trip.getPickUpDateTime().toString());
        return view;
    }
}

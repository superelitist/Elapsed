package com.example.lbodnyk.elapsed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ElapsedTimeObjectArrayAdapter extends ArrayAdapter<MyElapsedTimeObject> {

    ElapsedTimeObjectArrayAdapter(Context context, ArrayList<MyElapsedTimeObject> elapsedTimeObjects) {
        super(context, 0, elapsedTimeObjects);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        // Get the data item for this position
        final MyElapsedTimeObject anElapsedTimeObject = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView elapsedtimelistitemtitle = (TextView) convertView.findViewById(R.id.elapsedtimelistitemtitle);
        TextView elapsedtimelistitemelapsedtime = (TextView) convertView.findViewById(R.id.elapsedtimelistitemelapsedtime);
        // Populate the data into the template view using the data object
        if (anElapsedTimeObject.getIsNew()) {
            elapsedtimelistitemtitle.setText(anElapsedTimeObject.getTitle()); // if this is the first time we're interacting with this object, populate the associated EditText with the object title, instead of vice-versa
            anElapsedTimeObject.setIsNew(false);
        } else{
            anElapsedTimeObject.setTitle(elapsedtimelistitemtitle.getText().toString()); // otherwise, the other way around, obviously!
        }
        elapsedtimelistitemelapsedtime.setText(anElapsedTimeObject.getElapsedTime());
        // hopefully I can set a listener for each object here
        elapsedtimelistitemelapsedtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anElapsedTimeObject.updateTime();
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
package com.example.lbodnyk.elapsed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lbodnyk on 3/31/2017.
 */

public class ElapsedTimeObjectArrayAdapter extends ArrayAdapter<MainActivity.MyElapsedTimeObject> {
    public ElapsedTimeObjectArrayAdapter(Context context, ArrayList<MainActivity.MyElapsedTimeObject> elapsedTimeObjects) {
        super(context, 0, elapsedTimeObjects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MainActivity.MyElapsedTimeObject anElapsedTimeObject = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView elapsedtimelistitemtitle = (TextView) convertView.findViewById(R.id.elapsedtimelistitemtitle);
        TextView elapsedtimelistitemelapsedtime = (TextView) convertView.findViewById(R.id.elapsedtimelistitemelapsedtime);
        // Populate the data into the template view using the data object
        elapsedtimelistitemtitle.setText(anElapsedTimeObject.name);
        elapsedtimelistitemelapsedtime.setText(anElapsedTimeObject.hometown);
        // Return the completed view to render on screen
        return convertView;
    }
}



/*
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.list_item,parent,false);
        TextView myTitle = (TextView) row.findViewById(R.id.mytitle);
        TextView myDescription = (TextView) row.findViewById(R.id.text2);
        //TextView textElapsed = (TextView) row.findViewById(R.id.textElapsed);
        myTitle.setText(titles[position]);
        myDescription.setText(Integer.toString(myLoops));
        Log.d(ADAPTER, "calculatedElapsedTime: " + adapterCalculatedElapsedTime);
        //textElapsed.setText(adapterCalculatedElapsedTime);
        return row;
    }
*/

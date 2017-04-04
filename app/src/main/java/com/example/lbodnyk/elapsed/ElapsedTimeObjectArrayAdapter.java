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

    //`private static final String ADAPTER = "Adapter";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MyElapsedTimeObject anElapsedTimeObject = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView elapsedtimelistitemtitle = (TextView) convertView.findViewById(R.id.elapsedtimelistitemtitle);
        TextView elapsedtimelistitemelapsedtime = (TextView) convertView.findViewById(R.id.elapsedtimelistitemelapsedtime);

        // replace item text - would not be necessary if I implement a listener?
        anElapsedTimeObject.setTitle(elapsedtimelistitemtitle.getText().toString());

        // Populate the data into the template view using the data object
        elapsedtimelistitemtitle.setText(anElapsedTimeObject.getTitle());
        elapsedtimelistitemelapsedtime.setText(anElapsedTimeObject.getElapsedTime());
        // I think I would add a listener here
        // the following doesn't work -
        // elapsedtimelistitemtitle.addTextChangedListener(new MyTextWatcher(elapsedtimelistitemtitle, anElapsedTimeObject));
        // Return the completed view to render on screen
        return convertView;
    }
}
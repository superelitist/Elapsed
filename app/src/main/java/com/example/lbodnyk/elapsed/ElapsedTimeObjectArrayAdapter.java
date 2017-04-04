package com.example.lbodnyk.elapsed;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        // Get the data item for this position
        MyElapsedTimeObject anElapsedTimeObject = getItem(position);
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
        // I think I would add a listener here - but this listener clears focus with every character!
         /*elapsedtimelistitemtitle.addTextChangedListener(new TextWatcher() {

             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

             }

             @Override
             public void afterTextChanged(Editable s) {
                 parent.clearFocus();
             }
         });*/

        // Return the completed view to render on screen
        return convertView;
    }
}
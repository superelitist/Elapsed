package com.example.lbodnyk.elapsed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



class ElapsedTimeObjectArrayAdapter extends ArrayAdapter<MyElapsedTimeObject> {

    private static final String ARRAYADAPTER = "MyDebug_ArrayAdapter";

    ElapsedTimeObjectArrayAdapter(Context context, ArrayList<MyElapsedTimeObject> elapsedTimeObjects) {
        super(context, 0, elapsedTimeObjects);
    }

    @Override
    public View getView(final int position,View convertView, final ViewGroup parent) {
        Log.d(ARRAYADAPTER, "ElapsedTimeObjectArrayAdapter.getView()");

        // Get the data item for this position
        MyElapsedTimeObject currentElapsedTimeObject = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            TextView elapsedtimelistitemtitle = (TextView) convertView.findViewById(R.id.elapsedtimelistitemtitle);
            Log.d(ARRAYADAPTER, "elapsedtimelistitemtitle (position: " + String.valueOf(position) + " -> " + elapsedtimelistitemtitle.getText().toString());
            elapsedtimelistitemtitle.addTextChangedListener(new MyTextWatcher(elapsedtimelistitemtitle, currentElapsedTimeObject));
            if (currentElapsedTimeObject.getIsNew()) {
                Log.d(ARRAYADAPTER, "currentElapsedTimeObject.getIsNew(): " + currentElapsedTimeObject.getIsNew() + ", setting ListView field to: " + currentElapsedTimeObject.getTitle());
                elapsedtimelistitemtitle.setText(currentElapsedTimeObject.getTitle());
                currentElapsedTimeObject.setIsNew(false);
            } else {
                currentElapsedTimeObject.setTitle(elapsedtimelistitemtitle.getText().toString()); // otherwise, the other way around, obviously!
            }
        }

        // Lookup view for data population


        TextView elapsedtimelistitemelapsedtime = (TextView) convertView.findViewById(R.id.elapsedtimelistitemelapsedtime);
        Log.d(ARRAYADAPTER, "elapsedtimelistitemelapsedtime (position: " + String.valueOf(position) + " -> " + elapsedtimelistitemelapsedtime.getText().toString());

        // Populate the data into the template view using the data object

        // if this is the first time we're interacting with this object, populate the associated EditText with the object title, instead of vice-versa


        //elapsedtimelistitemtitle.setText(currentElapsedTimeObject.getTitle());
        elapsedtimelistitemelapsedtime.setText(currentElapsedTimeObject.getElapsedTime());




        convertView.setOnTouchListener(new OnSwipeTouchListener(convertView.getContext() ) {

            public void onClick() {
                //currentElapsedTimeObject.updateTime();

                Log.d(ARRAYADAPTER, "onClick! position" + String.valueOf(position));
            }

            public void onSwipeUp() {
                //Toast.makeText(ElapsedTimeObjectArrayAdapter.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                //Toast.makeText(ElapsedTimeObjectArrayAdapter.this, "right", Toast.LENGTH_SHORT).show();
                Log.d(ARRAYADAPTER, "Swiped Right!");
            }

            public void onSwipeLeft() {
                //Toast.makeText(ElapsedTimeObjectArrayAdapter.this, "left", Toast.LENGTH_SHORT).show();
                Log.d(ARRAYADAPTER, "Swiped Left!");
            }
            public void onSwipeDown() {
                //Toast.makeText(ElapsedTimeObjectArrayAdapter.this, "bottom", Toast.LENGTH_SHORT).show();
            }
            public void onDoubleClick() {

               // ElapsedTimeObjectArrayAdapter.super.remove(currentElapsedTimeObject);
                ElapsedTimeObjectArrayAdapter.super.notifyDataSetChanged();
                Log.d(ARRAYADAPTER, "onDoubleClick! position" + String.valueOf(position));
            }

        });
        // Return the completed view to render on screen
        return convertView;
    }
}
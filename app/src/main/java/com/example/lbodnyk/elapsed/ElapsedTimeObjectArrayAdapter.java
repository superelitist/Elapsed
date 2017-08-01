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

    private static final String ARRAYADAPTER = "ArrayAdapter";

    ElapsedTimeObjectArrayAdapter(Context context, ArrayList<MyElapsedTimeObject> elapsedTimeObjects) {
        super(context, 0, elapsedTimeObjects);
    }

    @Override
    public View getView(final int position,View convertView, final ViewGroup parent) {
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
        // if this is the first time we're interacting with this object, populate the associated EditText with the object title, instead of vice-versa
        if (anElapsedTimeObject.getIsNew()) {
            Log.d("ArrayAdaptor", "anElapsedTimeObject.getIsNew() = TRUE");
            elapsedtimelistitemtitle.setText(anElapsedTimeObject.getTitle());
            anElapsedTimeObject.setIsNew(false);
        } else{
            anElapsedTimeObject.setTitle(elapsedtimelistitemtitle.getText().toString()); // otherwise, the other way around, obviously!
        }



        //elapsedtimelistitemtitle.setText(anElapsedTimeObject.getTitle());
        elapsedtimelistitemelapsedtime.setText(anElapsedTimeObject.getElapsedTime());


        elapsedtimelistitemtitle.addTextChangedListener(new MyTextWatcher(elapsedtimelistitemtitle, anElapsedTimeObject));

        convertView.setOnTouchListener(new OnSwipeTouchListener(convertView.getContext() ) {

            public void onClick() {
                anElapsedTimeObject.updateTime();

                //Log.d(ARRAYADAPTER, "onClick! position" + String.valueOf(position));
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

                ElapsedTimeObjectArrayAdapter.super.remove(anElapsedTimeObject);
                ElapsedTimeObjectArrayAdapter.super.notifyDataSetChanged();
                Log.d(ARRAYADAPTER, "onDoubleClick! position" + String.valueOf(position));
            }

        });
        // Return the completed view to render on screen
        return convertView;
    }
}
package com.example.lbodnyk.elapsed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



class ElapsedTimeObjectArrayAdapter extends ArrayAdapter<MyElapsedTimeObject> {

    private static final String ARRAYADAPTER = "MyDebug_ArrayAdapter";
    private EditText elapsedtimelistitemtitle = null;
    private TextView elapsedtimelistitemelapsedtime = null;

    ElapsedTimeObjectArrayAdapter(Context context, ArrayList<MyElapsedTimeObject> elapsedTimeObjects) {
        super(context, 0, elapsedTimeObjects);
    }

    @Override
    public View getView(final int position,View convertView, final ViewGroup parent) {
//        Log.d(ARRAYADAPTER, "ElapsedTimeObjectArrayAdapter.getView()");
        // Get the data item for this position
        final MyElapsedTimeObject currentElapsedTimeObject = getItem(position);
        ViewHolder holder = null;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.elapsedtimelistitemtitle = (EditText) convertView.findViewById(R.id.elapsedtimelistitemtitle);
            holder.elapsedtimelistitemelapsedtime = (TextView) convertView.findViewById(R.id.elapsedtimelistitemelapsedtime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        elapsedtimelistitemtitle = (EditText) convertView.findViewById(R.id.elapsedtimelistitemtitle);
//        elapsedtimelistitemelapsedtime = (TextView) convertView.findViewById(R.id.elapsedtimelistitemelapsedtime);

        if (currentElapsedTimeObject.getIsNew()) {
            holder.elapsedtimelistitemtitle.addTextChangedListener(new MyTextWatcher(elapsedtimelistitemtitle, currentElapsedTimeObject));
            convertView.setOnTouchListener(new OnSwipeTouchListener(convertView.getContext() ) {

                public void onClick() {
                    currentElapsedTimeObject.updateTime();

                    Log.d(ARRAYADAPTER, "onClick! position" + String.valueOf(position));
                }

                public void onSwipeUp() {
                    //Toast.makeText(ElapsedTimeObjectArrayAdapter.this, "top", Toast.LENGTH_SHORT).show();
                }

                public void onSwipeRight() {
                    //Toast.makeText(ElapsedTimeObjectArrayAdapter.this, "right", Toast.LENGTH_SHORT).show();
                    ElapsedTimeObjectArrayAdapter.super.remove(currentElapsedTimeObject);
                    ElapsedTimeObjectArrayAdapter.super.notifyDataSetChanged();
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

                    ElapsedTimeObjectArrayAdapter.super.remove(currentElapsedTimeObject);
                    ElapsedTimeObjectArrayAdapter.super.notifyDataSetChanged();
                    Log.d(ARRAYADAPTER, "onDoubleClick! position" + String.valueOf(position));
                }

            });
            currentElapsedTimeObject.setIsNew(false);
        }

        // update the list_item with the values from the object.
        holder.elapsedtimelistitemtitle.setText(currentElapsedTimeObject.getTitle());
        holder.elapsedtimelistitemelapsedtime.setText(currentElapsedTimeObject.getElapsedTime());


        Log.d(ARRAYADAPTER, "(position: " + String.valueOf(position) + ", Id: " + getItemId(position) + ") -> " + holder.elapsedtimelistitemtitle.getText().toString() + ", " + holder.elapsedtimelistitemelapsedtime.getText().toString());
//        elapsedtimelistitemtitle.debug(1);

        // Return the completed view to render on screen
        return convertView;
   }

   public static class ViewHolder{
        public EditText elapsedtimelistitemtitle;
        public TextView elapsedtimelistitemelapsedtime;
    }
}
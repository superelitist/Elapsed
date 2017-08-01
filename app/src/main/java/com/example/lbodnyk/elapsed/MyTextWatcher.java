// watches for changes in text fields, and updates the MyElapsedTimeObject accordingly

package com.example.lbodnyk.elapsed;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.util.Log;

public class MyTextWatcher implements TextWatcher {

    private View view;
    private MyElapsedTimeObject ElapsedTimeObject;

    public MyTextWatcher(View view, MyElapsedTimeObject ElapsedTimeObject) {
        this.view = view;
        this.ElapsedTimeObject = ElapsedTimeObject;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    public void afterTextChanged(Editable s) {
        //Log.d("MyTextWatcher", "afterTextChanged s.toString(): " + s.toString());
        ElapsedTimeObject.setTitle(s.toString());
        Log.d("MyTextWatcher", "afterTextChanged ElapsedTimeObject.getTitle(): " + ElapsedTimeObject.getTitle());
    }
}
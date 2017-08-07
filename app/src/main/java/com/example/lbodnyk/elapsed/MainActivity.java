package com.example.lbodnyk.elapsed;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN = "MyDebug_MainActivity";
    private static final String LIFECYCLE = "MyDebug_LifeCycle";
    private ArrayList<MyElapsedTimeObject> myArrayOfElapsedTimeObjects = new ArrayList<>();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private ElapsedTimeObjectArrayAdapter arrayAdapter;
    private boolean MainActivityRunnableIsFinished = false;
    private Runnable MainActivityRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LIFECYCLE, "onCreate() invoked");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recovering the instance state
        if (savedInstanceState != null) {
            Log.d(LIFECYCLE, "===================================================================");
            Log.d(LIFECYCLE, "savedInstanceState is NOT null, restoring from Parcel...");
            myArrayOfElapsedTimeObjects = savedInstanceState.getParcelableArrayList("the_array_list");
            for (MyElapsedTimeObject eachObject : myArrayOfElapsedTimeObjects) {
                Log.d(MAIN, eachObject.getTitle().toString() );
                eachObject.setIsNew(true);
            }
            Log.d(LIFECYCLE, "===================================================================");
        } else {
            // for testing, prepopulate with a timer.
            Log.d(LIFECYCLE, "savedInstanceState IS null, creating a sample item.");
            myArrayOfElapsedTimeObjects.add(new MyElapsedTimeObject(new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a", Locale.US).format(Calendar.getInstance().getTimeInMillis()), Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis()));
        }

        initUIElements();

        // The main loop, so to speak. Will keep running until MainActivityRunnableIsFinished is true.
        MainActivityRunnable = new Runnable() {
            @Override
            public void run() {
                boolean anyRowHasFocus = false;
                for (int i = 0; i < ((ListView) findViewById(R.id.listOfTimers)).getChildCount(); i++) {
                    // I must check each row individually, because the containing list almost never loses focus.
                    if (((ListView) findViewById(R.id.listOfTimers)).getChildAt(i).hasFocus()) {
                        anyRowHasFocus = true;
                    }
                }
                if (!anyRowHasFocus) {
                    Log.d(MAIN, "----------------------------------------------------------------");
                    Log.d(MAIN, "MainActivityRunnable");
                    Log.d(MAIN, Integer.toString((myArrayOfElapsedTimeObjects.size())));
                    for (MyElapsedTimeObject eachObject : myArrayOfElapsedTimeObjects) {
                        Log.d(MAIN, eachObject.getTitle().toString() );
                    }
                    arrayAdapter.notifyDataSetChanged();
                    Log.d(MAIN, "----------------------------------------------------------------");
                }
                if (!MainActivityRunnableIsFinished) {
                    mainThreadHandler.postDelayed(this, 2000);
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LIFECYCLE,"onStart() invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LIFECYCLE,"onResume() invoked");
        mainThreadHandler.post(MainActivityRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LIFECYCLE,"onPause() invoked");
        mainThreadHandler.removeCallbacks(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LIFECYCLE,"onStop() invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LIFECYCLE,"onRestart() invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LIFECYCLE,"onDestroy() invoked");
        // lol I don't even think this is necessary
        MainActivityRunnableIsFinished = true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(LIFECYCLE,"onSaveInstanceState() invoked");
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("the_array_list", myArrayOfElapsedTimeObjects);
    }

    private void initUIElements() {
        // Create the adapter to convert the array to views
        arrayAdapter = new ElapsedTimeObjectArrayAdapter(this, myArrayOfElapsedTimeObjects);
        ((ListView) findViewById(R.id.listOfTimers)).setAdapter(arrayAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Trying to add a new MyElapsedTimeObject", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                arrayAdapter.add(new MyElapsedTimeObject(new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a", Locale.US).format(Calendar.getInstance().getTimeInMillis()), Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis()));
                //myArrayOfElapsedTimeObjects.add(new MyElapsedTimeObject(new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a", Locale.US).format(Calendar.getInstance().getTimeInMillis()), Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis()));
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // this fancy shit by zMan allows me to take focus off the EditText fields by touching anywhere else. Fucking invaluable. http://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}

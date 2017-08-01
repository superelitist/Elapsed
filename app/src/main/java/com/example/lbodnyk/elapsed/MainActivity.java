package com.example.lbodnyk.elapsed;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // private static final String MAIN = "MainActivity";
    static ArrayList<MyElapsedTimeObject> myArrayOfElapsedTimeObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("lifecycle","onCreate() invoked");
        super.onCreate(savedInstanceState);

        // recovering the instance state
        if (savedInstanceState != null) {
            Log.d("lifecycle","savedInstanceState is NOT null, restoring from Parcel...");
            myArrayOfElapsedTimeObjects = savedInstanceState.getParcelableArrayList("the_array_list");
        } else {
            Log.d("lifecycle","savedInstanceState IS null, creating a sample item.");
            myArrayOfElapsedTimeObjects.add(new MyElapsedTimeObject(new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a", Locale.US).format(Calendar.getInstance().getTimeInMillis()), Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis()));
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           Snackbar.make(view, "Trying to add a new MyElapsedTimeObject", Snackbar.LENGTH_LONG)
                   .setAction("Action", null).show();
           myArrayOfElapsedTimeObjects.add(new MyElapsedTimeObject(new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a", Locale.US).format(Calendar.getInstance().getTimeInMillis()), Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis()));

        }
        });

        // for testing, prepopulate with a timer.
        //myArrayOfElapsedTimeObjects.add(new MyElapsedTimeObject(new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a", Locale.US).format(Calendar.getInstance().getTimeInMillis()), Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis()));

        try {
            Thread.sleep(10); // Waits for a bit (100 milliseconds)
        } catch (InterruptedException e) {
            System.out.println("I was interrupted!");
            e.printStackTrace();
        }

        // Create the adapter to convert the array to views
        final ElapsedTimeObjectArrayAdapter arrayAdapter = new ElapsedTimeObjectArrayAdapter(this, myArrayOfElapsedTimeObjects);
        ((ListView) findViewById(R.id.listOfTimers)).setAdapter(arrayAdapter);

        final Handler myHandler = new Handler();

        class MainActivityRunnable implements Runnable {
            final private Object selfPauseLock;
            private boolean selfPaused;
            private boolean selfFinished;

            private MainActivityRunnable() {
                selfPauseLock = new Object();
                selfPaused = false;
                selfFinished = false;
            }

            public void run() {
                while (!selfFinished) {
                    try {
                        Thread.sleep(333); // Waits for 1 second (1000 milliseconds)
                    } catch (InterruptedException e) {
                        System.out.println("I am required to inform you that I was interrupted!");
                        e.printStackTrace();
                    }
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // This is where the main thread actually runs things. Fuck Java.
                            boolean anyRowHasFocus = false;
                            for ( int i = 0; i < ((ListView) findViewById(R.id.listOfTimers)).getChildCount(); i++ ) {
                                // I must check each row individually, because the containing list almost never loses focus.
                                if ( ((ListView) findViewById(R.id.listOfTimers)).getChildAt(i).hasFocus() ) {
                                    anyRowHasFocus = true;
                                }
                            }
                            if (!anyRowHasFocus) {
                                arrayAdapter.notifyDataSetChanged();
                            }
                            //Log.d(MAIN, "Time since OnCreate: " + (getRelativeTimeSpanString(timeAtOnCreate, Calendar.getInstance().getTimeInMillis(), 1000 )).toString());
                        }
                    });
                    synchronized (selfPauseLock) {
                        while (selfPaused) {
                            try {
                                selfPauseLock.wait();
                            } catch (InterruptedException e) {
                                System.out.println("I am required to inform you that I was interrupted!");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            public void onPause() {
                synchronized (selfPauseLock) {
                    selfPaused = true;
                }
            }

            public void onResume() {
                synchronized (selfPauseLock) {
                    selfPaused = false;
                    selfPauseLock.notifyAll();
                }
            }
        }

        // I just like cramming multiple lines together...
        (new Thread(new MainActivityRunnable())).start();
    }

/*    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle","onStart() invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume() invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause() invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle","onStop() invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle","onRestart() invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy() invoked");
    }*/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("lifecycle","onSaveInstanceState() invoked");
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("the_array_list", myArrayOfElapsedTimeObjects);
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

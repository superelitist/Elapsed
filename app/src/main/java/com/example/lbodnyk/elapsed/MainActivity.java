package com.example.lbodnyk.elapsed;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    // private static final String MAIN = "MainActivity";
    ArrayList<MyElapsedTimeObject> myArrayOfElapsedTimeObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           Snackbar.make(view, "Trying to add a new MyElapsedTimeObject", Snackbar.LENGTH_LONG)
                   .setAction("Action", null).show();
           myArrayOfElapsedTimeObjects.add(new MyElapsedTimeObject(new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a", Locale.US).format(Calendar.getInstance().getTimeInMillis()), Calendar.getInstance().getTimeInMillis()));

        }
        });

        // for testing, prepopulate with a timer.
        myArrayOfElapsedTimeObjects.add(new MyElapsedTimeObject(new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a", Locale.US).format(Calendar.getInstance().getTimeInMillis()), Calendar.getInstance().getTimeInMillis()));

        try {
            Thread.sleep(100); // Waits for 1 second (1000 milliseconds)
        } catch (InterruptedException e) {
            System.out.println("I was interrupted!");
            e.printStackTrace();
        }

        // Create the adapter to convert the array to views
        final ElapsedTimeObjectArrayAdapter arrayAdapter = new ElapsedTimeObjectArrayAdapter(this, myArrayOfElapsedTimeObjects);
        ((ListView) findViewById(R.id.listOfTimers)).setAdapter(arrayAdapter);

        final Handler myHandler = new Handler();

        // {
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
                        Thread.sleep(1000); // Waits for 1 second (1000 milliseconds)
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
                                    //Log.d(MAIN, "row " + i + " has focus!");
                                    anyRowHasFocus = true;
                                }
                            }
                            if (!anyRowHasFocus) {
                                //Log.d(MAIN, "Seems like I can update the list now.");
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

        //Runnable myRunnable = new MainActivityRunnable();
        //Thread myThread = new Thread(new MainActivityRunnable());
        //myThread.start();

        // I just like cramming multiple lines together...
        (new Thread(new MainActivityRunnable())).start();
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
}

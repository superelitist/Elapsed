package com.example.lbodnyk.elapsed;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

public class MainActivity_old2 extends AppCompatActivity {

    private static final String MAIN = "MainActivity";
    private static final String ADAPTER = "Adapter";
    String [] titles;
    int manualElapsedSeconds = 99;
    long timeAtOnCreate = Calendar.getInstance().getTimeInMillis();
    String calculatedElapsedTime = "YouShouldNeverSeeThis";
    MyElapsedTimeObject[] myArrayOfElapsedTimeObjects;


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
           Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                   .setAction("Action", null).show();
        }
        });

        Resources res = getResources();
        titles = res.getStringArray(R.array.titles);

        // initial calculation of elapsed time
        calculatedElapsedTime = (getRelativeTimeSpanString(timeAtOnCreate, Calendar.getInstance().getTimeInMillis(), 1000 )).toString();

        // use our custom adapter to parse our array - I guess this also populates the actual layout?
        MyAdapter myAdapter = new MyAdapter(this, titles, manualElapsedSeconds, calculatedElapsedTime);
        ((ListView) findViewById(R.id.listOfTimers)).setAdapter(myAdapter);

        //ListView myListView = (ListView) findViewById(R.id.listOfTimers);

        final Handler myHandler = new Handler();

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                int loops = 0;
                boolean exitThread = false;
                while (!exitThread) {
                    try {
                        Thread.sleep(1000); // Waits for 1 second (1000 milliseconds)
                    } catch (InterruptedException e) {
                        System.out.println("I was interrupted!");
                        e.printStackTrace();
                    }
                    loops += 1;
                    if (loops == 90) {
                        exitThread = true;
                    }
                    final int thisiswhateveriwant = loops;
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            manualElapsedSeconds += 1;
                            long timeNow = Calendar.getInstance().getTimeInMillis();
                            calculatedElapsedTime = (getRelativeTimeSpanString(timeAtOnCreate, timeNow, 1000 )).toString();

                            //myAdapter.notifyDataSetChanged();
                            Log.d(MAIN, "00000000000000000000000000000000000 loops:" + Integer.toString(thisiswhateveriwant));
                            Log.d(MAIN, String.valueOf(timeAtOnCreate));
                            Log.d(MAIN, String.valueOf(timeNow));
                            Log.d(MAIN, "calculatedElapsedTime: " + calculatedElapsedTime);
                        }
                    });
                }
            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();
    }

    class MyElapsedTimeObject {
        public MyElapsedTimeObject(String name, long creationTime) {
        }

        public String getElapsedTime() {
            return getRelativeTimeSpanString(timeAtOnCreate, Calendar.getInstance().getTimeInMillis(), 1000 ).toString();
        }
    }


    class MyAdapter extends ArrayAdapter<String> {


        Context context;
        String adapterCalculatedElapsedTime;
        String myTitles[];
        int myLoops;

        MyAdapter(Context c, String[] titles, int loops, String adapterCalculatedElapsedTime) {
            super(c,R.layout.list_item, R.id.mytitle, titles);
            this.context = c;
            this.myTitles = titles;
            this.myLoops = loops;
            this.adapterCalculatedElapsedTime = adapterCalculatedElapsedTime;

        }

        @Override
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

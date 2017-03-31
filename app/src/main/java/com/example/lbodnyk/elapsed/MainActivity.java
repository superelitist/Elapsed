package com.example.lbodnyk.elapsed;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;
import static android.text.format.DateUtils.getRelativeTimeSpanString;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String [] titles;
    String[] description;
    int[] imgs;
    long timeAtOnCreate = Calendar.getInstance(TimeZone.getTimeZone("EST"), Locale.US).get(Calendar.MILLISECOND);

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
        description = res.getStringArray(R.array.description);
        imgs = new int[] {R.drawable.windows_powershell_icon2,R.drawable.contact_book,R.drawable.digital_folder};

        java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm:ss", Locale.US);
        java.util.Date date1 = df.parse("-05:00:00", new ParsePosition(0));
        java.util.Date date2 = df.parse("09:05:15", new ParsePosition(0));

        // use our custom adapter to parse our array - I guess this also populates the actual layout?
        ((ListView) findViewById(R.id.listOfTimers)).setAdapter(new MyAdapter(this, titles, description, timeAtOnCreate));


        Log.d(TAG, "00000000000000000000000000000000000");

        final ListView myListView = (ListView) findViewById(R.id.listOfTimers);

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
                    Log.d(TAG, "00000000000000000000000000000000000 loops:" + String.valueOf(loops));
                    if (loops == 30) {
                        exitThread = true;
                    }
                }
                myListView.post(new Runnable() {
                    @Override
                    public void run() {
                        myListView.setAdapter(new MyAdapter(getApplicationContext(), titles, description, timeAtOnCreate));
                    }
                });
            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();
    }


    class MyAdapter extends ArrayAdapter<String> {


        Context context;
        long elapsed;
        String myTitles[];
        String myDescription[];

        MyAdapter(Context c, String[] titles, String[] description, long elapsed) {
            super(c,R.layout.row, R.id.mytitle, titles);
            this.context = c;
            this.myTitles = titles;
            this.myDescription = description;
            this.elapsed = elapsed;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            TextView myTitle = (TextView) row.findViewById(R.id.mytitle);
            TextView myDescription = (TextView) row.findViewById(R.id.text2);
            TextView textClock = (TextView) row.findViewById(R.id.textElapsed);
            myTitle.setText(titles[position]);
            myDescription.setText(description[position]);
            textClock.setText(getRelativeTimeSpanString(elapsed, Calendar.getInstance(TimeZone.getTimeZone("EST"), Locale.US).get(Calendar.MILLISECOND), 0));
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

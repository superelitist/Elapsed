package com.example.lbodnyk.elapsed;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity_old extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ListView list;
    String [] titles;
    String[] description;
    int[] imgs;

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


        Log.d(TAG, "00000000000000000000000000000000000 getResources()");
        Resources res = getResources();

        titles = res.getStringArray(R.array.titles);
        description = res.getStringArray(R.array.description);
        imgs = new int[] {R.drawable.windows_powershell_icon2,R.drawable.contact_book,R.drawable.digital_folder};
        Log.d(TAG, "00000000000000000000000000000000000 arrays populated");

        list = (ListView) findViewById(R.id.listOfTimers);
        MyAdapter adapter = new MyAdapter(this, titles, imgs, description);
        list.setAdapter(adapter);
    }



    class MyAdapter extends ArrayAdapter<String> {


        Context context;
        int[] imgs;
        String myTitles[];
        String myDescription[];
        int imageId;

        MyAdapter(Context c, String[] titles, int[] imgs, String[] description) {
            super(c,R.layout.list_item, R.id.mytitle, titles);
            this.context = c;
            this.imgs = imgs;
            this.myDescription = description;
            this.myTitles = titles;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.list_item,parent,false);
            TextView myTitle = (TextView) row.findViewById(R.id.mytitle);
            TextView myDescription = (TextView) row.findViewById(R.id.text2);

            ImageView myimageview = (ImageView) row.findViewById(R.id.eachrowimage);

            Log.d(TAG, "0000000000000000000000000000000000000000000000000000000000000000000");
            Log.d(TAG, "eachrowimage: " + Integer.toString(R.id.eachrowimage));
            Log.d(TAG, "myimageview NULL: " + Boolean.toString(null == myimageview));
            Log.d(TAG, "text1: " + Integer.toString(R.id.mytitle));
            Log.d(TAG, "0000000000000000000000000000000000000000000000000000000000000000000");
            //Log.d(TAG, java.util.Arrays.toString(imgs));
            //Log.d(TAG, Integer.toString(imgs[position]));
            imageId = imgs[position];
            android.graphics.drawable.Drawable drawable = getResources().getDrawable(imageId);
            //images.setImageDrawable(drawable);
            //icon.setImageDrawable(R.drawable.windows_powershell_icon2);
            myimageview.setImageDrawable(drawable);
            //Log.d(TAG, "00000000000000000000000000000000000 setText()");
            myTitle.setText(titles[position]);
            myDescription.setText(description[position]);
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

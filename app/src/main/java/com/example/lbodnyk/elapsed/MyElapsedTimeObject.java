package com.example.lbodnyk.elapsed;

import java.util.Calendar;
import static android.text.format.DateUtils.getRelativeTimeSpanString;

/**
 * Created by lbodnyk on 3/31/2017.
 */

public class MyElapsedTimeObject {
    String name;
    long creationTime;
    public MyElapsedTimeObject(String name, long creationTime) {
    }

    public String getElapsedTime() {
        return getRelativeTimeSpanString(creationTime, Calendar.getInstance().getTimeInMillis(), 1000 ).toString();
    }
}
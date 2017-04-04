package com.example.lbodnyk.elapsed;

import java.util.Calendar;
import static android.text.format.DateUtils.getRelativeTimeSpanString;

class MyElapsedTimeObject {

    private String name;
    private long creationTime;

    MyElapsedTimeObject(String name, long creationTime) {
        this.name = name;
        this.creationTime = creationTime;
    }

    void setTitle(String newName) {
        this.name = newName;
    }

    String getTitle() {
        return name;
    }

    String getElapsedTime() {
        return getRelativeTimeSpanString(creationTime, Calendar.getInstance().getTimeInMillis(), 1000).toString();
    }
}
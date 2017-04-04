package com.example.lbodnyk.elapsed;

import java.util.Calendar;
import static android.text.format.DateUtils.getRelativeTimeSpanString;

class MyElapsedTimeObject {

    private String title;
    private long creationTime;
    private boolean isNew = true;

    MyElapsedTimeObject(String name, long creationTime) {
        this.title = name;
        this.creationTime = creationTime;
    }

    void setTitle(String newName) {
        this.title = newName;
    }

    String getTitle() {
        return title;
    }

    String getElapsedTime() {
        return getRelativeTimeSpanString(creationTime, Calendar.getInstance().getTimeInMillis(), 1000).toString();
    }

    boolean getIsNew() {
        return isNew;
    }

    void setIsNew(boolean x) {
        this.isNew = x;
    }
}
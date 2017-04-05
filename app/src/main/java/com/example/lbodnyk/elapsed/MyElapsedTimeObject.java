package com.example.lbodnyk.elapsed;

import java.util.Calendar;
import static android.text.format.DateUtils.getRelativeTimeSpanString;

class MyElapsedTimeObject {

    private String title;
    private long creationTimestamp;
    private long latestTimestamp;
    private boolean isNew = true;

    MyElapsedTimeObject(String name, long creationTime, long latestTimestamp) {
        this.title = name;
        this.creationTimestamp = creationTime;
        this.latestTimestamp = latestTimestamp;
    }

    void setTitle(String newName) {
        this.title = newName;
    }

    String getTitle() {
        return title;
    }

    String getElapsedTime() {
        return getRelativeTimeSpanString(latestTimestamp, Calendar.getInstance().getTimeInMillis(), 1000).toString();
    }

    boolean getIsNew() {
        return isNew;
    }

    void setIsNew(boolean x) {
        this.isNew = x;
    }

    public void updateTime() {
        this.latestTimestamp = Calendar.getInstance().getTimeInMillis();
    }
}
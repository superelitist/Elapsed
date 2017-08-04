package com.example.lbodnyk.elapsed;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Calendar;
import static android.text.format.DateUtils.getRelativeTimeSpanString;

class MyElapsedTimeObject implements Parcelable {

    private static final String TAG = "MyDebug_Object";
    private String title;
    private long creationTimestamp;
    private long latestTimestamp;
    private boolean isNew = true;

    MyElapsedTimeObject(String name, long creationTime, long latestTimestamp) {
        this.title = name;
        Log.d(TAG, "MyElapsedTimeObject() name:" + title);
        this.creationTimestamp = creationTime;
        this.latestTimestamp = latestTimestamp;
    }

    MyElapsedTimeObject(Parcel src) {
        title = src.readString();
        creationTimestamp = src.readLong();
        latestTimestamp = src.readLong();
        isNew = (Boolean) src.readValue( null );
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeLong(creationTimestamp);
        dest.writeLong(latestTimestamp);
        dest.writeValue(isNew);
    }

    public static final Parcelable.Creator<MyElapsedTimeObject> CREATOR
            = new Parcelable.Creator<MyElapsedTimeObject>() {
        public MyElapsedTimeObject createFromParcel(Parcel src) {
            return new MyElapsedTimeObject(src);
        }

        public MyElapsedTimeObject[] newArray(int size) {
            return new MyElapsedTimeObject[size];
        }
    };
}
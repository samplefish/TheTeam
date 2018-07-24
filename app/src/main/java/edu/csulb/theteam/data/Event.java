package edu.csulb.theteam.data;

import android.content.ContentValues;
import android.database.Cursor;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.UUID;

public class Event {

    private long id = -1;
    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String title;
    private String description;
    private String time;


}

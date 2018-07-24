package edu.csulb.theteam.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public class EventsContentContract {

    public static final String AUTHORITY = "edu.csulb.theteam.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /*public static Uri uriBuilder(String noteId) {
        Uri item = new Uri.Builder()
                .scheme("content")
                .authority(EventsContentContract.AUTHORITY)
                .appendPath(EventsContentContract.Events.DIR_BASEPATH)
                .appendPath(noteId)
                .build();
        return item;
    }*/

}

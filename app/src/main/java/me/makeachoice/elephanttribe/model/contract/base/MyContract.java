package me.makeachoice.elephanttribe.model.contract.base;

import android.net.Uri;

/**
 * MyContract holds variables used by contentProvider
 */

public class MyContract {

    public static final String CONTENT_AUTHORITY = "me.makeachoice.elephanttribe.memorybuilder.app";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

}

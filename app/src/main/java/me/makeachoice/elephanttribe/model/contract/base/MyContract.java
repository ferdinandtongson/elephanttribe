package me.makeachoice.elephanttribe.model.contract.base;

import android.net.Uri;

/**
 * Created by tongson on 6/2/17.
 */

public class MyContract {

    protected static final String CONTENT_AUTHORITY = "me.makeachoice.elephanttribe.memorybuilder.app";


    protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

}

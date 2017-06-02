package me.makeachoice.elephanttribe.model.contract.user;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import me.makeachoice.elephanttribe.model.contract.base.MyContract;

/**
 * UserContract defines database table and column names, projection and index values, and uris
 */

public class UserContract extends MyContract implements BaseColumns {

/**************************************************************************************************/
/*
 * Table and Column Variables:
 */
/**************************************************************************************************/

    //table name
    public static final String TABLE_NAME = "user";

    //user info
    public static final String COLUMN_USERID = "user_id";
    public final static String COLUMN_USER = "user_name";
    public final static String COLUMN_EMAIL = "user_email";
    public final static String COLUMN_REGISTRATION = "registration";


    //default sort order
    public final static String DEFAULT_SORT_ORDER = COLUMN_USER + " COLLATE NOCASE ASC";

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Projection and Index:
 */
/**************************************************************************************************/
    //defines the columns that will be returned for each row
    public static String[] PROJECTION =
        {
                _ID,
                COLUMN_USERID,
                COLUMN_USER,
                COLUMN_EMAIL,
                COLUMN_REGISTRATION
        };


    public final static int INDEX_ID = 0;
    public final static int INDEX_USERID = 1;
    public final static int INDEX_USER = 2;
    public final static int INDEX_EMAIL = 3;
    public final static int INDEX_REGISTRATION = 4;

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Build Uri Methods:
 */
/**************************************************************************************************/

    public final static String PATH = "user";

    public static Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

    public static String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

    public static String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

    //"content://CONTENT_AUTHORITY/user/[_id]
    public static Uri buildUser(long id){
        return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
    }

    //"content://CONTENT_AUTHORITY/user
    public static Uri buildAllUser(){
        return CONTENT_URI;
    }

    //"content://CONTENT_AUTHORITY/user/user_id/[userId]
    public static Uri buildWithId(String userId){
        return CONTENT_URI.buildUpon().appendPath(COLUMN_USERID).appendPath(userId).build();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Uri Values:
 */
/**************************************************************************************************/

    //"content://CONTENT_AUTHORITY/user/user_id/[userId]
    public static String getId(Uri uri){ return uri.getPathSegments().get(2); }

/**************************************************************************************************/


}

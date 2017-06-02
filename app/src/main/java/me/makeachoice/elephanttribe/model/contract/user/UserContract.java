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
    public final static String TABLE_NAME = "user";

    //user info
    public final static String USERID = "userId";
    public final static String USERNAME = "userName";
    public final static String EMAIL = "email";
    public final static String REGISTRATION = "registration";

    public final static String CREATE_TABLE = "CREATE TABLE " +
            UserContract.TABLE_NAME + " (" +
            UserContract._ID + " INTEGER PRIMARY KEY, " +
            UserContract.USERID + " TEXT NOT NULL, " +
            UserContract.USERNAME + " TEXT NOT NULL, " +
            UserContract.EMAIL + " TEXT, " +
            UserContract.REGISTRATION + " TEXT);";


    //default sort order
    public final static String DEFAULT_SORT_ORDER = USERNAME + " COLLATE NOCASE ASC";

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
                USERID,
                USERNAME,
                EMAIL,
                REGISTRATION
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

    public final static String PATH = TABLE_NAME;

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
        return CONTENT_URI.buildUpon().appendPath(USERID).appendPath(userId).build();
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

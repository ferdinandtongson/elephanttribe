package me.makeachoice.elephanttribe.model.contract.deck;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import me.makeachoice.elephanttribe.model.contract.base.MyContract;

/**
 * DeckTagContract defines database table and column names, projection and index values, and uris
 */

public class DeckTagContract extends MyContract implements BaseColumns {

/**************************************************************************************************/
/*
 * Table and Column Variables:
 */
/**************************************************************************************************/

    //table name
    public static final String TABLE_NAME = "deckTag";


    //id keys
    public final static String USERID = "userId";
    public final static String DECKID = "deckId";

    public final static String TAG = "tag";

    public final static String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY, " +
            USERID + " TEXT NOT NULL, " +
            DECKID + " TEXT NOT NULL, " +
            TAG + " TEXT NOT NULL);";


    //default sort order
    public final static String DEFAULT_SORT_ORDER = TAG + " COLLATE NOCASE ASC";

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
                DECKID,
                TAG
        };


    public final static int INDEX_ID = 0;
    public final static int INDEX_USERID = 1;
    public final static int INDEX_DECKID = 2;
    public final static int INDEX_TAG = 3;

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

    //"content://CONTENT_AUTHORITY/deckTag/[_id]
    public static Uri buildDeckTag(long id){
        return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
    }

    //"content://CONTENT_AUTHORITY/deckTag/[userId]
    public static Uri buildWithUserId(String userId){
        return CONTENT_URI.buildUpon().appendPath(userId).build();
    }

    //"content://CONTENT_AUTHORITY/deckTag/[userId]/deckId/[deckId]
    public static Uri buildWithDeckId(String userId, String deckId){
        return CONTENT_URI.buildUpon().appendPath(userId).
                appendPath(DECKID).appendPath(deckId).build();
    }

    //"content://CONTENT_AUTHORITY/deckTag/[userId]/tag/[tag]
    public static Uri buildWithTag(String userId, String tag){
        return CONTENT_URI.buildUpon().appendPath(userId).
                appendPath(TAG).appendPath(tag).build();
    }

    //"content://CONTENT_AUTHORITY/deckTag/[[userId]/deckId/[deckId]/tag/[tag]
    public static Uri buildWithDeckTag(String userId, String deckId, String tag){
        return CONTENT_URI.buildUpon().appendPath(userId).
                appendPath(DECKID).appendPath(deckId).
                appendPath(TAG).appendPath(tag).build();
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Uri Values:
 */
/**************************************************************************************************/

    //"content://CONTENT_AUTHORITY/deckTag/[userId]/deckId/[deckId]
    public static String getUserId(Uri uri){ return uri.getPathSegments().get(1); }

    //"content://CONTENT_AUTHORITY/deckTag/[userId]/deckId/[deckId]
    public static String getDeckId(Uri uri){ return uri.getPathSegments().get(3); }

    //"content://CONTENT_AUTHORITY/deckTag/[userId]/tag/[tag]
    public static String getTag(Uri uri){ return uri.getPathSegments().get(3); }

    //"content://CONTENT_AUTHORITY/deckTag/[[userId]/deckId/[deckId]/tag/[tag]
    public static String getDeckTag(Uri uri){ return uri.getPathSegments().get(5); }


/**************************************************************************************************/


}

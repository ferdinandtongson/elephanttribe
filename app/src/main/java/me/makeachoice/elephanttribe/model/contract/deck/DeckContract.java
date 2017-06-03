package me.makeachoice.elephanttribe.model.contract.deck;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import me.makeachoice.elephanttribe.model.contract.base.MyContract;

/**
 * DeckContract defines database table and column names, projection and index values, and uris
 */

public class DeckContract extends MyContract implements BaseColumns{

/**************************************************************************************************/
/*
 * Table and Column Variables:
 */
/**************************************************************************************************/

    //table name
    public final static String TABLE_NAME = "deck";

    //id keys
    public final static String USERID = "userId";
    public final static String DECKID = "deckId";

    //deck info
    public final static String DECK = "deck";
    public final static String DESCRIPTION = "description";
    public final static String CARD_COUNT = "cardCount";

    //creator info
    public final static String CREATORID = "creatorId";
    public final static String CREATOR = "creator";
    public final static String CREATOR_PIC = "creatorPic";

    //deck status
    public final static String COST = "cost";
    public final static String ACCESS_TYPE = "accessType";
    public final static String RATING = "rating";
    public final static String ACTIVITY_COUNT = "activityCount";
    public final static String DOWNLOAD_COUNT = "downloadCount";
    public final static String STATUS = "status";

    public final static String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY, " +
            USERID + " TEXT NOT NULL, " +
            DECKID + " TEXT NOT NULL, " +
            DECK + " TEXT NOT NULL, " +
            DESCRIPTION + " TEXT, " +
            CARD_COUNT + " INTEGER, " +
            CREATORID + " TEXT NOT NULL, " +
            CREATOR + " TEXT, " +
            CREATOR_PIC + " TEXT, " +
            COST + " REAL, " +
            ACCESS_TYPE + " INTEGER, " +
            RATING + " REAL, " +
            ACTIVITY_COUNT + " INTEGER, " +
            DOWNLOAD_COUNT + " INTEGER, " +
            STATUS + " INTEGER);";


    //default sort order
    public final static String DEFAULT_SORT_ORDER = DECK + " COLLATE NOCASE ASC";

    //access type
    public final static int ACCESS_PUBLIC = 0;
    public final static int ACCESS_PRIVATE = 1;
    public final static int ACCESS_INVITE = 2;

    //status
    public final static int STATUS_ACTIVE = 0;
    public final static int STATUS_RETIRED = 1;

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
                DECK,
                DESCRIPTION,
                CARD_COUNT,
                CREATORID,
                CREATOR,
                CREATOR_PIC,
                COST,
                ACCESS_TYPE,
                RATING,
                ACTIVITY_COUNT,
                DOWNLOAD_COUNT,
                STATUS
        };


    public final static int INDEX_ID = 0;
    public final static int INDEX_USERID = 1;
    public final static int INDEX_DECKID = 2;
    public final static int INDEX_DECK = 3;
    public final static int INDEX_DESCRIPTION = 4;
    public final static int INDEX_CARD_COUNT = 5;
    public final static int INDEX_CREATORID = 6;
    public final static int INDEX_CREATOR = 7;
    public final static int INDEX_CREATOR_PIC = 8;
    public final static int INDEX_COST = 9;
    public final static int INDEX_ACCESS_TYPE = 10;
    public final static int INDEX_RATING = 11;
    public final static int INDEX_ACTIVITY_COUNT = 12;
    public final static int INDEX_DOWNLOAD_COUNT = 13;
    public final static int INDEX_STATUS = 14;

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

    //"content://CONTENT_AUTHORITY/deck/[_id]
    public static Uri buildDeck(long id){
        return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
    }

    //"content://CONTENT_AUTHORITY/deck/[userId]
    public static Uri buildWithUserId(String userId){
        return CONTENT_URI.buildUpon().appendPath(userId).build();
    }

    //"content://CONTENT_AUTHORITY/deck/[userId]/deckId/[deckId]
    public static Uri buildWithDeckId(String userId, String deckId){
        return CONTENT_URI.buildUpon().appendPath(userId).appendPath(DECKID).appendPath(deckId).build();
    }

    //"content://CONTENT_AUTHORITY/deck/[userId]/status/[status]
    public static Uri buildWithStatus(String userId, String status){
        return CONTENT_URI.buildUpon().appendPath(userId).appendPath(STATUS).appendPath(status).build();
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Uri Values:
 */
/**************************************************************************************************/

    //"content://CONTENT_AUTHORITY/deck/[userId]
    public static String getUserId(Uri uri){ return uri.getPathSegments().get(1); }

    //"content://CONTENT_AUTHORITY/deck/[userId]/deckId/[deckId]
    public static String getDeckId(Uri uri){ return uri.getPathSegments().get(3); }

    //"content://CONTENT_AUTHORITY/deck/[userId]/status/[status]
    public static String getStatus(Uri uri){ return uri.getPathSegments().get(3); }

/**************************************************************************************************/

}

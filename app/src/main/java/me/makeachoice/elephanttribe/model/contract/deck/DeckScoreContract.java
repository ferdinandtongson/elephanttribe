package me.makeachoice.elephanttribe.model.contract.deck;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import me.makeachoice.elephanttribe.model.contract.base.MyContract;

/**
 * DeckScoreContract defines database table and column names, projection and index values, and uris
 */

public class DeckScoreContract extends MyContract implements BaseColumns {

/**************************************************************************************************/
/*
 * Table and Column Variables:
 */
/**************************************************************************************************/

    //table name
    public static final String TABLE_NAME = "deckScore";


    //id keys
    public final static String USERID = "userId";
    public final static String DECKID = "deckId";
    public final static String QUIZ_DATE = "quizDate";

    public final static String SCORE = "score";
    public final static String TOTAL = "cardTotal";
    public final static String CORRECT = "cardMade";
    public final static String MISSED = "cardMissed";

    public final static String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY, " +
            USERID + " TEXT NOT NULL, " +
            DECKID + " TEXT NOT NULL, " +
            QUIZ_DATE + " TEXT NOT NULL, " +
            SCORE + " TEXT, " +
            TOTAL + " INTEGER, " +
            CORRECT + " INTEGER, " +
            MISSED + " INTEGER);";


    //default sort order
    public final static String DEFAULT_SORT_ORDER = QUIZ_DATE + " DESC";

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
                QUIZ_DATE,
                SCORE,
                TOTAL,
                CORRECT,
                MISSED
        };


    public final static int INDEX_ID = 0;
    public final static int INDEX_USERID = 1;
    public final static int INDEX_DECKID = 2;
    public final static int INDEX_QUIZ_DATE = 3;
    public final static int INDEX_SCORE = 4;
    public final static int INDEX_TOTAL = 5;
    public final static int INDEX_CORRECT = 6;
    public final static int INDEX_MISSED = 7;

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

    //"content://CONTENT_AUTHORITY/deckScore/[_id]
    public static Uri buildDeckScore(long id){
        return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
    }

    //"content://CONTENT_AUTHORITY/deckScore/[userId]
    public static Uri buildWithUserId(String userId){
        return CONTENT_URI.buildUpon().appendPath(userId).build();
    }

    //"content://CONTENT_AUTHORITY/deckScore/[userId]/deckId/[deckId]
    public static Uri buildWithDeckId(String userId, String deckId){
        return CONTENT_URI.buildUpon().appendPath(userId).
                appendPath(DECKID).appendPath(deckId).build();
    }

    //"content://CONTENT_AUTHORITY/deckScore/[[userId]/deckId/[deckId]/quizDate/[quizDate]
    public static Uri buildWithDeckQuizDate(String userId, String deckId, String quizDate){
        return CONTENT_URI.buildUpon().appendPath(userId).
                appendPath(DECKID).appendPath(deckId).
                appendPath(QUIZ_DATE).appendPath(quizDate).build();
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Uri Values:
 */
/**************************************************************************************************/

    //"content://CONTENT_AUTHORITY/deckScore/[userId]/deckId/[deckId]
    public static String getUserId(Uri uri){ return uri.getPathSegments().get(1); }

    //"content://CONTENT_AUTHORITY/deckScore/[userId]/deckId/[deckId]
    public static String getDeckId(Uri uri){ return uri.getPathSegments().get(3); }

    //"content://CONTENT_AUTHORITY/deckScore/[[userId]/deckId/[deckId]/quizDate/[quizDate]
    public static String getDeckQuizDate(Uri uri){ return uri.getPathSegments().get(5); }


/**************************************************************************************************/


}

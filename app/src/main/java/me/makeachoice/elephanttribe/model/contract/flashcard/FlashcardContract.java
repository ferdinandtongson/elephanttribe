package me.makeachoice.elephanttribe.model.contract.flashcard;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import me.makeachoice.elephanttribe.model.contract.base.MyContract;

/**
 * FlashcardContract defines database table and column names, projection and index values, and uris
 */

public class FlashcardContract extends MyContract implements BaseColumns{

/**************************************************************************************************/
/*
 * Table and Column Variables:
 */
/**************************************************************************************************/

    //table name
    public static final String TABLE_NAME = "flashcard";

    //id keys
    public static final String USERID = "userId";
    public static final String DECKID = "deckId";
    public static final String CARDID = "cardId";

    //flashcard values
    public final static String CARD = "card";
    public final static String ANSWER = "answer";
    public final static String PICTURE = "picture";
    public final static String VOICE = "voice";


    public final static String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY, " +
            USERID + " TEXT NOT NULL, " +
            DECKID + " TEXT NOT NULL, " +
            CARDID + " TEXT NOT NULL, " +
            CARD + " TEXT NOT NULL, " +
            ANSWER + " TEXT NOT NULL, " +
            PICTURE + " TEXT, " +
            VOICE + " TEXT);";


    //default sort order
    public final static String DEFAULT_SORT_ORDER = CARD + " COLLATE NOCASE ASC";

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
                CARDID,
                CARD,
                ANSWER,
                PICTURE,
                VOICE
        };

    public final static int INDEX_ID = 0;
    public final static int INDEX_USERID = 1;
    public final static int INDEX_DECKID = 2;
    public final static int INDEX_CARDID = 3;
    public final static int INDEX_CARD = 4;
    public final static int INDEX_ANSWER = 5;
    public final static int INDEX_PICTURE = 6;
    public final static int INDEX_VOICE = 7;

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

    //"content://CONTENT_AUTHORITY/flashcard/[_id]
    public static Uri buildFlashcard(long id){
        return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
    }

    //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]
    public static Uri buildWithDeckId(String userId, String deckId){
        return CONTENT_URI.buildUpon().appendPath(userId).appendPath(DECKID).appendPath(deckId).build();
    }

    //"content://CONTENT_AUTHORITY/flashcard/[userId]/cardId/[cardId]
    public static Uri buildWithCardId(String userId, String fKey){
        return CONTENT_URI.buildUpon().appendPath(userId).appendPath(CARDID).appendPath(fKey).build();
    }

    //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]/card/[card]
    public static Uri buildWithDeckIdCard(String userId, String deckId, String card){
        return CONTENT_URI.buildUpon().appendPath(userId).appendPath(DECKID).appendPath(deckId).
                appendPath(CARD).appendPath(card).build();
    }

    //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]/answer/[answer]
    public static Uri buildWithDeckIdAnswer(String userId, String deckId, String answer){
        return CONTENT_URI.buildUpon().appendPath(userId).appendPath(DECKID).appendPath(deckId).
                appendPath(ANSWER).appendPath(answer).build();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Uri Values:
 */
/**************************************************************************************************/

    //"content://CONTENT_AUTHORITY/flashcard/[userId]
    public static String getUserId(Uri uri){ return uri.getPathSegments().get(1); }

    //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]
    public static String getDeckId(Uri uri){ return uri.getPathSegments().get(3); }

    //"content://CONTENT_AUTHORITY/flashcard/[userId]/cardId/[cardId]
    public static String getCardId(Uri uri){ return uri.getPathSegments().get(3); }

    //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]/card/[card]
    public static String getDeckCard(Uri uri){ return uri.getPathSegments().get(5); }

    //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]/answer/[answer]
    public static String getDeckAnswer(Uri uri){ return uri.getPathSegments().get(5); }



/**************************************************************************************************/

}

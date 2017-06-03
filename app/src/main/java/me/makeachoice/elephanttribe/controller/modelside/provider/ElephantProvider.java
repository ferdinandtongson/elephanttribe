package me.makeachoice.elephanttribe.controller.modelside.provider;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import me.makeachoice.elephanttribe.controller.modelside.query.flashcard.FlashcardQuery;
import me.makeachoice.elephanttribe.controller.modelside.query.user.UserQuery;
import me.makeachoice.elephanttribe.model.db.DBHelper;

import static me.makeachoice.elephanttribe.controller.modelside.provider.UriMatcherHelper.FLASHCARD;
import static me.makeachoice.elephanttribe.controller.modelside.provider.UriMatcherHelper.FLASHCARD_WITH_CARDID;
import static me.makeachoice.elephanttribe.controller.modelside.provider.UriMatcherHelper.FLASHCARD_WITH_DECKID;
import static me.makeachoice.elephanttribe.controller.modelside.provider.UriMatcherHelper.FLASHCARD_WITH_DECK_ANSWER;
import static me.makeachoice.elephanttribe.controller.modelside.provider.UriMatcherHelper.FLASHCARD_WITH_DECK_CARD;
import static me.makeachoice.elephanttribe.controller.modelside.provider.UriMatcherHelper.FLASHCARD_WITH_USERID;
import static me.makeachoice.elephanttribe.controller.modelside.provider.UriMatcherHelper.USER;
import static me.makeachoice.elephanttribe.controller.modelside.provider.UriMatcherHelper.USER_WITH_ID;
import static me.makeachoice.elephanttribe.controller.modelside.provider.UriMatcherHelper.dbUriMatcher;

/**
 * ElephantProvider is the ContentProvider for FlashLearning
 */

public class ElephantProvider extends ContentProvider {

/**************************************************************************************************/
/*
 * Class Variables:
 *      mDBHelper - DBHelper object
 */
/**************************************************************************************************/

    //mDBHelper - DBHelper object
    private DBHelper mDBHelper;

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * ContentProvider Lifecycle:
 *      boolean onCreate() - called when ContentProvider is created
 *      String getType(...) - get Uri content type (single or multiple)
 *      void shutdown() - closes DBHelper
 */
/**************************************************************************************************/
    /*
     * boolean onCreate() - called when ContentProvider is created
     */
    @Override
    public boolean onCreate(){
        mDBHelper = new DBHelper(getContext());
        return true;
    }

    /*
     * String getType(...) - get Uri content type (single or multiple)
     */
    @Override
    public String getType(Uri uri){
        int match = dbUriMatcher.match(uri);

        return UriMatcherHelper.getContentType(match); 
    }

    /*
     * void shutdown() - closes DBHelper; specifically used to assist the testing framework in
     * running smoothly. Read at: http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
     */
    @Override
    @TargetApi(11)
    public void shutdown(){
        mDBHelper.close();
        super.shutdown();
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * ContentProvider Lifecycle Query:
 *      Cursor query(...) - gets cursor from uri query
 */
/**************************************************************************************************/
    /*
     * Cursor query(...) - gets cursor from uri query
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] args, String order){
        Cursor cursor = null;
        switch(dbUriMatcher.match(uri)){
            case USER_WITH_ID:
                cursor = UserQuery.getUserById(mDBHelper, uri, projection, order);
                break;
            case FLASHCARD_WITH_USERID:
                cursor = FlashcardQuery.getByUserId(mDBHelper, uri, projection, order);
                break;
            case FLASHCARD_WITH_DECKID:
                cursor = FlashcardQuery.getByDeckId(mDBHelper, uri, projection, order);
                break;
            case FLASHCARD_WITH_CARDID:
                cursor = FlashcardQuery.getByCardId(mDBHelper, uri, projection, order);
                break;
            case FLASHCARD_WITH_DECK_CARD:
                cursor = FlashcardQuery.getByDeckIdCard(mDBHelper, uri, projection, order);
                break;
            case FLASHCARD_WITH_DECK_ANSWER:
                cursor = FlashcardQuery.getByDeckIdAnswer(mDBHelper, uri, projection, order);
                break;
            default:
                Log.e("Choice", "ElephantProvider.query() - unknown uri: " + uri);
                throw new UnsupportedOperationException("Unkown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * ContentProvider Lifecycle Insert:
 *      Uri insert(...) - gets uri value from database insert request
 */
/**************************************************************************************************/
    /*
     * Uri insert(...) - gets uri value from database insert request
     */
    @Override
    public Uri insert(Uri uri, ContentValues values){
        //open database
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();

        //get uri matcher
        final int match = dbUriMatcher.match(uri);

        //uri to be returned for validation
        Uri returnUri = null;

        switch(match){
            case USER:
                returnUri = UserQuery.insert(db, values);
                break;
            case FLASHCARD:
                returnUri = FlashcardQuery.insert(db, values);
                break;
            default:
                Log.e("Choice", "ElephantProvider.insert() - Unknown uri: " + uri);
                throw new UnsupportedOperationException("Unkown uri: " + uri);
        }

        if(returnUri != null){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return returnUri;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * ContentProvider Lifecycle Delete:
 *      int delete(...) - gets number of deleted rows from database delete request
 */
/**************************************************************************************************/
    /*
     * int delete(...) - gets number of deleted rows from database delete request
     */
    @Override
    public int delete(Uri uri, String selection, String[] args){
        //open database
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();

        //get uri matcher
        final int match = dbUriMatcher.match(uri);

        //number of rows deleted
        int rowsDeleted = 0;

        switch(match){
            case USER:
                rowsDeleted = UserQuery.delete(db, uri, selection, args);
                break;
            case FLASHCARD:
                rowsDeleted = FlashcardQuery.delete(db, uri, selection, args);
                break;
            default:
                Log.e("Choice", "ElephantProvider.delete() - Unknown uri: " + uri);
                throw new UnsupportedOperationException("Unkown uri: " + uri);
        }

        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * ContentProvider Lifecycle Update:
 *      int update(...) - gets number of rows updated from database update request
 */
/**************************************************************************************************/
    /*
     * int update(...) - gets number of rows updated from database update request
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] args){
        //open database
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();

        //get uri matcher
        final int match = dbUriMatcher.match(uri);

        //number of rows updated
        int rowsUpdated = 0;

        switch(match){
            case USER:
                rowsUpdated = UserQuery.update(db, uri, values, selection, args);
                break;
            case FLASHCARD:
                rowsUpdated = FlashcardQuery.update(db, uri, values, selection, args);
                break;
            default:
                Log.e("Choice", "ElephantProvider.update() - Unknown uri: " + uri);
                throw new UnsupportedOperationException("Unkown uri: " + uri);
        }

        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;

    }

/**************************************************************************************************/

}

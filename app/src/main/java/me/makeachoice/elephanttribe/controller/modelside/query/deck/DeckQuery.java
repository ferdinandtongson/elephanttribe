package me.makeachoice.elephanttribe.controller.modelside.query.deck;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import me.makeachoice.elephanttribe.model.contract.deck.DeckContract;
import me.makeachoice.elephanttribe.model.db.DBHelper;

/**
 * DeckQuery helps in building deck table queries to the local database
 */

public class DeckQuery {

/**************************************************************************************************/
/*
 * Query builder:
 */
/**************************************************************************************************/

    //mQueryBuilder - sqliteQueryBuilder object
    private static final SQLiteQueryBuilder mQueryBuilder;

    static{
        //initialize queryBuilder
        mQueryBuilder = new SQLiteQueryBuilder();

        //set builder to query deck table
        mQueryBuilder.setTables(DeckContract.TABLE_NAME);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Selection Variables:
 */
/**************************************************************************************************/

    //userIdSelection - deck.userId = ?
    public final static String userIdSelection = DeckContract.TABLE_NAME + "." +
            DeckContract.USERID + " = ? ";

    //deckIdSelection - deck.userId = ? AND deckId = ?
    public final static String deckIdSelection = DeckContract.TABLE_NAME + "." +
            DeckContract.USERID + " = ? AND " +
            DeckContract.DECKID + " = ?";

    //statusSelection - deck.userId = ? AND status = ?
    public final static String statusSelection = DeckContract.TABLE_NAME + "." +
            DeckContract.USERID + " = ? AND " +
            DeckContract.STATUS + " = ?";

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Cursor Methods:
 *      Cursor getByUserId(...) - get decks owned by user id
 *      Cursor getByFkey(...) - get decks by user and deck id
 *      Cursor getByName(...) - get decks by user and deck name
 *      Cursor getByCreator(...) - get decks by user and deck creator
 *      Cursor getByStatus(...) - get decks by user and deck status
 */
/**************************************************************************************************/
    /*
     * Cursor getByUserId(...) - get decks owned by user id
     */
    public static Cursor getByUserId(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/deck/[userId]
        String userId = DeckContract.getUserId(uri);

        //query from deck table
        return DeckQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                userIdSelection,
                new String[]{userId},
                null,
                null,
                sort
        );
    }

    /*
     * Cursor getByDeckId(...) - get decks by user and deck id
     */
    public static Cursor getByDeckId(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/deck/[userId]/deckId/[deckId]
        String userId = DeckContract.getUserId(uri);
        String deckId = DeckContract.getDeckId(uri);

        //query from deck table
        return DeckQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                deckIdSelection,
                new String[]{userId, deckId},
                null,
                null,
                sort
        );
    }

    /*
     * Cursor getByStatus(...) - get decks by user and status
     */
    public static Cursor getByStatus(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/deck/[userId]/status/[status]
        String userId = DeckContract.getUserId(uri);
        String status = DeckContract.getStatus(uri);

        //query from deck table
        return DeckQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                statusSelection,
                new String[]{userId, status},
                null,
                null,
                sort
        );
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Database Methods:
 *      Uri insert(...) - insert deck into database
 *      int delete(...) - delete deck from database
 *      int update(...) - update deck in database
 */
/**************************************************************************************************/
    /*
     * Uri insert(...) - insert deck into database
     */
    public static Uri insert(SQLiteDatabase db, ContentValues values){
        long _id = -1;

        try{
            _id = db.insert(DeckContract.TABLE_NAME, null, values);
        }
        catch(SQLException exception){
            Log.e("Choice", "DeckQuery.insert() - " + exception);
            throw exception;
        }

        return DeckContract.buildDeck(_id);
    }

    /*
     * int delete(...) - delete deck from database
     */
    public static int delete(SQLiteDatabase db, Uri uri, String selection, String[] args){
        //number of rows deleted
        int rowsDeleted = -1;

        try{
            rowsDeleted = db.delete(DeckContract.TABLE_NAME, selection, args);
        }
        catch(SQLException exception){
            Log.e("Choice", "DeckQuery.delete() - " + exception);
            throw exception;
        }

        return rowsDeleted;
    }

    /*
     * int update(...) - update deck in database
     */
    public static int update(SQLiteDatabase db, Uri uri, ContentValues values, String selection,
                                 String[] args){
        //number of rows updated
        int rowsUpdated = -1;

        try{
            rowsUpdated = db.update(DeckContract.TABLE_NAME, values, selection, args);
        }
        catch(SQLException exception){
            Log.d("Choice", "DeckQuery.update() - " + exception);
            throw exception;
        }

        return rowsUpdated;
    }


/**************************************************************************************************/



}

package me.makeachoice.elephanttribe.controller.modelside.query.deck;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import me.makeachoice.elephanttribe.model.contract.deck.DeckTagContract;
import me.makeachoice.elephanttribe.model.db.DBHelper;

/**
 * DeckTagQueryHelper helps in building deck tag table queries to the local database
 */

public class DeckTagQuery {

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

        //set builder to query user table
        mQueryBuilder.setTables(DeckTagContract.TABLE_NAME);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Selection Variables:
 */
/**************************************************************************************************/

    //userIdSelection - deckTag.userId = ?
    public final static String userIdSelection = DeckTagContract.TABLE_NAME + "." +
        DeckTagContract.USERID + " = ? ";

    //deckIdSelection - deckTag.userId = ? AND deckId = ?
    public final static String deckIdSelection = DeckTagContract.TABLE_NAME + "." +
            DeckTagContract.USERID + " = ? AND " +
            DeckTagContract.DECKID + " = ?";

    //tagSelection - deckTag.userId = ? AND tag = ?
    public final static String tagSelection = DeckTagContract.TABLE_NAME + "." +
            DeckTagContract.USERID + " = ? AND " +
            DeckTagContract.TAG + " = ?";

    //deckTagSelection - deckTag.userId = ? AND deckId = ? AND tag = ?
    public final static String deckTagSelection = DeckTagContract.TABLE_NAME + "." +
            DeckTagContract.USERID + " = ? AND " +
            DeckTagContract.DECKID + " = ? AND " +
            DeckTagContract.TAG + " = ?";

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Cursor Methods:
 *      Cursor getByUserId(...) - get deck tags by userId
 *      Cursor getByDeckId(...) - get users by userId and deckId
 *      Cursor getByTag(...) - get users by userId and tag
 *      Cursor getByTag(...) - get users by userId, deckId and tag
 */
/**************************************************************************************************/
    /*
     * Cursor getByUserId(...) - get deck tags by userId
     */
    public static Cursor getByUserId(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/deckTag/[userId]
        String userId = DeckTagContract.getUserId(uri);

        //query from user table
        return DeckTagQuery.mQueryBuilder.query(
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
     * Cursor getByDeckId(...) - get users by userId and deckId
     */
    public static Cursor getByDeckId(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/deckTag/[userId]/deckId/[deckId]
        String userId = DeckTagContract.getUserId(uri);
        String deckId = DeckTagContract.getDeckId(uri);

        //query table
        return DeckTagQuery.mQueryBuilder.query(
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
     * Cursor getByTag(...) - get users by userId and tag
     */
    public static Cursor getByTag(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/deckTag/[userId]/tag/[tag]
        String userId = DeckTagContract.getUserId(uri);
        String tag = DeckTagContract.getTag(uri);

        //query table
        return DeckTagQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                tagSelection,
                new String[]{userId, tag},
                null,
                null,
                sort
        );
    }

    /*
     * Cursor getByTag(...) - get users by userId, deckId and tag
     */
    public static Cursor getByDeckTag(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/deckTag/[userId]/deckId/[deckId]/tag/[tag]
        String userId = DeckTagContract.getUserId(uri);
        String deckId = DeckTagContract.getDeckId(uri);
        String tag = DeckTagContract.getDeckTag(uri);

        //query from user table
        return DeckTagQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                deckTagSelection,
                new String[]{userId, deckId, tag},
                null,
                null,
                sort
        );
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Database Methods:
 *      Uri insert(...) - insert into database
 *      int delete(...) - delete from database
 *      int update(...) - update database
 */
/**************************************************************************************************/
    /*
     * Uri insert(...) - insert into database
     */
    public static Uri insert(SQLiteDatabase db, ContentValues values){
        long _id = -1;

        try{
            _id = db.insert(DeckTagContract.TABLE_NAME, null, values);
        }
        catch(SQLException exception){
            Log.e("Choice", "DeckTagQuery.insert() - " + exception);
            throw exception;
        }

        return DeckTagContract.buildDeckTag(_id);
    }

    /*
     * int delete(...) - delete from database
     */
    public static int delete(SQLiteDatabase db, Uri uri, String selection, String[] args){
        //number of rows deleted
        int rowsDeleted = -1;

        try{
            rowsDeleted = db.delete(DeckTagContract.TABLE_NAME, selection, args);
        }
        catch(SQLException exception){
            Log.e("Choice", "DeckTagQuery.delete() - " + exception);
            throw exception;
        }

        return rowsDeleted;
    }

    /*
     * int update(...) - update database
     */
    public static int update(SQLiteDatabase db, Uri uri, ContentValues values, String selection,
                                 String[] args){
        //number of rows updated
        int rowsUpdated = -1;

        try{
            rowsUpdated = db.update(DeckTagContract.TABLE_NAME, values, selection, args);
        }
        catch(SQLException exception){
            Log.e("Choice", "DeckTagQuery.update() - " + exception);
            throw exception;
        }

        return rowsUpdated;
    }


/**************************************************************************************************/



}

package me.makeachoice.elephanttribe.controller.modelside.query.deck;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import me.makeachoice.elephanttribe.model.contract.deck.DeckScoreContract;
import me.makeachoice.elephanttribe.model.db.DBHelper;

/**
 * DeckScoreQuery helps in building deck score table queries to the local database
 */

public class DeckScoreQuery {

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

        //set builder to query table
        mQueryBuilder.setTables(DeckScoreContract.TABLE_NAME);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Selection Variables:
 */
/**************************************************************************************************/

    //userIdSelection - deckScore.userId = ?
    public final static String userIdSelection = DeckScoreContract.TABLE_NAME + "." +
        DeckScoreContract.USERID + " = ? ";

    //deckIdSelection - deckScore.userId = ? AND deckId = ?
    public final static String deckIdSelection = DeckScoreContract.TABLE_NAME + "." +
            DeckScoreContract.USERID + " = ? AND " +
            DeckScoreContract.DECKID + " = ?";

    //deckQuizDateSelection - deckScore.userId = ? AND deckId = ? AND quizDate = ?
    public final static String deckQuizDateSelection = DeckScoreContract.TABLE_NAME + "." +
            DeckScoreContract.USERID + " = ? AND " +
            DeckScoreContract.DECKID + " = ? AND " +
            DeckScoreContract.QUIZ_DATE + " = ?";

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Cursor Methods:
 *      Cursor getByUserId(...) - get deck score by userId
 *      Cursor getByDeckId(...) - get deck score by userId and deckId
 *      Cursor getByDeckQuizDate(...) - get deck score by userId, deckId and quiz date
 */
/**************************************************************************************************/
    /*
     * Cursor getByUserId(...) - get deck score by userId
     */
    public static Cursor getByUserId(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/deckScore/[userId]
        String userId = DeckScoreContract.getUserId(uri);

        //query from user table
        return DeckScoreQuery.mQueryBuilder.query(
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
     * Cursor getByDeckId(...) - get deck score by userId and deckId
     */
    public static Cursor getByDeckId(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/deckScore/[userId]/deckId/[deckId]
        String userId = DeckScoreContract.getUserId(uri);
        String deckId = DeckScoreContract.getDeckId(uri);

        //query table
        return DeckScoreQuery.mQueryBuilder.query(
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
     * Cursor getByDeckQuizDate(...) - get deck score by userId, deckId and quiz date
     */
    public static Cursor getByDeckQuizDate(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/deckScore/[[userId]/deckId/[deckId]/quizDate/[quizDate]
        String userId = DeckScoreContract.getUserId(uri);
        String deckId = DeckScoreContract.getDeckId(uri);
        String quizDate = DeckScoreContract.getDeckQuizDate(uri);

        //query from user table
        return DeckScoreQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                deckQuizDateSelection,
                new String[]{userId, deckId, quizDate},
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
            _id = db.insert(DeckScoreContract.TABLE_NAME, null, values);
        }
        catch(SQLException exception){
            Log.e("Choice", "DeckScoreQuery.insert() - " + exception);
            throw exception;
        }

        return DeckScoreContract.buildDeckScore(_id);
    }

    /*
     * int delete(...) - delete from database
     */
    public static int delete(SQLiteDatabase db, Uri uri, String selection, String[] args){
        //number of rows deleted
        int rowsDeleted = -1;

        try{
            rowsDeleted = db.delete(DeckScoreContract.TABLE_NAME, selection, args);
        }
        catch(SQLException exception){
            Log.e("Choice", "DeckScoreQuery.delete() - " + exception);
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
            rowsUpdated = db.update(DeckScoreContract.TABLE_NAME, values, selection, args);
        }
        catch(SQLException exception){
            Log.e("Choice", "DeckScoreQuery.update() - " + exception);
            throw exception;
        }

        return rowsUpdated;
    }


/**************************************************************************************************/



}

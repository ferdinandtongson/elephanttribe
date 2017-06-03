package me.makeachoice.elephanttribe.controller.modelside.query.flashcard;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import me.makeachoice.elephanttribe.model.contract.flashcard.FlashcardContract;
import me.makeachoice.elephanttribe.model.db.DBHelper;

/**
 * FlashcardQuery helps in building card table queries to the local database
 */

public class FlashcardQuery {

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

        //set builder to query card table
        mQueryBuilder.setTables(FlashcardContract.TABLE_NAME);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Selection Variables:
 */
/**************************************************************************************************/

    //userIdSelection - flashcard.userId = ?
    public final static String userIdSelection = FlashcardContract.TABLE_NAME + "." +
            FlashcardContract.USERID + " = ? ";

    //deckIdSelection - flashcard.userId = ? AND deckId = ?
    public final static String deckIdSelection = FlashcardContract.TABLE_NAME + "." +
            FlashcardContract.USERID + " = ? AND " +
            FlashcardContract.DECKID + " = ?";

    //cardIdSelection - flashcard.userId = ? AND fkey = ?
    public final static String cardIdSelection = FlashcardContract.TABLE_NAME + "." +
            FlashcardContract.USERID + " = ? AND " +
            FlashcardContract.CARDID + " = ?";

    //deckIdCardSelection - flashcard.userId = ? AND deckId = ? AND card = ?
    public final static String deckIdCardSelection = FlashcardContract.TABLE_NAME + "." +
            FlashcardContract.USERID + " = ? AND " +
            FlashcardContract.DECKID + " = ? AND " +
            FlashcardContract.CARD + " = ?";

    //deckIdAnswerSelection - flashcard.userId = ? AND deckId = ? AND answer = ?
    public final static String deckIdAnswerSelection = FlashcardContract.TABLE_NAME + "." +
            FlashcardContract.USERID + " = ? AND " +
            FlashcardContract.DECKID + " = ? AND " +
            FlashcardContract.ANSWER + " = ?";

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Cursor Methods:
 *      Cursor getByUserId(...) - get flashcards owned by user id
 *      Cursor getByDeckId(...) - get flashcards by user and deck id
 *      Cursor getByCardId(...) - get flashcards by user and card id
 *      Cursor getByDeckCard(...) - get flashcards by user, deck and card
 *      Cursor getByDeckAnswer(...) - get flashcards by user, deck and answer
 */
/**************************************************************************************************/
    /*
     * Cursor getByUserId(...) - get flashcards owned by user id
     */
    public static Cursor getByUserId(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/flashcard/[userId]
        String userId = FlashcardContract.getUserId(uri);

        //query from card table
        return FlashcardQuery.mQueryBuilder.query(
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
     * Cursor getByDeckId(...) - get flashcards by user and deck id
     */
    public static Cursor getByDeckId(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]
        String userId = FlashcardContract.getUserId(uri);
        String deckId = FlashcardContract.getDeckId(uri);

        //query from card table
        return FlashcardQuery.mQueryBuilder.query(
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
     * Cursor getByCardId(...) - get flashcards by user and card id
     */
    public static Cursor getByCardId(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/flashcard/[userId]/cardId/[cardId]
        String userId = FlashcardContract.getUserId(uri);
        String cardId = FlashcardContract.getCardId(uri);

        //query from card table
        return FlashcardQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                cardIdSelection,
                new String[]{userId, cardId},
                null,
                null,
                sort
        );
    }

    /*
     * Cursor getByDeckIdCard(...) - get flashcards by user, deck and card
     */
    public static Cursor getByDeckIdCard(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]/card/[card]
        String userId = FlashcardContract.getUserId(uri);
        String deckId = FlashcardContract.getDeckId(uri);
        String card = FlashcardContract.getDeckCard(uri);

        //query from card table
        return FlashcardQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                deckIdCardSelection,
                new String[]{userId, deckId, card},
                null,
                null,
                sort
        );
    }

    /*
     * Cursor getByDeckIdAnswer(...) - get flashcards by user, deck and answer
     */
    public static Cursor getByDeckIdAnswer(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]/answer/[answer]
        String userId = FlashcardContract.getUserId(uri);
        String deckId = FlashcardContract.getDeckId(uri);
        String answer = FlashcardContract.getDeckAnswer(uri);

        //query from card table
        return FlashcardQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                deckIdAnswerSelection,
                new String[]{userId, deckId, answer},
                null,
                null,
                sort
        );
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Database Methods:
 *      Uri insert(...) - insert flashcard into database
 *      int delete(...) - delete flashcard from database
 *      int update(...) - update flashcard in database
 */
/**************************************************************************************************/
    /*
     * Uri insert(...) - insert flashcard into database
     */
    public static Uri insert(SQLiteDatabase db, ContentValues values){
        long _id = -1;

        try{
            _id = db.insert(FlashcardContract.TABLE_NAME, null, values);
        }
        catch(SQLException exception){
            Log.e("Choice", "FlashcardQuery.insertCard() - " + exception);
            throw exception;
        }

        return FlashcardContract.buildFlashcard(_id);
    }

    /*
     * int delete(...) - delete flashcard from database
     */
    public static int delete(SQLiteDatabase db, Uri uri, String selection, String[] args){
        //number of rows deleted
        int rowsDeleted = -1;

        try{
            rowsDeleted = db.delete(FlashcardContract.TABLE_NAME, selection, args);
        }
        catch(SQLException exception){
            Log.e("Choice", "FlashcardQuery.deleteCard() - " + exception);
            throw exception;
        }

        return rowsDeleted;
    }

    /*
     * int update(...) - update flashcard in database
     */
    public static int update(SQLiteDatabase db, Uri uri, ContentValues values, String selection,
                                 String[] args){
        //number of rows updated
        int rowsUpdated = -1;

        try{
            rowsUpdated = db.update(FlashcardContract.TABLE_NAME, values, selection, args);
        }
        catch(SQLException exception){
            Log.e("Choice", "FlashcardQuery.updateCard() - " + exception);
            throw exception;
        }

        return rowsUpdated;
    }


/**************************************************************************************************/



}

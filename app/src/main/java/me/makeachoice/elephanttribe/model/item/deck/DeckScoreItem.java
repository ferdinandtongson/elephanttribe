package me.makeachoice.elephanttribe.model.item.deck;

import android.content.ContentValues;
import android.database.Cursor;

import me.makeachoice.elephanttribe.model.contract.deck.DeckScoreContract;

/**
 * DeckScoreItem holds deck score values
 */

public class DeckScoreItem extends _DeckScoreBaseItem {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    public long _id;
    public String userId;
    public String deckId;
    public String quizDate;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckScoreItem(){}

    public DeckScoreItem(_DeckScoreBaseItem item){
        score = item.score;
        total = item.total;
        correct = item.correct;
        missed = item.missed;
    }

    public DeckScoreItem(DeckItem item){
        userId = item.userId;
        deckId = item.deckId;
        quizDate = "";
        score = "";
        total = 0;
        correct = 0;
        missed = 0;
    }


    public DeckScoreItem(Cursor cursor){
        _id = cursor.getLong(DeckScoreContract.INDEX_ID);
        userId = cursor.getString(DeckScoreContract.INDEX_USERID);
        deckId = cursor.getString(DeckScoreContract.INDEX_DECKID);
        quizDate = cursor.getString(DeckScoreContract.INDEX_QUIZ_DATE);
        score = cursor.getString(DeckScoreContract.INDEX_SCORE);
        total = cursor.getInt(DeckScoreContract.INDEX_TOTAL);
        correct = cursor.getInt(DeckScoreContract.INDEX_CORRECT);
        missed = cursor.getInt(DeckScoreContract.INDEX_MISSED);
    }



/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Methods:
 */
/**************************************************************************************************/

    public ContentValues getContentValues(){
        //create content value
        ContentValues values = new ContentValues();
        values.put(DeckScoreContract.USERID, userId);
        values.put(DeckScoreContract.DECKID, deckId);
        values.put(DeckScoreContract.QUIZ_DATE, quizDate);
        values.put(DeckScoreContract.SCORE, score);
        values.put(DeckScoreContract.TOTAL, total);
        values.put(DeckScoreContract.CORRECT, correct);
        values.put(DeckScoreContract.MISSED, missed);

        return values;
    }

    public _DeckScoreBaseItem getFBItem(){
        _DeckScoreBaseItem fbItem = new _DeckScoreBaseItem();
        fbItem.score = score;
        fbItem.total = total;
        fbItem.correct = correct;
        fbItem.missed = missed;

        return fbItem;
    }

/**************************************************************************************************/



}

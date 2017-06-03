package me.makeachoice.elephanttribe.model.item.flashcard;

import android.content.ContentValues;
import android.database.Cursor;

import me.makeachoice.elephanttribe.model.contract.flashcard.FlashcardContract;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;

/**
 * FlashcardItem is data object used to display information of cards owned by user; extends _FlashcardBaseItem
 */

public class FlashcardItem extends _FlashcardBaseItem {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    public long _id;
    public String userId;
    public String deckId;
    public String cardId;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public FlashcardItem(){
        userId = "";
        deckId = "";
        cardId = "";
        card = "";
        answer = "";
        picture = "";
        voice = "";
    }

    public FlashcardItem(DeckItem item){
        userId = item.userId;
        deckId = item.deckId;
        cardId = "";
        card = "";
        answer = "";
        picture = "";
        voice = "";
    }

    public FlashcardItem(_FlashcardBaseItem item){
        card = item.card;
        answer = item.answer;
        picture = item.picture;
        voice = item.voice;
    }

    public FlashcardItem(Cursor cursor){
        _id = cursor.getLong(FlashcardContract.INDEX_ID);
        userId = cursor.getString(FlashcardContract.INDEX_USERID);
        deckId = cursor.getString(FlashcardContract.INDEX_DECKID);
        cardId = cursor.getString(FlashcardContract.INDEX_CARDID);
        card = cursor.getString(FlashcardContract.INDEX_CARD);
        answer = cursor.getString(FlashcardContract.INDEX_ANSWER);
        picture = cursor.getString(FlashcardContract.INDEX_PICTURE);
        voice = cursor.getString(FlashcardContract.INDEX_VOICE);

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
        values.put(FlashcardContract.USERID, userId);
        values.put(FlashcardContract.DECKID, deckId);
        values.put(FlashcardContract.CARDID, cardId);
        values.put(FlashcardContract.CARD, card);
        values.put(FlashcardContract.ANSWER, answer);
        values.put(FlashcardContract.PICTURE, picture);
        values.put(FlashcardContract.VOICE, voice);

        return values;
    }

    public _FlashcardBaseItem getFBItem(){
        _FlashcardBaseItem fbItem = new _FlashcardBaseItem();
        fbItem.card = card;
        fbItem.answer = answer;
        fbItem.picture = picture;
        fbItem.voice = voice;

        return  fbItem;
    }

/**************************************************************************************************/


}

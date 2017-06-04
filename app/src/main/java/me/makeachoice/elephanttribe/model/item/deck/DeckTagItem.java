package me.makeachoice.elephanttribe.model.item.deck;

import android.content.ContentValues;
import android.database.Cursor;

import me.makeachoice.elephanttribe.model.contract.deck.DeckTagContract;

/**
 * DeckTagItem holds deck tag values
 */

public class DeckTagItem extends _DeckTagBaseItem {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    public long _id;
    public String userId;
    public String deckId;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckTagItem(){}

    public DeckTagItem(_DeckTagBaseItem item){
        tag = item.tag;
    }

    public DeckTagItem(DeckItem item, String tagValue){
        userId = item.userId;
        deckId = item.deckId;
        tag = tagValue;
    }


    public DeckTagItem(Cursor cursor){
        _id = cursor.getLong(DeckTagContract.INDEX_ID);
        userId = cursor.getString(DeckTagContract.INDEX_USERID);
        deckId = cursor.getString(DeckTagContract.INDEX_DECKID);
        tag = cursor.getString(DeckTagContract.INDEX_TAG);
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
        values.put(DeckTagContract.USERID, userId);
        values.put(DeckTagContract.DECKID, deckId);
        values.put(DeckTagContract.TAG, tag);

        return values;
    }

    public _DeckTagBaseItem getFBItem(){
        _DeckTagBaseItem fbItem = new _DeckTagBaseItem();
        fbItem.tag = tag;

        return fbItem;
    }

/**************************************************************************************************/



}

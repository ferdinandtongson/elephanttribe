package me.makeachoice.elephanttribe.model.item.deck;

import android.content.ContentValues;
import android.database.Cursor;

import me.makeachoice.elephanttribe.model.contract.deck.DeckContract;
import me.makeachoice.elephanttribe.model.item.user.UserItem;

/**
 * DeckItem is data object used to display information of decks owned by user; extends _DeckBaseItem
 */

public class DeckItem extends _DeckBaseItem {

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

    public DeckItem(){
        //deck info
        deck = "";
        description = "";
        cardCount = 0;

        //creator info
        creatorId = "";
        creator = "";
        creatorPic = "";

        //deck status
        cost = 0;
        accessType = DeckContract.ACCESS_PUBLIC;
        rating = 0;
        activityCount = 0;
        downloadCount = 0;
        status = DeckContract.STATUS_ACTIVE;

    };

    public DeckItem(UserItem item){
        userId = item.userId;

        //deck info
        deck = "";
        description = "";
        cardCount = 0;

        //user info
        creatorId = item.userId;
        creator = item.userName;
        creatorPic = "";

        //deck status
        cost = 0;
        accessType = DeckContract.ACCESS_PUBLIC;
        rating = 0;
        activityCount = 0;
        downloadCount = 0;
        status = DeckContract.STATUS_ACTIVE;

    };

    public DeckItem(_DeckBaseItem item){
        //deck info
        deck = item.deck;
        description = item.description;
        cardCount = item.cardCount;

        //user info
        creatorId = item.creatorId;
        creator = item.creator;
        creatorPic = item.creatorPic;

        //deck status
        cost = item.cost;
        accessType = item.accessType;
        rating = item.rating;
        activityCount = item.activityCount;
        downloadCount = item.downloadCount;
        status = item.status;
    }

    public DeckItem(Cursor cursor){
        _id = cursor.getLong(DeckContract.INDEX_ID);
        userId = cursor.getString(DeckContract.INDEX_USERID);
        deckId = cursor.getString(DeckContract.INDEX_DECKID);
        deck = cursor.getString(DeckContract.INDEX_DECK);
        description = cursor.getString(DeckContract.INDEX_DESCRIPTION);
        cardCount = cursor.getInt(DeckContract.INDEX_CARD_COUNT);
        creatorId = cursor.getString(DeckContract.INDEX_CREATORID);
        creator = cursor.getString(DeckContract.INDEX_CREATOR);
        creatorPic = cursor.getString(DeckContract.INDEX_CREATOR_PIC);
        cost = cursor.getDouble(DeckContract.INDEX_COST);
        accessType = cursor.getInt(DeckContract.INDEX_ACCESS_TYPE);
        rating = cursor.getDouble(DeckContract.INDEX_RATING);
        activityCount = cursor.getInt(DeckContract.INDEX_ACTIVITY_COUNT);
        downloadCount = cursor.getInt(DeckContract.INDEX_DOWNLOAD_COUNT);
        status = cursor.getInt(DeckContract.INDEX_STATUS);
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
        values.put(DeckContract.USERID, userId);
        values.put(DeckContract.DECKID, deckId);
        values.put(DeckContract.DECK, deck);
        values.put(DeckContract.DESCRIPTION, description);
        values.put(DeckContract.CARD_COUNT, cardCount);
        values.put(DeckContract.CREATORID, creatorId);
        values.put(DeckContract.CREATOR, creator);
        values.put(DeckContract.CREATOR_PIC, creatorPic);
        values.put(DeckContract.COST, cost);
        values.put(DeckContract.ACCESS_TYPE, accessType);
        values.put(DeckContract.RATING, rating);
        values.put(DeckContract.ACTIVITY_COUNT, activityCount);
        values.put(DeckContract.DOWNLOAD_COUNT, downloadCount);
        values.put(DeckContract.STATUS, status);

        return values;
    }

    public _DeckBaseItem getFBItem(){
        _DeckBaseItem fbItem = new _DeckBaseItem();
        fbItem.deck = deck;
        fbItem.description = description;
        fbItem.cardCount = cardCount;
        fbItem.creatorId = userId;
        fbItem.creator = creator;
        fbItem.creatorPic = creatorPic;
        fbItem.cost = cost;
        fbItem.accessType = accessType;
        fbItem.rating = rating;
        fbItem.activityCount = activityCount;
        fbItem.downloadCount = downloadCount;
        fbItem.status = status;

        return fbItem;
    }

/**************************************************************************************************/

}

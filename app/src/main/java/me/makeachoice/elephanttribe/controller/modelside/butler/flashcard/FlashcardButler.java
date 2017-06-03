package me.makeachoice.elephanttribe.controller.modelside.butler.flashcard;

import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.firebase.flashcard.FlashcardFirebase;
import me.makeachoice.elephanttribe.controller.modelside.loader.base.MyLoader;
import me.makeachoice.elephanttribe.controller.modelside.loader.flashcard.FlashcardLoader;
import me.makeachoice.elephanttribe.controller.modelside.query.flashcard.FlashcardQuery;
import me.makeachoice.elephanttribe.model.contract.flashcard.FlashcardContract;
import me.makeachoice.elephanttribe.model.item.flashcard.FlashcardItem;
import me.makeachoice.elephanttribe.utilities.DateTimeUtility;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * FlashcardButler handles the loading, saving, deleting and updating of flashcard data
 */

public class FlashcardButler extends MyButler implements MyLoader.OnCursorLoadListener{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //mList - data loaded from database
    private ArrayList<FlashcardItem> mList;

    //mLoader - loader class used to load data
    private FlashcardLoader mLoader;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public FlashcardButler(MyActivity activity, String userId){
        //get activity
        mActivity = activity;

        //get user id
        mUserId = userId;

        //get anonymousId
        mAnonymousId = activity.getString(R.string.anonymous);

        //initialize data list
        mList = new ArrayList<>();

        //initialize loader
        mLoader = new FlashcardLoader(mActivity, mUserId);
        mLoader.setOnCursorLoadListener(this);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Load Methods:
 *      void loadByDeckId(...) - load user flashcard data by deck id
 *      void loadByCardId(...) - load user flashcard data from database by cardId
 *      void loadByDeckCard(...) - load user flashcard data by deck id and card
 *      void loadByDeckAnswer(...) - load user flashcard data by deck id and answer
 *      void onLoadFinished(...) - called when cursor is loaded, convert cursor to item list array
 */
/**************************************************************************************************/
    /*
     * void loadByDeckId(...) - load user flashcard data by deck id
     */
    public void loadByDeckId(int loaderId, String deckId){
        mLoaderId = loaderId;

        mLoader.loadByDeckId(loaderId, deckId);
    }

    /*
     * void loadByCardId(...) - load user flashcard data from database by card id
     */
    public void loadByCardId(int loaderId, String cardId){
        mLoaderId = loaderId;

        mLoader.loadByCardId(loaderId, cardId);
    }

    /*
     * void loadByDeckIdCard(...) - load user flashcard data by deck id and card
     */
    public void loadByDeckIdCard(int loaderId, String deckId, String card){
        mLoaderId = loaderId;

        mLoader.loadByDeckIdCard(loaderId, deckId, card);
    }

    /*
     * void loadByDeckIdAnswer(...) - load user flashcard data by deck id and answer
     */
    public void loadByDeckIdAnswer(int loaderId, String deckId, String answer){
        mLoaderId = loaderId;

        mLoader.loadByDeckIdAnswer(loaderId, deckId, answer);
    }

    /*
     * void onLoadFinished(...) - called when cursor is loaded, convert cursor to item list array
     */
    public void onLoadFinished(Cursor cursor){
        //clear list
        mList.clear();

        //get size of cursor
        int count = cursor.getCount();

        //loop through cursor
        for(int i = 0; i < count; i++){
            //move cursor to position
            cursor.moveToPosition(i);

            //create item
            FlashcardItem item = new FlashcardItem(cursor);

            //add item to list
            mList.add(item);
        }

        //release loader from memory
        mLoader.destroyLoader(mLoaderId);

        //check onLoad listener is valid
        if(mLoadedListener != null){
            mLoadedListener.onLoaded(mList);
        }

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Save Methods:
 *      void save(...) - save flashcard item to database
 *      void saveToFirebase(...) - save flashcard item to firebase
 *      void saveToDatabase(...) - save flashcard to local database
 */
/**************************************************************************************************/
    /*
     * void save(...) - save flashcard item to database
     */
    public void save(FlashcardItem saveItem){
        //check if anonymous user
        if(mUserId.equals(mAnonymousId)){
            //anonymous user, generate unique card id
            saveItem.cardId = mAnonymousId + "_" + DateTimeUtility.getTimestamp();

            //save to local database only
            saveToDatabase(saveItem);
        }
        else{
            //save to firebase
            saveToFirebase(saveItem);
        }
    }

    /*
     * void saveToFirebase(...) - save flashcard item to firebase
     */
    private void saveToFirebase(FlashcardItem saveItem){
        //get firebase instance
        FlashcardFirebase fb = FlashcardFirebase.getInstance();

        //get firebase key to item
        saveItem.cardId = fb.addFlashcard(saveItem);

        //save to local database
        saveToDatabase(saveItem);
    }

    /*
     * void saveToDatabase(...) - save flashcard to local database
     */
    private void saveToDatabase(FlashcardItem saveItem){
        //get uri value to table
        Uri uri = FlashcardContract.CONTENT_URI;

        //save item to database
        Uri uriInsert = mActivity.getContentResolver().insert(uri, saveItem.getContentValues());

        //check onSaved listener is valid
        if(mSavedListener != null){
            mSavedListener.onSaved();
        }
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Update Methods:
 *      void update(...) - update flashcard item
 *      void updateFirebase(...) - update flashcard item in firebase
 *      void updateDatabase(...) - update flashcard item in local database
 */
/**************************************************************************************************/
    /*
     * void update(...) - update flashcard item
     */
    public void update(FlashcardItem updateItem){

        //check if anonymous user
        if(mUserId.equals(mAnonymousId)){
            //is anonymous, update local database only
            updateDatabase(updateItem);
        }
        else{
            //update firebase
            updateFirebase(updateItem);
        }
    }

    /*
     * void updateFirebase(...) - update flashcard item in firebase
     */
    private void updateFirebase(FlashcardItem updateItem){
        //todo - create update firebase
    }

    /*
     * void updateDatabase(...) - update flashcard item in local database
     */
    private void updateDatabase(FlashcardItem updateItem){
        //get cardId
        String cardId = updateItem.cardId;

        //get uri value to table
        Uri uri = FlashcardContract.CONTENT_URI;

        //update database
        int update = mActivity.getContentResolver().update(uri,
                updateItem.getContentValues(),
                FlashcardQuery.cardIdSelection,
                new String[]{mUserId, cardId});

        //check save listener is valid
        if(mSavedListener != null){
            mSavedListener.onSaved();
        }
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Delete Methods:
 *      void delete(...) - delete flashcard item
 *      void deleteFromFirebase(...) - delete flashcard from firebase
 *      void deleteFromDatabase(...) - delete flashcard from database
 */
/**************************************************************************************************/
    /*
     * void delete(...) - delete flashcard item
     */
    public void delete(FlashcardItem deleteItem){
        //check if anonymous
        if(mUserId.equals(mAnonymousId)){
            //is anonymous, delete from local database only
            deleteFromDatabase(deleteItem);
        }
        else{
            //delete from firebase
            deleteFromFirebase(deleteItem);
        }
    }

    /*
     * void deleteFromFirebase(...) - delete flashcard from firebase
     */
    private void deleteFromFirebase(FlashcardItem deleteItem){
        //todo - create delete from firebase
    }

    /*
     * void deleteFromDatabase(...) - delete flashcard from database
     */
    private void deleteFromDatabase(FlashcardItem deleteItem){
        //get firebase key
        String cardId = deleteItem.cardId;

        //get uri value to table
        Uri uri = FlashcardContract.CONTENT_URI;

        //remove from database
        int deleted = mActivity.getContentResolver().delete(uri,
                FlashcardQuery.cardIdSelection,
                new String[]{mUserId, cardId});

        //check delete listener is valid
        if(mDeletedListener != null){
            mDeletedListener.onDeleted(deleted);
        }
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize Starter Deck:
 *      int mCounter - keeps track of deck count
 *      ArrayList<FlashcardItem> mStarterToefl - starter toefl card items
 *
 *      void initializeStarterDeck() - loads then saves starter decks to database
 *      void saveStarterDeck(...) - saves start deck to database; recursive
 */
/**************************************************************************************************/

    //mCounter - keeps track of deck count
    private int mCounter;

    //mStarterCards - starter card items
    private ArrayList<FlashcardItem> mStarterCards;

    /*
     * void initializeStarterCard() - loads then saves starter cards to database
     */
    public void initializeStarterDeck(ArrayList<FlashcardItem> cards){
        //load starter toefl cards from flat file
        mStarterCards = cards;

        //set deck counter to zero
        mCounter = 0;

        //save starter cards
        saveStarterCard(mCounter);
    }

    /*
     * void saveStarterCard(...) - saves starter cards to database; recursive
     */
    private void saveStarterCard(int counter){
        //check if counter is greater than deck list
        if(counter < mStarterCards.size()){
            //get card item from list
            FlashcardItem item = mStarterCards.get(mCounter);

            //set onSave listener to recurse
            setOnSavedListener(new OnSavedListener() {
                @Override
                public void onSaved() {
                    //deck saved to database, increment counter
                    mCounter++;

                    //recursively call method
                    saveStarterCard(mCounter);
                }
            });

            //save deck item to database
            save(item);
        }
    }



/**************************************************************************************************/

}

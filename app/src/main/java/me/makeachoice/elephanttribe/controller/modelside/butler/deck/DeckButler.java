package me.makeachoice.elephanttribe.controller.modelside.butler.deck;

import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.firebase.deck.DeckFirebase;
import me.makeachoice.elephanttribe.controller.modelside.loader.base.MyLoader;
import me.makeachoice.elephanttribe.controller.modelside.loader.deck.DeckLoader;
import me.makeachoice.elephanttribe.controller.modelside.query.deck.DeckQuery;
import me.makeachoice.elephanttribe.model.contract.deck.DeckContract;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;
import me.makeachoice.elephanttribe.utilities.DateTimeUtility;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * DeckButler handles the loading, saving, deleting and updating of deck data
 */

public class DeckButler extends MyButler implements MyLoader.OnCursorLoadListener{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //mList - data loaded from database
    private ArrayList<DeckItem> mList;

    //mLoader - loader class used to load data
    private DeckLoader mLoader;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckButler(MyActivity activity, String userId){
        //get activity
        mActivity = activity;

        //get user id
        mUserId = userId;

        //get anonymousId
        mAnonymousId = activity.getString(R.string.anonymous);

        //initialize data list
        mList = new ArrayList<>();

        //initialize loader
        mLoader = new DeckLoader(mActivity, mUserId);
        mLoader.setOnCursorLoadListener(this);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Load Methods:
 *      void loadByUserId(...) - load user deck data from database
 *      void loadByFkey(...) - load user deck data with firebase key
 *      void loadByName(...) - load user deck data from database with deck name
 *      void loadByCreator(...) - load user deck data from database with creator
 *      void loadByStatus(...) - load user deck data from database with status
 *      void onLoadFinished(...) - called when cursor is loaded, convert cursor to item list array
 */
/**************************************************************************************************/
    /*
     * void loadByUserId(...) - load user deck data from database
     */
    public void loadByUserId(int loaderId){
        if(mUserId.equals(mAnonymousId)){
            mLoaderId = loaderId;

            mLoader.loadByUserId(mLoaderId);
        }
        else{
            //todo - need to check if network available
                //todo - if network, load from firebase
                //todo - no network, load from database
        }
    }

    /*
     * void loadByDeckId(...) - load user deck data with deck id
     */
    public void loadByDeckId(int loaderId, String deckId){
        mLoaderId = loaderId;

        mLoader.loadByDeckId(loaderId, deckId);
    }

    /*
     * void loadByStatus(...) - load user deck data from database with deck status
     */
    public void loadByStatus(int loaderId, String status){
        mLoaderId = loaderId;

        mLoader.loadByStatus(loaderId, status);
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
            DeckItem item = new DeckItem(cursor);

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
 */
/**************************************************************************************************/

    public void saveDeck(DeckItem saveItem){
        if(mUserId.equals(mAnonymousId)){
            //generate unique fkey
            saveItem.deckId = mAnonymousId + "_" + DateTimeUtility.getTimestamp();
            //save to database
            saveToDatabase(saveItem);
        }
        else{
            //save to firebase
            saveToFirebase(saveItem);
        }
    }

    private void saveToFirebase(DeckItem saveItem){
        DeckFirebase firebase = DeckFirebase.getInstance();

        //String key = firebase.addDeck(saveItem);
        //Log.d("Choice", "DeckButler.saveToFirebase: " + key);

        //todo - need to save to firebase then to local database

        //check onSaved listener is valid
        if(mSavedListener != null){
            mSavedListener.onSaved();
        }

    }

    private void saveToDatabase(DeckItem saveItem){

        //get uri value to table
        Uri uri = DeckContract.CONTENT_URI;

        //save item to database
        Uri mUri = mActivity.getContentResolver().insert(uri, saveItem.getContentValues());

        //check onSaved listener is valid
        if(mSavedListener != null){
            mSavedListener.onSaved();
        }
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Update Methods:
 */
/**************************************************************************************************/

    public void updateDeck(DeckItem updateItem){
        if(mUserId.equals(mAnonymousId)){
            //update local database
            updateDatabase(updateItem);
        }
        else{
            //update firebase
            updateFirebase(updateItem);
        }

    }

    private void updateFirebase(DeckItem updateItem){
        //todo - create update firebase
    }

    private void updateDatabase(DeckItem updateItem){
        //get deckId
        String deckId = updateItem.deckId;

        //get uri value to table
        Uri uri = DeckContract.CONTENT_URI;

        //update database
        int update = mActivity.getContentResolver().update(uri,
                updateItem.getContentValues(),
                DeckQuery.deckIdSelection,
                new String[]{mUserId, deckId});

        //check save listener is valid
        if(mSavedListener != null){
            mSavedListener.onSaved();
        }
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Delete Methods:
 */
/**************************************************************************************************/

    public void deleteDeck(DeckItem deleteItem){
        if(mUserId.equals(mAnonymousId)){
            //delete from local database
            deleteFromDatabase(deleteItem);
        }
        else{
            //delete from firebase
            deleteFromFirebase(deleteItem);
        }

    }

    private void deleteFromFirebase(DeckItem deleteItem){
        //todo - create delete from firebase
    }

    private void deleteFromDatabase(DeckItem deleteItem){
        //get deckId
        String deckId = deleteItem.deckId;

        //get uri value to table
        Uri uri = DeckContract.CONTENT_URI;

        //remove from database
        int deleted = mActivity.getContentResolver().delete(uri,
                DeckQuery.deckIdSelection,
                new String[]{mUserId, deckId});

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
 *      ArrayList<DeckItem> mStarterDecks - starter deck items
 *
 *      void initializeStarterDeck() - loads then saves starter decks to database
 *      void saveStarterDeck(...) - saves start deck to database; recursive
 */
/**************************************************************************************************/

    //mCounter - keeps track of deck count
    private int mCounter;

    //mStarterDecks - starter deck items
    private ArrayList<DeckItem> mStarterDecks;

    /*
     * void initializeStarterDeck() - loads then saves starter decks to database
     */
    public void initializeStarterDeck(){
        //load starter deck from flat file
        /*mStarterDecks = DeckData.getDeckInfoData(mActivity);

        //set deck counter to zero
        mCounter = 0;

        //save starter deck
        saveStarterDeck(mCounter);*/
    }

    /*
     * void saveStarterDeck(...) - saves start deck to database; recursive
     */
    private void saveStarterDeck(int counter){
        //check if counter is greater than deck list
        if(counter < mStarterDecks.size()){
            //get deck item from list
            DeckItem item = mStarterDecks.get(mCounter);

            //set onSave listener to recurse
            setOnSavedListener(new OnSavedListener() {
                @Override
                public void onSaved() {
                    //deck saved to database, increment counter
                    mCounter++;

                    //recursively call method
                    saveStarterDeck(mCounter);
                }
            });

            //save deck item to database
            saveDeck(item);
        }
    }

/**************************************************************************************************/

    public void downloadPublicDeck(){

        /*DeckFirebase firebase = DeckFirebase.getInstance();
        firebase.requestDeckPublic(new DeckFirebase.OnDataLoadListener() {
            @Override
            public void onDataLoaded(DataSnapshot dataSnapshot) {
                ArrayList<DeckItem> decks = new ArrayList<>();

                Log.d("Choice", "DeckButler.downloadPublicDeck: " + dataSnapshot.getChildrenCount());
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    DeckItem item = postSnapshot.getValue(DeckItem.class);
                    item.fkey = postSnapshot.getKey();
                    Log.d("Choice", "     fkey: " + item.fkey);
                    Log.d("Choice", "     deck: " + item.deck);
                    decks.add(item);
                }

                mLoadedListener.onLoaded(decks);
            }

            @Override
            public void onCancelled() {

            }
        });*/
    }
}

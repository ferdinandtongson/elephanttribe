package me.makeachoice.elephanttribe.controller.modelside.butler.deck;

import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.loader.base.MyLoader;
import me.makeachoice.elephanttribe.controller.modelside.loader.deck.DeckScoreLoader;
import me.makeachoice.elephanttribe.controller.modelside.query.deck.DeckScoreQuery;
import me.makeachoice.elephanttribe.model.contract.deck.DeckScoreContract;
import me.makeachoice.elephanttribe.model.item.deck.DeckScoreItem;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * DeckScoreButler handles the loading, saving, deleting and updating of deck score data
 */

public class DeckScoreButler extends MyButler implements MyLoader.OnCursorLoadListener{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //mList - data loaded from database
    private ArrayList<DeckScoreItem> mList;

    //mDeleteItem - data item to be deleted
    private DeckScoreItem mDeleteItem;

    //mLoader - loader class used to load data
    private DeckScoreLoader mLoader;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckScoreButler(MyActivity activity, String userId){
        //get activity
        mActivity = activity;

        //get user id
        mUserId = userId;

        //get anonymous id
        mAnonymousId = mActivity.getString(R.string.anonymous);

        //initialize data list
        mList = new ArrayList<>();

        //initialize loader
        mLoader = new DeckScoreLoader(mActivity, mUserId);
        mLoader.setOnCursorLoadListener(this);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Load Methods:
 *      void loadByUserId(...) - load deck score data by userId
 *      void loadByDeckId(...) - load user deck score data with deckId
 *      void loadByDeckQuizDate(...) - load deck score data with deckId and quiz date
 *      void onLoadFinished(...) - called when cursor is loaded, convert cursor to item list array
 */
/**************************************************************************************************/
    /*
     * void loadByUserId(...) - load deck score data by userId
     */
    public void loadByUserId(int loaderId){
        mLoaderId = loaderId;

        mLoader.loadByUserId(mLoaderId);
    }

    /*
     * void loadByDeckId(...) - load user deck score data with deckId
     */
    public void loadByDeckId(int loaderId, String deckId){
        mLoaderId = loaderId;

        mLoader.loadByDeckId(loaderId, deckId);
    }

    /*
     * void loadByDeckQuizDate(...) - load deck score data with deckId and quiz date
     */
    public void loadByDeckQuizDate(int loaderId, String deckId, String quizDate){
        mLoaderId = loaderId;

        mLoader.loadByDeckQuizDate(loaderId, deckId, quizDate);
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
            DeckScoreItem item = new DeckScoreItem(cursor);

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

    public void save(DeckScoreItem item){
        //check if NOT anonymous user
        if(mUserId.equals(mAnonymousId)){
            //anonymous, save to local database only
            saveToDatabase(item);
        }
        else{
            //not anonymous, save to firebase
            saveToFirebase(item);
        }
    }

    public void saveToFirebase(DeckScoreItem item){
        //Log.d("Choice", "UserButler.saveToFirebase: " + item.fkey);
        /*UserFirebase firebase = UserFirebase.getInstance();

        //get firebase key
        item.fkey = firebase.addUser(item);

        //save to local database
        saveToDatabase(item);*/
    }

    public void saveToDatabase(DeckScoreItem item){
        //get uri value to table
        Uri uri = DeckScoreContract.CONTENT_URI;

        //save item to database
        Uri uriValue = mActivity.getContentResolver().insert(uri, item.getContentValues());

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

    public void update(DeckScoreItem item){
        //check if NOT anonymous user
        if(mUserId.equals(mAnonymousId)){
            //anonymous, update local database only
            updateDatabase(item);
        }
        else{
            //not anonymous, update firebase
            updateFirebase(item);
        }
    }

    public void updateFirebase(DeckScoreItem item){
        //todo - create update firebase
    }

    public void updateDatabase(DeckScoreItem item){
         //get uri value to table
        Uri uri = DeckScoreContract.CONTENT_URI;

        //update database
        int update = mActivity.getContentResolver().update(uri,
                item.getContentValues(),
                DeckScoreQuery.deckQuizDateSelection,
                new String[]{mUserId, item.deckId, item.quizDate});


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

    public void delete(DeckScoreItem deleteItem){
        //check if NOT anonymous user
        if(mUserId.equals(mAnonymousId)){
            //anonymous, delete from local database only
            deleteFromDatabase(deleteItem);
        }
        else{
            //not anonymous, delete from firebase
            deleteFromFirebase(deleteItem);
        }
    }

    public void deleteFromFirebase(DeckScoreItem deleteItem){
        //todo - create delete from firebase
    }

    public void deleteFromDatabase(DeckScoreItem deleteItem){
        //get uri value to table
        Uri uri = DeckScoreContract.CONTENT_URI;

        //remove from database
        int deleted = mActivity.getContentResolver().delete(uri,
                DeckScoreQuery.deckQuizDateSelection,
                new String[]{mUserId, deleteItem.deckId, deleteItem.quizDate});

        //check delete listener is valid
        if(mDeletedListener != null){
            mDeletedListener.onDeleted(deleted);
        }
    }

/**************************************************************************************************/

}

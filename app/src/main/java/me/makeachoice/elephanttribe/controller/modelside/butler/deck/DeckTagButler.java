package me.makeachoice.elephanttribe.controller.modelside.butler.deck;

import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.loader.base.MyLoader;
import me.makeachoice.elephanttribe.controller.modelside.loader.deck.DeckTagLoader;
import me.makeachoice.elephanttribe.controller.modelside.query.deck.DeckTagQuery;
import me.makeachoice.elephanttribe.model.contract.deck.DeckTagContract;
import me.makeachoice.elephanttribe.model.item.deck.DeckTagItem;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * DeckTagButler handles the loading, saving, deleting and updating of deck tag data
 */

public class DeckTagButler extends MyButler implements MyLoader.OnCursorLoadListener{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //mList - data loaded from database
    private ArrayList<DeckTagItem> mList;

    //mDeleteItem - data item to be deleted
    private DeckTagItem mDeleteItem;

    //mLoader - loader class used to load data
    private DeckTagLoader mLoader;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckTagButler(MyActivity activity, String userId){
        //get activity
        mActivity = activity;

        //get user id
        mUserId = userId;

        //get anonymous id
        mAnonymousId = mActivity.getString(R.string.anonymous);

        //initialize data list
        mList = new ArrayList<>();

        //initialize loader
        mLoader = new DeckTagLoader(mActivity, mUserId);
        mLoader.setOnCursorLoadListener(this);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Load Methods:
 *      void loadByUserId(...) - load deck tag data by userId
 *      void loadByDeckId(...) - load user deck tag data with deckId
 *      void loadByTag(...) - load deck tag data by tag
 *      void loadByDeckTag(...) - load deck tag data with deckId and tag
 *      void onLoadFinished(...) - called when cursor is loaded, convert cursor to item list array
 */
/**************************************************************************************************/
    /*
     * void loadByUserId(...) - load deck tag data by userId
     */
    public void loadByUserId(int loaderId){
        mLoaderId = loaderId;

        mLoader.loadByUserId(mLoaderId);
    }

    /*
     * void loadByDeckId(...) - load user deck tag data with deckId
     */
    public void loadByDeckId(int loaderId, String deckId){
        mLoaderId = loaderId;

        mLoader.loadByDeckId(loaderId, deckId);
    }

    /*
     * void loadByTag(...) - load deck tag data by tag
     */
    public void loadByTag(int loaderId, String tag){
        mLoaderId = loaderId;

        mLoader.loadByTag(mLoaderId, tag);
    }

    /*
     * void loadByDeckTag(...) - load deck tag data with deckId and tag
     */
    public void loadByDeckTag(int loaderId, String deckId, String tag){
        mLoaderId = loaderId;

        mLoader.loadByDeckTag(loaderId, deckId, tag);
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
            DeckTagItem item = new DeckTagItem(cursor);

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

    public void save(DeckTagItem item){
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

    public void saveToFirebase(DeckTagItem item){
        //Log.d("Choice", "UserButler.saveToFirebase: " + item.fkey);
        /*UserFirebase firebase = UserFirebase.getInstance();

        //get firebase key
        item.fkey = firebase.addUser(item);

        //save to local database
        saveToDatabase(item);*/
    }

    public void saveToDatabase(DeckTagItem item){
        //get uri value to table
        Uri uri = DeckTagContract.CONTENT_URI;

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

    public void update(DeckTagItem item){
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

    public void updateFirebase(DeckTagItem item){
        //todo - create update firebase
    }

    public void updateDatabase(DeckTagItem item){
         //get uri value to table
        Uri uri = DeckTagContract.CONTENT_URI;

        //update database
        int update = mActivity.getContentResolver().update(uri,
                item.getContentValues(),
                DeckTagQuery.deckTagSelection,
                new String[]{mUserId, item.deckId, item.tag});


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

    public void delete(DeckTagItem deleteItem){
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

    public void deleteFromFirebase(DeckTagItem deleteItem){
        //todo - create delete from firebase
    }

    public void deleteFromDatabase(DeckTagItem deleteItem){
        //get uri value to table
        Uri uri = DeckTagContract.CONTENT_URI;

        //remove from database
        int deleted = mActivity.getContentResolver().delete(uri,
                DeckTagQuery.deckTagSelection,
                new String[]{mUserId, deleteItem.deckId, deleteItem.tag});

        //check delete listener is valid
        if(mDeletedListener != null){
            mDeletedListener.onDeleted(deleted);
        }
    }

/**************************************************************************************************/

}

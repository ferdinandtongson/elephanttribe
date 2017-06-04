package me.makeachoice.elephanttribe.controller.modelside.loader.deck;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import me.makeachoice.elephanttribe.controller.modelside.loader.base.MyLoader;
import me.makeachoice.elephanttribe.model.contract.deck.DeckTagContract;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * DeckTagLoader manages LoaderManager objects loading deck tag data from local database
 */

public class DeckTagLoader extends MyLoader {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private String mDeckId;
    private String mTag;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckTagLoader(MyActivity activity, String userId){
        //get current activity
        mActivity = activity;

        //get user id
        mUserId = userId;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Load Methods:
 *      void loadByUserId(...) - load deck tag data by userId
 *      void loadByDeckId(...) - load deck tag data by deckId
 *      void loadByTag(...) - load user deck tag data by tag
 *      void loadByDeckTag(...) - load deck tag data with deckId and tag
 */
/**************************************************************************************************/
    /*
     * void loadByUserId(...) - load deck tag data by userId
     */
    public void loadByUserId(int loaderId){

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = DeckTagContract.buildWithUserId(mUserId);
                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                DeckTagContract.PROJECTION,
                                null,
                                null,
                                DeckTagContract.DEFAULT_SORT_ORDER
                        );
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor){
                        //check valid listener
                        if(mListener != null){
                            mListener.onLoadFinished(cursor);
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> cursorLoader){
                        //does nothing
                    }
                });
    }

    /*
     * void loadByDeckId(...) - load deck tag data with deckId
     */
    public void loadByDeckId(int loaderId, String deckId){

        mDeckId = deckId;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = DeckTagContract.buildWithDeckId(mUserId, mDeckId);
                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                DeckTagContract.PROJECTION,
                                null,
                                null,
                                DeckTagContract.DEFAULT_SORT_ORDER
                        );
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor){
                        //check valid listener
                        if(mListener != null){
                            mListener.onLoadFinished(cursor);
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> cursorLoader){
                        //does nothing
                    }
                });
    }

    /*
     * void loadByTag(...) - load user deck tag data by tag
     */
    public void loadByTag(int loaderId, String tag){

        //get tag
        mTag = tag;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = DeckTagContract.buildWithTag(mUserId, mTag);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                DeckTagContract.PROJECTION,
                                null,
                                null,
                                DeckTagContract.DEFAULT_SORT_ORDER
                        );
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor){
                        //check valid listener
                        if(mListener != null){
                            mListener.onLoadFinished(cursor);
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> cursorLoader){
                        //does nothing
                    }
                });
    }

    /*
     * void loadByDeckTag(...) - load deck tag data with deckId and tag
     */
    public void loadByDeckTag(int loaderId, String deckId, String tag){
        //get deckId
        mDeckId = deckId;
        mTag = tag;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = DeckTagContract.buildWithDeckTag(mUserId, mDeckId, mTag);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                DeckTagContract.PROJECTION,
                                null,
                                null,
                                DeckTagContract.DEFAULT_SORT_ORDER
                        );
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor){
                        //check valid listener
                        if(mListener != null){
                            mListener.onLoadFinished(cursor);
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> cursorLoader){
                        //does nothing
                    }
                });
    }

/**************************************************************************************************/

}

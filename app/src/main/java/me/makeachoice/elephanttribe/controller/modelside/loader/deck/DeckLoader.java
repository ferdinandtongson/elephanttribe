package me.makeachoice.elephanttribe.controller.modelside.loader.deck;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import me.makeachoice.elephanttribe.controller.modelside.loader.base.MyLoader;
import me.makeachoice.elephanttribe.model.contract.deck.DeckContract;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * DeckLoader manages LoaderManager objects loading deck data from local database
 */

public class DeckLoader extends MyLoader{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private String mDeckId;
    private String mStatus;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckLoader(MyActivity activity, String userId){
        //get current activity
        mActivity = activity;

        //get user id
        mUserId = userId;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Load Methods:
 *      void loadByUserId(...) - load user deck data from database
 *      void loadByDeckId(...) - load user deck data with deck id
 *      void loadByStatus(...) - load user deck data from database with status
 */
/**************************************************************************************************/
    /*
     * void loadByUserId(...) - load user deck data from database
     */
    public void loadByUserId(int loaderId){

        mActivity.getSupportLoaderManager().initLoader(mLoaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = DeckContract.buildWithUserId(mUserId);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                DeckContract.PROJECTION,
                                null,
                                null,
                                DeckContract.DEFAULT_SORT_ORDER
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
     * void loadByDeckId(...) - load user deck data with deck id
     */
    public void loadByDeckId(int loaderId, String deckId){
        //get deck id
        mDeckId = deckId;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = DeckContract.buildWithDeckId(mUserId, mDeckId);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                DeckContract.PROJECTION,
                                null,
                                null,
                                DeckContract.DEFAULT_SORT_ORDER
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
     * void loadByStatus(...) - load user deck data from database with status
     */
    public void loadByStatus(int loaderId, String status){
        //get deck status
        mStatus = status;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = DeckContract.buildWithStatus(mUserId, mStatus);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                DeckContract.PROJECTION,
                                null,
                                null,
                                DeckContract.DEFAULT_SORT_ORDER
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

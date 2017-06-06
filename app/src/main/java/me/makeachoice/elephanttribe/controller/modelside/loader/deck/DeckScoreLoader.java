package me.makeachoice.elephanttribe.controller.modelside.loader.deck;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import me.makeachoice.elephanttribe.controller.modelside.loader.base.MyLoader;
import me.makeachoice.elephanttribe.model.contract.deck.DeckScoreContract;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * DeckScoreLoader manages LoaderManager objects loading deck score data from local database
 */

public class DeckScoreLoader extends MyLoader {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private String mDeckId;
    private String mQuizDate;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckScoreLoader(MyActivity activity, String userId){
        //get current activity
        mActivity = activity;

        //get user id
        mUserId = userId;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Load Methods:
 *      void loadByUserId(...) - load deck score data by userId
 *      void loadByDeckId(...) - load deck score data by deckId
 *      void loadByDeckQuizDate(...) - load user deck score data by deck id and quiz date
 */
/**************************************************************************************************/
    /*
     * void loadByUserId(...) - load deck score data by userId
     */
    public void loadByUserId(int loaderId){

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = DeckScoreContract.buildWithUserId(mUserId);
                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                DeckScoreContract.PROJECTION,
                                null,
                                null,
                                DeckScoreContract.DEFAULT_SORT_ORDER
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
     * void loadByDeckId(...) - load deck score data with deckId
     */
    public void loadByDeckId(int loaderId, String deckId){

        mDeckId = deckId;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = DeckScoreContract.buildWithDeckId(mUserId, mDeckId);
                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                DeckScoreContract.PROJECTION,
                                null,
                                null,
                                DeckScoreContract.DEFAULT_SORT_ORDER
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
     * void loadByDeckQuizDate(...) - load user deck score data by deck id and quiz date
     */
    public void loadByDeckQuizDate(int loaderId, String deckId, String quizDate){
        //get deckId
        mDeckId = deckId;

        //get quizDate
        mQuizDate = quizDate;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = DeckScoreContract.buildWithDeckQuizDate(mUserId, mDeckId, mQuizDate);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                DeckScoreContract.PROJECTION,
                                null,
                                null,
                                DeckScoreContract.DEFAULT_SORT_ORDER
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

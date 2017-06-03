package me.makeachoice.elephanttribe.controller.modelside.loader.flashcard;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import me.makeachoice.elephanttribe.controller.modelside.loader.base.MyLoader;
import me.makeachoice.elephanttribe.model.contract.flashcard.FlashcardContract;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * FlashcardLoader manages LoaderManager objects loading card data from local database
 */

public class FlashcardLoader extends MyLoader {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private String mDeckId;
    private String mCardId;
    private String mCard;
    private String mAnswer;

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public FlashcardLoader(MyActivity activity, String userId){
        //get current activity
        mActivity = activity;

        //get user id
        mUserId = userId;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Load Methods:
 *      void loadByDeckId(...) - load user flashcard data from database with deck id
 *      void loadByCardId(...) - load user flashcard data from database with firebase key
 *      void loadByDeckIdCard(...) - load user flashcard data from database with deck and card
 *      void loadByDeckIdAnswer(...) - load user flashcard data from database with deck and answer
 */
/**************************************************************************************************/
    /*
     * void loadByDeckId(...) - load user flashcard data from database with deck id
     */
    public void loadByDeckId(int loaderId, String deckId){
        //get deck name
        mDeckId = deckId;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = FlashcardContract.buildWithDeckId(mUserId, mDeckId);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                FlashcardContract.PROJECTION,
                                null,
                                null,
                                FlashcardContract.DEFAULT_SORT_ORDER
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
     * void loadByCardId(...) - load user flashcard data from database with cardId
     */
    public void loadByCardId(int loaderId, String cardId){
        //get cardId
        mCardId = cardId;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = FlashcardContract.buildWithCardId(mUserId, mCardId);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                FlashcardContract.PROJECTION,
                                null,
                                null,
                                FlashcardContract.DEFAULT_SORT_ORDER
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
     * void loadByDeckIdCard(...) - load user flashcard data from database with deck and card
     */
    public void loadByDeckIdCard(int loaderId, String deckId, String card){
        //get deck id
        mDeckId = deckId;

        //get card
        mCard = card;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = FlashcardContract.buildWithDeckIdCard(mUserId, mDeckId, mCard);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                FlashcardContract.PROJECTION,
                                null,
                                null,
                                FlashcardContract.DEFAULT_SORT_ORDER
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
     * void loadByDeckIdAnswer(...) - load user flashcard data from database with deck and answer
     */
    public void loadByDeckIdAnswer(int loaderId, String deckId, String answer){
        //get deck id
        mDeckId = deckId;

        //get answer
        mAnswer = answer;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = FlashcardContract.buildWithDeckIdAnswer(mUserId, mDeckId, mAnswer);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                FlashcardContract.PROJECTION,
                                null,
                                null,
                                FlashcardContract.DEFAULT_SORT_ORDER
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

package me.makeachoice.elephanttribe.controller.modelside.loader.user;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import me.makeachoice.elephanttribe.controller.modelside.loader.base.MyLoader;
import me.makeachoice.elephanttribe.model.contract.user.UserContract;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * UserLoader manages LoaderManager objects loading deck data from local database
 */

public class UserLoader extends MyLoader {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private String mId;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public UserLoader(MyActivity activity, String userId){
        //get current activity
        mActivity = activity;

        //get user id
        mUserId = userId;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Load Methods:
 *      void loadUser(...) - load all user data
 *      void loadUserById(...) - load user with id
 */
/**************************************************************************************************/
    /*
     * void loadUser(...) - load all user data
     */
    public void loadAll(int loaderId){

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = UserContract.buildAllUser();

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                UserContract.PROJECTION,
                                null,
                                null,
                                UserContract.DEFAULT_SORT_ORDER
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
     * void loadUserById(...) - load user with id
     */
    public void loadUserById(int loaderId, String id){
        //get id
        mId = id;

        mActivity.getSupportLoaderManager().initLoader(loaderId, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
                        //request cursor from local database
                        Uri uri = UserContract.buildWithId(mId);

                        //get cursor
                        return new CursorLoader(
                                mActivity,
                                uri,
                                UserContract.PROJECTION,
                                null,
                                null,
                                UserContract.DEFAULT_SORT_ORDER
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

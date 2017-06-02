package me.makeachoice.elephanttribe.controller.modelside.loader.base;

import android.database.Cursor;

import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * MyLoader abstract base class for Cursor loaders
 */

public abstract class MyLoader {

/**************************************************************************************************/
/*
 * Class Variables:
 *      mActivity - current activity
 *      mUserId - user id from firebase
 *      mListener - listens for cursor load events
 */
/**************************************************************************************************/

    //mActivity - current activity
    protected MyActivity mActivity;

    //mUserId - user id from firebase
    protected String mUserId;

    //mLoaderId - loader id number
    protected int mLoaderId;

    //mListener - listens for cursor load events
    protected OnCursorLoadListener mListener;
    public interface OnCursorLoadListener{
        void onLoadFinished(Cursor cursor);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Methods:
 *      void setOnCursorLoadListener(...) - set listener for cursor load events
 *      void destroyLoader(...) - destroy loader and any data managed by the loader
 */
/**************************************************************************************************/
    /*
     * void setOnCursorLoadListener(...) - set listener for cursor load events
     */
    public void setOnCursorLoadListener(OnCursorLoadListener listener){
        mListener = listener;
    }


    /*
     * void destroyLoader(...) - destroy loader and any data managed by the loader
     */
    public void destroyLoader(int loaderId){
        //destroy loader
        mActivity.getSupportLoaderManager().destroyLoader(loaderId);
    }


/**************************************************************************************************/
}

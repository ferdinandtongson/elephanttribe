package me.makeachoice.elephanttribe.controller.modelside.butler.base;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * MyButler abstract base class for Butlers
 */

public abstract class MyButler {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //mActivity - current activity
    protected MyActivity mActivity;

    //mUserId - user id from firebase
    protected String mUserId;

    //mLoaderId - loader id number
    protected int mLoaderId;

    //mAnonymousId - anonymous user id
    protected String mAnonymousId;

    //mLoadedListener - used to listen for cursor loaded events
    protected OnLoadedListener mLoadedListener;
    public interface OnLoadedListener{
        void onLoaded(ArrayList itemList);
    }

    //mSavedListener - used to listen for data saved events
    protected OnSavedListener mSavedListener;
    public interface OnSavedListener{
        void onSaved();
    }

    //mDeletedListener - used to listen for data delete events
    protected OnDeletedListener mDeletedListener;
    public interface OnDeletedListener{
        void onDeleted(int deleted);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setter Methods:
 *      void setOnLoadedListener(...) - set listener for onLoaded events
 *      void setOnSavedListener(...) - set listener for onSaved events
 *      void setOnDeletedListener(...) - set listener for onDeleted events
 */
/**************************************************************************************************/
    /*
     * void setOnLoadedListener(...) - set listener for onLoaded events
     */
    public void setOnLoadedListener(OnLoadedListener listener){
        mLoadedListener = listener;
    }

    /*
     * void setOnSavedListener(...) - set listener for onSaved events
     */
    public void setOnSavedListener(OnSavedListener listener){
        mSavedListener = listener;
    }

    /*
     * void setOnDeletedListener(...) - set listener for onDeleted events
     */
    public void setOnDeletedListener(OnDeletedListener listener){
        mDeletedListener = listener;
    }

/**************************************************************************************************/

}

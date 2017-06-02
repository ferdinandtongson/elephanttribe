package me.makeachoice.elephanttribe.controller.modelside.butler.user;

import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.firebase.user.UserFirebase;
import me.makeachoice.elephanttribe.controller.modelside.loader.base.MyLoader;
import me.makeachoice.elephanttribe.controller.modelside.loader.user.UserLoader;
import me.makeachoice.elephanttribe.controller.modelside.query.user.UserQuery;
import me.makeachoice.elephanttribe.model.contract.user.UserContract;
import me.makeachoice.elephanttribe.model.item.user.UserItem;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * UserButler handles the loading, saving, deleting and updating of user data
 */

public class UserButler extends MyButler implements MyLoader.OnCursorLoadListener{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //mList - data loaded from database
    private ArrayList<UserItem> mList;

    //mDeleteItem - data item to be deleted
    private UserItem mDeleteItem;

    //mLoader - loader class used to load data
    private UserLoader mLoader;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public UserButler(MyActivity activity, String userId){
        //get activity
        mActivity = activity;

        //get user id
        mUserId = userId;

        //get anonymous id
        mAnonymousId = mActivity.getString(R.string.anonymous);

        //initialize data list
        mList = new ArrayList<>();

        //initialize loader
        mLoader = new UserLoader(mActivity, mUserId);
        mLoader.setOnCursorLoadListener(this);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Load Methods:
 *      void loadAllUser(...) - load user data from database
 *      void loadUserByFkey(...) - load user data with userId
 *      void onLoadFinished(...) - called when cursor is loaded, convert cursor to item list array
 */
/**************************************************************************************************/
    /*
     * void loadAllUser(...) - load user data from database
     */
    public void loadAllUser(int loaderId){
        mLoaderId = loaderId;

        mLoader.loadAll(mLoaderId);
    }

    /*
     * void loadUserById(...) - load user data with id
     */
    public void loadUserById(int loaderId, String id){
        mLoaderId = loaderId;

        mLoader.loadUserById(loaderId, id);
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
            UserItem item = new UserItem(cursor);

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

    public void save(UserItem item){
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

    public void saveToFirebase(UserItem item){
        UserFirebase firebase = UserFirebase.getInstance();

        //get userId
        item.userId = firebase.addUser(item);

        //save to local database
        saveToDatabase(item);
    }

    public void saveToDatabase(UserItem item){
        //get uri value to table
        Uri uri = UserContract.CONTENT_URI;

        //save item to database
        mActivity.getContentResolver().insert(uri, item.getContentValues());

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

    public void update(UserItem item){
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

    public void updateFirebase(UserItem item){
        //todo - create update firebase
    }

    public void updateDatabase(UserItem item){
        //get userId
        String userId = item.userId;

        //get uri value to table
        Uri uri = UserContract.CONTENT_URI;

        //update database
        int update = mActivity.getContentResolver().update(uri,
                item.getContentValues(),
                UserQuery.idSelection,
                new String[]{userId});

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

    public void delete(UserItem deleteItem){
        if(!mUserId.equals(mAnonymousId)){
            //delete from firebase
            deleteFromFirebase(deleteItem);

            //delete from local database
            deleteFromDatabase(deleteItem);
        }
    }

    public void deleteFromFirebase(UserItem deleteItem){
        //todo - create delete from firebase
    }

    public void deleteFromDatabase(UserItem deleteItem){
        //get userId
        String userId = deleteItem.userId;

        //get uri value to table
        Uri uri = UserContract.CONTENT_URI;

        //remove from database
        int deleted = mActivity.getContentResolver().delete(uri,
                UserQuery.idSelection,
                new String[]{userId});

        //check delete listener is valid
        if(mDeletedListener != null){
            mDeletedListener.onDeleted(deleted);
        }
    }

/**************************************************************************************************/


}

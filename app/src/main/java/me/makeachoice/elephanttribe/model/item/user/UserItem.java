package me.makeachoice.elephanttribe.model.item.user;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.firebase.auth.FirebaseUser;

import me.makeachoice.elephanttribe.model.contract.user.UserContract;
import me.makeachoice.elephanttribe.utilities.DateTimeUtility;

/**
 * UserItem holds basic user registration data
 */

public class UserItem extends _UserBaseItem {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    public long _id;
    public String userId;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public UserItem(){
        userId = "";
        userName = "";
        email = "";
        registration = "";
    }

    public UserItem(_UserBaseItem item){
        userName = item.userName;
        email = item.email;
        registration = item.registration;
    }

    public UserItem(FirebaseUser fbUser){
        userId = fbUser.getUid();
        userName = fbUser.getDisplayName();
        email = fbUser.getEmail();
        registration = DateTimeUtility.getToday();
    }

    public UserItem(Cursor cursor){
        _id = cursor.getLong(UserContract.INDEX_ID);
        userId = cursor.getString(UserContract.INDEX_USERID);
        userName = cursor.getString(UserContract.INDEX_USER);
        email = cursor.getString(UserContract.INDEX_EMAIL);
        registration = cursor.getString(UserContract.INDEX_REGISTRATION);
    }



/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Methods:
 */
/**************************************************************************************************/

    public ContentValues getContentValues(){
        //create content value
        ContentValues values = new ContentValues();
        values.put(UserContract.USERID, userId);
        values.put(UserContract.USERNAME, userName);
        values.put(UserContract.EMAIL, email);
        values.put(UserContract.REGISTRATION, registration);

        return values;
    }

    public _UserBaseItem getFBItem(){
        _UserBaseItem fbItem = new _UserBaseItem();
        fbItem.userName = userName;
        fbItem.email = email;
        fbItem.registration = registration;

        return fbItem;
    }

/**************************************************************************************************/



}

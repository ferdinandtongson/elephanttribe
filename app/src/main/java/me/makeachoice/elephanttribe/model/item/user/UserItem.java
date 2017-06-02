package me.makeachoice.elephanttribe.model.item.user;

import android.content.ContentValues;
import android.database.Cursor;

import me.makeachoice.elephanttribe.model.contract.user.UserContract;

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

    public UserItem(){}

    public UserItem(_UserBaseItem item){
        user = item.user;
        email = item.email;
        registration = item.registration;
    }


    public UserItem(Cursor cursor){
        _id = cursor.getLong(UserContract.INDEX_ID);
        userId = cursor.getString(UserContract.INDEX_USERID);
        user = cursor.getString(UserContract.INDEX_USER);
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
        values.put(UserContract.COLUMN_USERID, userId);
        values.put(UserContract.COLUMN_USER, user);
        values.put(UserContract.COLUMN_EMAIL, email);
        values.put(UserContract.COLUMN_REGISTRATION, registration);

        return values;
    }

    public _UserBaseItem getFBItem(){
        _UserBaseItem fbItem = new _UserBaseItem();
        fbItem.user = user;
        fbItem.email = email;
        fbItem.registration = registration;

        return fbItem;
    }

/**************************************************************************************************/



}

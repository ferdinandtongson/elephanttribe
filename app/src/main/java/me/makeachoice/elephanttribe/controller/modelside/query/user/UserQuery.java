package me.makeachoice.elephanttribe.controller.modelside.query.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import me.makeachoice.elephanttribe.model.contract.user.UserContract;
import me.makeachoice.elephanttribe.model.db.DBHelper;

/**
 * UserQuery helps in building user table queries to the local database
 */

public class UserQuery {

/**************************************************************************************************/
/*
 * Query builder:
 */
/**************************************************************************************************/

    //mQueryBuilder - sqliteQueryBuilder object
    private static final SQLiteQueryBuilder mQueryBuilder;

    static{
        //initialize queryBuilder
        mQueryBuilder = new SQLiteQueryBuilder();

        //set builder to query user table
        mQueryBuilder.setTables(UserContract.TABLE_NAME);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Selection Variables:
 */
/**************************************************************************************************/

    //allSelection - *
    public final static String allSelection = "*";

    //idSelection - user.user_id = ?
    public final static String idSelection = UserContract.TABLE_NAME + "." +
            UserContract.USERNAME + " = ? ";

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Cursor Methods:
 *      Cursor getAll(...) - get all users
 *      Cursor getUserById(...) - get users by userId
 */
/**************************************************************************************************/
    /*
     * Cursor getAll(...) - get all users
     */
    public static Cursor getAll(DBHelper dbHelper, Uri uri, String[] prj, String sort){

        //query from user table
        return UserQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                allSelection,
                new String[]{},
                null,
                null,
                sort
        );
    }

    /*
     * Cursor getUserById(...) - get users by userId
     */
    public static Cursor getUserById(DBHelper dbHelper, Uri uri, String[] prj, String sort){
        //"content://CONTENT_AUTHORITY/user/user_id/[userId]
        String userId = UserContract.getId(uri);

        //query from user table
        return UserQuery.mQueryBuilder.query(
                dbHelper.getReadableDatabase(),
                prj,
                idSelection,
                new String[]{userId},
                null,
                null,
                sort
        );
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Database Methods:
 *      Uri insertUser(...) - insert user into database
 *      int deleteUser(...) - delete user from database
 *      int updateUser(...) - update user in database
 */
/**************************************************************************************************/
    /*
     * Uri insert(...) - insert into database
     */
    public static Uri insert(SQLiteDatabase db, ContentValues values){
        long _id = -1;

        try{
            _id = db.insert(UserContract.TABLE_NAME, null, values);
        }
        catch(SQLException exception){
            Log.e("Choice", "UserQuery.insert() - " + exception);
            throw exception;
        }

        return UserContract.buildUser(_id);
    }

    /*
     * int delete(...) - delete from database
     */
    public static int delete(SQLiteDatabase db, Uri uri, String selection, String[] args){
        //number of rows deleted
        int rowsDeleted = -1;

        try{
            rowsDeleted = db.delete(UserContract.TABLE_NAME, selection, args);
        }
        catch(SQLException exception){
            Log.e("Choice", "UserQuery.delete() - " + exception);
            throw exception;
        }

        return rowsDeleted;
    }

    /*
     * int update(...) - update database
     */
    public static int update(SQLiteDatabase db, Uri uri, ContentValues values, String selection,
                                 String[] args){
        //number of rows updated
        int rowsUpdated = -1;

        try{
            rowsUpdated = db.update(UserContract.TABLE_NAME, values, selection, args);
        }
        catch(SQLException exception){
            Log.e("Choice", "UserQuery.update() - " + exception);
            throw exception;
        }

        return rowsUpdated;
    }


/**************************************************************************************************/



}

package me.makeachoice.elephanttribe.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.makeachoice.elephanttribe.model.contract.flashcard.FlashcardContract;
import me.makeachoice.elephanttribe.model.contract.user.UserContract;

/**
 * DBHelper extends SQLiteOpenHelper and helps in creating, updating and dropping db tables
 */

public class DBHelper extends SQLiteOpenHelper {

/**************************************************************************************************/
/*
 * Class Variables:
 *      DATABASE_VERSION - current database version for app
 *      DATABASE_NAME - database name space reserved for app
 */
/**************************************************************************************************/
    //DATABASE_VERSION - current database version for app
    private static final int DATABASE_VERSION = 1;

    //DATABASE_NAME - database name space reserved for app
    public static final String DATABASE_NAME = "elephanttribe.db";

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Methods:
 *      void onCreate(...) - called when the database is first opened
 *      void onUpgrade(...) - called when the database version is being upgraded
 *      void dropTable(...) - used to drop database tables
 */
/**************************************************************************************************/
    /*
     * void onCreate(...) - called when the database is first opened
     */
    @Override
    public void onCreate(SQLiteDatabase sql){
        sql.execSQL(UserContract.CREATE_TABLE);
        sql.execSQL(FlashcardContract.CREATE_TABLE);
    }

    /*
     * void onUpgrade(...) - called when the database version is being upgraded
     */
    @Override
    public void onUpgrade(SQLiteDatabase sql, int oldVersion, int newVersion){
        dropTable(sql, UserContract.TABLE_NAME);
        dropTable(sql, FlashcardContract.TABLE_NAME);

        onCreate(sql);
    }

    /*
     * void dropTable(...) - used to drop database tables
     */
    public void dropTable(SQLiteDatabase sql, String table){
        sql.execSQL("DROP TABLE IF EXISTS " + table);
    }

/**************************************************************************************************/


}

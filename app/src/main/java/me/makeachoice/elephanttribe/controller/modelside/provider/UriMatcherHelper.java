package me.makeachoice.elephanttribe.controller.modelside.provider;

import android.content.UriMatcher;

import java.util.HashMap;

import me.makeachoice.elephanttribe.model.contract.user.UserContract;

import static me.makeachoice.elephanttribe.model.contract.base.MyContract.CONTENT_AUTHORITY;

/**
 * UriMatcherHelper assists in add Uri values into the UriMatcher object
 */

public class UriMatcherHelper {

/**************************************************************************************************/
/*
 * Class Variables:
 *      dbUriMatcher - used to access UriMatcher object used by FlashLearning
 *      UriMatcher buildUriMatcher() - used to create singleton UriMatcher object
 */
/**************************************************************************************************/
    //dbUriMatcher - used to access UriMatcher object used by FlashLearning
    public final static UriMatcher dbUriMatcher = buildUriMatcher();

    /*
     * UriMatcher buildUriMatcher() - used to create singleton UriMatcher object
     */
    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        mContentMap = new HashMap<>();

        //user: range (100 - 199)
        addUserUri(matcher);

        return matcher;
    }

    private static HashMap<Integer,String> mContentMap;

    public static String getContentType(int key){
        return mContentMap.get(key);
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * User Uri: (100 - 199)
 *      void addUserUri(...) - add uri values to uriMatcher object
 */
/**************************************************************************************************/

    //user uri variables (100 - 199)
    public final static int USER = 100;
    public final static int USER_WITH_ID = 101;

    /*
     * void addUserUri(...) - add uri values to uriMatcher object
     */
    private static void addUserUri(UriMatcher matcher){
        String path = UserContract.PATH;

        //"content://CONTENT_AUTHORITY/user
        matcher.addURI(CONTENT_AUTHORITY, path, USER);
        mContentMap.put(USER, UserContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/user/user_id/[userId]
        String idPath = path + "/" + UserContract.USERID + "/*";
        matcher.addURI(CONTENT_AUTHORITY, idPath, USER_WITH_ID);
        mContentMap.put(USER_WITH_ID, UserContract.CONTENT_ITEM_TYPE);

    }


/**************************************************************************************************/


}

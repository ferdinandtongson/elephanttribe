package me.makeachoice.elephanttribe.controller.modelside.provider;

import android.content.UriMatcher;

import java.util.HashMap;

import me.makeachoice.elephanttribe.model.contract.flashcard.FlashcardContract;
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

        //flashcard: range (300 - 399)
        addUriFlashcard(matcher);

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







/**************************************************************************************************/
/*
 * Flashcard Uri: (300 - 399)
 *      void addUriFlashcard(...) - add uri values to uriMatcher object
 */
/**************************************************************************************************/

    //card uri variables (300 - 399)
    public static final int FLASHCARD = 300;
    public static final int FLASHCARD_WITH_USERID = 301;
    public static final int FLASHCARD_WITH_DECKID = 302;
    public static final int FLASHCARD_WITH_CARDID = 303;
    public static final int FLASHCARD_WITH_DECK_CARD = 304;
    public static final int FLASHCARD_WITH_DECK_ANSWER = 305;

    /*
     * void addUriFlashcard(...) - add uri values to uriMatcher object
     */
    private static void addUriFlashcard(UriMatcher matcher){
        String path = FlashcardContract.PATH;

        //"content://CONTENT_AUTHORITY/flashcard
        matcher.addURI(CONTENT_AUTHORITY, path, FLASHCARD);
        mContentMap.put(FLASHCARD, FlashcardContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/flashcard/[userId]
        String userIdPath = path + "/*";
        matcher.addURI(CONTENT_AUTHORITY, userIdPath, FLASHCARD_WITH_USERID);
        mContentMap.put(FLASHCARD_WITH_USERID, FlashcardContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]
        String deckIdPath = userIdPath + "/" + FlashcardContract.DECKID + "/*";
        matcher.addURI(CONTENT_AUTHORITY, deckIdPath, FLASHCARD_WITH_DECKID);
        mContentMap.put(FLASHCARD_WITH_DECKID, FlashcardContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/flashcard/[userId]/cardId/[cardId]
        String cardIdPath = userIdPath + "/" + FlashcardContract.CARDID + "/*";
        matcher.addURI(CONTENT_AUTHORITY, cardIdPath, FLASHCARD_WITH_CARDID);
        mContentMap.put(FLASHCARD_WITH_CARDID, FlashcardContract.CONTENT_ITEM_TYPE);

        //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]/card/[card]
        String deckIdCardPath = userIdPath + "/" + FlashcardContract.DECKID + "/*/" +
                FlashcardContract.CARD + "/*";
        matcher.addURI(CONTENT_AUTHORITY, deckIdCardPath, FLASHCARD_WITH_DECK_CARD);
        mContentMap.put(FLASHCARD_WITH_DECK_CARD, FlashcardContract.CONTENT_ITEM_TYPE);

        //"content://CONTENT_AUTHORITY/flashcard/[userId]/deckId/[deckId]/answer/[answer]
        String deckIdAnswerPath = userIdPath + "/" + FlashcardContract.DECKID + "/*/" +
                FlashcardContract.ANSWER + "/*";
        matcher.addURI(CONTENT_AUTHORITY, deckIdAnswerPath, FLASHCARD_WITH_DECK_ANSWER);
        mContentMap.put(FLASHCARD_WITH_DECK_ANSWER, FlashcardContract.CONTENT_TYPE);

    }

/**************************************************************************************************/


}

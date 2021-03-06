package me.makeachoice.elephanttribe.controller.modelside.provider;

import android.content.UriMatcher;

import java.util.HashMap;

import me.makeachoice.elephanttribe.model.contract.deck.DeckContract;
import me.makeachoice.elephanttribe.model.contract.deck.DeckScoreContract;
import me.makeachoice.elephanttribe.model.contract.deck.DeckTagContract;
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

        //deck: range (200 - 299)
        addDeckUri(matcher);

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
 * Deck Uri: (200 - 299)
 *      void addDeckUri(...) - add uri values to uriMatcher object
 */
/**************************************************************************************************/

    //deck uri variables (200 - 299)
    public static final int DECK = 200;
    public static final int DECK_WITH_USERID = 201;
    public static final int DECK_WITH_DECKID = 202;
    public static final int DECK_WITH_STATUS = 203;

    //deck tag uri variables
    public static final int DECK_TAG = 220;
    public static final int DECK_TAG_WITH_USERID = 221;
    public static final int DECK_TAG_WITH_DECKID = 222;
    public static final int DECK_TAG_WITH_TAG = 223;
    public static final int DECK_TAG_WITH_DECK_TAG = 224;

    //deck tag uri variables
    public static final int DECK_SCORE = 230;
    public static final int DECK_SCORE_WITH_USERID = 231;
    public static final int DECK_SCORE_WITH_DECKID = 232;
    public static final int DECK_SCORE_WITH_DECK_QUIZ = 234;

    /*
     * void addDeckUri(...) - add uri values to uriMatcher object
     */
    private static void addDeckUri(UriMatcher matcher){
        String path = DeckContract.PATH;

        //"content://CONTENT_AUTHORITY/deck
        matcher.addURI(CONTENT_AUTHORITY, path, DECK);
        mContentMap.put(DECK, DeckContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/deck/[userId]
        String userIdPath = path + "/*";
        matcher.addURI(CONTENT_AUTHORITY, userIdPath, DECK_WITH_USERID);
        mContentMap.put(DECK_WITH_USERID, DeckContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/deck/[userId]/deckId/[deckId]
        String deckIdPath = userIdPath + "/" + DeckContract.DECKID + "/*";
        matcher.addURI(CONTENT_AUTHORITY, deckIdPath, DECK_WITH_DECKID);
        mContentMap.put(DECK_WITH_DECKID, DeckContract.CONTENT_ITEM_TYPE);

        //"content://CONTENT_AUTHORITY/deck/[userId]/status/[status]
        String statusPath = userIdPath + "/" + DeckContract.STATUS + "/*";
        matcher.addURI(CONTENT_AUTHORITY, statusPath, DECK_WITH_STATUS);
        mContentMap.put(DECK_WITH_STATUS, DeckContract.CONTENT_TYPE);


        String pathTag = DeckTagContract.PATH;

        //"content://CONTENT_AUTHORITY/deckTag
        matcher.addURI(CONTENT_AUTHORITY, pathTag, DECK_TAG);
        mContentMap.put(DECK_TAG, DeckTagContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/deckTag/userId/[userId]
        String tagUserIdPath = pathTag + "/" + DeckTagContract.USERID + "/*";
        matcher.addURI(CONTENT_AUTHORITY, tagUserIdPath, DECK_TAG_WITH_USERID);
        mContentMap.put(DECK_TAG_WITH_USERID, DeckTagContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/deckTag/[userId]/deckId/[deckId]
        String tagDeckIdPath = pathTag + "/*/" +
                DeckTagContract.DECKID + "/*";
        matcher.addURI(CONTENT_AUTHORITY, tagDeckIdPath, DECK_TAG_WITH_DECKID);
        mContentMap.put(DECK_TAG_WITH_DECKID, DeckTagContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/deckTag/[userId]/tag/[tag]
        String deckTagPath = pathTag + "/*/" +
                DeckTagContract.TAG + "/*";
        matcher.addURI(CONTENT_AUTHORITY, deckTagPath, DECK_TAG_WITH_TAG);
        mContentMap.put(DECK_TAG_WITH_TAG, DeckTagContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/deckTag/[[userId]/deckId/[deckId]/tag/[tag]
        String tagDeckTagPath = pathTag + "/*/" +
                DeckTagContract.DECKID + "/*/" +
                DeckTagContract.TAG + "/*";
        matcher.addURI(CONTENT_AUTHORITY, tagDeckTagPath, DECK_TAG_WITH_DECK_TAG);
        mContentMap.put(DECK_TAG_WITH_DECK_TAG, DeckTagContract.CONTENT_ITEM_TYPE);


        String pathScore = DeckScoreContract.PATH;

        //"content://CONTENT_AUTHORITY/deckScore
        matcher.addURI(CONTENT_AUTHORITY, pathScore, DECK_SCORE);
        mContentMap.put(DECK_SCORE, DeckScoreContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/deckScore/userId/[userId]
        String scoreUserIdPath = pathScore + "/" + DeckScoreContract.USERID + "/*";
        matcher.addURI(CONTENT_AUTHORITY, scoreUserIdPath, DECK_SCORE_WITH_USERID);
        mContentMap.put(DECK_SCORE_WITH_USERID, DeckScoreContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/deckScore/[userId]/deckId/[deckId]
        String scoreDeckIdPath = pathScore + "/*/" +
                DeckScoreContract.DECKID + "/*";
        matcher.addURI(CONTENT_AUTHORITY, scoreDeckIdPath, DECK_SCORE_WITH_DECKID);
        mContentMap.put(DECK_SCORE_WITH_DECKID, DeckScoreContract.CONTENT_TYPE);

        //"content://CONTENT_AUTHORITY/deckScore/[[userId]/deckId/[deckId]/tag/[tag]
        String scoreDeckQuizPath = pathScore + "/*/" +
                DeckScoreContract.DECKID + "/*/" +
                DeckScoreContract.QUIZ_DATE + "/*";
        matcher.addURI(CONTENT_AUTHORITY, scoreDeckQuizPath, DECK_SCORE_WITH_DECK_QUIZ);
        mContentMap.put(DECK_SCORE_WITH_DECK_QUIZ, DeckScoreContract.CONTENT_ITEM_TYPE);

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

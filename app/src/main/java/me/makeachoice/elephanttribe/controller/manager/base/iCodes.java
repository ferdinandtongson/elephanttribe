package me.makeachoice.elephanttribe.controller.manager.base;

/**
 * iCodes is an interface class holding application codes
 */

public interface iCodes {

/**************************************************************************************************/
/*
 * Loader Codes:
 */
/**************************************************************************************************/

    final static int LOADER_USER = 1000;
    final static int LOADER_DECK = 1100;
    final static int LOADER_DECK_TAG = 1101;
    final static int LOADER_DECK_SCORE = 1102;
    final static int LOADER_FLASHCARD = 1200;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Dialog Codes:
 */
/**************************************************************************************************/

    final static String DIA_DECK_INPUT = "diaDeckInput";
    final static String DIA_TAG_INPUT = "diaTagInput";

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Request Code:
 */
/**************************************************************************************************/

    final static int RC_SIGN_IN = 100;

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Flashcard Mode:
 */
/**************************************************************************************************/

    final static int FLASHCARD_MODE_SIMPLE = 1;
    final static int FLASHCARD_MODE_CHOICE = 2;

    final static int FLASHCARD_DISPLAY_CARD = 1;
    final static int FLASHCARD_DISPLAY_ANSWER = 2;
    final static int FLASHCARD_DISPLAY_RANDOM = 3;

/**************************************************************************************************/


}

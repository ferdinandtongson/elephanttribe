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
    final static int LOADER_FLASHCARD = 1200;

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

    final static int MODE_FLASHCARD_SIMPLE = 1;
    final static int MODE_FLASHCARD_CHOICE = 2;

    final static int DISTRACTOR_TYPE_FLASHCARD = 1;
    final static int DISTRACTOR_TYPE_ANSWER = 2;
    final static int DISTRACTOR_TYPE_RANDOM = 3;

/**************************************************************************************************/


}

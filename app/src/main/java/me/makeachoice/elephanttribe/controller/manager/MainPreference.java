package me.makeachoice.elephanttribe.controller.manager;

import android.content.Context;
import android.content.SharedPreferences;

import me.makeachoice.elephanttribe.R;

/**
 * MainPreference manages access to user shared preference data
 */

public class MainPreference {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //PREF_FIRST_TIME - first time login
    private final static String PREF_FIRST_TIME = "firstTime";

    //Flashcard Preference codes
    private final static String PREF_CARD_MODE = "flashcardMode";
    private final static String PREF_CARD_RANDOMIZED = "flashcardRandomized";
    private final static String PREF_CARD_ANIM_DURATION = "animationDuration";


    private SharedPreferences mPref;
    private Boss mBoss;
    private String mUserId;

    private int mCardAnimDuration;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public MainPreference(Boss boss){
        mBoss = boss;

        mCardAnimDuration = mBoss.getResources().getInteger(R.integer.card_flip_time_full);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialization Method:
 */
/**************************************************************************************************/

    public void requestUser(String userId){
        //get user id
        mUserId = userId;

        //get user specific shared preference
        mPref = mBoss.getSharedPreferences(mUserId, Context.MODE_PRIVATE);

        //check if user has already created a shared preference
        if(!mPref.contains(mUserId)){
            setPreference(mUserId, userId);
            setPreference(PREF_FIRST_TIME, true);
        }
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Getter Methods:
 */
/**************************************************************************************************/

    public boolean getFirstTime(){
        return mPref.getBoolean(PREF_FIRST_TIME, true);
    }

    public void setFirstTime(boolean isFirst){
        setPreference(PREF_FIRST_TIME, isFirst);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Flashcard Preferences:
 *      int getFlashcardMode() - flashcard mode, simple card flip or multiple choice mode
 *      boolean getFlashcardAutoFlip() - flashcard simple mode, automatic card flip status flag
 */
/**************************************************************************************************/

    public int getFlashcardMode(){
        return mPref.getInt(PREF_CARD_MODE, Boss.MODE_FLASHCARD_SIMPLE);
    }

    public void setFlashcardMode(int mode){
        setPreference(PREF_CARD_MODE, mode);
    }

    public boolean getFlashcardRandomized(){
        return mPref.getBoolean(PREF_CARD_RANDOMIZED, true);
    }

    public void setFlashcardRandomized(boolean isRandom){
        setPreference(PREF_CARD_RANDOMIZED, isRandom);
    }

    public long getAnimationDuration(){
        return mPref.getLong(PREF_CARD_ANIM_DURATION, mCardAnimDuration);
    }

    public void setFlashcardAnimationDuration(int millisec){
        setPreference(PREF_CARD_ANIM_DURATION, millisec);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setter Methods:
 *      void setPreference(...) - set preference value
 */
/**************************************************************************************************/
    /*
     * void setPreference(...) - set preference value
     */
    public void setPreference(String key, boolean value){
        //get shared preferences editor
        SharedPreferences.Editor editor = mBoss.getSharedPreferences(mUserId, Context.MODE_PRIVATE).edit();

        //update value
        editor.putBoolean(key, value);

        //commit change
        editor.commit();
    }

    public void setPreference(String key, int value){
        //get shared preferences editor
        SharedPreferences.Editor editor = mBoss.getSharedPreferences(mUserId, Context.MODE_PRIVATE).edit();

        //update value
        editor.putInt(key, value);

        //commit change
        editor.commit();
    }

    public void setPreference(String key, String value){
        //get shared preferences editor
        SharedPreferences.Editor editor = mBoss.getSharedPreferences(mUserId, Context.MODE_PRIVATE).edit();

        //update value
        editor.putString(key, value);

        //commit change
        editor.commit();
    }

/**************************************************************************************************/

}

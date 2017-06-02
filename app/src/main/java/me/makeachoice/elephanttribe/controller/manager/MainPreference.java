package me.makeachoice.elephanttribe.controller.manager;

import android.content.Context;
import android.content.SharedPreferences;

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

    private SharedPreferences mPref;
    private Boss mBoss;
    private String mUserId;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public MainPreference(Boss boss){
        mBoss = boss;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialization Method:
 */
/**************************************************************************************************/

    public void initialize(String userId){
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

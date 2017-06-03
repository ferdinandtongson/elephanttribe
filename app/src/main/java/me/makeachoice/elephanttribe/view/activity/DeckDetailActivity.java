package me.makeachoice.elephanttribe.view.activity;

import android.os.Bundle;

import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**************************************************************************************************/
/*
 * DeckDetailActivity
 * The HouseKeeper does most of the work for the activity lifecycle, all the activity needs to do is
 * notify the Boss application that it is the current activity and get the housekeeper. The main purpose
 * for doing this is to decouple the logic of the activity from the AndroidManifest file.
 *
 * MyActivity Class Variables:
 *      Bridge mBridge - Bridge interface
 *      OptionsMenuListener mMenuListener - OptionsMenuListener interface
 *      ContextItemSelectedListener mContextItemListener - ContextItemSelectedListener interface
 *      PermissionListener mPermissionListener - PermissionListener interface
 *
 * MyActivity Inherited Methods:
 *      void setOptionsMenuListener() - set options menu listener
 *      void setContextItemSelectedListener() - set context menu item select listener
 *      void setPermissionListener() - set permission result listener
 *      void finishActivity() - closes the activity
 *      void showPermissionExplanation(...) - show alert dialog explaining permission request
 *      void requestPermission(...) - request permission from user
 */

/**************************************************************************************************/

public class DeckDetailActivity extends MyActivity {

/**************************************************************************************************/
/*
 * Class Variables:
 *      String KEEPER_NAME - housekeeper name
 */
/**************************************************************************************************/

    //KEEPER_NAME - housekeeper name
    private String KEEPER_NAME = Boss.KEEPER_DECK_DETAIL;

/**************************************************************************************************/
/*
 * Initialization Methods:
 *      void onCreate(...) - called when activity is first created
 */
/**************************************************************************************************/
    /*
     * void onCreate(...) - called when activity is first created; get Boss application and request
     *      HouseKeeper for this activity
     */
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);

        //get Boss application
        Boss boss = (Boss)getApplicationContext();

        //set current activity
        boss.setActivity(this);

        //get HouseKeeper
        mBridge = (Bridge)boss.requestHouseKeeper(KEEPER_NAME);

        //use houseKeeper to maintain activity
        mBridge.create(this, bundle);
    }


}

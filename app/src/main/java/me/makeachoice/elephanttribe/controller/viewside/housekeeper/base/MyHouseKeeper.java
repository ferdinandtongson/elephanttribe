package me.makeachoice.elephanttribe.controller.viewside.housekeeper.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;


/**************************************************************************************************/
/*
 * MyHouseKeeper is an Abstract class which implements MyActivity.Bridge. The HouseKeeper manages
 * the activity lifecycle and top-level activity components
 */

/**************************************************************************************************/

public abstract class MyHouseKeeper implements MyActivity.Bridge {

/**************************************************************************************************/
/*
 * Class Variables:
 *      Boss mBoss - Boss application object
 *      MyActivity mActivity - activity maintained by HouseKeeper
 *      int mLayoutId - activity layout id
 *      boolean mIsTablet - tablet device status flag
 */
/**************************************************************************************************/

    //mBoss - Boss application object
    protected Boss mBoss;

    //mActivity - activity maintained by HouseKeeper
    protected MyActivity mActivity;

    //mLayoutId - activity layout id
    protected int mLayoutId;

    //mIsTablet - tablet device status flag
    protected boolean mIsTablet;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * MyActivity.Bridge Methods:
 *      void create(...) - called when onCreate() is called in activity
 */
/**************************************************************************************************/
    /*
     * void create(...) - called when onCreate() is called in activity; sets the activity layout,
     * fragmentManager and other child views of the activity
     *
     * NOTE: both FragmentManager and FAB are context sensitive and need to be recreated every time
     * onCreate() is called in the activity
     */
    public void create(MyActivity activity, Bundle bundle){
        //save activity
        mActivity = activity;

        //get Boss
        mBoss = (Boss)mActivity.getApplication();

        //Remove title bar
        mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set activity layout
        mActivity.setContentView(mLayoutId);

        //check if device is a tablet
        mIsTablet = checkForTabletDevice();
    }



/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Tablet Check Methods:
 *      boolean checkForTabletDevice() - check if device is a tablet or not
 *      boolean isTablet() - get tablet device status flag
 */
/**************************************************************************************************/
    /*
     * boolean checkForTabletDevice() - check if device is a tablet or not
     */
    private boolean checkForTabletDevice(){
        //todo - allow user option to adjust screen orientation, currently fixed
        //get tablet view layout
        View tabletView = mActivity.findViewById(-1);

        //check if tablet view is valid
        if(tabletView != null){
            //set tablet orientation to landscape mode only
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            //is tablet device
            return true;
        }
        else{
            //set tablet orientation to portrait mode only
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            //not tablet device
            return false;
        }
    }

    /*
     * boolean isTablet() - get tablet device status flag
     */
    protected boolean isTablet(){ return mIsTablet; }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Methods:
 */
/**************************************************************************************************/

    protected View viewById(int id){
        return mActivity.findViewById(id);
    }

/**************************************************************************************************/

}

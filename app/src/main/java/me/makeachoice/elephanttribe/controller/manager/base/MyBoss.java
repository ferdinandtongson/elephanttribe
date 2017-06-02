package me.makeachoice.elephanttribe.controller.manager.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Executor;

import me.makeachoice.elephanttribe.controller.viewside.housekeeper.base.MyHouseKeeper;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;


/**************************************************************************************************/
/*
 * MyBoss is an Abstract class that extends the Application class. It is the main controller of the
 * app and handles the classes that interface with the View and Model classes
 */

/**************************************************************************************************/

public abstract class MyBoss extends Application implements iServerName, iCodes{

/**************************************************************************************************/
/*
 * Class Variables:
 *      MyActivity mActivity - current activity
 *      SQLiteDatabase mDB - SQLiteDatabase object
 *      HashMap<String, MyHouseKeeper> mKeeperRegistry - houseKeeper registry
 */
/**************************************************************************************************/

    //mActivity - current Activity
    protected MyActivity mActivity;

    //mDB - SQLiteDatabase object
    protected SQLiteDatabase mDB;

    //mKeeperRegistry - houseKeeper registry
    protected HashMap<String, MyHouseKeeper> mKeeperRegistry;

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Getter & Setter Methods:
 *      MyActivity getActivity() - get current activity
 *      void setActivity() - set current activity
 *      Executor getExecutor() - get AsyncTask thread pool executor
 */
/**************************************************************************************************/
    /*
     * MyActivity getActivity() - get current activity
     */
    public MyActivity getActivity(){
        return mActivity;
    }

    /*
     * void setActivity() - set current activity
     */
    public void setActivity(MyActivity activity){
        mActivity = activity;
    }

    /*
     * Executor getExecutor() - get AsyncTask thread pool executor
     */
    public Executor getExecutor(){
        return AsyncTask.THREAD_POOL_EXECUTOR;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * HouseKeeper Registry Methods:
 *      MyHouseKeeper requestHouseKeeper(...) - request houseKeeper from registry
 *      void registerHouseKeeper(...) - add houseKeeper to registry
 *      void removeHouseKeeper(...) - remove houseKeeper from registry
 *      void clearHouseKeeperRegistry() - clear houseKeeper registry
 */
/**************************************************************************************************/
    /*
     * MyHouseKeeper requestHouseKeeper(...) - request houseKeeper from registry
     */
    public MyHouseKeeper requestHouseKeeper(String keeperName){
        //check if registry has houseKeeper
        if(mKeeperRegistry.containsKey(keeperName)){
            return mKeeperRegistry.get(keeperName);
        }

        //invalid name
        return null;
    }

    /*
     * void registerHouseKeeper(...) - add houseKeeper to registry
     */
    public void registerHouseKeeper(String keeperName, MyHouseKeeper houseKeeper){
        //add houseKeeper to registry
        mKeeperRegistry.put(keeperName, houseKeeper);
    }

    /*
     * void removeHouseKeeper(...) - remove houseKeeper from registry
     */
    public void removeHouseKeeper(String keeperName){
        //remove houseKeeper from registry
        mKeeperRegistry.remove(keeperName);
    }

    /*
     * void clearHouseKeeperRegistry() - clear houseKeeper registry
     */
    public void clearHouseKeeperRegistry(){
        //clear houseKeeper registry
        mKeeperRegistry.clear();
        mKeeperRegistry = null;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * User Methods:
 *      void signInUser() - create user item object
 *      String getUserId() - get current user id
 *      String getUserName() - get current user name
 *      String getUserEmail() - get current user email
 */
/**************************************************************************************************/
    /*
     * void signInUser() - create user item object
     */
    public void signInUser(){
        mActivity.startActivityForResult(AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setProviders(Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
            .setIsSmartLockEnabled(false)
            //.setTheme()
            .build(), RC_SIGN_IN);
    }

/**************************************************************************************************/


}

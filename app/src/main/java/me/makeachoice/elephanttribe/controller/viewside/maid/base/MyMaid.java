package me.makeachoice.elephanttribe.controller.viewside.maid.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;
import me.makeachoice.elephanttribe.view.fragment.BasicFragment;
import me.makeachoice.elephanttribe.view.fragment.MyFragment;

/**************************************************************************************************/
/*
 * MyMaid abstract class implements MyFragment.Bridge interface and manages fragment classes
 */
/**************************************************************************************************/

public abstract class MyMaid implements MyFragment.Bridge{

/**************************************************************************************************/
/*
 * Class Variables:
 *      MyActivity mActivity - current activity
 *      Boss mBoss - Boss application
 *      int mLayoutId - activity layout id
 *      View mContainer - layout holding the child views
 *      MyFragment mFragment - fragment being maintained by the Maid
 *      String mMaidName - maid name
 */
/**************************************************************************************************/

    //mActivity - current activity
    protected MyActivity mActivity;

    //mBoss - Boss application
    protected Boss mBoss;

    //mUserId - firebase user id number
    protected String mUserId;

    //mLayoutId - activity layout id
    protected int mLayoutId;

    //mContainer - layout holding the child views
    protected View mContainer;

    //mFragment - fragment being maintained by the Maid
    protected MyFragment mFragment;

    //mMaidName - maid name
    protected String mMaidName;


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * HouseKeeper Names:
 *      View createView(...) - prepares the fragment view to be presented
 *      void activityCreated(...) - called when Activity.onCreate() is completed
 *      void start() - called during onStart() fragment lifecycle
 *      void resume() - called during onResume() fragment lifecycle
 *      void detach() - called when fragment is being disassociated from Activity
 *      void destroyView() - called when fragment is being removed
 *      void saveInstanceState(...) - save state to bundle
 *      String getMaidName() - get maid name
 *      Fragment getFragment() - get fragment
 */
/**************************************************************************************************/
    /*
     * View createView(...) - prepares the fragment view to be presented
     */
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        //inflate fragment layout
        mContainer = inflater.inflate(mLayoutId, container, false);

        //return fragment container
        return mContainer;
    }

    /*
     * void activityCreated(...) - called when Activity.onCreate() is completed
     */
    public void activityCreated(Bundle bundle){
        //get activity
        mActivity = (MyActivity)mFragment.getActivity();

        //get Boss
        mBoss = (Boss)mActivity.getApplication();
    }

    /*
     * void start() - called during onStart() fragment lifecycle
     */
    public void start(){
        //does nothing
    }

    /*
     * void resume() - called during onResume() fragment lifecycle
     */
    public void resume(){
        //does nothing
    }


    /*
     * void detach() - called when fragment is being disassociated from Activity
     */
    public void detach(){
        //does nothing
    }

    /*
     * void destroyView() - called when fragment is being removed
     */
    public void destroyView(){
        //does nothing
    }

    /*
     * void saveInstanceState(...) - save state to bundle
     */
    public void saveInstanceState(Bundle bundle){
        //does nothing
    }

    /*
     * String getMaidName() - get maid name
     */
    public String getMaidName(){ return mMaidName; }

    /*
     * Fragment getFragment() - get fragment
     */
    public Fragment getFragment(){
        mFragment = BasicFragment.newInstance(mMaidName);
        return mFragment;
    }

/**************************************************************************************************/

}

package me.makeachoice.elephanttribe.controller.viewside.maid.base;

import android.os.Bundle;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.MainPreference;
import me.makeachoice.elephanttribe.model.item.user.UserItem;

/**
 * BaseMaid is base class for maids in elephant tribe
 */

public class BaseMaid extends MyMaid {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    protected String mStrAnonymous;

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * LifeCycle Methods:
 */
/**************************************************************************************************/

    @Override
    public void activityCreated(Bundle bundle){
        super.activityCreated(bundle);

        mStrAnonymous = mActivity.getString(R.string.anonymous);

        mUserItem = getCurrentUser();
        mUserId = mUserItem.userId;

        initializePreference();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * User Methods:
 */
/**************************************************************************************************/

    protected UserItem mUserItem;

    /*
     * UserItem getCurrentUser() - get current user
     */
    protected UserItem getCurrentUser(){

        //return logged in user
        return mBoss.getUser();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Preference Methods:
 */
/**************************************************************************************************/

    protected MainPreference mPref;

    private void initializePreference(){
        //get main app preference
        mPref = new MainPreference(mBoss);
        mPref.requestUser(mUserId);

    }


/**************************************************************************************************/

}

package me.makeachoice.elephanttribe.controller.viewside.housekeeper;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.controller.manager.base.MaidRegistry;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.base.BaseKeeper;
import me.makeachoice.elephanttribe.controller.viewside.maid.base.MyMaid;
import me.makeachoice.elephanttribe.controller.viewside.navigation.toolbar.MainToolbar;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * FlashcardKeeper manages the flashcard activity that display different flashcard options
 */

public class FlashcardKeeper extends BaseKeeper {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //simple or multiple choice
    private int mMode;

    //automatic or manual card change
    private boolean mAutoFlipOn;

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public FlashcardKeeper(int layoutId){
        mLayoutId = layoutId;
    }

    public void create(MyActivity activity, Bundle bundle) {
        super.create(activity, bundle);

    }

    /*
     * void onUserLoaded() - user login status and user data loaded
     */
    protected void onUserLoaded() {
        //initialize components
        initializeNavigation();

        //initialize shared preferences
        initializePreferenceValues();

        //initialize maids
        initializeMaid(mUserItem.userId);

        //create maid object buffer
        MyMaid maid;

        //get maid registry
        MaidRegistry maidRegistry = MaidRegistry.getInstance();
        maid = maidRegistry.requestMaid(Boss.MAID_FLASHCARD_SIMPLE);

        //check flashcard mode
        if(mMode == Boss.MODE_FLASHCARD_SIMPLE){
            //todo - flashcard auot-flip option is disabled
            //request regular flip maid
            //maid = maidRegistry.requestMaid(Boss.MAID_FLASHCARD_SIMPLE);
        }
        else{
            //request multiple choice maid
            //maid = maidRegistry.requestMaid(Boss.MAID_FLASHCARD_CHOICE);
        }

        //load fragment from maid
        loadFragment(maid);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Toolbar Methods:
 *      void initializeNavigation() - initialize toolbar component
 */
/**************************************************************************************************/
    /*
     * void initializeNavigation() - initialize toolbar component
     */
    private void initializeNavigation(){
        //initialize toolbar navigation
        initializeToolbar();

        //set navigation icon
        setToolbarNavigationIcon(MainToolbar.NAV_ICON_UP_ID);

        //set navigation icon onClick listener
        setToolbarNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Maid Methods:
 */
/**************************************************************************************************/
    /*
     * void initializeMaid() - initialize maids responsible for fragments
     */
    private void initializeMaid(String userId) {
        //get maid registry
        MaidRegistry maidRegistry = MaidRegistry.getInstance();
        maidRegistry.initializeFlashcardMaid(Boss.MAID_FLASHCARD_SIMPLE, R.layout.frg_flashcard, userId);
        //maidRegistry.initializeFlashcardChoiceMaid(Boss.MAID_FLASHCARD_CHOICE, R.layout.frg_flashcard_choice, userId);

        //flashcard auto-flip disabled
        //maidRegistry.initializeFlashcardAutoMaid(Boss.MAID_FLASHCARD_SIMPLE_AUTO, R.layout.frg_flashcard_auto, userId);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Preference Methods:
 */
/**************************************************************************************************/
    /*
     * void initializePreferenceValues() - initialize preference values
     */
    private void initializePreferenceValues(){
        //initialize preference with userId
        mPref.requestUser(mUserItem.userId);

        //get flashcard mode
        //mMode = mPref.getFlashcardMode();
        //mMode = Boss.MODE_FLASHCARD_SIMPLE;
        //mAutoFlipOn = mPref.getFlashcardAutoFlip();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Fragment Methods:
 *      void loadFragment(...) - load fragment from maid
 */
/**************************************************************************************************/
    /*
     * void loadFragment(...) - load fragment from maid
     */
    private void loadFragment(MyMaid maid){
        //get fragment manager
        FragmentManager fm = mActivity.getFragmentManager();

        //get fragment transaction object
        FragmentTransaction transaction = fm.beginTransaction();

        //get fragment from maid
        Fragment fragment = maid.getFragment();

        //add fragment to container
        transaction.replace(R.id.choiceContainer, fragment);

        //commit transaction
        transaction.commit();
    }

/**************************************************************************************************/

}

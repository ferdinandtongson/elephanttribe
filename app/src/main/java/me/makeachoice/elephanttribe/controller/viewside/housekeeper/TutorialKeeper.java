package me.makeachoice.elephanttribe.controller.viewside.housekeeper;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.base.BaseKeeper;
import me.makeachoice.elephanttribe.controller.viewside.navigation.drawer.MainDrawer;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * TutorialKeeper manages the display of tutorial videos and info
 */

public class TutorialKeeper extends BaseKeeper {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private CardView mCrdStart;
    private CardView mCrdDeck;
    private CardView mCrdSettings;
    private CardView mCrdLeitner;

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public TutorialKeeper(int layoutId){
        mLayoutId = layoutId;
    }


/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Activity Lifecycle:
 */
/**************************************************************************************************/

    @Override
    public void create(MyActivity activity, Bundle bundle) {
        super.create(activity, bundle);

        initializeCardViews();

    }

    @Override
    public void start(){
        super.start();



    }

    protected void onUserLoaded(){

        initializeNavigation();

        mPref.initialize(mUserItem.userId);

        if(mPref.getFirstTime()){
            //todo - display welcome message for anonymous user
            Toast.makeText(mActivity, "Welcome Message - first time", Toast.LENGTH_LONG).show();
            mPref.setFirstTime(false);
        }
    }


/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Initialize Method:
 *      void initializeNavigation() - initialize toolbar and drawer navigation
 *      void initializeCardViews() - initialize tutorial cardView components
 */
/**************************************************************************************************/
    /*
     * void initializeNavigation() - initialize toolbar and drawer navigation
     */
    private void initializeNavigation(){

        //initialize toolbar navigation
        initializeToolbar();

        //initialize drawer navigation
        initializeDrawer();
        setDrawerMenuItemSelected(MainDrawer.MENU_TUTORIAL);
    }

    /*
     * void initializeCardViews() - initialize tutorial cardView components
     */
    private void initializeCardViews(){
        mCrdStart = (CardView)viewById(R.id.tutorial_crdStart);
        //todo - tutorial video "getting started"
        /* Tutorial: getting started
         * - open nav
         * - login
         * - download deck
         * - flashcard mode
         */
        mCrdStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Start tutorial", Toast.LENGTH_SHORT).show();
                if(checkNetwork()){
                    //todo - show video
                }
            }
        });

        //todo - tutorial video "creating deck"
        /* Tutorial: creating deck
         * - open nav
         * - login
         * - deck page
         * - create new
         * - import deck
         * - add card
         */
        mCrdDeck = (CardView)viewById(R.id.tutorial_crdDeck);
        mCrdDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Deck tutorial", Toast.LENGTH_SHORT).show();
                if(checkNetwork()){
                    //todo - show video
                }
            }
        });

        //todo - tutorial video "modify settings"
        /* Tutorial: modify settings
         * - open nav
         * - change settings
         * - explain settings....
         */
        mCrdSettings = (CardView)viewById(R.id.tutorial_crdSettings);
        mCrdSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Settings tutorial", Toast.LENGTH_SHORT).show();
                if(checkNetwork()){
                    //todo - show video
                }
            }
        });

        //todo - tutorial video "leitner method"
        /* Tutorial: leitner method
         * - open nav
         * - deck page
         * - leitner
         * - start/reset
         * - explain leitner.....
         */
        mCrdLeitner = (CardView)viewById(R.id.tutorial_crdLeitner);
        mCrdLeitner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Leitner tutorial", Toast.LENGTH_SHORT).show();
                if(checkNetwork()){
                    //todo - show video
                }
            }
        });
    }


/**************************************************************************************************/




/**************************************************************************************************/
/*
 * Activity Lifecycle:
 */
/**************************************************************************************************/



/**************************************************************************************************/

}

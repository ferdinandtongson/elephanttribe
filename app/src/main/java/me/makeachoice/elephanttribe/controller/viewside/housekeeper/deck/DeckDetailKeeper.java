package me.makeachoice.elephanttribe.controller.viewside.housekeeper.deck;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.controller.manager.MaidRegistry;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.base.BaseKeeper;
import me.makeachoice.elephanttribe.controller.viewside.maid.deck.DeckInfoMaid;
import me.makeachoice.elephanttribe.controller.viewside.navigation.bottomnav.DeckDetailNav;
import me.makeachoice.elephanttribe.controller.viewside.navigation.toolbar.MainToolbar;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * DeckDetailKeeper manages the activity that displays general deck information
 */

public class DeckDetailKeeper extends BaseKeeper {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private DeckDetailNav mNav;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckDetailKeeper(int layoutId){
        mLayoutId = layoutId;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Activity Lifecycle Methods:
 */
/**************************************************************************************************/

    /*
     * void create(...) - called when onCreate() is called in activity; sets the activity layout,
     * fragmentManager and other child views of the activity
     *
     * NOTE: both FragmentManager and FAB are context sensitive and need to be recreated every time
     * onCreate() is called in the activity
     */
    @Override
    public void create(MyActivity activity, Bundle bundle) {
        super.create(activity, bundle);

        initializeBottomNavigation();
    }

    /*
     * void start() - called when activity is visible to user; register broadcast receivers here
     */
    @Override
    public void start(){
        super.start();

    }

    //todo - user loaded
    protected void onUserLoaded(){
        initializeNavigation();

        if(mUserItem.userId.equals(mStrAnonymousId)){
            Toast.makeText(mActivity, "Anonymous user", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(mActivity, "User: " + mUserItem.userName, Toast.LENGTH_SHORT).show();
        }

        initializeMaid(mUserItem.userId);
        mNav.navigateTo(DeckDetailNav.NAV_INFO);
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
 * Bottom Navigation Methods:
 */
/**************************************************************************************************/
    /*
     * void initializeBottomNavigation() - initialize bottom navigation component
     */
    private void initializeBottomNavigation(){
        //create bottom navigator
        mNav = new DeckDetailNav(mActivity, R.menu.nav_deck_detail);

        //set navigation map
        mNav.addToNavigationMap(DeckDetailNav.NAV_INFO, Boss.MAID_DECK_DETAIL_INFO);
        //todo - mNav.addToNavigationMap(DeckDetailNav.NAV_SCHOOL, Boss.MAID_DECK_DETAIL_SCHOOL);
        mNav.addToNavigationMap(DeckDetailNav.NAV_FLASHCARD, Boss.MAID_DECK_DETAIL_FLASHCARD);
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
    private void initializeMaid(String userId){
        //get maid registry
        MaidRegistry registry = MaidRegistry.getInstance();

        //initialize info maid
        DeckInfoMaid infoMaid= registry.initializeDeckInfo(Boss.MAID_DECK_DETAIL_INFO,
                R.layout.frg_deck_info);

        //add navigation request listener
        infoMaid.setOnNavRequestListener(new DeckInfoMaid.OnNavRequestListener() {
            @Override
            public void onNavRequest(int navRequest) {
                mNav.navigateTo(navRequest);
            }
        });

        //todo - registry.initializeDeckSchool(Boss.MAID_DECK_DETAIL_SCHOOL, R.layout.frg_deck_school);
        registry.initializeDeckFlashcard(Boss.MAID_DECK_DETAIL_FLASHCARD, R.layout.frg_deck_flashcard);

    }

/**************************************************************************************************/




}

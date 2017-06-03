package me.makeachoice.elephanttribe.controller.viewside.navigation.bottomnav;

import android.support.design.widget.BottomNavigationView;

import java.util.HashMap;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * DeckDetailNav extends MyBottomNav and navigates from the dictionary and leitner bucket info of a deck
 *
 * MyBottomNav Variables:
 *      DEFAULT_BOTTOMNAV_ID - bottom navigation view resource id
 *      mActivity - current activity
 *      mNav - bottom navigation view component
 *      mNavigationMap - hashMap for mapping navigation menu items to maidId
 *
 * MyBottomNav Methods:
 *      void addToNavigationMap(...) - add to navigation hashMap
 *      void navigateTo(...) - navigate to requested fragment
 *      boolean onNavigationItemSelected(...) - called when a navigation item is selected
 *      void checkItem(...) - check (highlight) selected menu item
 *      void uncheckAll() - uncheck all menu items in bottom navigation
 *      void loadFragment(...) - load selected fragment from maid
 */

public class DeckDetailNav extends MyBottomNav{

/**************************************************************************************************/
/*
 * Class Variables:
 *      NAV_INFO - menu item for deck info
 *      NAV_SCHOOL - menu item for deck school info
 *      NAV_FLASHCARD - menu item for deck flashcards
 */
/**************************************************************************************************/
    //NAV_INFO - menu item for deck info
    public final static int NAV_INFO = R.id.nav_item01;

    //NAV_SCHOOL - menu item for deck school info
    public final static int NAV_SCHOOL = R.id.nav_item02;

    //NAV_FLASHCARD - menu item for deck flashcards
    public final static int NAV_FLASHCARD = R.id.nav_item03;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckDetailNav(MyActivity activity, int menuId){
        //get activity
        mActivity = activity;

        //get navigation bar
        mNav = (BottomNavigationView)mActivity.findViewById(DEFAULT_BOTTOMNAV_ID);

        //inflate menu
        mNav.inflateMenu(menuId);

        //set navigation item selected listener
        mNav.setOnNavigationItemSelectedListener(this);

        //initialize hashMap
        mNavigationMap = new HashMap<>();

    }

/**************************************************************************************************/

}

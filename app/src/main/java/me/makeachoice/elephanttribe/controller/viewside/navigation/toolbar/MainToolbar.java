package me.makeachoice.elephanttribe.controller.viewside.navigation.toolbar;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**************************************************************************************************/
/*
 * MainToolbar extends MyToolbar and is the main toolbar used for the application.
 *
 * MyToolbar Variables:
 *      int NO_TOOLBAR_MENU - no menu options
 *      MyActivity mActivity - current activity
 *      Toolbar mToolbar - toolbar object
 *      int mMenuId - menu resource id
 *
 * MyToolbar Methods:
 *      Toolbar getToolbar() - get toolbar object
 *      void setBackgroundColor(...) - set toolbar background color
 *      void setBackgroundDrawable(...) - set toolbar background drawable
 *      void setNavigationIcon(...) - set toolbar navigation icon
 *      void setNavigationOnClickListener(...) - set navigation onClick listener
 *      void setOverflowIcon(...) - set overflow icon
 *      void setTextColor(...) - set title and subtitle text color
 *      void setTitle(...) - set title values
 *      void setSubtitle(...) - set subtitle values
 *
 * MyActivity.OptionsMenuListener Methods:
 *      void createOptionsMenu(...) - called by activity to inflate menu
 *      boolean optionsItemSelected(...) - menu item clicked
 */

/**************************************************************************************************/

public class MainToolbar extends MyToolbar{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //DEFAULT_TOOLBAR_ID - toolbar component resource id
    private final static int DEFAULT_TOOLBAR_ID = R.id.choiceToolbar;

    //DEFAULT_NAV_ICON_ID - navigation icon drawable resource id
    public final static int DEFAULT_NAV_ICON_ID = R.drawable.ic_more_vert_black_36dp;

    //DEFAULT_BG_COLOR_ID - background color resource id
    public final static int DEFAULT_BG_COLOR_ID = R.color.colorToolbarBackground;

    //DEFAULT_TEXT_COLOR_ID - toolbar text color resource id
    public final static int DEFAULT_TEXT_COLOR_ID = R.color.black;

    //NAV_ICON_UP_ID - navigation icon "up" drawable resource id
    public final static int NAV_ICON_UP_ID = R.drawable.ic_chevron_left_black_36dp;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public MainToolbar(MyActivity activity){
        //current activity
        mActivity = activity;

        //toolbar component
        mToolbar = (Toolbar)mActivity.findViewById(DEFAULT_TOOLBAR_ID);

        //no toolbar menu
        mMenuId = NO_TOOLBAR_MENU;
    }

    public MainToolbar(MyActivity activity, int menuId){
        //current activity
        mActivity = activity;

        //toolbar component
        mToolbar = (Toolbar)mActivity.findViewById(DEFAULT_TOOLBAR_ID);

        //toolbar menu resource id
        mMenuId = menuId;
    }

    public MainToolbar(MyActivity activity, int menuId, int toolbarId){
        //current activity
        mActivity = activity;

        //toolbar component
        mToolbar = (Toolbar)mActivity.findViewById(toolbarId);

        //toolbar menu resource id
        mMenuId = menuId;

    }

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * MyActivity.OptionsMenuListener Methods
 *      boolean optionsItemSelected(...) - menu item clicked
 */
/**************************************************************************************************/
    /*
     * boolean optionsItemSelected(...) - menu item clicked
     */
    public boolean optionsItemSelected(MenuItem item){
        return false;
    }

/**************************************************************************************************/

}

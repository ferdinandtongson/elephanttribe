package me.makeachoice.elephanttribe.controller.viewside.housekeeper;

import android.os.Bundle;

import me.makeachoice.elephanttribe.controller.viewside.housekeeper.base.BaseKeeper;
import me.makeachoice.elephanttribe.controller.viewside.navigation.drawer.MainDrawer;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * MarketKeeper manages the display of online flashcard market
 */

public class MarketKeeper extends BaseKeeper {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/


/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public MarketKeeper(int layoutId){
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

    }

    @Override
    public void start(){
        super.start();



    }

    protected void onUserLoaded(){

        initializeNavigation();

        mPref.initialize(mUserItem.userId);

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
        setDrawerMenuItemSelected(MainDrawer.MENU_MARKET);
    }


/**************************************************************************************************/




/**************************************************************************************************/
/*
 * Activity Lifecycle:
 */
/**************************************************************************************************/



/**************************************************************************************************/

}

package me.makeachoice.elephanttribe.controller.viewside.navigation.bottomnav;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import java.util.HashMap;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.base.MaidRegistry;
import me.makeachoice.elephanttribe.controller.viewside.maid.base.MyMaid;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * MyBottomNav an abstract class for managing bottom navigation objects
 */

public abstract class MyBottomNav implements BottomNavigationView.OnNavigationItemSelectedListener{

/**************************************************************************************************/
/*
 * Class Variables:
 *      DEFAULT_BOTTOMNAV_ID - bottom navigation view resource id
 *      mActivity - current activity
 *      mNav - bottom navigation view component
 *      mNavigationMap - hashMap for mapping navigation menu items to maidId
 */
/**************************************************************************************************/

    //DEFAULT_BOTTOMNAV_ID - bottom navigation view resource id
    protected int DEFAULT_BOTTOMNAV_ID = R.id.choiceBottomNav;

    //mActivity - current activity
    protected MyActivity mActivity;

    //mNav - bottom navigation view component
    protected BottomNavigationView mNav;

    //mNavigationMap - hashMap for mapping navigation menu items to maidId
    protected HashMap<Integer,String> mNavigationMap;


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Public Methods:
 *      void addToNavigationMap(...) - add to navigation hashMap
 *      void navigateTo(...) - navigate to requested fragment
 *      boolean onNavigationItemSelected(...) - called when a navigation item is selected
 */
/**************************************************************************************************/
    /*
     * void addToNavigationMap(...) - add to navigation hashMap
     */
    public void addToNavigationMap(int menuId, String maidId){
        mNavigationMap.put(menuId, maidId);
    }

    /*
     * void navigateTo(...) - navigate to requested fragment
     */
    public void navigateTo(int menuId){
        //check menu item icon
        checkItem(menuId);

        //get maid registry instance
        MaidRegistry maidRegistry = MaidRegistry.getInstance();

        if(mNavigationMap.containsKey(menuId)){
            //get maid id from hashMap
            String maidId = mNavigationMap.get(menuId);

            //load fragment from maid
            loadFragment(maidRegistry.requestMaid(maidId));
        }

    }

    /*
     * boolean onNavigationItemSelected(...) - called when a navigation item is selected
     */
    public boolean onNavigationItemSelected(MenuItem item){
        //get menu id
        int menuId = item.getItemId();

        //navigate to request fragment
        navigateTo(menuId);

        return false;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Methods:
 *      void checkItem(...) - check (highlight) selected menu item
 *      void uncheckAll() - uncheck all menu items in bottom navigation
 *      void loadFragment(...) - load selected fragment from maid
 */
/**************************************************************************************************/
    /*
     * void checkItem(...) - check (highlight) selected menu item
     */
    protected void checkItem(int menuId){
        //get number of menu items
        int count = mNav.getMenu().size();

        //menu item buffer
        MenuItem item;

        //loop through items
        for(int i = 0; i < count; i++){
            //get menu item
            item = mNav.getMenu().getItem(i);

            //check if menu item has menuId
            if(item.getItemId() == menuId){
                //set item checked
                item.setChecked(true);
            }
        }

    }

    /*
     * void uncheckAll() - uncheck all menu items in bottom navigation
     */
    protected void uncheckAll(){
        int count = mNav.getMenu().size();

        for(int i = 0; i < count; i++){
            mNav.getMenu().getItem(i).setChecked(false);
        }

    }

    /*
     * void loadFragment(...) - load selected fragment from maid
     */
    protected void loadFragment(MyMaid maid){
        //get fragment manager
        FragmentManager fragmentManager = mActivity.getFragmentManager();

        //get fragment transaction object
        FragmentTransaction ft = fragmentManager.beginTransaction();

        //get fragment managed by maid
        Fragment fragment = maid.getFragment();

        //add fragment to fragment container
        ft.replace(R.id.choiceContainer, fragment);

        //commit fragment transaction
        ft.commit();
    }

/**************************************************************************************************/

}

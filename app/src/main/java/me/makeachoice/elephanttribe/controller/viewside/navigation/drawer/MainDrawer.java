package me.makeachoice.elephanttribe.controller.viewside.navigation.drawer;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.utilities.SignInUtility;
import me.makeachoice.elephanttribe.view.activity.DeckActivity;
import me.makeachoice.elephanttribe.view.activity.MarketActivity;
import me.makeachoice.elephanttribe.view.activity.SettingsActivity;
import me.makeachoice.elephanttribe.view.activity.TutorialActivity;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**************************************************************************************************/
/*
 * MainDrawer extends MyDrawer and is the main drawer used by this application
 *
 * MyDrawer Variables:
 *      MyActivity mActivity - current activity
 *      Drawer mDrawer - drawer layout object
 *      NavigationView mNavigation - navigation view layout object
 *
 *      View mHeader - drawer header view layout object
 *      ImageView mHeaderIcon - header icon imageView object
 *      TextView mHeaderTitle - header title textView object
 *      TextView mHeaderSubtitle - header subtitle textView object
 *
 * MyDrawer Method:
 *      void createNavigationMenu(...) - create menu options for navigationView
 *      DrawerLayout getDrawer() - get drawer object
 *      View getHeader() - get drawer header view object
 *      NavigationView getNavigation() - get navigation view object
 *      void openDrawer() - open drawer to display navigationView
 *      void setHeaderIcon(...) - set header icon
 *      void setTextColor(...) - set title and subtitle text color
 *      void setTitle(...) - set title values
 *      void setSubtitle(...) - set subtitle values
 *
 */
/**************************************************************************************************/

public class MainDrawer extends MyDrawer implements NavigationView.OnNavigationItemSelectedListener{

/**************************************************************************************************/
/*
 * Class Variables:
 *      int DEFAULT_DRAWER_ID - drawer component resource id
 *      int DEFAULT_NAVIGATION_VIEW_ID - navigationView component resource id
 *      int MENU_DECKS - Deck menu id
 *      int MENU_FLASHCARDS - Flashcards menu id
 *      int MENU_SETTINGS - Settings menu id
 *      int MENU_MARKET - Market menu id
 *      int MENU_HELP - Help menu id
 *      int MENU_SIGN_OUT - Sign Out menu id
 *      boolean mSignedIn - user signed in status flag
 *      OnSignOutListener mOnSignOutListener - listens for User sign out event
 */
/**************************************************************************************************/

    //DEFAULT_DRAWER_ID - drawer component resource id
    private final static int DEFAULT_DRAWER_ID = R.id.choiceDrawer;

    //DEFAULT_NAVIGATION_VIEW_ID - navigationView component resource id
    private final static int DEFAULT_NAVIGATION_VIEW_ID = R.id.choiceNavigationView;

    //MENU_DECKS - Deck menu id
    public final static int MENU_DECK = R.id.menu_dwr_menu01;

    //MENU_FLASHCARDS - Flashcards menu id
    public final static int MENU_MARKET = R.id.menu_dwr_menu02;

    //MENU_MARKET - Market menu id
    public final static int MENU_TUTORIAL = R.id.menu_dwr_menu03;

    //MENU_SETTINGS - Settings menu id
    public final static int MENU_SETTINGS = R.id.menu_dwr_menu04;

    //MENU_HELP - Help menu id
    public final static int MENU_HELP = R.id.menu_dwr_menu05;

    //MENU_SIGN_OUT - Sign Out menu id
    public final static int MENU_SIGN_OUT = R.id.menu_dwr_menu06;

    //mSignedIn - user signed in status flag
    private boolean mSignedIn;

    //mOnSignOutListener - listens for User sign out event
    private OnSignOutListener mOnSignOutListener;

    //OnSignOutListener - interface for user sign out event
    public interface OnSignOutListener{
        //user has signed out
        public void onUserSignOut();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public MainDrawer(MyActivity activity){
        mActivity = activity;

        mDrawer = (DrawerLayout)mActivity.findViewById(DEFAULT_DRAWER_ID);
        mNavigation = (NavigationView)mActivity.findViewById(DEFAULT_NAVIGATION_VIEW_ID);

        mHeader = mNavigation.getHeaderView(0);

        mHeaderIcon = (ImageView)mHeader.findViewById(R.id.dwrHeader_imgIcon);
        mHeaderTitle = (TextView)mHeader.findViewById(R.id.dwrHeader_txtTitle);
        mHeaderSubtitle = (TextView)mHeader.findViewById(R.id.dwrHeader_txtSubtitle);

        mNavigation.setNavigationItemSelectedListener(this);

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setter Methods:
 *      void setSignedIn(...) - set user signed in status flag
 *      void setOnSignOutListener(...) - set listener for user sign out event
 */
/**************************************************************************************************/
    /*
     * void setSignedIn(...) - set user signed in status flag
     */
    public void setSignedIn(boolean isSignedIn){
        mSignedIn = isSignedIn;
    }

    /*
     * void setOnSignOutListener(...) - set listener for user sign out event
     */
    public void setOnSignOutListener(OnSignOutListener listener){
        mOnSignOutListener = listener;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Methods:
 *      boolean onNavigationItemSelected(...) - called when a navigation item is selected
 *      void drawerMenuRequest(...) - drawer menu request
 *      void activityRequest() - start activity has been requested from menu selection
 */
/**************************************************************************************************/
    /*
     * boolean onNavigationItemSelected(...) - called when a navigation item is selected
     */
    public boolean onNavigationItemSelected(MenuItem menuItem){
        //check if item is in checked state or not
        if(menuItem.isChecked()){
            //already checked, close drawer
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            //not checked, drawer request
            drawerMenuRequest(menuItem.getItemId());
        }

        return false;
    }

    /*
     * void drawerMenuRequest(...) - drawer menu request
     */
    private void drawerMenuRequest(int menuId){
        switch(menuId){
            case MENU_DECK:
                Toast.makeText(mActivity,"Decks",Toast.LENGTH_SHORT).show();
                activityRequest(DeckActivity.class);
                break;
            case MENU_MARKET:
                Toast.makeText(mActivity,"Market",Toast.LENGTH_SHORT).show();
                activityRequest(MarketActivity.class);
                break;
            case MENU_TUTORIAL:
                Toast.makeText(mActivity,"Tutorial",Toast.LENGTH_SHORT).show();
                activityRequest(TutorialActivity.class);
                break;
            case MENU_SETTINGS:
                Toast.makeText(mActivity,"Settings",Toast.LENGTH_SHORT).show();
                activityRequest(SettingsActivity.class);
                break;
            case MENU_HELP:
                Toast.makeText(mActivity,"Help",Toast.LENGTH_SHORT).show();
                break;
            case MENU_SIGN_OUT:
                //check if user is signed in
                if(mSignedIn){
                    //user is signed in, sign out user
                    SignInUtility.signOut();

                    //check if there is a sign out listener
                    if(mOnSignOutListener != null){
                        //notify listener of sign out event
                        mOnSignOutListener.onUserSignOut();
                    }
                }
                else{
                    //sign in request
                    SignInUtility.signIn(mActivity);
                }
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
    }

    /*
     * void activityRequest() - start activity has been requested from menu selection
     */
    private void activityRequest(Class activityClass){
        Intent intent = new Intent(mActivity, activityClass);
        mActivity.startActivity(intent);
    }

/**************************************************************************************************/


}

package me.makeachoice.elephanttribe.controller.viewside.housekeeper.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.controller.manager.MainPreference;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.butler.user.UserButler;
import me.makeachoice.elephanttribe.controller.viewside.navigation.drawer.MainDrawer;
import me.makeachoice.elephanttribe.controller.viewside.navigation.toolbar.MainToolbar;
import me.makeachoice.elephanttribe.model.item.user.UserItem;
import me.makeachoice.elephanttribe.utilities.DateTimeUtility;
import me.makeachoice.elephanttribe.utilities.DeprecatedUtility;
import me.makeachoice.elephanttribe.utilities.NetworkUtility;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

import static android.app.Activity.RESULT_OK;

/**************************************************************************************************/
/*
 * BaseKeeper extends MyHouseKeeper. Maintains toolbar and drawer components
 *
 * MyHouseKeeper Variables:
 *      Boss mBoss - Boss application object
 *      MyActivity mActivity - activity maintained by HouseKeeper
 *      int mLayoutId - activity layout id
 *      boolean mIsTablet - tablet device status flag
 *
 * MyHouseKeeper Methods:
 *      void create(...) - called when onCreate() is called in activity
 *      boolean checkForTabletDevice() - check if device is a tablet or not
 *      boolean isTablet() - get tablet device status flag
 *
 */
/**************************************************************************************************/

public abstract class BaseKeeper extends MyHouseKeeper {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private String mStrNavTitle;
    private String mStrNavSubtitle;
    private String mStrSignIn;
    private String mStrSignOut;
    protected String mStrAnonymousId;

    private MainToolbar mMainToolbar;
    private MainDrawer mMainDrawer;
    private int mSelectedMenuItem;

    protected MainPreference mPref;
    private UserButler mUserButler;
    protected UserItem mUserItem;

    protected boolean mHaveNetwork;
    protected boolean mIsAuth;

    protected AlertDialog mAlertDialog;

    protected DialogInterface.OnClickListener mDiaRefreshListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mActivity.startActivity(new Intent(Settings.ACTION_SETTINGS));
        }
    };

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialization Methods:
 *      void create(...) - called when onCreate() is called in activity
 *      void start() - called when onStart() is called in activity
 *      void resume() - called when onResume() is called in activity
 *      void pause() - called when onPause() is called in activity
 *      void stop() - called when onStop() is called in activity
 *      void destroy() - called when onDestroy() is called in activity
 *      void backPressed() - called when onBackPressed() is called in activity
 *      void saveInstanceState(...) - called when onSaveInstanceState() is called in activity
 *      void activityResult(...) - called when onActivityResult() is called in activity
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
    public void create(MyActivity activity, Bundle bundle){
        super.create(activity, bundle);

        //get main app preference
        mPref = new MainPreference(mBoss);

        //get navigation titles
        mStrNavTitle = mActivity.getString(R.string.app_name) + ":";
        mStrNavSubtitle = mActivity.getString(R.string.app_sub_name);

        //get sign in string value
        mStrSignIn = activity.getString(R.string.sign_in);

        //get sign out string value
        mStrSignOut = activity.getString(R.string.sign_out);

        //get anonymous string value
        mStrAnonymousId = activity.getString(R.string.anonymous);

        //set user authenticated status flag
        mIsAuth = false;

        //set default selected menu item
        mSelectedMenuItem = MainDrawer.MENU_DECK;

        //check network connection
        checkNetwork();
    }

    /*
     * void start() - called when activity is visible to user; register broadcast receivers here
     */
    @Override
    public void start(){
        //todo - register broadcast receivers

         //get Firebase instance
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();

        //get Firebase user
        FirebaseUser user = fbAuth.getCurrentUser();

        //todo - #1 check user
        if(user == null){
            //todo - #2 checkInAnonymous
            checkInAnonymous();
        }
        else{
            //todo - #2  checkInUser
            Log.d("Choice", "     user: " + user.getDisplayName());
            checkInFirebaseUser(user);
        }

        if(mMainDrawer != null){
            //set drawer menu
            setDrawerSignInMenu();
        }

    }

    /*
     * void resume() - called when user can start interacting with activity; begin animation, open
     * exclusive-access devices (such as cameras) and restore activity states
     */
    public void resume(){
        //user can interact with activity, begin animation, restore states, open exclusive-access devices
    }

    /*
     * void pause() - called when activity is going into background; lightweight operations only,
     * save persistent state, stop animations, close exclusive-access devices
     */
    public void pause(){
        //lightweight operations only, stop animation, save states, close exclusive-access devices
    }

    /*
     * void stop() - called when activity is no longer visible; unregister broadcast receivers
     */
    public void stop(){
        //activity no longer visible, unregister broadcast receivers
    }

    /*
     * void destroy() - called by onFinish() or system is saving space; do final cleanup
     */
    public void destroy(){
        //final cleanup
    }

    /*
     * void backPressed() - called when "back" button is pressed
     */
    public void backPressed(){
        //finish activity
        mActivity.finishActivity();
    }

    /*
     * void saveInstanceState(...) - called before destroy(), save state to bundle
     */
    public void saveInstanceState(Bundle bundle){
        //save instance data before destoy() is called
    }

    /*
     * void activityResult(...) - result of activity called by this activity
     */
    public void activityResult(int requestCode, int resultCode, Intent data){
        Log.d("BaseKeeper", "BaseKeeper.activityResult: " + requestCode);
        //get result from activity
        if(requestCode == Boss.RC_SIGN_IN){
            Log.d("BaseKeeper", "     result: " + resultCode);
            if(resultCode == RESULT_OK){
                Log.d("BaseKeeper", "          ok");
                mIsAuth = true;
                //get Firebase instance
                FirebaseAuth fbAuth = FirebaseAuth.getInstance();

                //get Firebase user
                FirebaseUser user = fbAuth.getCurrentUser();
                checkInFirebaseUser(user);
            }
        }

    }

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Drawer Methods:
 *      void initializeDrawer() - initialize drawer component
 *      void setDrawerTitle(...) - set drawer title
 *      void setDrawerSubtitle(...) - set drawer subtitle
 *      void setDrawerTextColor(...) - set drawer text color
 *      void setDrawerIcon(...) - set drawer icon
 *      void setDrawerMenuItemSelected(...) - set selected menu item in drawer view
 *      void openDrawer() - open drawer component
 *      void openDrawer(...) - open drawer component with menu item checked
 */
/**************************************************************************************************/
    /*
     * void initializeDrawer() - initialize drawer component
     */
    protected void initializeDrawer(){
        //get main drawer object
        mMainDrawer = new MainDrawer(mActivity);

        //set drawer title
        setDrawerTitle(mStrNavTitle);
        setDrawerSubtitle(mStrNavSubtitle);

        //set drawer head icon
        setDrawerIcon(R.drawable.elephant01c);

    }

    protected void setDrawerSignInMenu(){
        // get menu from navigationView
        Menu menu = mMainDrawer.getNavigation().getMenu();

        //find MenuItem you want to change
        MenuItem nav_auth = menu.findItem(R.id.menu_dwr_menu06);

        if(mUserItem.userId.equals(mStrAnonymousId)){
            // set new title to the MenuItem
            nav_auth.setTitle(mStrSignIn);
            nav_auth.setIcon(R.drawable.ic_account_circle_black_36dp);
            mMainDrawer.setSignedIn(false);

        }
        else{
            // set new title to the MenuItem
            nav_auth.setTitle(mStrSignOut);
            nav_auth.setIcon(R.drawable.ic_cancel_black_36dp);
            mMainDrawer.setSignedIn(true);
        }
    }

    /*
     * void setDrawerTitle(...) - set drawer title
     */
    protected void setDrawerTitle(String title){
        mMainDrawer.setTitle(title);
    }

    /*
     * void setDrawerSubtitle(...) - set drawer subtitle
     */
    protected void setDrawerSubtitle(String subtitle){
        mMainDrawer.setSubtitle(subtitle);
    }

    /*
     * void setDrawerTextColor(...) - set drawer text color
     */
    protected void setDrawerTextColor(int titleColorId, int subtitleColorId){
        mMainDrawer.setTextColor(titleColorId, subtitleColorId);
    }

    /*
     * void setDrawerIcon(...) - set drawer icon
     */
    protected void setDrawerIcon(int resId){
        mMainDrawer.setHeaderIcon(resId);
    }

    /*
     * void setDrawerMenuItemSelected(...) - set selected menu item in drawer view
     */
    protected void setDrawerMenuItemSelected(int menuId){
        mSelectedMenuItem = menuId;
    }

    protected void setOnSignOutListner(MainDrawer.OnSignOutListener listener){
        mMainDrawer.setOnSignOutListener(listener);
    }

    /*
     * void openDrawer() - open drawer component
     */
    protected void openDrawer(){
        mMainDrawer.openDrawer();
    }

    /*
     * void openDrawer(...) - open drawer component with menu item checked
     */
    protected void openDrawer(int menuId){
        mMainDrawer.openDrawer(menuId);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Toolbar Methods:
 *      void initializeToolbar() - initialize toolbar component
 *      oid initializeToolbar(...) - initialize toolbar component with menu
 *      void setToolbarTitle(...) - set toolbar title
 *      void setToolbarSubtitle(...) - set toolbar subtitle
 *      void setToolbarTextColor(...) - set toolbar text color
 *      void setToolbarBackgroundColor(...) - set toolbar background color
 *      void setToolbarBackgroundDrawable(...) - set toolbar background drawable
 *      void setToolbarNavigationIcon(...) - set toolbar navigation icon
 *      void setToolbarOverflowIcon(...) - set toolbar overflow icon
 *      oid setToolbarNavigationOnClickListener(...) - set toolbar navigation listener
 */
/**************************************************************************************************/
    /*
     * void initializeToolbar() - initialize toolbar component
     */
    protected void initializeToolbar(){
        initializeToolbar(MainToolbar.NO_TOOLBAR_MENU);
    }

    /*
     * void initializeToolbar(...) - initialize toolbar component with menu
     */
    protected void initializeToolbar(int menuId){

        //create main toolbar
        if(menuId == MainToolbar.NO_TOOLBAR_MENU){
            mMainToolbar = new MainToolbar(mActivity);
        }
        else{
            mMainToolbar = new MainToolbar(mActivity, menuId);
        }

        //set main toolbar object as actionBar
        mActivity.setSupportActionBar(mMainToolbar.getToolbar());

        //disable showTitle to allow manipulation of Title and Subtitle values
        //mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        //add drop shadow to actionBar
        mActivity.getSupportActionBar().setElevation(4);

        //set toolbar background color
        int bgColor = DeprecatedUtility.getColor(mActivity, MainToolbar.DEFAULT_BG_COLOR_ID);
        setToolbarBackgroundColor(bgColor);

        //set toolbar text color
        int textColor = DeprecatedUtility.getColor(mActivity, MainToolbar.DEFAULT_TEXT_COLOR_ID);
        setToolbarTextColor(textColor, textColor);

        //set toolbar title
        setToolbarTitle(mStrNavTitle);
        setToolbarSubtitle(mStrNavSubtitle);

        //set toolbar navigation icon
        setToolbarNavigationIcon(MainToolbar.DEFAULT_NAV_ICON_ID);
        setToolbarNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(mSelectedMenuItem);
            }
        });


    }

    /*
     * void setToolbarTitle(...) - set toolbar title
     */
    protected void setToolbarTitle(String title){
        mMainToolbar.setTitle(title);
    }

    /*
     * void setToolbarSubtitle(...) - set toolbar subtitle
     */
    protected void setToolbarSubtitle(String subtitle){
        mMainToolbar.setSubtitle(subtitle);
    }

    /*
     * void setToolbarTextColor(...) - set toolbar text color
     */
    protected void setToolbarTextColor(int titleColorId, int subtitleColorId){
        mMainToolbar.setTextColor(titleColorId, subtitleColorId);
    }

    /*
     * void setToolbarBackgroundColor(...) - set toolbar background color
     */
    protected void setToolbarBackgroundColor(int color){
        mMainToolbar.setBackgroundColor(color);
    }

    /*
     * void setToolbarBackgroundDrawable(...) - set toolbar background drawable
     */
    protected void setToolbarBackgroundDrawable(int resId){
        mMainToolbar.setBackgroundDrawable(resId);
    }

    /*
     * void setToolbarNavigationIcon(...) - set toolbar navigation icon
     */
    protected void setToolbarNavigationIcon(int resId){
        mMainToolbar.setNavigationIcon(resId);
    }

    /*
     * void setToolbarOverflowIcon(...) - set toolbar overflow icon
     */
    protected void setToolbarOverflowIcon(int resId){
        mMainToolbar.setOverflowIcon(resId);
    }

    protected void setToolbarUpNavigation(boolean enabled){
        mActivity.getActionBar().setDisplayHomeAsUpEnabled(enabled);
    }

    /*
     * void setToolbarNavigationOnClickListener(...) - set toolbar navigation listener
     */
    protected void setToolbarNavigationOnClickListener(View.OnClickListener listener){
        mMainToolbar.setNavigationOnClickListener(listener);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * User CheckIn Methods:
 */
/**************************************************************************************************/
    /*
     * void checkInAnonymous() - check in anonymous user
     */
    private void checkInAnonymous(){
        Log.d("BaseKeeper", "BaseKeeper.checkInAnonymous");
        //todo - #3 create anonymous userItem
        //check if user item is null
        if(mBoss.getUser() == null){
            Log.d("BaseKeeper", "     user null");
            //user item is null, get user from database
            requestUserData(mStrAnonymousId);
        }
        else{
            //user item NOT null, check userId is anonymous id
            if(mBoss.getUser().userId.equals(mStrAnonymousId)){
                Log.d("BaseKeeper", "     anonymous here!!!!");
                //user item is Anonymous user
                mUserItem = mBoss.getUser();

                //user item loaded
                onUserLoaded();
            }
            else{
                //user is NOT Anonymous, get Anonymous user
                requestUserData(mStrAnonymousId);
            }
        }

        //todo - #4 check local db for user
    }

    /*
     * void requestUserData(...) - request user data from butler class
     */
    private void requestUserData(String userId){
        //todo (anonymous) - #4 check local db for user
        mUserButler = new UserButler(mActivity, userId);
        mUserButler.loadUserById(Boss.LOADER_USER, userId);
        mUserButler.setOnLoadedListener(new MyButler.OnLoadedListener() {
            @Override
            public void onLoaded(ArrayList itemList) {
                //check if user is in local database
                if(itemList.size() == 0){
                    //todo (anonymous) - #4a not in db, initialize local db for user
                    //initialize local user db
                    initializeLocalUserDB();
                }
                else{
                    //todo (anonymous) - #5 user loaded
                    mUserItem = (UserItem)itemList.get(0);
                    mBoss.setUser(mUserItem);
                    onUserLoaded();
                }
            }
        });

    }

    private void initializeLocalUserDB(){
        //todo (anonymous - #4a initialize loca db for Anonymous user only!!!!
        Log.d("BaseKeeper", "BaseKeeper.initializeUserDB");
        mUserButler.setOnSavedListener(new MyButler.OnSavedListener() {
            @Override
            public void onSaved() {
                //todo (anonymous - #5 user loaded
                onUserLoaded();
            }
        });

        mUserItem = new UserItem();
        mUserItem.userId = mStrAnonymousId;
        mUserItem.userName = mStrAnonymousId;
        mUserItem.email = "";
        mUserItem.registration = DateTimeUtility.getToday();

        mBoss.setUser(mUserItem);

        mUserButler.save(mUserItem);

    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * User CheckIn Methods:
 */
/**************************************************************************************************/

    private void checkInFirebaseUser(FirebaseUser fbUser){
        //todo - #3 create userItem
        if(mBoss.getUser() == null){
            Log.d("BaseKeeper", "     initialize user");
            mUserItem = new UserItem(fbUser);

            //todo - #4 check firebase data for user
            getFirebaseUserData(mUserItem.userId);
        }
        else{
            mUserItem = mBoss.getUser();
        }

    }

    private void getFirebaseUserData(String userId){
        Log.d("BaseKeeper", "BaseKeeper.getFirebaseUserData: " + userId);
        //check if we have network
        /*if(mHaveNetwork){
            Log.d("BaseKeeper", "     have network");
            //get user data from firebase
            MyFirebase userFirebase = MyFirebase.getInstance();
            userFirebase.requestUserByFkey(userId, new MyFirebase.OnDataLoadListener() {
                @Override
                public void onDataLoaded(DataSnapshot dataSnapshot) {
                    //todo - need to process snapShot
                    //todo - need to save user to Boss
                    //todo - need to save user to keeper

                }

                @Override
                public void onCancelled() {

                }
            });

        }
        else{
            Log.d("BaseKeeper", "     no network");
            //get local user data
            getLocalUserData();
        }*/
    }


    abstract protected void onUserLoaded();

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Network Methods:
 */
/**************************************************************************************************/

    protected boolean checkNetwork(){
        //get network connection utility
        NetworkUtility network = NetworkUtility.getInstance();
        mHaveNetwork = network.hasConnection(mActivity);

        if(!mHaveNetwork){
            //no connection, show alert dialog
            mAlertDialog = network.showNoNetworkDialog(mActivity, mDiaRefreshListener);
            mAlertDialog.show();
        }

        return mHaveNetwork;
    }

/**************************************************************************************************/

}

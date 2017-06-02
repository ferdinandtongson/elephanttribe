package me.makeachoice.elephanttribe.controller.viewside.navigation.drawer;

import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.makeachoice.elephanttribe.utilities.DeprecatedUtility;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**************************************************************************************************/
/*
 * MyToolbar is an Abstract class that implements MyActivity.OptionsMenuListener.
 */
/**************************************************************************************************/

public abstract class MyDrawer {

/**************************************************************************************************/
/*
 * Class Variables:
 *      MyActivity mActivity - current activity
 *      Drawer mDrawer - drawer layout object
 *      NavigationView mNavigation - navigation view layout object
 *
 *      View mHeader - drawer header view layout object
 *      ImageView mHeaderIcon - header icon imageView object
 *      TextView mHeaderTitle - header title textView object
 *      TextView mHeaderSubtitle - header subtitle textView object
 */
/**************************************************************************************************/

    //mActivity - current activity
    protected MyActivity mActivity;

    //mDrawer - drawer layout object
    protected DrawerLayout mDrawer;

    //mNavigation - navigation view layout object
    protected NavigationView mNavigation;

    //mHeader - drawer header view layout object
    protected View mHeader;

    //mHeaderIcon - header icon imageView object
    protected ImageView mHeaderIcon;

    //mHeaderTitle - header title textView object
    protected TextView mHeaderTitle;

    //mHeaderSubtitle - header subtitle textView object
    protected TextView mHeaderSubtitle;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Getters:
 *      DrawerLayout getDrawer() - get drawer object
 *      NavigationView getNavigation() - get navigation view object
 *      View getHeader() - get drawer header view object
 */
/**************************************************************************************************/
    /*
     * DrawerLayout getDrawer() - get drawer object
     */
    public DrawerLayout getDrawer(){
        return mDrawer;
    }

    /*
     * NavigationView getNavigation() - get navigation view object
     */
    public NavigationView getNavigation(){
        return mNavigation;
    }

    /*
     * View getHeader() - get drawer header view object
     */
    public View getHeader(){
        return mHeader;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setters:
 *      void setTitle(...) - set title values
 *      void setSubtitle(...) - set subtitle values
 *      void setTextColor(...) - set title and subtitle text color
 *      void setHeaderIcon(...) - set header icon
 */
/**************************************************************************************************/
    /*
     * void setTitle(...) - set title values
     */
    public void setTitle(String title){
        //set title value
        mHeaderTitle.setText(title);
    }

    /*
     * void setSubtitle(...) - set subtitle values
     */
    public void setSubtitle(String subtitle){
        //set subtitle value
        mHeaderSubtitle.setText(subtitle);
    }

    /*
     * void setTextColor(...) - set title and subtitle text color
     */
    public void setTextColor(int titleColor, int subtitleColor){
        mHeaderTitle.setTextColor(titleColor);

        mHeaderSubtitle.setTextColor(subtitleColor);
    }

    /*
     * void setHeaderIcon(...) - set header icon
     */
    public void setHeaderIcon(int resId){
        //get drawable from resource id
        Drawable drawable = DeprecatedUtility.getDrawable(mActivity, resId);

        mHeaderIcon.setImageDrawable(drawable);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Methods:
 *      void createNavigationMenu(...) - create menu options for navigationView
 *      void openDrawer() - open drawer to display navigationView
 *      void openDrawer(...) - open drawer to display navigationView with menu item checked
 */
/**************************************************************************************************/
    /*
     * void createNavigationMenu(...) - create menu options for navigationView
     */
    public void createNavigationMenu(int menuId){
        mNavigation.inflateMenu(menuId);
    }

    /*
     * void openDrawer() - open drawer to display navigationView
     */
    public void openDrawer(){
        mDrawer.openDrawer(GravityCompat.START);
    }

    /*
     * void openDrawer(...) - open drawer to display navigationView with menu item checked
     */
    public void openDrawer(int menuId){
        mDrawer.openDrawer(GravityCompat.START);
        mNavigation.setCheckedItem(menuId);
    }

/**************************************************************************************************/


}

package me.makeachoice.elephanttribe.controller.viewside.navigation.toolbar;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.makeachoice.elephanttribe.utilities.DeprecatedUtility;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**************************************************************************************************/
/*
 * MyToolbar is an Abstract class that implements MyActivity.OptionsMenuListener.
 */

/**************************************************************************************************/

public abstract class MyToolbar implements MyActivity.OptionsMenuListener{

/**************************************************************************************************/
/*
 * Class Variables:
 *      int NO_TOOLBAR_MENU - no menu options
 *      MyActivity mActivity - current activity
 *      Toolbar mToolbar - toolbar object
 *      int mMenuId - menu resource id
 */
/**************************************************************************************************/

    //NO_TOOLBAR_MENU - no menu options
    public final static int NO_TOOLBAR_MENU = -1;

    //mActivity - current activity
    protected MyActivity mActivity;

    //mToolbar - toolbar object
    protected Toolbar mToolbar;

    //mMenuId - menu resource id
    protected int mMenuId;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * MyActivity.OptionsMenuListener Methods:
 *      void createOptionsMenu(...) - called by activity to inflate menu
 *      boolean optionsItemSelected(...) - menu item clicked
 */
/**************************************************************************************************/
    /*
     * void createOptionsMenu(...) - called by activity to inflate menu
     */
    public void createOptionsMenu(Menu menu){
        if(mMenuId != NO_TOOLBAR_MENU){
            mActivity.getMenuInflater().inflate(mMenuId, menu);
        }
    }

    /*
     * boolean optionsItemSelected(...) - menu item clicked
     */
    public abstract boolean optionsItemSelected(MenuItem item);

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Getters:
 *      Toolbar getToolbar() - get toolbar object
 */
/**************************************************************************************************/
    /*
     * Toolbar getToolbar() - get toolbar object
     */
    public Toolbar getToolbar(){ return mToolbar; }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setters:
 *      void setTitle(...) - set title values
 *      void setSubtitle(...) - set subtitle values
 *      void setTextColor(...) - set title and subtitle text color
 *      void setBackgroundColor(...) - set toolbar background color
 *      void setBackgroundDrawable(...) - set toolbar background drawable
 *      void setNavigationIcon(...) - set toolbar navigation icon
 *      void setOverflowIcon(...) - set overflow icon
 *      void setNavigationOnClickListener(...) - set navigation onClick listener
 */
/**************************************************************************************************/
    /*
     * void setTitle(...) - set title values
     */
    public void setTitle(String title){
        //set title value
        mToolbar.setTitle(title);
    }

    /*
     * void setSubtitle(...) - set subtitle values
     */
    public void setSubtitle(String subtitle){
        //set subtitle value
        mToolbar.setSubtitle(subtitle);
    }

    /*
     * void setTextColor(...) - set title and subtitle text color
     */
    public void setTextColor(int titleColor, int subtitleColor){
        //set title text color
        mToolbar.setTitleTextColor(titleColor);

        //set subtitle text color
        mToolbar.setSubtitleTextColor(subtitleColor);
    }

    /*
     * void setBackgroundColor(...) - set toolbar background color
     */
    public void setBackgroundColor(int color){
        mToolbar.setBackgroundColor(color);
    }

    /*
     * void setBackgroundDrawable(...) - set toolbar background drawable
     */
    public void setBackgroundDrawable(int resId){
        //get drawable from resource id
        Drawable drawable = DeprecatedUtility.getDrawable(mActivity, resId);

        //set background drawable
        mToolbar.setBackground(drawable);
    }

    /*
     * void setNavigationIcon(...) - set toolbar navigation icon
     */
    public void setNavigationIcon(int resId){
        mToolbar.setNavigationIcon(resId);
    }

    /*
     * void setOverflowIcon(...) - set overflow icon
     */
    public void setOverflowIcon(int resId){
        //get drawable from resource id
        Drawable drawable = DeprecatedUtility.getDrawable(mActivity, resId);

        //set overflow icon
        mToolbar.setOverflowIcon(drawable);
    }

    /*
     * void setNavigationOnClickListener(...) - set navigation onClick listener
     */
    public void setNavigationOnClickListener(View.OnClickListener listener){
        //set navigation icon onClick listener
        mToolbar.setNavigationOnClickListener(listener);
    }

/**************************************************************************************************/

}

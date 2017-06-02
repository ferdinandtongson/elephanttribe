package me.makeachoice.elephanttribe.view.activity.base;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


/**************************************************************************************************/
/*
 * MyActivity abstract class extends AppCompatActivity
 */

/**************************************************************************************************/


public abstract class MyActivity extends AppCompatActivity{

/**************************************************************************************************/
/*
 * Class Variable:
 *      Bridge mBridge - bridge interface
 *
 * Bridge Interface:
 *      void create(...) - called by onCreate() in the activity lifecycle
 *      void start() - called by onStart() in the activity lifecycle
 *      void resume() - called by onResume() in the activity lifecycle
 *      void pause() - called by onPause() in the activity lifecycle
 *      void stop() - called by onStop() in the activity lifecycle
 *      void destroy() - called by onDestroy() in the activity lifecycle
 *      void backPressed() - called by onBackPressed() in the activity lifecycle
 *      void saveInstanceState() - called by onSaveInstanceState(), before onDestroy(), in the activity lifecycle
 *      void activityResult() - called by onActivityResult()
 */
/**************************************************************************************************/

    //mBridge - bridge interface variable
    protected Bridge mBridge;

    //Bridge interface
    public interface Bridge{
        //called by onCreate() in the activity lifecycle
        void create(MyActivity myActivity, Bundle bundle);

        //called by onStart() in the activity lifecycle
        void start();

        //called by onResume() in the activity lifecycle
        void resume();

        //called by onPause() in the activity lifecycle
        void pause();

        //called by onStop() in the activity lifecycle
        void stop();

        //called by onDestroy() in the activity lifecycle
        void destroy();

        //called by onBackPressed() in the activity lifecycle
        void backPressed();

        //called by onSaveInstanceState() in the activity lifecycle
        void saveInstanceState(Bundle bundle);

        //called by onActivityResult() in the activity lifecycle
        void activityResult(int requestCode, int resultCode, Intent data);

    }

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Class Variable:
 *      OptionsMenuListener mMenuListener - OptionsMenuListener interface
 *
 * OptionsMenuListener Interface:
 *      void createOptionsMenu(...) - called by onCreateOptionsMenu()
 *      void optionsItemSelected(...) - called by onOptionsItemSelected, menu item clicked
 */
/**************************************************************************************************/

    //mMenuListener - OptionsMenuListener interface
    protected OptionsMenuListener mMenuListener;

    //OptionsMenuListener Interface
    public interface OptionsMenuListener{
        //called by onCreateOptionsMenu()
        void createOptionsMenu(Menu menu);

        //called by onOptionsItemSelected, menu item clicked
        boolean optionsItemSelected(MenuItem item);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Class Variable:
 *      ContextItemSelectedListener mContextItemListener - ContextItemSelectedListener interface
 *
 * ContextItemSelectedListener Interface:
 *      boolean contextItemSelected(...) - called when a context menu item clicked
 */
/**************************************************************************************************/

    //mContextItemListener - ContextItemSelectedListener interface
    protected ContextItemSelectedListener mContextItemListener;

    //ContextItemSelectedListener Interface
    public interface ContextItemSelectedListener{
        //called when a context menu item clicked
        boolean contextItemSelected(MenuItem item);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Variable:
 *      PermissionListener mPermissionListener - PermissionListener interface
 *
 * PermissionListener Interface:
 *      void permissionResult(...) - called on a permission result event
 */
/**************************************************************************************************/

    //mPermissionListener - PermissionListener interface
    protected PermissionListener mPermissionListener;

    //PermissionListener Interface
    public interface PermissionListener{
        //called on a permission result event
        void permissionResult(int requestCode, String[] permissions, int[] grantResults);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Setter Methods:
 *      void setOptionsMenuListener() - set options menu listener
 *      void setContextItemSelectedListener() - set context menu item select listener
 *      void setPermissionListener() - set permission result listener
 */
/**************************************************************************************************/
    /*
     * void setOptionsMenuListener(...) - set options menu listener
     */
    public void setOptionsMenuListener(OptionsMenuListener listener){
        mMenuListener = listener;
    }

    /*
     * void setContextItemSelectedListener(...) - set context menu item select listener
     */
    public void setContextItemSelectedListener(ContextItemSelectedListener listener){
        mContextItemListener = listener;
    }

    /*
     * void setPermissionListener(...) - set permission result listener
     */
    public void setPermissionListener(PermissionListener listener){
        mPermissionListener = listener;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Class Methods - Activity Lifecycle:
 *      void onStart() - called when activity is visible to user
 *      void onResume() - called when user can start interacting with activity
 *      void onPause() - called when activity is going into the background
 *      void onStop() - called when activity is no longer visible to user
 *      void onDestroy() - called by finish() or system is saving space
 *      void onBackPressed() - called when the User presses the "back" button
 *      void onSaveInstanceState(...) - called before onDestroy(), save state to bundle
 *      void onActivityResult(...) - result of an activity called by this activity
 *      void finishActivity() - closes the activity
 */
/**************************************************************************************************/
    /*
     * void onStart() - called when activity is visible to user; place register broadcast receivers here
     */
    @Override
    public void onStart(){
        super.onStart();
        mBridge.start();
    }

    /*
     * void onResume() - called when user can start interacting with activity; begin animation, open
     *      exclusive-access devices (such as cameras) and restore states here
     */
    @Override
    public void onResume(){
        super.onResume();
        mBridge.resume();
    }

    /*
     * void onPause() - called when activity is going into the background; only lightweight operations,
     *      save persistent states, stop animation, etc
     */
    @Override
    public void onPause(){
        super.onPause();
        mBridge.pause();
    }

    /*
     * void onStop() - called when activity is no longer visible to user; unregister broadcast receivers here
     */
    @Override
    public void onStop(){
        super.onStop();
        mBridge.stop();
    }

    /*
     * void onDestroy() - called by finish() or system is saving space; use for final cleanup
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        mBridge.destroy();
    }

    /*
     * void onBackPressed() - called when the User presses the "back" button
     */
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mBridge.backPressed();
    }

    /*
     * void onSaveInstanceState(...) - called before onDestory(), save state to bundle
     */
    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        mBridge.saveInstanceState(bundle);
    }

    /*
     * void onActivityResult(...) - result of an activity called by this activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        mBridge.activityResult(requestCode, resultCode, data);
    }

    /*
     * void finishActivity() - closes the activity
     */
    public void finishActivity(){ this.finish(); }

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Class Methods - Options & Context Menu:
 *      boolean onCreateOptionsMenu(...) - called when creating options menu
 *      boolean onOptionsItemSelected(...) - called when option menu item is selected
 *      boolean onContextItemSelected(...) - called when context menu item is selected
 */
/**************************************************************************************************/
    /*
     * boolean onCreateOptionsMenu(...) - called when creating options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(mMenuListener != null){
            mMenuListener.createOptionsMenu(menu);
        }
        return true;
    }

    /*
     * boolean onOptionsItemSelected(...) - called when option menu item is selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        if(mMenuListener != null){
            mMenuListener.optionsItemSelected(item);
        }

        return true;
    }

    /*
     * boolean onContextItemSelected(...) - called wehn context menu item is selected
     */
    @Override
    public boolean onContextItemSelected(MenuItem item){
        super.onContextItemSelected(item);

        if(mContextItemListener != null){
            mContextItemListener.contextItemSelected(item);
        }

        return true;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Class Methods - Permissions:
 *      void onRequestPermissionsResult(...) - called on permission result event
 *      void showPermissionExplanation(...) - show alert dialog explaining permission request
 *      void requestPermission(...) - request permission from user
 */
/**************************************************************************************************/
    /*
     * void onRequestPermissionsResult(...) - called on permission result event
     */
    @TargetApi(23)
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(mPermissionListener != null){
            mPermissionListener.permissionResult(requestCode, permissions, grantResults);
        }
    }

    /*
     * void showPermissionExplanation(...) - show alert dialog explaining permission request
     */
    public void showPermissionExplanation(String title, String msg,
                                          final String permission, final int permissionCode){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        requestPermission(permission, permissionCode);
                    }
                });
        builder.create().show();
    }

    /*
     * void requestPermission(...) - request permission from user
     */
    @TargetApi(23)
    private void requestPermission(String permission, int permissionCode){
        ActivityCompat.requestPermissions(this, new String[]{permission}, permissionCode);
    }

/**************************************************************************************************/

}

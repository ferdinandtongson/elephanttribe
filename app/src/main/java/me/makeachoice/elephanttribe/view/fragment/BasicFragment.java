package me.makeachoice.elephanttribe.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.makeachoice.elephanttribe.controller.manager.base.MaidRegistry;

/**************************************************************************************************/
/*
 * BasicFragment handles the creation of a fragment then passes the maintenance and initialization
 * to a Maid class
 *
 * MyFragment Variables:
 *      String TAG_MAID_KEY - string values used as a tag when putting the maidKey into a bundle
 *      String mMaidName - maid name managing fragment
 *      View mLayout - view layout used by the fragment
 *      Bridge mBridge - bridge interface
 *
 * MyFragment Methods:
 *      void onActivityCreated(...) - called when Activity.onCreate() is completed
 *      void onDestroyView() - called when fragment is being removed
 *      void onDetach() - called when fragment is being disassociated from Activity
 *      void onSaveInstanceState(...) - save state to bundle
 *
 */

/**************************************************************************************************/

public class BasicFragment extends MyFragment {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 *
 * Fragment subclasses require and empty default constructor. If you don't provide one but specify
 * a non-empty constructor, Lint will give you an error.
 *
 * Android may destroy and later re-create an activity and all its associated fragments when the app
 * goes into the background. When the activity comes back, its FragmentManager starts re-creating
 * fragments by using the empty default constructor. If it cannot find one, you get an exception
 */
/**************************************************************************************************/

    public static BasicFragment newInstance(String maidName){
        BasicFragment f = new BasicFragment();

        //create bundle
        Bundle args = new Bundle();

        //add maid key to bundle
        args.putString(TAG_MAID_KEY, maidName);

        //set bundle in fragment
        f.setArguments(args);

        return f;
    }

    public BasicFragment(){}

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Fragment Lifecycle Methods:
 *      View onCreateView(...) - called when the fragment UI is drawn
 */
/**************************************************************************************************/
    /*
     * View onCreateView(...) - called when the fragment UI is drawn. To draw a UI for your fragment
     * you must return a View from this method. It is the root of your fragments' layout. You can
     * return null if the fragment does not provide a UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        //check if bundle sent/saved
        if(bundle != null){
            mMaidName = bundle.getString(TAG_MAID_KEY);
        }
        else{
            mMaidName = getArguments().getString(TAG_MAID_KEY);
        }

        //get maid registry
        MaidRegistry registry = MaidRegistry.getInstance();

        //get maid
        mBridge = (Bridge)registry.requestMaid(mMaidName);

        //check if view layout is null
        if(mLayout == null){
            //inflate layout
            mLayout = mBridge.createView(inflater, container, bundle);
        }

        return mLayout;
    }

    /*
     * void onSaveInstanceState(...) - save state to bundle
     */
    @Override
    public void onSaveInstanceState(Bundle bundle){
        //save maid name to bundle
        bundle.putString(TAG_MAID_KEY, mMaidName);


        super.onSaveInstanceState(bundle);
    }

/**************************************************************************************************/

}

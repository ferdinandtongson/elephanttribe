package me.makeachoice.elephanttribe.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**************************************************************************************************/
/*
 * MyFragment abstract class extends Fragment
 */
/**************************************************************************************************/

public abstract class MyFragment extends Fragment {

/**************************************************************************************************/
/*
 * Class Variables:
 *      String TAG_MAID_KEY - string values used as a tag when putting the maidKey into a bundle
 *      String mMaidName - maid name managing fragment
 *      View mLayout - view layout used by the fragment
 *      Bridge mBridge - bridge interface
 *
 * Bridge Interface:
 *      View createView(...) - called by onCreateView() in Fragment lifecycle
 *      void activityCreated(...) - called by onActivityCreated() in fragment lifecycle
 *      void start() - called by onStart() in fragment lifecycle
 *      void resume() - called by onResume() in fragment lifecycle
 *      void destroyView() - called by onDestroyView() in fragment lifecycle
 *      void detach() - called by onDetach() in fragment lifecycle
 *      void saveInstanceState(...) - called before onDestroy(), part of fragment lifecycle
 */
/**************************************************************************************************/

    //TAG_MAID_KEY - string values used as a tag when putting the maidKey into a bundle
    public final static String TAG_MAID_KEY = "tag_maidKey";

    //mMaidName - maid name managing fragment
    protected String mMaidName;

    //mLayout - view layout used by the fragment
    protected View mLayout;

    //mBridge - bridge interface
    protected Bridge mBridge;

    //Bridge interface
    public interface Bridge{
        //called by onCreateView() in fragment lifecycle; fragment UI is drawn
        View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle);

        //called by onActivityCreated(...) in fragment lifecycle; Activity.onCreate() completed
        void activityCreated(Bundle bundle);

        //void start() - called by onStart() in fragment lifecycle
        void start();

        //void resume() - called by onResume() in fragment lifecycle
        void resume();

        //called by onDestroyView() in fragment lifecycle; fragment is being removed
        void destroyView();

        //called by onDetach() in fragment lifecycle; fragment is disassociated from activity
        void detach();

        //called before onDestroy(); save state to bundle
        void saveInstanceState(Bundle bundle);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Methods:
 *      void onActivityCreated(...) - called when Activity.onCreate() is completed
 *      void onStart() - called when fragment is ready for user interaction
 *      void onResume() - called when fragment resumes active state
 *      void onDestroyView() - called when fragment is being removed
 *      void onDetach() - called when fragment is being disassociated from Activity
 *      void onSaveInstanceState(...) - save state to bundle
 */
/**************************************************************************************************/
    /*
     * void onActivityCreated(...) - called when Activity.onCreate() is completed
     */
    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        mBridge.activityCreated(bundle);
    }

    /*
     * void onStart() - called when fragment is ready for user interaction
     */
    @Override
    public void onStart(){
        super.onStart();

        mBridge.start();
    }

    /*
     * void onResume() - called when fragment resumes active state
     */
    @Override
    public void onResume(){
        super.onResume();
        mBridge.resume();
    }

    /*
     * onDestroyView() - called when fragment is being removed
     */
    @Override
    public void onDestroyView(){
        super.onDestroyView();

        mBridge.destroyView();
    }


    /*
     * void onDetach() - called when fragment is being disassociated from Activity
     */
    @Override
    public void onDetach(){
        super.onDetach();

        mBridge.detach();
    }

    /*
     * void onSaveInstanceState(...) - save state to bundle
     */
    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);

        mBridge.saveInstanceState(bundle);
    }

/**************************************************************************************************/

}

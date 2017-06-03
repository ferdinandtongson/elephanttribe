package me.makeachoice.elephanttribe.view.dialog.base;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * MyDialog is a base class for Elephant DialogFragment classes
 */

public abstract class MyDialog extends DialogFragment {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //mActivity - current activity
    protected MyActivity mActivity;

    //mLayoutId - layout id
    private int mLayoutId;

    //mRootView - root view component containing dialog child components
    protected View mRootView;

    //mOnDismissListener - listens for dialog dismiss event
    private OnDismissListener mOnDismissListener;
    public interface OnDismissListener{
        void onDismiss(DialogInterface dialogInterface);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public MyDialog(){}

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setter Methods:
 *      void setActivity(...) - set activity context
 *      void setOnDismissListener(...) - set listener for dialog dismiss event
 *      void setOnActivateListener(...) - set listener for dialog deck activation event
 */
/**************************************************************************************************/
    /*
     * void setActivity(...) - set activity context
     */
    public void setActivity(MyActivity activity, int layoutId){
        //get activity
        mActivity = activity;

        //get layout id
        mLayoutId = layoutId;
    }

    /*
     * void setOnDismissListener(...) - set listener for dialog dismiss event
     */
    public void setOnDismissListener(OnDismissListener listener){
        mOnDismissListener = listener;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        //inflate root view
        mRootView = inflater.inflate(mLayoutId, container, false);

        createView(bundle);

        return mRootView;
    }

    protected abstract void createView(Bundle bundle);


    @Override
    public void onDismiss(DialogInterface dialogInterface){
        //check for listener
        if(mOnDismissListener != null){
            mOnDismissListener.onDismiss(dialogInterface);
        }
    }

    protected View getChildView(int id){
        return mRootView.findViewById(id);
    }

/**************************************************************************************************/

}

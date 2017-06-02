package me.makeachoice.elephanttribe.controller.viewside.housekeeper.base;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.viewside.recycler.BasicRecycler;

/**
 * BaseRecyclerKeeper extends BaseKeeper and adds recyclerView methods
 */

public abstract class BaseRecyclerKeeper extends BaseKeeper {

/**************************************************************************************************/
/*
 * Class Variables:
 *      BasicRecycler mBasicRecycler - recyclerView component
 */
/**************************************************************************************************/
    //mBasicRecycler - recyclerView component
    protected BasicRecycler mBasicRecycler;

    //mFAB - floating action button
    protected FloatingActionButton mFAB;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Recycler Methods:
 *      void initializeLinearRecycler() - initialize basic recycler class to display a linear recycler
 *      void initializeStaggeredGridRecycler() - initialize basic recycler class to display grid recycler
 *      void setRecyclerEmptyMessage(...) - message to display when recycler is empty
 *      void checkRecyclerEmpty(...) - check if recycler is empty or not; display empty message if so
 *      void setRecyclerAdapter(...) - set adapter consumed by recycler
 */
/**************************************************************************************************/
    /*
     * void initializeLinearRecycler() - initialize basic recycler class to display a linear recycler
     */
    protected void initializeLinearRecycler(){
        mBasicRecycler = new BasicRecycler(mActivity);
        mBasicRecycler.initializeLinearRecycler(mActivity, true);
    }

    /*
     * void initializeStaggeredGridRecycler() - initialize basic recycler class to display grid recycler
     */
    protected void initializeStaggeredGridRecycler(){
        mBasicRecycler = new BasicRecycler(mActivity);
        mBasicRecycler.initializeStaggeredGridRecycler(mActivity, 2, StaggeredGridLayoutManager.VERTICAL, true);
    }

    /*
     * void setRecyclerEmptyMessage(...) - message to display when recycler is empty
     */
    protected void setRecyclerEmptyMessage(String msg){
        mBasicRecycler.setEmptyMessage(msg);
    }

    /*
     * void checkRecyclerEmpty(...) - check if recycler is empty or not; display empty message if so
     */
    protected void checkRecyclerEmpty(int itemCount){
        mBasicRecycler.checkRecyclerEmpty(itemCount);
    }

    /*
     * void setRecyclerAdapter(...) - set adapter consumed by recycler
     */
    protected void setRecyclerAdapter(RecyclerView.Adapter adapter){
        mBasicRecycler.setAdapter(adapter);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * FAB Methods:
 *      void initializeFAB() - initialize floating action button component
 *      void setFABOnClickListener(...) - set onClick listener for FAB
 */
/**************************************************************************************************/
    /*
     * void initializeFAB() - initialize floating action button component
     */
    protected void initializeFAB(){
        mFAB = (FloatingActionButton)viewById(R.id.choiceFAB);
    }

    /*
     * void setFABOnClickListener(...) - set onClick listener for FAB
     */
    protected void setFABOnClickListener(View.OnClickListener listener){
        mFAB.setOnClickListener(listener);
    }

/**************************************************************************************************/

    abstract protected void onUserLoaded();
}

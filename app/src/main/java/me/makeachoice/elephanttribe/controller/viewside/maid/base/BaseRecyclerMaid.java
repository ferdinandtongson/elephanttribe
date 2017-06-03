package me.makeachoice.elephanttribe.controller.viewside.maid.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import me.makeachoice.elephanttribe.controller.viewside.recycler.BasicRecycler;

/**
 * BaseRecylcerMaid is a base class for Maids using a recyclerView component
 */

public class BaseRecyclerMaid extends BaseMaid {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    protected BasicRecycler mBasicRecycler;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Methods:
 */
/**************************************************************************************************/

    @Override
    public void activityCreated(Bundle bundle){
        super.activityCreated(bundle);
    }

    protected void initializeLinearRecycler(){
        mBasicRecycler = null;
        mBasicRecycler = new BasicRecycler(mActivity);
        mBasicRecycler.initializeLinearRecycler(mActivity, true);
    }

    protected void initializeStaggeredGridRecycler(){
        mBasicRecycler = new BasicRecycler(mActivity);
        mBasicRecycler.initializeStaggeredGridRecycler(mActivity, 2, StaggeredGridLayoutManager.VERTICAL, true);
    }

    protected void setRecyclerEmptyMessage(String msg){
        mBasicRecycler.setEmptyMessage(msg);
    }

    protected void checkRecyclerEmpty(int itemCount){
        mBasicRecycler.checkRecyclerEmpty(itemCount);
    }

    protected void setRecyclerAdapter(RecyclerView.Adapter adapter){
        mBasicRecycler.setAdapter(adapter);
    }

    protected void addRecyclerItemDecoration(RecyclerView.ItemDecoration decor){
        mBasicRecycler.addItemDecoration(decor);
    }

/**************************************************************************************************/

}

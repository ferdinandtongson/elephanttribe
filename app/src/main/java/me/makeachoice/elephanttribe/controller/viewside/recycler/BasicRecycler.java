package me.makeachoice.elephanttribe.controller.viewside.recycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**************************************************************************************************/
/*
 * BasicRecycler manages simple recyclerView objects
 */
/**************************************************************************************************/

public class BasicRecycler {

/**************************************************************************************************/
/*
 * Class Variables:
 *      DEFAULT_EMPTYVIEW_ID - textView component resource id used when recycler is empty
 *      DEFAULT_RECYCLER_ID - recyclerView component resource id
 *
 *      mRecycler - recyclerView component
 *      mTxtEmpty - textView component shown when recycler is empty
 */
/**************************************************************************************************/

    //DEFAULT_EMPTYVIEW_ID - textView component resource id used when recycler is empty
    private final static int DEFAULT_EMPTYVIEW_ID = R.id.choiceEmptyView;

    //DEFAULT_RECYCLER_ID - recyclerView component resource id
    private final static int DEFAULT_RECYCLER_ID = R.id.choiceRecycler;

    //mRecycler - recyclerView component
    private RecyclerView mRecycler;

    //mTxtEmpty - textView component shown when recycler is empty
    private TextView mTxtEmpty;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public BasicRecycler(MyActivity activity){
        //get recyclerView object
        mRecycler = (RecyclerView)activity.findViewById(DEFAULT_RECYCLER_ID);

        //get "empty" textView object
        mTxtEmpty = (TextView)activity.findViewById(DEFAULT_EMPTYVIEW_ID);
    }

    public BasicRecycler(View layout){
        //get recyclerView object
        mRecycler = (RecyclerView)layout.findViewById(DEFAULT_RECYCLER_ID);

        //get "empty" textView object
        mTxtEmpty = (TextView)layout.findViewById(DEFAULT_EMPTYVIEW_ID);
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize Recycler:
 *      void initializeLinearRecycler(...) - initialize recycler with linear layout manager
 *      void initializeStaggeredGridRecycler(...) - initialize recycler with staggered grid layout manager
 */
/**************************************************************************************************/
    /*
     * void initializeLinearRecycler(...) - initialize recycler with linear layout manager
     */
    public void initializeLinearRecycler(Context ctx, boolean isFixedSize){
        //create linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);

        //set layout manager of recyclerView
        mRecycler.setLayoutManager(layoutManager);

        //recyclerView size not dynamic
        mRecycler.setHasFixedSize(isFixedSize);
    }

    /*
     * void initializeStaggeredGridRecycler(...) - initialize recycler with staggered grid layout manager
     */
    public void initializeStaggeredGridRecycler(Context ctx, int numColumn, int orientation,
                                                boolean isFixedSize){
        //create staggeredGrid layout manager
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(numColumn, orientation);

        //set layout manager of recyclerView
        mRecycler.setLayoutManager(layoutManager);

        //recyclerView size not dynamic
        mRecycler.setHasFixedSize(isFixedSize);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Methods:
 *      void checkRecyclerEmpty(...) - check if recycler data is empty
 *      TextView getEmptyView() - get "empty" textView object
 *      RecyclerView getRecycler() - get recyclerView object
 *      void setAdapter(...) - set adapter used by recyclerView
 *      void setEmptyMessage(...) - set message to display if recycler is "empty"
 *      void setEmptyMessageTextColor(...) - set text color for "empty" message
 *      void addItemDecoration(...) - set item decoration
 */
/**************************************************************************************************/
    /*
     * void checkRecyclerEmpty(...) - check if recycler data is empty
     */
    public void checkRecyclerEmpty(int itemCount){
        //check number of items in recycler
        if(itemCount <= 0){
            //no items, show "empty" textView and hide recycler
            mTxtEmpty.setVisibility(View.VISIBLE);
        }
        else{
            //has items, hide "empty" textView and show recycler
            mTxtEmpty.setVisibility(View.GONE);
        }
    }

    /*
     * TextView getEmptyView() - get "empty" textView object
     */
    public TextView getEmptyView(){ return mTxtEmpty; }

    /*
     * RecyclerView getRecycler() - get recyclerView object
     */
    public RecyclerView getRecycler(){ return mRecycler; }

    /*
     * void setAdapter(...) - set adapter used by recyclerView
     */
    public void setAdapter(RecyclerView.Adapter adapter){
        mRecycler.setAdapter(adapter);
    }

    /*
     * void setEmptyMessage(...) - set message to display if recycler is "empty"
     */
    public void setEmptyMessage(String msg){
        mTxtEmpty.setText(msg);
    }

    /*
     * void setEmptyMessageTextColor(...) - set text color for "empty" message
     */
    public void setEmptyMessageTextColor(int color){
        mTxtEmpty.setTextColor(color);
    }

    /*
     * void addItemDecoration(...) - set item decoration
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decor){
        mRecycler.addItemDecoration(decor);
    }

/**************************************************************************************************/


}

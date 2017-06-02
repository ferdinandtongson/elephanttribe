package me.makeachoice.elephanttribe.utilities;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import static android.view.View.SCROLL_AXIS_VERTICAL;

/**
 * FabRecyclerScrollBehavior extends FloatingActionButton.Behavior; fab reacts to recyclerView scrolling
 * events
 */

public class FabRecyclerScrollBehavior extends FloatingActionButton.Behavior {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/



/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public FabRecyclerScrollBehavior(Context context, AttributeSet attrs){ super(); }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Scroll Methods:
 *      boolean onStartNestedScroll(...) - detects start of nested scroll event in coordinator layout
 *      void onNestedScroll(...) - called during nested scrolling in coordinator layout
 */
/**************************************************************************************************/
    /*
     * boolean onStartNestedScroll(...) - detects start of nested scroll event in coordinator layout
     */
    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coorLayout,
                                       final FloatingActionButton fab,
                                       final View directTargetChild, final View target,
                                       final int nestedScrollAxes){

        //ensure we react to vertical scrolling
        return nestedScrollAxes == SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coorLayout, fab, directTargetChild, target, nestedScrollAxes);
    }

    /*
     * void onNestedScroll(...) - called during nested scrolling in coordinator layout
     */
    @Override
    public void onNestedScroll(final CoordinatorLayout coorLayout,
                               final FloatingActionButton fab,
                               final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed){

        super.onNestedScroll(coorLayout, fab, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        //get recyclerView component
        RecyclerView recycler = (RecyclerView)target;

        //get scrollOffset
        LinearLayoutManager lm = (LinearLayoutManager)recycler.getLayoutManager();
        int pos = lm.findFirstVisibleItemPosition();
        int last = lm.findLastCompletelyVisibleItemPosition();
        int showFabAt = lm.getItemCount() - 1;

        if(last == showFabAt && fab.getVisibility() != View.VISIBLE){

            //user scrolled to bottom and FAB is not visible --> show fab
            fab.show();

            //wait 3 seconds, then use invisible instead of fab.hide(), scroll detection stops when fab is hidden
            fab.postDelayed(new Runnable(){public void run(){ fab.setVisibility(View.INVISIBLE); }}, 3000);
        }
        else if(dyConsumed < 0 && fab.getVisibility() == View.VISIBLE){
            //user scrolled up and FAB is visible --> hide fab
            //use invisible instead of fab.hide(), scroll detection stops when fab is hidden
            fab.setVisibility(View.INVISIBLE);
        }
    }

/**************************************************************************************************/


}

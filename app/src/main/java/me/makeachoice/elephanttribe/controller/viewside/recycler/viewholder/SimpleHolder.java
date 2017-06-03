package me.makeachoice.elephanttribe.controller.viewside.recycler.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.makeachoice.elephanttribe.R;

/**
 * TagHolder is the ViewHolder object for item_tag layout
 */

public class SimpleHolder extends RecyclerView.ViewHolder{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private RelativeLayout mItemView;

    private TextView mTxtName;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public SimpleHolder(View recyclerView){
        super(recyclerView);
        mItemView = (RelativeLayout)itemView.findViewById(R.id.choiceItemView);
        mTxtName = (TextView)itemView.findViewById(R.id.itemSimple_txtName);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Bind Methods:
 *      void bindItemView(...) - bind parent view component
 *      void  bindTextView(...) - bind textView child components
 */
/**************************************************************************************************/
    /*
     * void bindItemView(...) - bind parent view component
     */
    public void bindItemView(String item, int index, View.OnClickListener onClickListener,
                             View.OnCreateContextMenuListener onCreateContextMenuListener){
        //set tag values
        mItemView.setTag(R.string.tag_position, index);
        mItemView.setTag(R.string.tag_item, item);

        //bind listener
        if(onClickListener != null){
            mItemView.setOnClickListener(onClickListener);
        }

        if(onCreateContextMenuListener != null){
            mItemView.setOnCreateContextMenuListener(onCreateContextMenuListener);
        }
    }

    /*
     * void  bindTextView(...) - bind textView child components
     */
    public void bindTextView(String item){
        mTxtName.setText(item);
    }


/**************************************************************************************************/

}

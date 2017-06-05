package me.makeachoice.elephanttribe.controller.viewside.recycler.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import me.makeachoice.elephanttribe.R;

/**
 * ChoiceTextHolder is the viewHolder class for ChoiceTextAdapter
 */

public class ChoiceTextHolder extends RecyclerView.ViewHolder{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

private CardView mCardView;

    private TextView mTxtName;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public ChoiceTextHolder(View recyclerView){
        super(recyclerView);

        mCardView = (CardView)itemView.findViewById(R.id.choiceCardView);
        mTxtName = (TextView)itemView.findViewById(R.id.cardChoiceText_txtChoice);
    }

    public void bindTextView(String item){

        mTxtName.setText(item);
    }

    public void bindTextView(String item, int textSize){
        mTxtName.setText(item);
        mTxtName.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void bindItemView(int bgColor, int textColor){
        mCardView.setCardBackgroundColor(bgColor);
        mTxtName.setTextColor(textColor);
    }

    public void bindItemOnClickListener(String item, int position, View.OnClickListener listener){
        mCardView.setTag(R.string.tag_position, position);
        mCardView.setTag(R.string.tag_item, item);
        mCardView.setOnClickListener(listener);
    }


/**************************************************************************************************/

}

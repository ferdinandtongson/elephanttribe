package me.makeachoice.elephanttribe.controller.viewside.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.viewside.recycler.viewholder.ChoiceTextHolder;
import me.makeachoice.elephanttribe.utilities.DeprecatedUtility;

/**************************************************************************************************/
/*
 * SimpleChoiceAdapter is a simple textView item layout adapter used for displaying text only
 * distractor choices
 */

/**************************************************************************************************/

public class ChoiceTextAdapter extends RecyclerView.Adapter<ChoiceTextHolder>{

/**************************************************************************************************/
/*
 * Class Variables:
 *      int EMPTY_TEXTSIZE - empty textSize
 *      Context mContext - activity context
 *      ArrayList<String> mData - data used by adapter
 *      int mLayoutId - item/card layout resource id
 *      mTextSize - textSize of textView
 *      View.OnClickListener mItemOnClickListener - item onClick listener
 */
/**************************************************************************************************/

    public final static int SELECTION_NONE = 0;
    public final static int SELECTION_WRONG = 1;
    public final static int SELECTION_RIGHT = 2;

    //EMPTY_TEXTSIZE - empty textSize
    private int EMPTY_TEXTSIZE = -1;

    //mContext - activity context
    private Context mContext;

    //mData - data used by adapter
    private ArrayList<String> mData;

    //mSelectedList - status flag for items already selected or not
    private ArrayList<Integer> mSelectedList;

    //mLayoutId - item/card layout resource id
    private int mLayoutId;

    //mTextSize - textSize of textView
    private int mTextSize;

    private int mColorSand;
    private int mColorSilver;
    private int mColorWine;
    private int mColorBlack;
    private int mColorGray;

    //mItemOnClickListener - item onClick listener
    private View.OnClickListener mItemOnClickListener;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public ChoiceTextAdapter(Context ctx, int layoutId){
        //set context
        mContext = ctx;

        //set layout id
        mLayoutId = layoutId;

        //initialize arrayList
        mData = new ArrayList<>();
        mSelectedList = new ArrayList<>();

        //initialize textSize
        mTextSize = EMPTY_TEXTSIZE;

        mColorSand = DeprecatedUtility.getColor(ctx, R.color.sand);
        mColorSilver = DeprecatedUtility.getColor(ctx, R.color.silver);
        mColorWine = DeprecatedUtility.getColor(ctx, R.color.wine);
        mColorBlack = DeprecatedUtility.getColor(ctx, R.color.black);
        mColorGray = DeprecatedUtility.getColor(ctx, R.color.gray);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Getter Methods:
 *      int getItemCount() - get number of data items in adapter
 *      String getItem(...) - get data item at given index
 *      ArrayList<String> getData() - get data used by adapter
 */
/**************************************************************************************************/
    /*
     * int getItemCount() - get number of data items in adapter
     */
    @Override
    public int getItemCount(){
        //check data Not null
        if(mData != null){
            //return number of items
            return mData.size();
        }

        //no data, return 0
        return 0;
    }

    /*
     * String getItem(...) - get data item at given index
     */
    public String getItem(int index){
        //check data Not null
        if(mData != null){
            //return data at given index
            return mData.get(index);

        }

        //no data, return empty string
        return "";
    }

    /*
     * ArrayList<String> getData() - get data used by adapter
     */
    public ArrayList<String> getData(){
        //check data Not null
        if(mData != null){
            return mData;
        }

        //no data, return empty array
        return new ArrayList<>();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setter:
 *      void setItemOnClickListener(...) - set item onClick listener
 *      void setTextSize(...) - set textSize to be used
 */
/**************************************************************************************************/
    /*
     * void setItemOnClickListener(...) - set item onClick listener
     */
    public void setItemOnClickListener(View.OnClickListener listener){
        //set listener
        mItemOnClickListener = listener;
    }

    /*
     * void setTextSize(...) - set textSize to be used
     */
    public void setTextSize(int size){
        mTextSize = size;
    }

    public void setSelectedList(ArrayList<Integer> selected){
        mSelectedList.clear();

        mSelectedList.addAll(selected);

        //notify adapter of data change
        this.notifyDataSetChanged();

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Public Methods:
 *      void addItem(...) - add item to adapter
 *      void addItemAt(...) - add item to adapter at index
 *      void removeItemAt(...) - remove item from adapter at index
 *      void swapData(...) - swap old data with new data
 *      void clearData() - remove all data from adapter
 */
/**************************************************************************************************/
    /*
     * void addItem(...) - add item to adapter
     */
    public void addItem(String item){
        //add item to data array
        mData.add(item);

        //notify adapter of data change
        this.notifyDataSetChanged();
    }

    /*
     * void addItemAt(...) - add item to adapter at index
     */
    public void addItemAt(int index, String item){
        //add item to data array at index
        mData.add(index, item);

        //notify adapter of data change
        this.notifyDataSetChanged();
    }

    /*
     * void removeItemAt(...) - remove item from adapter at index
     */
    public void removeItemAt(int index){
        //remove item from data array at index
        mData.remove(index);

        //notify adapter item was removed
        this.notifyItemRemoved(index);

        //notify adapter of data change
        this.notifyDataSetChanged();
    }

    /*
     * void swapData(...) - swap old data with new data
     */
    public void swapData(ArrayList<String> newData){
        //clear old data
        mData.clear();

        //add new data
        mData.addAll(newData);

        //notify adapter of data change
        this.notifyDataSetChanged();
    }

    /*
     * void clearData() - remove all data from adapter
     */
    public void clearData(){
        //clear data
        mData.clear();

        //notify adapter of data change
        this.notifyDataSetChanged();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * RecyclerView.Adapter Implementation Methods:
 *      ViewHolder onCreateViewHolder(...) - create viewHolder class for adapter
 *      void onBindViewHolder(...) - bind data to viewHolder
 */
/**************************************************************************************************/
    /*
     * ViewHolder onCreateViewHolder(...) - create viewHolder class for adapter
     */
    @Override
    public ChoiceTextHolder onCreateViewHolder(ViewGroup viewGroup, int index){
        //inflate itemView
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(mLayoutId, viewGroup, false);

        //return viewHolder
        return new ChoiceTextHolder(itemView);
    }

    /*
     * void onBindViewHolder(...) - bind data to viewHolder
     */
    @Override
    public void onBindViewHolder(ChoiceTextHolder holder, int index){
        //get data item
        String item = mData.get(index);

        //bind textView component
        if(mTextSize == EMPTY_TEXTSIZE){
            //bind without textView size
            holder.bindTextView(item);
        }
        else{
            //bind with textView size
            holder.bindTextView(item, mTextSize);
        }

        if(mItemOnClickListener != null){
            holder.bindItemOnClickListener(item, index, mItemOnClickListener);
        }

        switch(mSelectedList.get(index)){
            case SELECTION_NONE:
                holder.bindItemView(mColorSand, mColorBlack);
                break;
            case SELECTION_WRONG:
                holder.bindItemView(mColorSilver, mColorGray);
                break;
            case SELECTION_RIGHT:
                holder.bindItemView(mColorWine, mColorBlack);
                break;
            default:
                holder.bindItemView(mColorSand, mColorBlack);
        }
    }

/**************************************************************************************************/


}

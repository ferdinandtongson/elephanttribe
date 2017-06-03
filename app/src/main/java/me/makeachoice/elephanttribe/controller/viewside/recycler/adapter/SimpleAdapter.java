package me.makeachoice.elephanttribe.controller.viewside.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.controller.viewside.recycler.viewholder.SimpleHolder;

/**************************************************************************************************/
/*
 * TagAdapter is a simple textView item layout adapter used to display deck and flashcard tag values
 */

/**************************************************************************************************/

public class SimpleAdapter extends RecyclerView.Adapter<SimpleHolder>{

/**************************************************************************************************/
/*
 * Class Variables:
 *      Context mContext - activity context
 *      ArrayList<String> mData - data used by adapter
 *      int mLayoutId - item/card layout resource id
 *      View.OnClickListener mItemOnClickListener - item onClick listener
 */
/**************************************************************************************************/

    //mContext - activity context
    private Context mContext;

    //mData - data used by adapter
    private ArrayList<String> mData;

    //mLayoutId - item/card layout resource id
    private int mLayoutId;

    //mItemOnClickListener - item onClick listener
    private View.OnClickListener mItemOnClickListener;

    //mCreateMenuListener - create context menu event listener
    private View.OnCreateContextMenuListener mCreateMenuListener;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public SimpleAdapter(Context ctx, int layoutId){
        //set context
        mContext = ctx;

        //set layout id
        mLayoutId = layoutId;

        //initialize arrayList
        mData = new ArrayList<>();

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
 */
/**************************************************************************************************/
    /*
     * void setItemOnClickListener(...) - set item onClick listener
     */
    public void setItemOnClickListener(View.OnClickListener listener){
        //set listener
        mItemOnClickListener = listener;
    }

    public void setOnCreateMenuListener(View.OnCreateContextMenuListener listener){
        mCreateMenuListener = listener;
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
    public SimpleHolder onCreateViewHolder(ViewGroup viewGroup, int index){
        //inflate itemView
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(mLayoutId, viewGroup, false);

        //return viewHolder
        return new SimpleHolder(itemView);
    }

    /*
     * void onBindViewHolder(...) - bind data to viewHolder
     */
    @Override
    public void onBindViewHolder(SimpleHolder holder, int index){
        //get data item
        String item = mData.get(index);

        //bind to viewHolder
        holder.bindTextView(item);

        holder.bindItemView(item, index, mItemOnClickListener, mCreateMenuListener);
    }

/**************************************************************************************************/


}

package me.makeachoice.elephanttribe.controller.viewside.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.OnBoomListener;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.viewside.recycler.viewholder.UserDeckHolder;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;

/**
 * DeckAdapter displays a list of decks available for the user
 */

public class UserDeckAdapter extends RecyclerView.Adapter<UserDeckHolder>{

/**************************************************************************************************/
/*
 * Class Variables:
 *      Context mContext - activity context
 *      ArrayList<DeckItem> mData - data used by adapter
 *      int mLayoutId - item/card layout resource id
 *      View.OnClickListener mItemOnClickListener - item onClick listener
 */
/**************************************************************************************************/

    //mContext - activity context
    private Context mContext;

    //mData - data used by adapter
    private ArrayList<DeckItem> mData;

    //mBoomMenuTitles - list of boom menu titles
    private ArrayList<String> mBoomMenuTitles;

    //mBoomMenuSubtitles - list of boom menu subtitles
    private ArrayList<String> mBoomMenuSubtitles;

    //mBoomMenuIcons - list of boom menu image resource ids
    private ArrayList<Integer> mBoomMenuIcons;

    //mLayoutId - item/card layout resource id
    private int mLayoutId;

    private String mStrEdit;

    //mItemOnClickListener - item onClick listener
    private View.OnClickListener mItemOnClickListener;

    private OnBMClickListener mBMClickListener;
    private OnBoomListener mBoomListener;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public UserDeckAdapter(Context ctx, int layoutId){
        //set context
        mContext = ctx;

        //set layout id
        mLayoutId = layoutId;

        mStrEdit = ctx.getString(R.string.edit);

        //initialize arrayList
        mData = new ArrayList<>();
        initializeBoomMenu();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    private void initializeBoomMenu(){
        mBoomMenuTitles = new ArrayList<>();
        mBoomMenuTitles.add(mContext.getString(R.string.bm_edit));
        mBoomMenuTitles.add(mContext.getString(R.string.bm_leitner));
        mBoomMenuTitles.add(mContext.getString(R.string.bm_flashcard));
        mBoomMenuTitles.add(mContext.getString(R.string.bm_stats));

        mBoomMenuSubtitles = new ArrayList<>();
        mBoomMenuSubtitles.add("");
        mBoomMenuSubtitles.add(mContext.getString(R.string.bm_leitner_subtitle));
        mBoomMenuSubtitles.add(mContext.getString(R.string.bm_flashcard_subtitle));
        mBoomMenuSubtitles.add(mContext.getString(R.string.bm_stats_subtitle));

        mBoomMenuIcons = new ArrayList<>();
        mBoomMenuIcons.add(R.drawable.ic_edit_black_36dp);
        mBoomMenuIcons.add(R.drawable.ic_archive_black_36dp);
        mBoomMenuIcons.add(R.drawable.ic_style_black_36dp);
        mBoomMenuIcons.add(R.drawable.ic_assessment_black_36dp);

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Getter Methods:
 *      int getItemCount() - get number of data items in adapter
 *      DeckItem getItem(...) - get data item at given index
 *      ArrayList<DeckItem> getData() - get data used by adapter
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
     * DeckItem getItem(...) - get data item at given index
     */
    public DeckItem getItem(int index){
        //check data Not null
        if(mData != null){
            //return data at given index
            return mData.get(index);

        }

        //no data, return empty string
        return null;
    }

    /*
     * ArrayList<DeckItem> getData() - get data used by adapter
     */
    public ArrayList<DeckItem> getData(){
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
 *      void setBoomMenuValues(...) - set boom menu title, subtitle and icon values
 */
/**************************************************************************************************/
    /*
     * void setItemOnClickListener(...) - set item onClick listener
     */
    public void setItemOnClickListener(View.OnClickListener listener){
        //set listener
        mItemOnClickListener = listener;
    }

    public void setBoomMenuListener(OnBMClickListener listener){
        mBMClickListener = listener;
    }

    public void setBoomListener(OnBoomListener listener){
        mBoomListener = listener;
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
    public void addItem(DeckItem item){
        //add item to data array
        mData.add(item);

        //notify adapter of data change
        this.notifyDataSetChanged();
    }

    /*
     * void addItemAt(...) - add item to adapter at index
     */
    public void addItemAt(int index, DeckItem item){
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
    public void swapData(ArrayList<DeckItem> newData){
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
 *      int getBackgroundColor(...) - get background color depending on status
 */
/**************************************************************************************************/
    /*
     * ViewHolder onCreateViewHolder(...) - create viewHolder class for adapter
     */
    @Override
    public UserDeckHolder onCreateViewHolder(ViewGroup viewGroup, int index){
        //inflate itemView
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(mLayoutId, viewGroup, false);

        //return viewHolder
        return new UserDeckHolder(itemView);
    }

    /*
     * void onBindViewHolder(...) - bind data to viewHolder
     */
    @Override
    public void onBindViewHolder(UserDeckHolder holder, int index){
        //get data item
        DeckItem item = mData.get(index);

        //bind item onClick listener
        holder.bindItemOnClickListener(item, index, mItemOnClickListener);

        //bind textView data
        holder.bindTextView(item);

        //update boom menu subtitle
        mBoomMenuSubtitles.set(0, "(" + mStrEdit + ": " + item.deck + ")");

        holder.bindBoomMenuButton(mBoomMenuTitles, mBoomMenuSubtitles, mBoomMenuIcons, mBMClickListener);
    }

/**************************************************************************************************/


}

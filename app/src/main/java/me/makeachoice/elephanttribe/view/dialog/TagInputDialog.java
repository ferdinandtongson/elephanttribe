package me.makeachoice.elephanttribe.view.dialog;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.butler.deck.DeckTagButler;
import me.makeachoice.elephanttribe.controller.viewside.recycler.BasicRecycler;
import me.makeachoice.elephanttribe.controller.viewside.recycler.adapter.SimpleAdapter;
import me.makeachoice.elephanttribe.controller.viewside.recycler.decoration.SimpleDividerItemDecoration;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;
import me.makeachoice.elephanttribe.model.item.deck.DeckTagItem;
import me.makeachoice.elephanttribe.view.dialog.base.MyDialog;

/**
 * TagInputDialog is used to input tag values for decks and flashcards
 */

public class TagInputDialog extends MyDialog implements RecyclerView.OnCreateContextMenuListener,
        MenuItem.OnMenuItemClickListener{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private int CONTEXT_MENU_DELETE = 0;

    //string values
    private String mMsgInvalid;
    private String mMsgExceedMax;
    private String mMsgBlank;
    private String mMsgDuplicate;
    private String mMsgAddTag;
    private String mMsgNoTag;

    private String mStrDelete;
    private String mStrDeleted;
    private String mStrSaved;

    //Tag string value buffer
    private int mTagIndex;
    private String mTagValue;
    private ArrayList<DeckTagItem> mTagList;
    private ArrayList<String> mTagNames;

    //Max input number
    private int mMaxInput;

    private DeckItem mDeckItem;

    InputFilter charFilter = new InputFilter() {

        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {

            for (int i = start; i < end; i++) {
                char currentChar = source.charAt(i);

                if (!Character.isLetterOrDigit(currentChar) && currentChar != '_' && currentChar != '-' &&
                        !Character.isWhitespace(currentChar)) {
                    String msg = mMsgInvalid + ": " + currentChar;
                    showToastMessage(msg);
                    return "";
                }

            }
            return null;
        }

    };

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public TagInputDialog(){}

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setter Methods:
 *      void setInputMax(...) - set character max input values
 *      void setDeckTags(...) - set list of tags for deck
 *      String getTagValue(...) - get tag value created by dialog
 */
/**************************************************************************************************/
    /*
     * void setInputMax(...) - set character max input values
     */
    public void setInputMax(int maxInput){
        //get maximum number of input characters
        mMaxInput = maxInput;
    }

    /*
     * void setDeckTags(...) - set list of tags for deck
     */
    public void setDeckTags(ArrayList<DeckTagItem> tagList){
        //check if list buffer is null
        if(mTagList == null){
            //create list buffers
            mTagList = new ArrayList<>();
            mTagNames = new ArrayList<>();
        }

        //clear
        mTagList.clear();

        //add tag list to buffer
        mTagList.addAll(tagList);

        //convert tag list to name list and tag value
        mTagNames.addAll(convertToTagNames(mTagList));
    }

    public void setDeck(DeckItem item){
        mDeckItem = item;
    }

    public ArrayList<DeckTagItem> getDeckTags(){
        return mTagList;
    }

    /*
     * String convertToTagNameValue(...) - get tag value created by dialog
     */
    private ArrayList<String> convertToTagNames(ArrayList<DeckTagItem> deckTagList){

        ArrayList<String> names = new ArrayList<>();

        String tag = "";
        int count = deckTagList.size();
        for(int i = 0; i < count; i++){
            tag = deckTagList.get(i).tag;
            names.add(tag);
        }

        return names;
    }
    
/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Dialog Lifecycle:
 */
/**************************************************************************************************/

    public void createView(Bundle bundle){

        mMsgInvalid = mActivity.getString(R.string.msg_invalid_character);
        mMsgExceedMax = mActivity.getString(R.string.msg_exceed_max);
        mMsgBlank = mActivity.getString(R.string.msg_tag_blank);
        mMsgDuplicate = mActivity.getString(R.string.msg_tag_duplicate);
        mMsgAddTag = mActivity.getString(R.string.msg_tag_add);
        mMsgNoTag = mActivity.getString(R.string.msg_no_defined_tags);

        mStrDelete = mActivity.getString(R.string.delete);
        mStrDeleted = mActivity.getString(R.string.deleted);
        mStrSaved = mActivity.getString(R.string.saved);

        initializeTextCount();
        initializeEditText();
        initializeTextButton();
        initializeAdapter();
        initializeRecycler();

        initializeDeckTagButler();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize EditText components:
 *      void initializeEditText() - initialize editText components to input deck tags
 *      void updateEditText() - update editText text value
 */
/**************************************************************************************************/

    //EditTExt components
    private EditText mEdtValue;

    /*
     * void initializeEditText() - initialize editText components to input deck tags
     */
    private void initializeEditText(){
        //get editText
        mEdtValue = (EditText)getChildView(R.id.diaTagInput_edtValue);
        mEdtValue.setHint(mMsgAddTag);

        //add textChange listener to editText input
        mEdtValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int total = start + count;
                mTxtMaxCount.setText(formatCountValue(total, mMaxInput));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //add filter
        mEdtValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxInput), charFilter});

        //request focus
        mEdtValue.requestFocus();
    }

    /*
     * void updateEditText() - update editText text value
     */
    private void updateEditText(String tagValue){
        mEdtValue.setText(tagValue);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize TextView counter components:
 *      void initializeTextCount() - initialize textView components displaying character count
 *      String formatCountValue(...) - get count value to be displayed by textView
 */
/**************************************************************************************************/

    //TextView components display character count
    private TextView mTxtMaxCount;

    /*
     * void initializeTextCount() - initialize textView components displaying character count
     */
    private void initializeTextCount(){
        //get deck name character count display
        mTxtMaxCount = (TextView)getChildView(R.id.diaTagInput_txtMaxCount);
        mTxtMaxCount.setText(formatCountValue(0, mMaxInput));
    }

    /*
     * String formatCountValue(...) - get count value to be displayed by textView
     */
    private String formatCountValue(int count, int maxLength){
        if(count >= maxLength){
            showToastMessage(mMsgExceedMax);
        }

        return count + "/" + maxLength;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize RecyclerView component:
 *      void initializeAdapter() - initialize adapter used by recycler
 *      void initializeRecycler() - initialize recycler view
 *      void updateAdapter(...) - update adapter with new list and check if recycler is empty
 */
/**************************************************************************************************/

    //RecyclerView and adapter components
    private SimpleAdapter mAdapter;
    private BasicRecycler mBasicRecycler;

    /*
     * void initializeAdapter() - initialize adapter used by recycler
     */
    private void initializeAdapter(){
        //initialize adapter with layout
        mAdapter = new SimpleAdapter(mActivity, R.layout.item_simple);
        mAdapter.setOnCreateMenuListener(this);

        mAdapter.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get tag item clicked
                String tagItem = (String)v.getTag(R.string.tag_item);

                //update editText component with selected tag
                updateEditText(tagItem);
            }
        });

        //add empty array list to adapter
        mAdapter.swapData(mTagNames);
    }

    /*
     * void initializeRecycler() - initialize recycler view
     */
    private void initializeRecycler(){

        //initialize recycler to linear layout
        mBasicRecycler = new BasicRecycler(mRootView);
        mBasicRecycler.initializeLinearRecycler(mActivity, true);

        //set empty recycler message
        mBasicRecycler.setEmptyMessage(mMsgNoTag);

        mBasicRecycler.addItemDecoration(new SimpleDividerItemDecoration(mActivity));

        //check if adapter for recycler is empty
        mBasicRecycler.checkRecyclerEmpty(mAdapter.getItemCount());

        //set adapter for recycler
        mBasicRecycler.setAdapter(mAdapter);
    }

    /*
     * void updateAdapter(...) - update adapter with new list and check if recycler is empty
     */
    private void updateAdapter(ArrayList<String> tagList){
        //clear adapter
        mAdapter.clearData();
        mAdapter.swapData(tagList);

        //check if adapter for recycler is empty
        mBasicRecycler.checkRecyclerEmpty(tagList.size());
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize TextView buttons components:
 *      void initializeButtons() - initialize textView button components
 *      void addTag() - add tag button clicked, validate input and save, if valid
 *      boolean isDuplicate(...) - check if tag is duplicate
 *      void showToastMessage(...) - display toast message to user
 */
/**************************************************************************************************/

    //TextView button components
    private TextView mTxtAdd;
    private TextView mTxtClose;

    /*
     * void initializeTextButton() - initialize textView button components
     */
    private void initializeTextButton(){
        //save and close textView buttons
        mTxtAdd = (TextView)getChildView(R.id.diaTagInput_txtAdd);
        mTxtClose = (TextView)getChildView(R.id.diaTagInput_txtClose);

        //set onClick listener for add button
        mTxtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTag();
            }
        });

        mTxtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /*
     * void addTag() - add tag button clicked, validate input and save, if valid
     */
    private void addTag(){
        //get tag value from editText component
        String tag = mEdtValue.getText().toString().trim();

        //check if tag is empty
        if(tag.isEmpty()){
            //display toast "blank tag" message
            showToastMessage(mMsgBlank);
        }
        else{
            //check if duplicate
            if(isDuplicate(tag)){
                //display toast "duplicate" message
                showToastMessage(mMsgDuplicate);
            }
            else{
                //save tag to deck tag table
                butlerSaveTag(tag);
            }
        }

        //clear editText
        mEdtValue.setText("");
    }

    /*
     * boolean isDuplicate(...) - check if tag is duplicate
     */
    private boolean isDuplicate(String tag){
        //get size of tag list
        int count = mTagList.size();

        //create string buffer
        String oldTag;

        //loop through list
        for(int i = 0; i < count; i++){
            //get tag, convert to lower case
            oldTag = mTagList.get(i).tag.toLowerCase();

            //check if tag is equal to old tag
            if(oldTag.equals(tag.toLowerCase())){
                //yes, return isDuplicate true
                return true;
            }
        }

        //return, not duplicate
        return false;
    }

    /*
     * void showToastMessage(...) - display toast message to user
     */
    private void showToastMessage(String msg){
        //toast object
        Toast toast = Toast.makeText(mActivity, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * DeckTagButler Method:
 *      void initializeDeckTagButler() - initialize butler to access deck tag data
 *      void butlerSaveTag(...) - request butler to save tag to database
 *      void butlerDeleteTag(...) - request butler to delete tag from database
 */
/**************************************************************************************************/

    //DeckTag Butler
    private DeckTagButler mDTagButler;

    /*
     * void initializeDeckTagButler() - initialize butler to access deck tag data
     */
    private void initializeDeckTagButler(){
        //create butler
        mDTagButler = new DeckTagButler(mActivity, mDeckItem.userId);

        //add onLoad listener
        mDTagButler.setOnLoadedListener(new MyButler.OnLoadedListener() {
            @Override
            public void onLoaded(ArrayList itemList) {
                //deck tags loaded
                onTagsLoaded(itemList);
            }
        });

        //add onSave listener
        mDTagButler.setOnSavedListener(new MyButler.OnSavedListener() {
            @Override
            public void onSaved() {
                showToastMessage(mTagValue + " " + mStrSaved + "!");
                //load new deck tags
                mDTagButler.loadByDeckId(Boss.LOADER_DECK_TAG, mDeckItem.deckId);
            }
        });

        mDTagButler.setOnDeletedListener(new MyButler.OnDeletedListener() {
            @Override
            public void onDeleted(int deleted) {
                showToastMessage(mTagValue + " " + mStrDeleted + "!");

                mDTagButler.loadByDeckId(Boss.LOADER_DECK_TAG, mDeckItem.deckId);
            }
        });
    }

    /*
     * void butlerSaveTag(...) - request butler to save tag to database
     */
    private void butlerSaveTag(String tag){
        mTagValue = tag;
        DeckTagItem deckTagItem = new DeckTagItem(mDeckItem, tag);

        mDTagButler.save(deckTagItem);
    }

    /*
     * void butlerDeleteTag(...) - request butler to delete tag from database
     */
    private void butlerDeleteTag(int index){
        //get tag item to delete
        DeckTagItem deleteItem = mTagList.get(index);

        //delete tag item from database
        mDTagButler.delete(deleteItem);
    }

    /*
     * void onTagsLoaded(...) - deck tags loaded from database
     */
    private void onTagsLoaded(ArrayList<DeckTagItem> tagList){
        //clear deck tag list
        mTagList.clear();
        //add new list to buffer
        mTagList.addAll(tagList);

        //clear deck tag names
        mTagNames.clear();
        //convert list to tag names and tagValue
        mTagNames.addAll(convertToTagNames(mTagList));

        //update adapter with new tag names
        updateAdapter(mTagNames);
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Context Menu Methods:
 *      void onCreateContextMenu(...) - create context menu event occurred
 *      boolean onMenuItemClick(...) - context menu item onClick event occurred
 */
/**************************************************************************************************/
    /*
     * void onCreateContextMenu(...) - create context menu event occurred
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info){
        //get tag string value of menu item clicked
        mTagValue = (String)v.getTag(R.string.tag_item);
        mTagIndex = (int)v.getTag(R.string.tag_position);

        //add menu item to context menu
        menu.add(0, CONTEXT_MENU_DELETE, 0, mStrDelete + ": " + mTagValue);

        //get number of menu items
        int count = menu.size();

        //loop through menu items
        for(int i = 0; i < count; i++){
            //add onClick listener to menuItem
            menu.getItem(i).setOnMenuItemClickListener(this);
        }
    }

    /*
     * boolean onMenuItemClick(...) - context menu item onClick event occurred
     */
    public boolean onMenuItemClick(MenuItem item){

        //delete tag from db
        butlerDeleteTag(mTagIndex);

        return false;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Conversion Methods:
 *      void sortListAlphabetical(...) - sort arrayList into alphabetical order
 */
/**************************************************************************************************/
    /*
     * void sortListAlphabetical(...) - sort arrayList into alphabetical order
     */
    private void sortListAlphabetical(ArrayList<String> tagList){
        Collections.sort(tagList, String.CASE_INSENSITIVE_ORDER);
    }

/**************************************************************************************************/


}

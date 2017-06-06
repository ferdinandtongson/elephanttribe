package me.makeachoice.elephanttribe.controller.viewside.maid.deck;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.butler.flashcard.FlashcardButler;
import me.makeachoice.elephanttribe.controller.viewside.maid.base.BaseRecyclerMaid;
import me.makeachoice.elephanttribe.controller.viewside.recycler.adapter.SimpleAdapter;
import me.makeachoice.elephanttribe.controller.viewside.recycler.decoration.SimpleDividerItemDecoration;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;
import me.makeachoice.elephanttribe.model.item.flashcard.FlashcardItem;

/**
 * DeckFlashcardMaid displays flashcard input for user defined decks
 */

public class DeckFlashcardMaid extends BaseRecyclerMaid {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //string values
    private String mStrFlashcard;
    private String mStrAnswer;
    private String mCardValue;

    private String mStrAdd;
    private String mStrClear;
    private String mStrUpdate;
    private String mStrCancel;
    private String mStrModify;
    private String mStrDelete;
    private String mStrDeleted;
    private String mStrSaved;

    private String mMsgInvalid;
    private String mMsgExceedMax;
    private String mMsgBlank;
    private String mMsgDuplicate;

    //input character max values
    private int mMaxFlashcard;
    private int mMaxAnswer;

    private boolean mIsNewCard;

    private ArrayList<FlashcardItem> mFlashcards;
    private FlashcardItem mSelectedCardItem;

    private ArrayList<String> mCardNames;

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

    public DeckFlashcardMaid(String maidName, int layoutId){
        //get maid name
        mMaidName = maidName;

        //get fragment layout resource id
        mLayoutId = layoutId;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * LifeCycle Methods:
 */
/**************************************************************************************************/

    @Override
    public void activityCreated(Bundle bundle){
        super.activityCreated(bundle);

        //string values
        mStrFlashcard = mActivity.getString(R.string.flashcard);
        mStrAnswer = mActivity.getString(R.string.answer);

        mStrAdd = mActivity.getString(R.string.add);
        mStrClear = mActivity.getString(R.string.clear);
        mStrUpdate = mActivity.getString(R.string.update);
        mStrCancel = mActivity.getString(R.string.cancel);
        mStrDelete = mActivity.getString(R.string.delete);
        mStrDeleted = mActivity.getString(R.string.deleted);
        mStrModify = mActivity.getString(R.string.modify);
        mStrSaved = mActivity.getString(R.string.saved);

        //message string values
        mMsgInvalid = mActivity.getString(R.string.msg_invalid_character);
        mMsgExceedMax = mActivity.getString(R.string.msg_exceed_max);
        mMsgBlank = mActivity.getString(R.string.msg_invalid_blank);
        mMsgDuplicate = mActivity.getString(R.string.msg_already_exists);

        //get integer values
        mMaxFlashcard = mActivity.getResources().getInteger(R.integer.deck_flashcard_max_length);
        mMaxAnswer = mActivity.getResources().getInteger(R.integer.deck_answer_max_length);


        mIsNewCard = true;

        initializeTextModifyCard();
        initializeTextCount();
        initializeEditText();
        initializeTextButton();
        initializeAdapter();
        initializeRecycler();


        //get user item
        mUserItem = getCurrentUser();

        //get selected deck from Boss
        mDeckItem = mBoss.getDeckSelected();

        mFlashcardButler = initializeButler();

        mFlashcardButler.loadByDeckId(Boss.LOADER_FLASHCARD, mDeckItem.deckId);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize User and Deck values:
 *      void createDeckListAndMap() - get deck name list and deck hashMap
 *      DeckItem getSelectedDeck(...) - get selected deck from Boss
 *      int getDeckIndex(...) - get index location of deck
 *      String generateGenericName() - generate generic deck name
 */
/**************************************************************************************************/
    /*
     * void createCardListAndMap() - get card list and hashMap
     */
    private void createDeckListAndMap(){

        if(mCardNames == null){
            //create deck name list and hashMap buffer
            mCardNames = new ArrayList<>();
        }
        else{
            mCardNames.clear();
        }

        //get user deck from Boss
        ArrayList<DeckItem> decks = mBoss.getDeckList();

        //get size of deck
        int count = decks.size();

        String deckName;

        Log.d("Choice", "DeckInfoMaid.createDeckListAndMap");
        //loop through deck list
        for(int i = 0; i < count; i++){

            deckName = decks.get(i).deck.toLowerCase();
            Log.d("Choice", "     deck: " + deckName);

            //add deck names to name list
            //mDeckNames.add(deckName);
            //mDeckMap.put(deckName,i);
        }
    }

    private void initializeViewStatus(){
        //check view status
        /*if(mBoss.getDeckViewStatus() == Boss.DECK_CREATE){
            //enable show dialog
            mShowDialog = true;

            //set create new deck status values
            mIsNewDeck = true;
        }
        else{
            //set create deck status values
            mIsNewDeck = false;
        }*/

    }

    /*
     * int getDeckIndex(...) - get index location of deck
     */
    private int getDeckIndex(DeckItem item){
        Log.d("Choice", "DeckInfoMaid.getDeckIndex: " + item.deck);
        //check view status
        /*if(mIsNewDeck){
            Log.d("Choice", "     is new Deck");
            //is new deck, check if saving tag value
            if(mShownDeckDialog){
                Log.d("Choice", "          tag dialog shown");
                //tag dialog has been shown, return index
                return mDeckMap.get(item.deck.toLowerCase());
            }
            //return empty index
            return EMPTY_INDEX;
        }
        else{
            Log.d("Choice", "     view status info");
            Log.d("Choice", "          deck: " + item.deck.toLowerCase());
            //return index
            return mDeckMap.get(item.deck.toLowerCase());
        }*/
        return -1;
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
    protected TextView mTxtMaxCount01;
    protected TextView mTxtMaxCount02;

    /*
     * void initializeTextCount() - initialize textView components displaying character count
     */
    private void initializeTextCount(){
        //get deck name character count display
        mTxtMaxCount01 = (TextView)mActivity.findViewById(R.id.deckFlashcard_txtMaxCount01);
        mTxtMaxCount01.setText(formatCountValue(0, mMaxFlashcard));

        //get deck description character count display
        mTxtMaxCount02 = (TextView)mActivity.findViewById(R.id.deckFlashcard_txtMaxCount02);
        mTxtMaxCount02.setText(formatCountValue(0, mMaxAnswer));
    }

    /*
     * String formatCountValue(...) - get count value to be displayed by textView
     */
    protected String formatCountValue(int count, int maxLength){
        if(count >= maxLength){
            String msg = mMsgExceedMax + ": " + maxLength;
            showToastMessage(msg);
        }

        return count + "/" + maxLength;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize TextView Card components:
 *      void initializeTextCount() - initialize textView components displaying character count
 *      String formatCountValue(...) - get count value to be displayed by textView
 */
/**************************************************************************************************/

    //TextView components display flashcard value
    protected TextView mTxtModifyCard;

    /*
     * void initializeTextModifyCard() - initialize textView components displaying card being modified
     */
    private void initializeTextModifyCard(){
        //get textView to display card value
        mTxtModifyCard = (TextView)mActivity.findViewById(R.id.deckFlashcard_txtModified);

        mTxtModifyCard.setVisibility(View.GONE);
    }

    private void showModifyCard(String card){
        mTxtModifyCard.setVisibility(View.VISIBLE);
        mTxtModifyCard.setText(mStrModify + ": " + card);
    }

    private void hideModifyCard(){
        mTxtModifyCard.setText("");
        mTxtModifyCard.setVisibility(View.GONE);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize EditText components:
 *      void initializeEditText() - initialize editText components
 */
/**************************************************************************************************/

    //EditTExt components
    protected EditText mEdtValue01;
    protected EditText mEdtValue02;

    /*
     * void initializeEditText() - initialize editText components
     */
    private void initializeEditText(){
        //get editText 01
        mEdtValue01 = (EditText)mActivity.findViewById(R.id.deckFlashcard_edtValue01);

        //get editText 02
        mEdtValue02 = (EditText)mActivity.findViewById(R.id.deckFlashcard_edtValue02);

        //add textChange listener to editText input
        mEdtValue01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int total = start + count;
                mTxtMaxCount01.setText(formatCountValue(total, mMaxFlashcard));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //add textChange listener to editText input
        mEdtValue02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int total = start + count;
                mTxtMaxCount02.setText(formatCountValue(total, mMaxAnswer));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //add filter
        mEdtValue01.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxFlashcard), charFilter});
        mEdtValue02.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxAnswer), charFilter});

    }

    private void clearEditText(){
        mEdtValue01.setText("");
        mEdtValue02.setText("");

        mEdtValue01.requestFocus();
    }

    private void updateEditText(String value01, String value02){
        mEdtValue01.setText(value01);
        mEdtValue02.setText(value02);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize TextView buttons components:
 *      void initializeButtons() - initialize textView button components
 *      void setButtonsByStatus() - set button values by viewStatus flag
 */
/**************************************************************************************************/

    //TextView button components
    protected TextView mTxtSave;
    protected TextView mTxtCancel;

    /*
     * void initializeTextButton() - initialize textView button components
     */
    private void initializeTextButton(){
        //get tag, save and cancel textView buttons
        mTxtSave = (TextView)mActivity.findViewById(R.id.deckFlashcard_txtSave);
        mTxtCancel = (TextView)mActivity.findViewById(R.id.deckFlashcard_txtCancel);

        //set buttons as visible
        mTxtSave.setVisibility(View.VISIBLE);
        mTxtCancel.setVisibility(View.VISIBLE);

        //set onClick listener for save button
        mTxtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate flashcard;
                validateFlashcard();
            }
        });

        //set onClick listener for cancel button
        mTxtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCardInput();
            }
        });

        updateButtons();
    }

    private void updateButtons(){
        if(mIsNewCard){
            mTxtSave.setText(mStrAdd);
            mTxtCancel.setText(mStrClear);
        }
        else{
            mTxtSave.setText(mStrUpdate);
            mTxtCancel.setText(mStrCancel);
        }
    }

    private void cancelCardInput(){
        if(!mIsNewCard){
            mSelectedCardItem = null;
        }
        clearInputComponents();
    }

    private void validateFlashcard(){

        if(mIsNewCard){
            validateNewCard();
        }
        else{
            validateOldCard();
        }


    }

    private void validateNewCard(){
        FlashcardItem saveCardItem = new FlashcardItem(mDeckItem);
        saveCardItem.card = mEdtValue01.getText().toString();
        saveCardItem.answer = mEdtValue02.getText().toString();

        if(!hasBlankInput()){
            if(!isDuplicate(mCardNames, saveCardItem.card)){
                butlerSaveFlashcard(saveCardItem);
            }
        }
    }

    private void validateOldCard(){
        Log.d("Choice", "DeckFlashcardMaid.validateOldCard: " + mSelectedCardItem.card);
        FlashcardItem saveCardItem = new FlashcardItem(mDeckItem);
        saveCardItem.card = mEdtValue01.getText().toString();
        saveCardItem.answer = mEdtValue02.getText().toString();

        saveCardItem.cardId = mSelectedCardItem.cardId;
        saveCardItem.picture = mSelectedCardItem.picture;
        saveCardItem.voice = mSelectedCardItem.voice;

        if(!saveCardItem.card.equals(mSelectedCardItem.card) ||
                !saveCardItem.answer.equals(mSelectedCardItem.answer)){

            Log.d("Choice", "     card values have changed");
            if(!hasBlankInput()){
                if(!isDuplicate(mCardNames, saveCardItem.card)){
                    butlerSaveFlashcard(saveCardItem);
                }
            }
        }
        else{
            //todo - put message in resource
            mSelectedCardItem = null;
            showToastMessage("Nothing modified");
            clearInputComponents();
        }

    }

    private boolean hasBlankInput(){

        String msg;
        if(isBlankInput(mEdtValue01) && isBlankInput(mEdtValue02)){
            msg = mStrFlashcard + ", " + mStrAnonymous + " " + mMsgBlank;

            showToastMessage(msg);
            return true;
        }
        else if(isBlankInput(mEdtValue01)){
            msg = mStrFlashcard + " " + mMsgBlank;

            showToastMessage(msg);
            return true;
        }
        else if(isBlankInput(mEdtValue02)){
            msg = mStrAnswer + " " + mMsgBlank;

            showToastMessage(msg);
            return true;
        }

        return false;
    }

    /*
     * boolean isBlankInput(...) - check editText component is blank
     */
    protected boolean isBlankInput(EditText edt){
        if(edt.getText().toString().isEmpty()){
            return true;
        }

        return false;
    }

    /*
     * boolean isDuplicate(...) - check if tag is duplicate
     */
    private boolean isDuplicate(ArrayList<String> cardList, String value){
        ArrayList<String> filteredList = createFilteredList(cardList);

        //get size of value list
        int count = filteredList.size();

        //create string buffer
        String savedValue;

        //loop through list
        for(int i = 0; i < count; i++){
            //get saved value, convert to lower case
            savedValue = filteredList.get(i).toLowerCase();

            //check if value is equal to saved value
            if(savedValue.equals(value.toLowerCase().trim())){
                String msg = value + " " + mMsgDuplicate;
                showToastMessage(msg);
                //yes, return isDuplicate true
                return true;
            }
        }

        //return, not duplicate
        return false;
    }

    private ArrayList<String> createFilteredList(ArrayList<String> cardList){
        ArrayList<String> filterList = new ArrayList<>();
        filterList.addAll(cardList);

        if(!mIsNewCard){
            filterList.remove(mSelectedCardItem.card);
        }

        return filterList;
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * RecyclerView and Adapter:
 */
/**************************************************************************************************/

    private int CONTEXT_MENU_DELETE = 0;
    private SimpleAdapter mAdapter;
    private int mCardIndex;

    /*
     * void initializeAdapter() - initialize adapter used by recycler
     */
    private void initializeAdapter(){
        //initialize adapter with layout
        mAdapter = new SimpleAdapter(mActivity, R.layout.item_simple);

        mAdapter.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int)v.getTag(R.string.tag_position);
                onFlashcardSelected(index);
            }
        });

        mAdapter.setOnCreateMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                onCreateFlashcardMenu(menu, v, menuInfo);
            }
        });

        //add empty array list to adapter
        mAdapter.swapData(new ArrayList<String>());
    }

    /*
     * void initializeRecycler() - initialize recycler view
     */
    private void initializeRecycler(){
        //get empty recycler message
        String msg = mActivity.getString(R.string.emptyList_flashcard);

        //initialize recycler to linear layout
        initializeLinearRecycler();

        //set empty recycler message
        setRecyclerEmptyMessage(msg);

        addRecyclerItemDecoration(new SimpleDividerItemDecoration(mActivity));

        //check if adapter for recycler is empty
        checkRecyclerEmpty(mAdapter.getItemCount());

        //set adapter for recycler
        setRecyclerAdapter(mAdapter);
    }

    /*
     * void updateAdapter(...) - update adapter with new list and check if recycler is empty
     */
    private void updateAdapter(ArrayList<String> tagList){
        //clear adapter
        mAdapter.clearData();
        mAdapter.swapData(tagList);

        //check if adapter for recycler is empty
        checkRecyclerEmpty(tagList.size());
    }

    private void onFlashcardSelected(int index){
        mIsNewCard = false;
        mSelectedCardItem = mFlashcards.get(index);
        updateButtons();
        updateEditText(mSelectedCardItem.card, mSelectedCardItem.answer);
        showModifyCard(mSelectedCardItem.card);
    }

    /*
    * void onCreateContextMenu(...) - create context menu event occurred
    */
    public void onCreateFlashcardMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info){
        //get index value of menu item clicked
        int index = (int)v.getTag(R.string.tag_position);
        mSelectedCardItem = mFlashcards.get(index);

        //add menu item to context menu
        menu.add(0, CONTEXT_MENU_DELETE, 0, mStrDelete + ": " + mSelectedCardItem.card);

        //get number of menu items
        int count = menu.size();

        //loop through menu items
        for(int i = 0; i < count; i++){
            //add onClick listener to menuItem
            menu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    onFlashcardMenuClick(item);
                    return false;
                }
            });
        }
    }

    /*
     * boolean onFlashcardMenuClick(...) - context menu item onClick event occurred
     */
    public boolean onFlashcardMenuClick(MenuItem item){

        //delete flashcard
        butlerDeleteFlashcard(mSelectedCardItem);

        return false;
    }



/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Flashcard Butler:
 */
/**************************************************************************************************/

    private FlashcardButler mFlashcardButler;

    private FlashcardButler initializeButler(){
        FlashcardButler butler = new FlashcardButler(mActivity, mUserId);

        butler.setOnLoadedListener(new MyButler.OnLoadedListener() {
            @Override
            public void onLoaded(ArrayList itemList) {
                onFlashcardsLoaded(itemList);
            }
        });

        butler.setOnSavedListener(new MyButler.OnSavedListener() {
            @Override
            public void onSaved() {
                onFlashcardSaved();
            }
        });

        butler.setOnDeletedListener(new MyButler.OnDeletedListener() {
            @Override
            public void onDeleted(int deleted) {
                onFlashcardDeleted();
            }
        });

        return butler;
    }

    private void onFlashcardsLoaded(ArrayList<FlashcardItem> cardList){
        Log.d("Choice", "DeckFlashcardMaid.onFlashcardsLoaded");
        if(mFlashcards == null){
            mFlashcards = new ArrayList<>();
            mCardNames = new ArrayList<>();
        }

        mFlashcards.clear();
        mFlashcards.addAll(cardList);

        mCardNames.clear();
        mCardNames.addAll(convertFlashcardToNameList(cardList));
        updateAdapter(mCardNames);
    }

    private void onFlashcardSaved(){
        Log.d("Choice", "DeckFlashcardMaid.onFlashcardSaved");
        showToastMessage(mCardValue + " " + mStrSaved + "!");
        clearInputComponents();
        mFlashcardButler.loadByDeckId(Boss.LOADER_FLASHCARD, mDeckItem.deckId);
    }

    private void onFlashcardDeleted(){
        Log.d("Choice", "DeckFlashcardMaid.onFlashcardDeleted");
        mSelectedCardItem = null;
        //todo
        showToastMessage(mCardValue + " " + mStrDeleted + "!");
        clearInputComponents();
        mFlashcardButler.loadByDeckId(Boss.LOADER_FLASHCARD, mDeckItem.deckId);
    }

    private void butlerSaveFlashcard(FlashcardItem saveItem){
        Log.d("Choice", "DeckFlashcardMaid.butlerSaveFlashcard: " + saveItem.card);
        mCardValue = saveItem.card;
        if(mIsNewCard){
            Log.d("Choice", "     new card");
            mFlashcardButler.save(saveItem);
        }
        else{
            Log.d("Choice", "     old card");
            mFlashcardButler.update(saveItem);
        }
    }

    private void butlerDeleteFlashcard(FlashcardItem deleteItem){
        mFlashcardButler.delete(deleteItem);
    }

    private ArrayList<String> convertFlashcardToNameList(ArrayList<FlashcardItem> cardList){
        ArrayList<String> names = new ArrayList<>();

        int count = cardList.size();

        for(int i = 0; i < count; i++){
            names.add(cardList.get(i).card);
        }

        return names;
    }

/**************************************************************************************************/






/**************************************************************************************************/


    /*
     * void showToastMessage(...) - display toast message to user
     */
    private void showToastMessage(String msg){
        //toast object
        Toast toast = Toast.makeText(mActivity, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }


    private void clearInputComponents(){
        mIsNewCard = true;
        hideModifyCard();
        updateButtons();
        clearEditText();
    }

}

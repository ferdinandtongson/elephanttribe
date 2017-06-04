package me.makeachoice.elephanttribe.controller.viewside.maid.deck;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.butler.deck.DeckButler;
import me.makeachoice.elephanttribe.controller.modelside.butler.deck.DeckTagButler;
import me.makeachoice.elephanttribe.controller.viewside.maid.base.BaseMaid;
import me.makeachoice.elephanttribe.controller.viewside.navigation.bottomnav.DeckDetailNav;
import me.makeachoice.elephanttribe.model.contract.deck.DeckContract;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;
import me.makeachoice.elephanttribe.model.item.deck.DeckTagItem;
import me.makeachoice.elephanttribe.model.item.user.UserItem;
import me.makeachoice.elephanttribe.utilities.DeprecatedUtility;
import me.makeachoice.elephanttribe.view.dialog.DeckInputDialog;
import me.makeachoice.elephanttribe.view.dialog.TagInputDialog;
import me.makeachoice.elephanttribe.view.dialog.base.BaseInputDialog;
import me.makeachoice.elephanttribe.view.dialog.base.MyDialog;

/**
 * DeckInfoMaid creates, edits and displays deck information
 */

public class DeckInfoMaid extends BaseMaid {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private static int EMPTY_INDEX = -1;

    private ArrayList<String> mDeckNames;
    private HashMap<String,Integer> mDeckMap;
    private DeckItem mDeckItem;
    private int mDeckIndex;

    //deck dialog strings
    private String mStrName;
    private String mStrDescription;

    private String mStrDeck;
    private String mMsgNoTags;

    //max character length values
    private int mMaxTags;
    private int mMaxName;
    private int mMaxDescription;

    //color values
    private int mColorBlack;
    private int mColorGray;

    //Dialog status flags
    private boolean mShowDialog = false;
    private boolean mIsCanceled = false;

    //create deck status flags
    private boolean mIsNewDeck = false;
    private boolean mShownDeckDialog = false;

    private OnNavRequestListener mOnNavRequestListener;
    public interface OnNavRequestListener{
        void onNavRequest(int navRequest);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckInfoMaid(String maidName, int layoutId){
        //get maid name
        mMaidName = maidName;

        //get fragment layout resource id
        mLayoutId = layoutId;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setter Method:
 *      void setOnNavRequestListener(...) - set bottom navigation request listener
 */
/**************************************************************************************************/
    /*
     * void setOnNavRequestListener(...) - set bottom navigation request listener
     */
    public void setOnNavRequestListener(OnNavRequestListener listener){
        mOnNavRequestListener = listener;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Fragment Lifecycle:
 */
/**************************************************************************************************/

    @Override
    public void activityCreated(Bundle bundle){
        super.activityCreated(bundle);

        //get string values
        mStrName = mActivity.getString(R.string.name);
        mStrDescription = mActivity.getString(R.string.description);

        mStrDeck = mActivity.getString(R.string.deck);
        mMsgNoTags = mActivity.getString(R.string.msg_no_tags);

        //get integer values
        mMaxTags = mActivity.getResources().getInteger(R.integer.deck_tag_max_length);
        mMaxName = mActivity.getResources().getInteger(R.integer.deck_name_max_length);
        mMaxDescription = mActivity.getResources().getInteger(R.integer.deck_description_max_length);

        //get color
        mColorBlack = DeprecatedUtility.getColor(mActivity, R.color.black);
        mColorGray = DeprecatedUtility.getColor(mActivity, R.color.gray);

        //create deck name list and deck hashMap
        createDeckListAndMap();

        //get selected deck from Boss
        mDeckItem = getSelectedDeck(mUserItem);

        //get deck item index
        mDeckIndex = getDeckIndex(mDeckItem);

        //initialize fragment components
        initializeTextInfo();
        initializeButler();
        initializeDeckTagButler();

        if(mIsNewDeck){
            //show deck dialog with editText focus
            showDeckDialog(BaseInputDialog.FOCUS_INPUT01);
        }

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize User and Deck values:
 *      void createDeckListAndMap() - get deck name list and deck hashMap
 *      void deckStatusNew() - set status flags for old or new deck
 *      DeckItem getSelectedDeck(...) - get selected deck from Boss
 *      int getDeckIndex(...) - get index location of deck
 *      String generateGenericName() - generate generic deck name
 */
/**************************************************************************************************/
    /*
     * void createDeckListAndMap() - get deck name list and deck hashMap
     */
    private void createDeckListAndMap(){
        if(mDeckNames == null){
            //create deck name list and hashMap buffer
            mDeckNames = new ArrayList<>();
            mDeckMap = new HashMap<>();
        }
        else{
            mDeckNames.clear();
            mDeckMap.clear();
        }

        //get user deck from Boss
        ArrayList<DeckItem> decks = mBoss.getDeckList();

        //get size of deck
        int count = decks.size();

        String deckName;

        //loop through deck list
        for(int i = 0; i < count; i++){

            deckName = decks.get(i).deck.toLowerCase();

            //add deck names to name list
            mDeckNames.add(deckName);
            mDeckMap.put(deckName,i);
        }
    }

    /*
     * void deckStatusNew() - set status flags for old or new deck
     */
    private void deckStatusNew(boolean isNewDeck){
        //set isNewDeck status flag
        mIsNewDeck = isNewDeck;

        //check view status
        if(isNewDeck){
            //enable show dialog
            mShowDialog = true;
            //set create new deck status values
            mShownDeckDialog = false;
        }
        else{
            //set create deck status values
            mShownDeckDialog = true;
        }

    }

    /*
     * DeckItem getSelectedDeck(...) - get selected deck from Boss
     */
    private DeckItem getSelectedDeck(UserItem userItem){
        //check if deck has been selected
        if(mBoss.getDeckSelected() == null){
            //set deck status new
            deckStatusNew(true);

            //none selected, create new deck
            DeckItem deckItem = new DeckItem(userItem);

            //set fkey and isPublic false if anonymous user
            if(mUserId.equals(mStrAnonymous)){
                deckItem.accessType = DeckContract.ACCESS_PRIVATE;
            }
            
            //generate generic deck name
            deckItem.deck = generateGenericName();

            //save deck item to Boss
            mBoss.setDeckSelected(deckItem);
        }
        else{
            //set deck status old
            deckStatusNew(false);
        }

        //return deck item from Boss
        return mBoss.getDeckSelected();
    }

    /*
     * int getDeckIndex(...) - get index location of deck
     */
    private int getDeckIndex(DeckItem item){
        //check view status
        if(mIsNewDeck){
            //is new deck, check if saving tag value
            if(mShownDeckDialog){
                //tag dialog has been shown, return index
                return mDeckMap.get(item.deck.toLowerCase());
            }
            //return empty index
            return EMPTY_INDEX;
        }
        else{
            //return index
            return mDeckMap.get(item.deck.toLowerCase());
        }
    }

    /*
     * String generateGenericName() - generate generic deck name
     */
    private String generateGenericName(){
        //get counter
        int counter = 1;

        //create generic deck name
        String baseName = mStrDeck + "_";
        String deckName = baseName + "0" + counter;

        //check if name already exists
        while(mDeckNames.contains(deckName.toLowerCase())){
            //name exist, increment counter
            counter++;
            
            if(counter < 10){
                //create generic deck name
                deckName = baseName + "0" + counter;
            }
            else{
                //create generic deck name
                deckName = baseName + counter;
            }
        }

        //valid name, return name
        return deckName;

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize TextView Info components:
 *      oid initializeTextInfo() - initialize TextView info components
 *      void setTagInfoValue() - set textView tagInfo component value
 *      void updateTextInfo() - update textView info components with deck info values
 *      void onTagInfoClicked() - textView tag info component clicked
 */
/**************************************************************************************************/

    //TextView components used to display deck info
    private TextView mTxtNameInfo;
    private TextView mTxtDescriptionInfo;
    private TextView mTxtTagInfo;

    /*
     * void initializeTextInfo() - initialize TextView info components
     */
    private void initializeTextInfo(){
        //get textView info components
        mTxtNameInfo = (TextView)mActivity.findViewById(R.id.deckInfo_edtName);
        mTxtDescriptionInfo = (TextView)mActivity.findViewById(R.id.deckInfo_edtDescription);
        mTxtTagInfo = (TextView)mActivity.findViewById(R.id.deckInfo_txtTagInfo);

        //set deck name and description
        mTxtNameInfo.setText(mDeckItem.deck);
        mTxtDescriptionInfo.setText(mDeckItem.description);

        //set tag info value
        setTagInfoValue("");

        //set onClick listeners
        mTxtNameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowDialog = true;
                showDeckDialog(DeckInputDialog.FOCUS_INPUT01);
            }
        });

        mTxtDescriptionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowDialog = true;
                showDeckDialog(DeckInputDialog.FOCUS_INPUT02);
            }
        });

        mTxtTagInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTagInfoClicked();
            }
        });

    }

    /*
     * void setTagInfoValue() - set textView tagInfo component value
     */
    private void setTagInfoValue(String tagValue){

        if(tagValue.isEmpty()){
            mTxtTagInfo.setText(mMsgNoTags);
            mTxtTagInfo.setGravity(Gravity.CENTER);
            mTxtTagInfo.setTextColor(mColorGray);
        }
        else{
            mTxtTagInfo.setText(tagValue);
            mTxtTagInfo.setGravity(Gravity.START);
            mTxtTagInfo.setTextColor(mColorBlack);
        }
    }

    /*
     * void updateTextInfo() - update textView info components with deck info values
     */
    private void updateDeckTextInfo(){
        mTxtNameInfo.setText(mDeckItem.deck);
        mTxtDescriptionInfo.setText(mDeckItem.description);
    }

    /*
     * void onTagInfoClicked() - textView tag info component clicked
     */
    private void onTagInfoClicked(){
        //set show dialog status flag
        mShowDialog = true;

        //show tag input dialog
        showTagDialog();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * DeckButler components:
 *      void initializeButler() - initialize butler handling deck data request
 *      void saveDeckItem(...) - save deck item to database
 */
/**************************************************************************************************/

    //DeckButler component
    private DeckButler mDeckButler;

    /*
     * void initializeButler() - initialize butler handling deck data request
     */
    private void initializeButler(){
        //create deck butler
        mDeckButler = new DeckButler(mActivity, mUserId);

        //set onSave listener
        mDeckButler.setOnSavedListener(new MyButler.OnSavedListener() {
            @Override
            public void onSaved() {
                //save deck item
                saveDeckItem(mDeckItem);
            }
        });
    }

    /*
     * void saveDeckItem(...) - save deck item to database
     */
    private void saveDeckItem(DeckItem item){
        //check if deck is new
        if(mIsNewDeck){
            //check if deck input dialog has been shown
            if(mShownDeckDialog){
                //already shown, update deck in Boss
                mBoss.updateDeckListItem(mDeckIndex, item);
            }
            else{
                //first time, change status flag
                mShownDeckDialog = true;

                //new deck, add deck to Boss
                mBoss.addToDeckList(item);

                //update deck name list and map
                createDeckListAndMap();

                //update deck index
                mDeckIndex = getDeckIndex(item);
            }
        }
        else{
            //update deck in Boss
            mBoss.updateDeckListItem(mDeckIndex, item);
        }

        //update boss selected deck
        mBoss.setDeckSelected(item);

        //update textView info values
        updateDeckTextInfo();

        //check if saved new deck
        if(mIsNewDeck){
            //request navigation to other fragment
            mOnNavRequestListener.onNavRequest(DeckDetailNav.NAV_FLASHCARD);
        }
    }

    private void butlerSaveDeck(){
        //check view status
        if(mIsNewDeck){
            //new deck, save deck item
            mDeckButler.saveDeck(mDeckItem);
        }
        else{
            //old deck, update deck item
            mDeckButler.updateDeck(mDeckItem);
        }
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * DeckTagButler components:
 *      void initializeDeckTagButler() - initialize butler handling deck tag data requests
 *      void onDeckTagsLoaded(...) - deck tags loaded from database
 *      String convertListToString(...) - convert tag list to string value
 */
/**************************************************************************************************/

    //DeckTag Butler component
    private DeckTagButler mTagButler;
    //deck tag list
    private ArrayList<DeckTagItem> mTagList;

    /*
     * void initializeDeckTagButler() - initialize butler handling deck tag data requests
     */
    private void initializeDeckTagButler(){
        //initialize tag list
        mTagList = new ArrayList<>();

        //initialize tab butler
        mTagButler = new DeckTagButler(mActivity, mUserId);

        //set onLoad listener
        mTagButler.setOnLoadedListener(new MyButler.OnLoadedListener() {
            @Override
            public void onLoaded(ArrayList itemList) {
                //deck tag values loaded
                onDeckTagsLoaded(itemList);
            }
        });

        //check if new deck
        if(mIsNewDeck){
            //clear tag list buffer
            mTagList.clear();

            //set tag info value to empty
            setTagInfoValue("");
        }
        else{
            //old deck, load tag data from db
            mTagButler.loadByDeckId(Boss.LOADER_DECK_TAG, mDeckItem.deckId);
        }
    }

    /*
     * void onDeckTagsLoaded(...) - deck tags loaded from database
     */
    private void onDeckTagsLoaded(ArrayList<DeckTagItem> tagList){
        //clear tag list buffer
        mTagList.clear();

        //add tag list to buffer
        mTagList.addAll(tagList);

        //set tag info value
        setTagInfoValue(convertListToString(mTagList));
    }

    /*
     * String convertListToString(...) - convert tag list to string value
     */
    private String convertListToString(ArrayList<DeckTagItem> tagList){
        //get size of list
        int count = tagList.size();

        //create value buffer
        String value = "";

        //loop through tag list
        for(int i = 0; i < count; i++){
            //check if first tag
            if(i == 0){
                //add first tag to value
                value = tagList.get(i).tag;
            }
            else{
                //append tag to value
                value = value + ", " + tagList.get(i).tag;
            }
        }

        //return tag value
        return value;
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * DeckInputDialog Methods:
 *      void showDeckInputDialog(...) - show deck input dialog
 *      void onDeckInputSave(...) - save deck item to database
 *      void onDeckInputCancel() - deck input dialog has been canceled
 *      void onDeckInputDismiss() - deck input dialog was dismiss
 */
/**************************************************************************************************/

    //Input dialog to input deck info
    private DeckInputDialog mDiaDeck;

    /*
     * void showDeckInputDialog(...) - show deck input dialog
     */
    private DeckInputDialog showDeckDialog(int focusValue){

        if(mShowDialog) {
            //initialize isCanceled status flag
            mIsCanceled = false;

            //get fragment manager
            FragmentManager fm = mActivity.getFragmentManager();

            //create dialog
            mDiaDeck = new DeckInputDialog();
            mDiaDeck.setCancelable(!mIsNewDeck);
            mDiaDeck.setActivity(mActivity, R.layout.dia_input);
            mDiaDeck.setTitles(mStrName, mStrDescription);
            mDiaDeck.setInputMax(mMaxName, mMaxDescription);
            mDiaDeck.setDeckValues(mDeckItem, mDeckNames);
            mDiaDeck.setInputFocus(focusValue);

            //set dismiss listener
            mDiaDeck.setOnDismissListener(new MyDialog.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //set show dialog status flag
                    mShowDialog = false;

                    //check if canceled
                    if(mIsCanceled){
                        //was canceled, call onDeckInputCancel event
                        onDeckInputCancel();
                    }
                    else{
                        onDeckInputDismiss();
                    }
                }
            });

            mDiaDeck.setOnSavedListener(new DeckInputDialog.OnSaveListener() {
                @Override
                public void onSave(DeckItem item) {
                    //set cancel status flag false
                    mIsCanceled = false;

                    //call onDeckInputSave event
                    onDeckInputSave(item);
                    mDiaDeck.dismiss();
                }
            });

            //set cancel listener
            mDiaDeck.setOnCancelListener(new BaseInputDialog.OnCancelListener() {
                @Override
                public void onCancel() {
                    //set cancel status flag true
                    mIsCanceled = true;

                    //dismiss dialog
                    mDiaDeck.dismiss();
                }
            });

            //show input deck dialog
            mDiaDeck.show(fm, Boss.DIA_DECK_INPUT);
        }

        return mDiaDeck;
    }

    /*
     * void onDeckInputSave(...) - save deck item to database
     */
    private void onDeckInputSave(DeckItem item){
        //update deckItem with deck input values
        mDeckItem.deck = item.deck;
        mDeckItem.description = item.description;

        butlerSaveDeck();
    }

    /*
     * void onDeckInputCancel() - deck input dialog has been canceled
     */
    private void onDeckInputCancel(){
        //check view status
        if(mIsNewDeck){
            //cancel create deck, null selected deck from boss
            mBoss.setDeckSelected(null);

            //return to parent activity
            mActivity.onBackPressed();
        }

    }

    /*
     * void onDeckInputDismiss() - deck input dialog was dismiss
     */
    private void onDeckInputDismiss(){
        //check if new deck
        if(mIsNewDeck){
            //navigate to flashcard fragment
            mOnNavRequestListener.onNavRequest(DeckDetailNav.NAV_FLASHCARD);
        }
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * DeckInputDialog Methods:
 *      void showTagDialog(...) - show deck tag input dialog
 *      void onTagInputDismiss() - deck tag input dialog has been dismissed
 */
/**************************************************************************************************/

    //Input dialog to input deck info
    private TagInputDialog mDiaTag;

    /*
     * void showTagDialog(...) - show deck tag input dialog
     */
    private TagInputDialog showTagDialog(){

        if(mShowDialog) {
            //get fragment manager
            FragmentManager fm = mActivity.getFragmentManager();

            //create dialog
            mDiaTag = new TagInputDialog();
            mDiaTag.setCancelable(true);
            mDiaTag.setActivity(mActivity, R.layout.dia_input_tag);
            mDiaTag.setInputMax(mMaxTags);
            mDiaTag.setDeckTags(mTagList);
            mDiaTag.setDeck(mDeckItem);

            //set dismiss listener
            mDiaTag.setOnDismissListener(new MyDialog.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //set show dialog status flag
                    mShowDialog = false;

                    //dismiss tag input dialog
                    onTagInputDismiss();
                }
            });

            //show tag input dialog
            mDiaTag.show(fm, Boss.DIA_TAG_INPUT);
        }

        return mDiaTag;
    }

    /*
     * void onTagInputDismiss() - deck tag input dialog has been dismissed
     */
    private void onTagInputDismiss(){
        //clear tag list buffer
        mTagList.clear();

        //get tags from input tag dialog
        mTagList.addAll(mDiaTag.getDeckTags());

        //set tag info textView value
        setTagInfoValue(convertListToString(mTagList));
    }

/**************************************************************************************************/


}

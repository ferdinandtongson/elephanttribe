package me.makeachoice.elephanttribe.view.dialog;

import android.os.Bundle;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.model.item.deck.DeckItem;
import me.makeachoice.elephanttribe.view.dialog.base.BaseInputDialog;

/**
 * DeckInputDialog allows the user to input deck name and description for a new or old deck item
 */

public class DeckInputDialog extends BaseInputDialog {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private DeckItem mDeckItem;
    private ArrayList<String> mDeckNames = new ArrayList<>();

    //mOnSaveListener - listens for dialog save event
    protected OnSaveListener mOnSaveListener;
    public interface OnSaveListener{
        void onSave(DeckItem item);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckInputDialog(){}

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setter Methods:
 *      void setDeckValues(...) - set deck item and deck name list values
 *      void setOnSavedListener(...) - set listener for dialog save event
 */
/**************************************************************************************************/
    /*
     * void setDeckValues(...) - set deck item and deck name list values
     */
    public void setDeckValues(DeckItem deck, ArrayList<String> deckNames){
        //get deck item
        mDeckItem = deck;

        //clear names
        mDeckNames.clear();

        if(deckNames.size() > 0){
            //get deck names
            mDeckNames.addAll(deckNames);
        }
    }

    /*
     * void setOnSavedListener(...) - set listener for dialog save event
     */
    public void setOnSavedListener(OnSaveListener listener){
        mOnSaveListener = listener;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    @Override
    protected void createView(Bundle bundle){
        super.createView(bundle);
        updateView();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Update components:
 *      void updateView() - update view components with deck info values
 */
/**************************************************************************************************/
    /*
     * void updateView() - update view components with deck info values
     */
    private void updateView(){
        //set editText input values
        mEdtValue01.setText(mDeckItem.deck);
        mEdtValue02.setText(mDeckItem.description);

        //set textView character counter values
        mTxtMaxCount01.setText(formatCountValue(mDeckItem.deck.length(), mMaxInput01));
        mTxtMaxCount02.setText(formatCountValue(mDeckItem.description.length(), mMaxInput02));

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Validate and Save:
 *      void onSave() - save event click has occurred
 */
/**************************************************************************************************/
    /*
     * void onSave() - save event click has occurred
     */
    protected void onSave(){
        //check if input values are valid
        if(validateInput()){
            //valid input, save input values to deck item
            mDeckItem.deck = mEdtValue01.getText().toString().trim();
            mDeckItem.description = mEdtValue02.getText().toString().trim();

            //notify save listener
            mOnSaveListener.onSave(mDeckItem);
        }

    }

    /*
     * boolean validateInput() - check if input values are valid
     */
    private boolean validateInput(){

        //check if input value 01 is blank
        if(!isBlankInput(mEdtValue01, mTxtTitle01, mTitle01)){
            //input not blank, get input value
            String value = mEdtValue01.getText().toString();

            boolean duplicate = isDuplicate(mDeckNames, value);

            setIsDuplicate(duplicate, mEdtValue01, mTxtTitle01, mTitle01);

            //check if duplicate true
            if(duplicate){
                //invalid input - is duplicate
                return false;
            }
        }
        else{
            //invalid input - blank value
            return false;
        }

        //valid input
        return true;
    }


/**************************************************************************************************/



}

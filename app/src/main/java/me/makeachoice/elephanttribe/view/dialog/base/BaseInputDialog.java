package me.makeachoice.elephanttribe.view.dialog.base;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.utilities.DeprecatedUtility;

/**
 * BaseInputDialog is the base fragment dialog class used
 */

public abstract class BaseInputDialog extends MyDialog {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    public final static int FOCUS_INPUT01 = 1;
    public final static int FOCUS_INPUT02 = 2;

    private int mInputFocus = FOCUS_INPUT01;

    //string values
    private String mMsgInvalid;
    private String mMsgExceedMax;
    protected String mMsgBlank;
    protected String mMsgDuplicate;

    //color values
    protected int mColorBlack;
    protected int mColorRed;

    //Title string value buffer
    protected String mTitle01;
    protected String mTitle02;

    //Max input number
    protected int mMaxInput01;
    protected int mMaxInput02;

    //valid status flag
    protected boolean mValid;

    //mOnSavedListener - listens for dialog save event
    protected OnCancelListener mOnCancelListener;
    public interface OnCancelListener{
        void onCancel();
    }

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

    public BaseInputDialog(){}

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Setter Methods:
 *      void setTitles(...) - set title string values
 *      void setInputMax(...) - set character max input values
 *      void setInputFocus(...) - set which editText component will have focus
 *      void setOnCancelListener(...) - set listener for dialog cancel event
 */
/**************************************************************************************************/
    /*
     * void setTitles(...) - set title string values
     */
    public void setTitles(String title01, String title02){
        //get title values
        mTitle01 = title01;
        mTitle02 = title02;
    }

    /*
     * void setInputMax(...) - set character max input values
     */
    public void setInputMax(int maxInput01, int maxInput02){
        //get maximum number of input characters
        mMaxInput01 = maxInput01;
        mMaxInput02 = maxInput02;
    }

    /*
     * void setInputFocus(...) - set which editText component will have focus
     */
    public void setInputFocus(int inputFocus){
        //
        mInputFocus = inputFocus;
    }

    /*
     * void setOnCancelListener(...) - set listener for dialog cancel event
     */
    public void setOnCancelListener(OnCancelListener listener){
        mOnCancelListener = listener;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Dialog Lifecycle:
 */
/**************************************************************************************************/

    protected void createView(Bundle bundle){
        //initialize string values
        mMsgInvalid = mActivity.getString(R.string.msg_invalid_character);
        mMsgExceedMax = mActivity.getString(R.string.msg_exceed_max);
        mMsgBlank = mActivity.getString(R.string.msg_invalid_blank);
        mMsgDuplicate = mActivity.getString(R.string.msg_already_exists);

        //initialize color values
        mColorBlack = DeprecatedUtility.getColor(mActivity, R.color.black);
        mColorRed = DeprecatedUtility.getColor(mActivity, R.color.colorAccent);

        //initialize dialog components
        initializeTextTitle();
        initializeTextCount();
        initializeEditText();
        initializeTextButton();

        //display soft-keyboard
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize TextView title components:
 *      void initializeTextTitle() - initialize textView title components
 */
/**************************************************************************************************/

    //TextView title components
    protected TextView mTxtTitle01;
    protected TextView mTxtTitle02;

    /*
     * void initializeTextTitle() - initialize textView title components
     */
    private void initializeTextTitle(){
        //get textView component
        mTxtTitle01 = (TextView)getChildView(R.id.diaInput_txtTitle01);
        mTxtTitle02 = (TextView)getChildView(R.id.diaInput_txtTitle02);

        //set title value
        mTxtTitle01.setText(mTitle01);
        mTxtTitle02.setText(mTitle02);
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
        mEdtValue01 = (EditText)getChildView(R.id.diaInput_edtValue01);
        mEdtValue01.setHint(mTitle01);

        //get editText 02
        mEdtValue02 = (EditText)getChildView(R.id.diaInput_edtValue02);
        mEdtValue02.setHint(mTitle02);

        //add textChange listener to editText input
        mEdtValue01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get total number of characters in editText
                int total = start + count;

                //update textView displaying character count
                mTxtMaxCount01.setText(formatCountValue(total, mMaxInput01));
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
                //get total characters in EditText component
                int total = start + count;

                //update textView displaying character count
                mTxtMaxCount02.setText(formatCountValue(total, mMaxInput02));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //add filter
        mEdtValue01.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxInput01), charFilter});
        mEdtValue02.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxInput02), charFilter});

        //update editText focus
        if(mInputFocus == FOCUS_INPUT02){
            mEdtValue02.requestFocus();
        }
        else{
            mEdtValue01.requestFocus();
        }

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
        mTxtMaxCount01 = (TextView)getChildView(R.id.diaInput_txtMaxCount01);
        mTxtMaxCount01.setText(formatCountValue(0, mMaxInput01));

        //get deck description character count display
        mTxtMaxCount02 = (TextView)getChildView(R.id.diaInput_txtMaxCount02);
        mTxtMaxCount02.setText(formatCountValue(0, mMaxInput02));
    }

    /*
     * String formatCountValue(...) - get count value to be displayed by textView
     */
    protected String formatCountValue(int count, int maxLength){
        //check if count is greater than max character length
        if(count >= maxLength){
            //count exceed max, create message
            String msg = mMsgExceedMax + ": " + maxLength;
            //display message
            showToastMessage(msg);
        }

        //count okay, return character count display
        return count + "/" + maxLength;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Initialize TextView buttons components:
 *      void initializeButtons() - initialize textView button components
 *      abstract void onSave() - textView save button clicked
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
        mTxtSave = (TextView)getChildView(R.id.diaInput_txtSave);
        mTxtCancel = (TextView)getChildView(R.id.diaInput_txtCancel);

        //set buttons as visible
        mTxtSave.setVisibility(View.VISIBLE);
        mTxtCancel.setVisibility(View.VISIBLE);

        //set onClick listener for save button
        mTxtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });

        //set onClick listener for cancel button
        mTxtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCancelListener.onCancel();
            }
        });
    }

    /*
     * abstract void onSave() - textView save button clicked
     */
    protected abstract void onSave();

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Validation Methods:
 *      boolean isBlankInput(...) - check editText component is blank
 *      void setIsDuplicate(...) - display duplicate message for invalid input
 *      void showToastMessage(...) - display toast message to user
 */
/**************************************************************************************************/
    /*
     * boolean isBlankInput(...) - check editText component is blank
     */
    protected boolean isBlankInput(EditText edt, TextView titleView, String title){
        //check if editText component is blank
        if(edt.getText().toString().isEmpty()){
            //is blank, create "blank" error message
            String msg = title + " " + mMsgBlank;

            //change textView component text color to read
            titleView.setTextColor(mColorRed);
            //display error message in textView
            titleView.setText(msg);

            //show toast with error message
            showToastMessage(msg);

            //return isBlank true
            return true;
        }

        //not blank, change textView component text color black
        titleView.setTextColor(mColorBlack);
        //display textView title
        titleView.setText(title);

        //return isBlank false
        return false;
    }

    /*
     * boolean isDuplicate(...) - check if tag is duplicate
     */
    public boolean isDuplicate(ArrayList<String> valueList, String value){
        //get size of value list
        int count = valueList.size();

        //create string buffer
        String savedValue;

        //loop through list
        for(int i = 0; i < count; i++){
            //get saved value, convert to lower case
            savedValue = valueList.get(i).toLowerCase();

            //check if value is equal to saved value
            if(savedValue.equals(value.toLowerCase().trim())){
                //yes, return isDuplicate true
                return true;
            }
        }

        //return, not duplicate
        return false;
    }

    /*
     * void setIsDuplicate(...) - display duplicate message for invalid input
     */
    protected void setIsDuplicate(boolean isDuplicate, EditText edt, TextView titleView, String title){
        //check if duplicate
        if(isDuplicate){
            //is duplicate, create "duplicate" error message
            String msg = title + " " + mMsgDuplicate;

            //change textColor to red
            titleView.setTextColor(mColorRed);

            //display error message in textView
            titleView.setText(msg);

            //display error message in toast
            showToastMessage(msg);
        }
        else{
            //not duplicate, change textColor to black
            titleView.setTextColor(mColorBlack);

            //display normal textView title
            titleView.setText(title);
        }
    }

    /*
     * void showToastMessage(...) - display toast message to user
     */
    private void showToastMessage(String msg){
        //toast object
        Toast toast = Toast.makeText(mActivity, msg, Toast.LENGTH_LONG);
        //display top center of device
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        //show toast
        toast.show();
    }


/**************************************************************************************************/


}

package me.makeachoice.elephanttribe.controller.viewside.maid.flashcard;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.butler.flashcard.FlashcardButler;
import me.makeachoice.elephanttribe.controller.viewside.maid.base.BaseMaid;
import me.makeachoice.elephanttribe.model.item.flashcard.FlashcardItem;
import me.makeachoice.elephanttribe.utilities.RandomizerUtility;

/**
 * FlashcardMaid handles the fragment that displays a simple user-interactive flashcard
 */

public class FlashcardMaid extends BaseMaid {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private int INDEX_START = -1;

    private String mMsgStart;
    private String mMsgLoading;


    //preference variables
    private boolean mIsRandomized;
    private long mDuration;
    private long mDurationHalf;

    private HashMap<Integer,Integer> mRandomOrder;
    private ArrayList<FlashcardItem> mCards;
    private int mMaxCount;
    private int mCardIndex;

    private boolean mFlippingCard;
    private boolean mCardUp;


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public FlashcardMaid(String maidName, int layoutId){
        //get maid name
        mMaidName = maidName;

        //get fragment layout resource id
        mLayoutId = layoutId;

        mCards = new ArrayList<>();

        mCardIndex = INDEX_START;
        mMaxCount = 0;
        mCardUp = true;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Fragment Lifecycle Methods:
 */
/**************************************************************************************************/

    @Override
    public void activityCreated(Bundle bundle){
        super.activityCreated(bundle);

        //get string values
        mMsgStart = mActivity.getString(R.string.msg_click_start);
        mMsgLoading = mActivity.getString(R.string.msg_loading_flashcards);

        //get user info
        mUserItem = getCurrentUser();
        mUserId = mUserItem.userId;

        //initialize view components
        initializePreferenceValues();
        initializeFlashcardAnswerView();
        initializeCounterView();
        initializeRelativeLayout();
        initializeCardView();

        //initialize animator last (needs view components initialized)
        initializeAnimator();

        //initialize butler
        initializeButler();
        butlerFlashcardRequest();

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Preference Methods:
 *      void initializePreferenceValues() - initialize preference values
 */
/**************************************************************************************************/
    /*
     * void initializePreferenceValues() - initialize preference values
     */
    private void initializePreferenceValues(){
        //get randomize order status flag
        mIsRandomized = mPref.getFlashcardRandomized();

        //get animation duration time
        mDuration = mPref.getFlashcardFlipDuration();

        //get 1/2 duration time
        mDurationHalf = mDuration/2;
    }

/**************************************************************************************************/



/**************************************************************************************************/
/*
 * Flashcard Answer Methods:
 *      void initializeFlashcardAnswerView() - initialize flashcard and answer textView components
 *      void updateFlashcardAnswerView() - update flashcard and answer textView components
 */
/**************************************************************************************************/

    //textView components
    private TextView mTxtFlashcard;
    private TextView mTxtAnswerTop;
    private TextView mTxtAnswerBottom;

    /*
     * void initializeFlashcardAnswerView() - initialize flashcard and answer textView components
     */
    private void initializeFlashcardAnswerView(){

        //initialize flashcard textView (card flashcard)
        mTxtFlashcard = (TextView)mActivity.findViewById(R.id.flashcard_txtFlashcard);
        mTxtFlashcard.setText(mMsgStart);

        //initialize answer "top" textView (card answer)
        mTxtAnswerTop = (TextView)mActivity.findViewById(R.id.flashcard_txtTop);
        mTxtAnswerTop.setText("");

        //initialize answer "bottom" textView (card answer)
        mTxtAnswerBottom = (TextView)mActivity.findViewById(R.id.flashcard_txtBottom);
        mTxtAnswerBottom.setText("");
    }

    /*
     * void updateFlashcardAnswerView() - update flashcard and answer textView components
     */
    private void updateFlashcardAnswerView(String flashcard, String answer){
        //set flashcard text value
        mTxtFlashcard.setText(flashcard);

        //set answer text value
        mTxtAnswerTop.setText(flashcard);
        mTxtAnswerBottom.setText(answer);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Counter View Methods:
 *      void initializeCounterView() - initialize counter textView components
 *      void updateCounterView(...) - update counter textView component
 *      void resetCounterViewRotation() - reset counter textView initial rotation setting
 *      String formatCounterValue(...) - format counter string value
 */
/**************************************************************************************************/

    //counter textView components
    private TextView mTxtFlashcardCounter;
    private TextView mTxtAnswerCounter;

    /*
     * void initializeCounterView() - initialize counter textView components
     */
    private void initializeCounterView(){
        mTxtFlashcardCounter = (TextView)mActivity.findViewById(R.id.flashcard_txtCounter);
        mTxtFlashcardCounter.setText("");

        mTxtAnswerCounter = (TextView)mActivity.findViewById(R.id.flashcard_txtFlippedCounter);
        mTxtAnswerCounter.setRotationY(180f);
        mTxtAnswerCounter.setText("");

    }

    /*
     * void updateCounterView(...) - update counter textView component
     */
    private void updateCounterView(int index){
        //get counter string value
        String value = formatCounterValue(index);

        //check card status
        if(mCardUp){
            //flashcard up, set flashcard counter
            mTxtFlashcardCounter.setText(value);
            mTxtAnswerCounter.setText("");
        }
        else{
            //flashcard down, set answer counter
            mTxtFlashcardCounter.setText("");
            mTxtAnswerCounter.setText(value);
        }
    }

    /*
     * void resetCounterViewRotation() - reset counter textView initial rotation setting
     */
    private void resetCounterViewRotation(){
        //set initialize y-rotation
        mTxtFlashcardCounter.setRotationY(0.0f);
        mTxtAnswerCounter.setRotationY(180f);
    }

    /*
     * String formatCounterValue(...) - format counter string value
     */
    private String formatCounterValue(int index){
        if(index == INDEX_START){
            return "";
        }
        return (index + 1) + "/" + mMaxCount;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * RelativeLayout Methods:
 *      void initializeRelativeLayout() - initialize relativeLayout components
 *      void hideLayoutAnswer() - hide relativeLayout answer component
 */
/**************************************************************************************************/

    //relativeLayout components
    private RelativeLayout mRelCard;
    private RelativeLayout mRelAnswer;

    /*
     * void initializeRelativeLayout() - initialize relativeLayout components
     */
    private void initializeRelativeLayout(){
        //initialize relativeLayout displaying flashcard-side
        mRelCard = (RelativeLayout)mActivity.findViewById(R.id.flashcard_relFlashcard);

        //initialize relativeLayout displaying answer-side
        mRelAnswer = (RelativeLayout)mActivity.findViewById(R.id.flashcard_relAnswer);
        mRelAnswer.setAlpha(0.0f);
    }

    /*
     * void hideLayoutAnswer() - hide relativeLayout answer component
     */
    private void hideLayoutAnswer(){
        //set alpha layout to zero
        mRelAnswer.setAlpha(0.0f);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * CardView Methods:
 *      void initializeCardView() - initialize cardView component
 *      void enableCardOnClick(...) - enable cardView to accept onClick events or not
 */
/**************************************************************************************************/

    //cardView component
    private CardView mCrdFlashcard;

    /*
     * void initializeCardView() - initialize cardView component
     */
    private void initializeCardView(){
        //get display scale, used to calculate camera distance for card flip
        float scale = mActivity.getResources().getDisplayMetrics().density;

        //get cardView component
        mCrdFlashcard = (CardView)mActivity.findViewById(R.id.choiceCardView);

        //set camera distance
        mCrdFlashcard.setCameraDistance(10000 * scale);

        mCrdFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make sure flip card animation isn't running

                if(!mFlippingCard){
                    //check if card face is up or down
                    if(mCardUp){
                        //card face up, check if start of deck
                        if(mCardIndex == INDEX_START){
                            //increment counter
                            mCardIndex++;
                            //show first flashcard
                            showFlashcard();
                        }
                        else{
                            //flip card to show answer
                            flipFlashcard();
                        }
                    }
                    else{
                        //show next flashcard
                        nextFlashcard();
                    }
                }
            }
        });
    }

    /*
     * void enableCardOnClick(...) - enable cardView to accept onClick events or not
     */
    private void enableCardOnClick(boolean isEnabled){
        mCrdFlashcard.setClickable(isEnabled);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Animator Methods:
 *      void initializeAnimator() - initialize animator objects
 */
/**************************************************************************************************/

    //animator cardView objects
    private AnimatorSet mFlipCard;
    private AnimatorSet mFadeInAnswer;
    private AnimatorSet mFadeInFlippedCounter;

    //animator textView objects
    private AnimatorSet mFadeInFlashcard;
    private AnimatorSet mFadeOutFlashcard;
    private AnimatorSet mFadeInCounter;
    private AnimatorSet mFadeOutCounter;

    /*
     * void initializeAnimator() - initialize animator objects
     */
    private void initializeAnimator(){
        //flashcard fade in animator
        mFadeInFlashcard = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.flashcard_fade_in);
        mFadeInFlashcard.setTarget(mRelCard);
        mFadeInFlashcard.setDuration(mDurationHalf);

        //flashcard fade out animator
        mFadeOutFlashcard = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.flashcard_fade_out);
        mFadeOutFlashcard.setTarget(mRelCard);
        mFadeOutFlashcard.setDuration(mDurationHalf);

        //counter fade in animator (displayed on flashcard side)
        mFadeInCounter = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.flashcard_fade_in);
        mFadeInCounter.setTarget(mTxtFlashcardCounter);
        mFadeInCounter.setDuration(mDurationHalf);

        //counter fade out animator (displayed on flashcard side)
        mFadeOutCounter = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.flashcard_fade_out);
        mFadeOutCounter.setTarget(mTxtFlashcardCounter);
        mFadeOutCounter.setDuration(mDurationHalf);

        //answer fade in animator
        mFadeInAnswer = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.answer_fade_in);
        mFadeInAnswer.setTarget(mRelAnswer);
        mFadeInAnswer.setDuration(mDurationHalf);

        //counter fade in animator (displayed on answer side)
        mFadeInFlippedCounter = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.answer_fade_in);
        mFadeInFlippedCounter.setTarget(mTxtAnswerCounter);
        mFadeInFlippedCounter.setDuration(mDurationHalf);

        //card flip animator
        mFlipCard = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.card_flip);
        mFlipCard.setTarget(mCrdFlashcard);
        mFlipCard.setDuration(mDuration);

        mFlipCard.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mFlippingCard = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFlippingCard = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //does nothing
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //does nothing
            }
        });

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * FlashcardButler Methods:
 *      void initializeButler() - initialize flashcard butler for retrieving flashcard data
 *      void butlerFlashcardRequest() - request butler to retrieve flashcard data
 *      void flashcardLoaded() - flashcard item data has been loaded
 */
/**************************************************************************************************/

    //butler component
    private FlashcardButler mFlashcardButler;

    /*
     * void initializeButler() - initialize flashcard butler for retrieving flashcard data
     */
    private void initializeButler(){
        //get card butler class
        mFlashcardButler = new FlashcardButler(mActivity, mUserId);

        //set butler onLoaded listener
        mFlashcardButler.setOnLoadedListener(new MyButler.OnLoadedListener() {
            @Override
            public void onLoaded(ArrayList itemList) {
                //loaded flashcard data
                flashcardLoaded(itemList);
            }
        });

    }

    /*
     * void butlerFlashcardRequest() - request butler to retrieve flashcard data
     */
    private void butlerFlashcardRequest(){
        //update flashcard & answer textView component
        updateFlashcardAnswerView(mMsgLoading, "");

        //disable cardView component
        enableCardOnClick(false);

        //get deck id from selected deck
        String deckId = mBoss.getDeckSelected().deckId;

        //load flashcards
        mFlashcardButler.loadByDeckId(Boss.LOADER_DECK, deckId);
    }

    /*
     * void flashcardLoaded() - flashcard item data has been loaded
     */
    private void flashcardLoaded(ArrayList<FlashcardItem> itemList){

        //get number of cards in deck
        mMaxCount = itemList.size();

        //save card items to buffer
        mCards.addAll(itemList);

        //enable cardView component
        enableCardOnClick(true);

        //shuffle deck
        shuffleDeck();

    }

/**************************************************************************************************/



/**************************************************************************************************/
/*
 * Flashcard Methods:
 *      void showFlashcard(...) - display flashcard
 *      void flipFlashcard() - flip flashcard to display answer
 *      void nextFlashcard() - display next flashcard
 *      void endFlashcard() - end of flashcard deck
 */
/**************************************************************************************************/
    /*
     * void showFlashcard(...) - display flashcard
     */
    public void showFlashcard(){
        Log.d("Choice", "FlashcardMaid.showFlashcard");
        FlashcardItem card;

        if(mIsRandomized){
            //get next random card item
            card = mCards.get(mRandomOrder.get(mCardIndex));
        }
        else{
            //get next card item
            card = mCards.get(mCardIndex);
        }

        prepareNextFlashcard(mCardIndex);

        //start flashcard-side animation
        mFadeInFlashcard.start();
        mFadeInCounter.start();

        //update flashcard and answer textViews
        updateFlashcardAnswerView(card.card, card.answer);

    }

    /*
     * void flipFlashcard() - flip flashcard to display answer
     */
    private void flipFlashcard(){
        Log.d("Choice", "FlashcardMaid.flipFlashcard");
        //start flip card animation
        mFlipCard.start();

        //start fade out flashcard-side animation
        mFadeOutFlashcard.start();
        mFadeOutCounter.start();

        //start fade in answer-side animation
        mFadeInAnswer.start();
        mFadeInFlippedCounter.start();

        //set card status flag
        mCardUp = false;
        //set counter value
        updateCounterView(mCardIndex);

    }

    /*
     * void nextFlashcard() - display next flashcard
     */
    private void nextFlashcard(){
        //increment counter
        mCardIndex++;

        //change card status flag
        mCardUp = true;

        //check counter
        if(mCardIndex < mMaxCount){
            //show next flashcard
            showFlashcard();
        }
        else{
            //end of flashcard deck
            endFlashcard();
        }

    }

    /*
     * void endFlashcard() - end of flashcard deck
     */
    private void endFlashcard(){
        shuffleDeck();

        //start flashcard-side animation
        mFadeInFlashcard.start();
        mFadeInCounter.start();

        prepareNextFlashcard(mCardIndex);

    }

    private void shuffleDeck(){
        //check if order should be randomized
        if(mIsRandomized){
            //yes, randomize order
            mRandomOrder = RandomizerUtility.randomizeOrder(mMaxCount);
        }

        //initialize counter
        mCardIndex = INDEX_START;

        //set flashcard-side values
        updateFlashcardAnswerView(mMsgStart, "");

    }

    private void prepareNextFlashcard(int index){
        //reset view component rotation values
        resetCounterViewRotation();
        mCrdFlashcard.setRotationY(0.0f);

        //hide answer-side components and values
        hideLayoutAnswer();

        //set counter values
        updateCounterView(index);

    }

/**************************************************************************************************/






}

package me.makeachoice.elephanttribe.controller.viewside.maid.flashcard;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.butler.deck.DeckScoreButler;
import me.makeachoice.elephanttribe.controller.modelside.butler.flashcard.FlashcardButler;
import me.makeachoice.elephanttribe.controller.viewside.maid.base.BaseRecyclerMaid;
import me.makeachoice.elephanttribe.controller.viewside.recycler.adapter.ChoiceTextAdapter;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;
import me.makeachoice.elephanttribe.model.item.deck.DeckScoreItem;
import me.makeachoice.elephanttribe.model.item.flashcard.FlashcardItem;
import me.makeachoice.elephanttribe.utilities.DateTimeUtility;
import me.makeachoice.elephanttribe.utilities.RandomizerUtility;
import me.makeachoice.elephanttribe.utilities.ScoreUtility;

/**
 * FlashcardMaid handles the fragment that displays a simple user-interactive flashcard
 */

public class FlashcardChoiceMaid extends BaseRecyclerMaid {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private int INDEX_START = -1;

    private int NUM_OF_CHOICES = 4;

    private int FONT_SIZE_XLARGE = 24;
    private int FONT_SIZE_LARGE = 20;
    private int FONT_SIZE_MEDIUM = 16;

    //preference values
    private boolean mIsRandomized;
    private long mDuration;
    private long mDurationHalf;
    private long mFlipDelay;
    private int mCardDisplayType;


    private HashMap<Integer,Integer> mRandomOrder;
    private HashMap<Integer,Integer> mChoiceMap;
    private ArrayList<FlashcardItem> mFlashcards;
    private ArrayList<FlashcardItem> mCardChoices;
    private ArrayList<String> mChoices;
    private ArrayList<String> mEmptyList;
    private FlashcardItem mCard;
    private int mMaxCount;
    private int mCardIndex;

    private String mMsgStart;
    private String mMsgLoading;
    private String mMsgNone;

    private String mStrName;
    private String mStrOverall;
    private String mStrLastDate;
    private String mStrLastScore;

    private DeckItem mDeckItem;
    private DeckScoreItem mDeckScore;
    private ArrayList<DeckScoreItem> mScoreList;
    private boolean mScoredCard;

    //todo - private CardStatsButler mCardStatsButler;
    private ArrayList<Integer> mSelectedList;
    //private HashMap<Long,CardStatsItem> mCardStatsMap;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public FlashcardChoiceMaid(String maidName, int layoutId){
        //get maid name
        mMaidName = maidName;

        //get fragment layout resource id
        mLayoutId = layoutId;

        mRandomOrder = new HashMap<>();
        mChoiceMap = new HashMap<>();

        mFlashcards = new ArrayList<>();
        mCardChoices = new ArrayList<>();
        mChoices = new ArrayList<>();
        mEmptyList = new ArrayList<>();

        mCardIndex = 0;
        mMaxCount = 0;

        mSelectedList = new ArrayList<>();
        mScoreList = new ArrayList<>();
        //todo mCardStatsMap = new HashMap<>();
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

        mMsgStart = mActivity.getString(R.string.msg_click_start);
        mMsgLoading = mActivity.getString(R.string.msg_loading_flashcards);
        mMsgNone = "<" + mActivity.getString(R.string.msg_none) + ">";

        mStrName = mActivity.getString(R.string.name) + ": ";
        mStrOverall = mActivity.getString(R.string.overall_score) + ": ";
        mStrLastDate = mActivity.getString(R.string.last_date) + ": ";
        mStrLastScore = mActivity.getString(R.string.last_score) + ": ";

        mDeckItem = mBoss.getDeckSelected();

        initializePreferenceValues();
        initializeCardDisplay();
        initializeScoreDisplay();
        initializeAdapter();
        initializeRecycler();
        initializeAnimator();

        initializeDeckScoreButler();
        butlerDeckScoreRequest();

        initializeFlashcardButler();
        butlerFlashcardRequest();

        //get card stats butler class
        //todo - mCardStatsButler = new CardStatsButler(mActivity, mBoss.getUserId());
        
        //set card stats butler onLoaded listener
        /*mCardStatsButler.setOnLoadedListener(new MyButler.OnLoadedListener() {
            @Override
            public void onLoaded(ArrayList itemList) {
                //loaded card stats data
                cardStatsDataLoaded(itemList);
            }
        });*/
        
        //todo - need to change creator name
        //todo - mCardStatsButler.loadCardStatsByDeckCreator(Boss.LOADER_CARD_STATS, deckName, "FlashLearning");
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

        //get flip animation duration time
        mDuration = mPref.getFlashcardFlipDuration();

        //get 1/2 duration time
        mDurationHalf = mDuration/2;

        //get correct answer display delay
        mFlipDelay = mPref.getFlashcardCorrectDuration();

        //get flashcard display type
        mCardDisplayType = mPref.getFlashcardDisplayType();
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Flashcard Display Methods:
 *      void initializeCardDisplay() - initialize textView components display card info
 *      void updateCardDisplay() - update textView components display card info
 *      void cardOnClick() - card component onClick event
 *      String formatCounterValue(...) - format counter string value
 */
/**************************************************************************************************/

    //Card display components
    private RelativeLayout mRelCard;
    private TextView mTxtCounter;
    private TextView mTxtCard;

    /*
     * void initializeCardDisplay() - initialize textView components display card info
     */
    private void initializeCardDisplay(){

        //initialize relativeLayout displaying card
        mRelCard = (RelativeLayout)mActivity.findViewById(R.id.flashcard_relFlashcard);
        mRelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //card clicked
                cardOnClick();
            }
        });


        //initialize counter textView
        mTxtCounter = (TextView)mActivity.findViewById(R.id.flashcard_txtCounter);
        mTxtCounter.setText("");

        //initialize card textView
        mTxtCard = (TextView)mActivity.findViewById(R.id.flashcard_txtFlashcard);
        mTxtCard.setText(mMsgStart);

        //modify textSize of card textView
        if(mCardDisplayType == Boss.FLASHCARD_DISPLAY_CARD){
            //card displaying flashcard, xLarge font size
            mTxtCard.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE_XLARGE);
        }
        else{
            //card displaying answer, medium font size
            mTxtCard.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE_MEDIUM);
        }

    }

    /*
     * void updateCardDisplay() - update textView components display card info
     */
    private void updateCardDisplay(String card, int index){
        mTxtCard.setText(card);
        mTxtCounter.setText(formatCounterValue(index));
    }

    /*
     * void cardOnClick() - card component onClick event
     */
    private void cardOnClick(){
        //check if card index is at start
        if(mCardIndex == INDEX_START){
            //hide score display
            showScore(false);

            mScoredCard = false;
            initializeDeckScoreItem(mDeckItem);

            //at start, get next card
            nextCard();
        }

    }

    /*
     * String formatCounterValue(...) - format counter string value
     */
    private String formatCounterValue(int index){
        //check if index is at start
        if(index == INDEX_START){
            //return blank
            return "";
        }
        return (index + 1) + "/" + mMaxCount;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * DeckScore Methods:
 *
 */
/**************************************************************************************************/

    //DeckScore view component
    private RelativeLayout mRelScore;
    private TextView mTxtDeck;
    private TextView mTxtOverall;
    private TextView mTxtLastDate;
    private TextView mTxtLastScore;

    private void initializeScoreDisplay(){
        //initialize relativeLayout displaying card
        mRelScore = (RelativeLayout)mActivity.findViewById(R.id.flashcard_relScore);


        //initialize counter textView
        mTxtDeck = (TextView)mActivity.findViewById(R.id.flashcard_txtDeck);
        mTxtDeck.setText(mDeckItem.deck);

        //initialize card textView
        mTxtOverall = (TextView)mActivity.findViewById(R.id.flashcard_txtOverall);
        mTxtOverall.setText(mStrOverall + mMsgNone);

        //initialize card textView
        mTxtLastDate = (TextView)mActivity.findViewById(R.id.flashcard_txtLastDate);
        mTxtLastDate.setText(mStrLastDate + mMsgNone);

        //initialize card textView
        mTxtLastScore = (TextView)mActivity.findViewById(R.id.flashcard_txtLastScore);
        mTxtLastScore.setText(mStrLastScore + mMsgNone);

    }

    private void showScore(boolean isShown){
        if(isShown){
            mFadeInScore.start();
        }
        else{
            mFadeOutScore.start();
        }
    }

    private void updateScoreDisplay(DeckScoreItem item){
        String lastDate = DateTimeUtility.convertTimestampToDateTime(item.quizDate);
        mTxtLastDate.setText(mStrLastDate + lastDate);

        String overall = ScoreUtility.getOverallScore(mScoreList);
        mTxtOverall.setText(mStrOverall + overall);

        String value = ScoreUtility.getDeckScore(item);

        mTxtLastScore.setText(mStrLastScore + value);
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * RecyclerView and Adapter Methods:
 *      void initializeAdapter() - initialize adapter used by recycler
 *      void initializeRecycler() - initialize recyclerView
 *      void itemOnClicked(...) - item selected from adapter, score choice
 *      void startTimer(...) - start countdown timer
 */
/**************************************************************************************************/

    //Adapter and Recycler components
    private ChoiceTextAdapter mAdapter;
    private RecyclerView mRecycler;

    /*
     * void initializeAdapter() - initialize adapter used by recycler
     */
    private void initializeAdapter(){

        //initialize adapter with layout
        mAdapter = new ChoiceTextAdapter(mActivity, R.layout.card_choice_text);

        //add empty array list to adapter
        mAdapter.swapData(mEmptyList);

        //set adapter item onClick listener
        mAdapter.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int)v.getTag(R.string.tag_position);
                itemOnClicked(index);
            }
        });

        //initialize choice font size
        if(mCardDisplayType == Boss.FLASHCARD_DISPLAY_CARD){
            //choices displaying answers, font size medium
            mAdapter.setTextSize(FONT_SIZE_MEDIUM);
        }
        else{
            //choices displaying cards, font size large
            mAdapter.setTextSize(FONT_SIZE_LARGE);
        }

    }

    /*
     * void initializeRecycler() - initialize recyclerView
     */
    private void initializeRecycler(){

        //initialize linear recyclerView
        initializeLinearRecycler();

        mRecycler = mBasicRecycler.getRecycler();

        //get display scale, used to calculate camera distance for card flip
        float scale = mActivity.getResources().getDisplayMetrics().density;

        //set camera distance
        mRecycler.setCameraDistance(10000 * scale);

        //set empty list message
        setRecyclerEmptyMessage("");

        //check if adapter is empty
        checkRecyclerEmpty(mAdapter.getItemCount());

        //add adapter to recyclerView
        setRecyclerAdapter(mAdapter);

    }

    /*
     * void itemOnClicked(...) - item selected from adapter, score choice
     */
    private void itemOnClicked(int index){
        //get flashcard index from choice map
        int flashcardIndex = mChoiceMap.get(index);

        //get flashcard item from choice list
        FlashcardItem item = mCardChoices.get(flashcardIndex);

        updateDeckScore(item);

        if(item.card.equals(mCard.card)){
            if(mSelectedList.get(index) == ChoiceTextAdapter.SELECTION_NONE){
                mSelectedList.set(index, ChoiceTextAdapter.SELECTION_RIGHT);
                mAdapter.setSelectedList(mSelectedList);
                startTimer(mFlipDelay);
            }
        }
        else{
            //todo - solidfy code
            String msg = "Card: " + item.card + "\nAnswer: " + item.answer;
            Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();

            //check if item has already been selected
            if(mSelectedList.get(index) == ChoiceTextAdapter.SELECTION_NONE){
                //not selected, change selection to "wrong"
                mSelectedList.set(index, ChoiceTextAdapter.SELECTION_WRONG);
                //update adapter
                mAdapter.setSelectedList(mSelectedList);
            }
        }
    }

    /*
     * void startTimer(...) - start countdown timer
     */
    private void startTimer(long countdown){
        CountDownTimer timer = new CountDownTimer(countdown, 1000) {

            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                fadeOutCard();
            }
        }.start();
    }

    private void unflipRecycler(){
        mRecycler.setRotationY(0.0f);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Animator Methods:
 *      void initializeAnimator() - initialize animator objects
 */
/**************************************************************************************************/

    //animator sets
    private AnimatorSet mFadeInCard;
    private AnimatorSet mFadeOutCard;
    private AnimatorSet mFadeInScore;
    private AnimatorSet mFadeOutScore;
    private AnimatorSet mFlipInChoices;
    private AnimatorSet mFlipOutChoices;

    /*
     * void initializeAnimator() - initialize animator objects
     */
    private void initializeAnimator(){
        //card fade in animator
        mFadeInCard = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.flashcard_fade_in);
        mFadeInCard.setTarget(mTxtCard);
        mFadeInCard.setDuration(mDurationHalf);

        //card fade out animator
        mFadeOutCard = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.flashcard_fade_out);
        mFadeOutCard.setTarget(mTxtCard);
        mFadeOutCard.setDuration(mDurationHalf);

        //card fade in animator
        mFadeInScore = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.flashcard_fade_in);
        mFadeInScore.setTarget(mRelScore);
        mFadeInScore.setDuration(mDurationHalf);

        //card fade out animator
        mFadeOutScore = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.flashcard_fade_out);
        mFadeOutScore.setTarget(mRelScore);
        mFadeOutScore.setDuration(mDurationHalf);

        //recycler choices fade in animator
        mFlipInChoices = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.card_flip_in);
        mFlipInChoices.setTarget(mRecycler);
        mFlipInChoices.setDuration(mDurationHalf);

        //recycler choices fade out animator
        mFlipOutChoices = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.card_flip_out);
        mFlipOutChoices.setTarget(mRecycler);
        mFlipOutChoices.setDuration(mDurationHalf);


        mFadeOutCard.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                nextCard();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * DeckScoreButler Methods:
 *      void initializeButler() - initialize flashcard butler for retrieving flashcard data
 *      void butlerFlashcardRequest() - request butler to retrieve flashcard data
 *      void flashcardLoaded() - flashcard item data has been loaded
 */
/**************************************************************************************************/

    //DeckScore butler
    private DeckScoreButler mDeckScoreButler;

    /*
     * void initializeDeckScoreButler() - initialize flashcard butler for retrieving flashcard data
     */
    private void initializeDeckScoreButler(){
        //get deck score butler class
        mDeckScoreButler = new DeckScoreButler(mActivity, mUserId);

        //set butler onLoaded listener
        mDeckScoreButler.setOnLoadedListener(new MyButler.OnLoadedListener() {
            @Override
            public void onLoaded(ArrayList itemList) {
                //loaded flashcard data
                deckScoreLoaded(itemList);
            }
        });
    }

    private void deckScoreLoaded(ArrayList<DeckScoreItem> itemList){
        mScoreList.clear();
        mScoreList.addAll(itemList);

        if(itemList.size() > 0){
            mDeckScore = itemList.get(0);
            updateScoreDisplay(itemList.get(0));
        }
    }

    private void butlerDeckScoreRequest(){

        mDeckScoreButler.loadByDeckId(Boss.LOADER_DECK_SCORE, mDeckItem.deckId);
    }

    private void initializeDeckScoreItem(DeckItem item){
        mDeckScore = new DeckScoreItem(item);
        mDeckScore.total = mMaxCount;
    }

    private void updateDeckScore(FlashcardItem selectedChoice){
        if(!mScoredCard){
            mScoredCard = true;

            if(selectedChoice.card.equals(mCard.card)){
                mDeckScore.correct = mDeckScore.correct + 1;
            }
            else{
                mDeckScore.missed = mDeckScore.missed + 1;
            }
        }

    }

    private void butlerSaveDeckScore(){

        mDeckScoreButler.save(mDeckScore);
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
     * void initializeFlashcardButler() - initialize flashcard butler for retrieving flashcard data
     */
    private void initializeFlashcardButler(){
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
        updateCardDisplay(mMsgLoading, INDEX_START);

        //load flashcards
        mFlashcardButler.loadByDeckId(Boss.LOADER_DECK, mDeckItem.deckId);
    }

    /*
     * void flashcardLoaded() - flashcard item data has been loaded
     */
    private void flashcardLoaded(ArrayList<FlashcardItem> itemList){

        //get number of cards in deck
        mMaxCount = itemList.size();

        //save card items to buffer
        mFlashcards.addAll(itemList);

        //shuffle deck
        shuffleDeck();

    }

/**************************************************************************************************/



/**************************************************************************************************/
/*
 * Data Method:
 *      void flashcardDataLoaded() - flashcard item data has been loaded
 */
/**************************************************************************************************/

    /*private void cardStatsDataLoaded(ArrayList<CardStatsItem> itemList){
        mCardStatsMap.clear();

        int count = itemList.size();

        for(int i = 0; i < count; i++){

        }
        if(itemList.isEmpty()){
            createStatsMap(mFlashcards);
        }
        else{

        }
        
    }*/

    /*private void createStatsMap(ArrayList<FlashcardItem> flashcards){
        int count = flashcards.size();

        for(int i = 0; i < count; i++){
            FlashcardItem flashcardItem = flashcards.get(i);
            createStats(flashcardItem);
        }
    }

    private void createStats(FlashcardItem flashcard){
        CardStatsItem statsItem = CardStatsItem.getEmptyItem(flashcard);

        mCardStatsButler.saveCardStats(statsItem);
    }

    private void updateStats(CardStatsItem stats){
        mCardStatsButler.updateCardStats(stats);
    }*/


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Quiz Methods:
 *      void showCard(...) - display card and choices
 *      void createDistractors(...) - create choice distractors for card
 *      void fadeOutCard() - fade out card and choices
 *      void nextCard() - show next card or end quiz
 *      void endQuiz() - end of flashcard quiz
 *      String getCardValue(...) - get string value for card to display
 *      String getChoiceValue(...) - get string value for choices to display
 */
/**************************************************************************************************/
    /*
     * void showCard(...) - display card and choices
     */
    public void showCard(int index){
        unflipRecycler();

        //check if cards are randomized
        if(mIsRandomized){
            //get next random card item
            mCard = mFlashcards.get(mRandomOrder.get(index));
        }
        else{
            //get next card item
            mCard = mFlashcards.get(index);
        }

        //create distractors for card
        createDistractors(mCard);


        //start fadeIn animation
        mFadeInCard.start();
        mFlipInChoices.start();

        //set card values
        updateCardDisplay(getCardValue(mCard), index);
    }

    /*
     * void createDistractors(...) - create choice distractors for card
     */
    private void createDistractors(FlashcardItem item){
        //clear list buffers
        mCardChoices.clear();
        mChoices.clear();
        mSelectedList.clear();

        //clear choice map
        mChoiceMap.clear();

        //clear adapter
        mAdapter.clearData();

        //select choice distractors for card from deck
        mCardChoices = RandomizerUtility.selectDistractors(item, NUM_OF_CHOICES, mFlashcards);

        //create randomized choiceMap index
        mChoiceMap = RandomizerUtility.randomizeOrder(NUM_OF_CHOICES);


        //loop through distractors using randomized index
        for(int i = 0; i < NUM_OF_CHOICES; i++){
            //get card item using randomized index
            FlashcardItem card = mCardChoices.get(mChoiceMap.get(i));

            //add value to choice list
            mChoices.add(getChoiceValue(card));

            //initialize selected list
            mSelectedList.add(ChoiceTextAdapter.SELECTION_NONE);
        }

        //add choice list to adapter
        mAdapter.swapData(mChoices);

        //set selected list
        mAdapter.setSelectedList(mSelectedList);

        //check if adapter is empty
        checkRecyclerEmpty(mAdapter.getItemCount());

    }

    /*
     * void fadeOutCard() - fade out card and choices
     */
    private void fadeOutCard(){

        //start fade out flashcard-side animation
        mFadeOutCard.start();
        mFlipOutChoices.start();

    }

    /*
     * void nextCard() - show next card or end quiz
     */
    private void nextCard(){
        //increment counter
        mCardIndex++;

        //check counter
        if(mCardIndex < mMaxCount){
            mScoredCard = false;

            //show next flashcard
            showCard(mCardIndex);
        }
        else{
            //end of quiz
            endQuiz();
        }

    }

    /*
     * void endQuiz() - end of flashcard quiz
     */
    private void endQuiz(){
        //check if order should be randomized
        if(mIsRandomized){
            //clear card map
            mRandomOrder.clear();

            //yes, randomize order
            mRandomOrder = RandomizerUtility.randomizeOrder(mMaxCount);
        }

        //initialize counter
        mCardIndex = INDEX_START;

        //display "start" message
        updateCardDisplay(mMsgStart, mCardIndex);

        //empty adapter
        mAdapter.swapData(mEmptyList);
        checkRecyclerEmpty(mAdapter.getItemCount());

        //start flashcard-side animation
        mFadeInCard.start();
        mFlipInChoices.start();

        mDeckScore.quizDate = String.valueOf(DateTimeUtility.getTodayInMillis());
        updateScoreDisplay(mDeckScore);
        showScore(true);
        butlerSaveDeckScore();
    }

    /*
     * String getCardValue(...) - get string value for card to display
     */
    private String getCardValue(FlashcardItem item){

        //check if choice distractor type is "answer" type
        if(mCardDisplayType == Boss.FLASHCARD_DISPLAY_CARD){
            //choices are "answers" return flashcard value
            return item.card;
        }

        //choices are "flashcard" return answer value
        return item.answer;
    }

    /*
     * String getChoiceValue(...) - get string value for choices to display
     */
    private String getChoiceValue(FlashcardItem item){

        //check if choice distractor type is "answer"
        if(mCardDisplayType == Boss.FLASHCARD_DISPLAY_CARD){
            //choices are "answer" return answer value
            return item.answer;
        }

        //choices are "flashcard" return flashcard value
        return item.card;
    }

/**************************************************************************************************/

    private void shuffleDeck(){
        //check if order should be randomized
        if(mIsRandomized){
            //yes, randomize order
            mRandomOrder = RandomizerUtility.randomizeOrder(mMaxCount);
        }

        //initialize counter
        mCardIndex = INDEX_START;

        //set flashcard-side values
        updateCardDisplay(mMsgStart, mCardIndex);

    }

    private String calculateOverall(){
        Log.d("Choice", "FlashcardChoiceMaid.calculateOverall");
        int count = mScoreList.size();

        Log.d("Choice", "     count: " + count);
        int total = 0;
        int correct = 0;
        for(int i = 0; i < count; i++){
            total = total + mScoreList.get(i).total;
            correct = correct + mScoreList.get(i).correct;
        }

        double score = (float)Math.round(correct * 100) / total;

        DecimalFormat twoDForm = new DecimalFormat("#.##");
        //int decimal = (item.missed*100)%item.total;
        Log.d("Choice", "     score: " + score);

        return Double.valueOf(twoDForm.format(score)) + "%";
    }


}

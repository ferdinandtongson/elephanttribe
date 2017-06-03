package me.makeachoice.elephanttribe.controller.viewside.housekeeper.deck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.controller.modelside.butler.base.MyButler;
import me.makeachoice.elephanttribe.controller.modelside.butler.deck.DeckButler;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.base.BaseRecyclerKeeper;
import me.makeachoice.elephanttribe.controller.viewside.navigation.drawer.MainDrawer;
import me.makeachoice.elephanttribe.controller.viewside.recycler.adapter.UserDeckAdapter;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;
import me.makeachoice.elephanttribe.view.activity.FlashcardActivity;
import me.makeachoice.elephanttribe.view.activity.TutorialActivity;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * DeckKeeper extends BaseRecyclerKeeper and manages the displaying of user deck information
 */

public class DeckKeeper extends BaseRecyclerKeeper {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private DeckItem mSelectedDeck;

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public DeckKeeper(int layoutId){
        mLayoutId = layoutId;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Activity Lifecycle Methods:
 */
/**************************************************************************************************/
    /*
     * void create(...) - called when onCreate() is called in activity; sets the activity layout,
     * fragmentManager and other child views of the activity
     *
     * NOTE: both FragmentManager and FAB are context sensitive and need to be recreated every time
     * onCreate() is called in the activity
     */
    @Override
    public void create(MyActivity activity, Bundle bundle) {
        super.create(activity, bundle);

        initializeAdapter();
        initializeRecycler();

    }

    /*
     * void onUserLoaded() - user login status and user data loaded
     */
    protected void onUserLoaded(){

        initializeNavigation();

        //initialize shared preferences
        mPref.requestUser(mUserItem.userId);

        //initialize butler
        initializeButler();

        //todo - #1 check user id
        if(mUserItem.userId.equals(mStrAnonymousId)){
            //todo - #2 check first time anonymous
            //check if first time anonymous
            if(mPref.getFirstTime()){
                Toast.makeText(mActivity, "Anonymous user - First Time!!!!", Toast.LENGTH_SHORT).show();
                //go to tutorial activity
                Intent intent = new Intent(mActivity, TutorialActivity.class);
                mActivity.startActivity(intent);
            }
            else{
                //todo - #3 get anonymous decks
                butlerDeckRequest();
                Toast.makeText(mActivity, "Anonymous user", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if(mPref.getFirstTime()){
                Toast.makeText(mActivity, "Welcome Message: " + mUserItem.userName, Toast.LENGTH_SHORT).show();
                //todo - show dialog option to see tutorial page
                //mPref.setFirstTime(false);
            }

            butlerDeckRequest();
            Toast.makeText(mActivity, "User: " + mUserItem.userName, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void stop(){
        super.stop();

        Log.d("Choice", "DeckKeeper.stop");
        if(mFAB.isShown()){
            Log.d("Choice", "     hide fab");
            mFAB.hide();
        }
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Navigation Methods:
 *      void initializeNavigation() - initialize toolbar and drawer ui components
 *      void addNewDeck() - add new deck request
 */
/**************************************************************************************************/
    /*
     * void initializeNavigation() - initialize toolbar and drawer ui components
     */
    private void initializeNavigation(){
        //initialize toolbar navigation
        initializeToolbar();

        //initialize drawer navigation
        initializeDrawer();
        setDrawerMenuItemSelected(MainDrawer.MENU_DECK);

        //initialize floating action button
        initializeFAB();
        setFABOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add new deck
                addNewDeck();
            }
        });
    }

    /*
     * void addNewDeck() - add new deck request
     */
    private void addNewDeck(){
        //set deck item selected to null
        mBoss.setDeckSelected(null);

        //start deck detail activity
        //Intent intent = new Intent(mActivity, DeckDetailActivity.class);
        //mActivity.startActivity(intent);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Adapter/Recycler Methods:
 *      void initializeAdapter() - initialize adapter used by recycler
 *      void initializeRecycler() - initialize recycler view
 *      void updateAdapter(...) - update adapter with new list and check if recycler is empty
 *      void boomMenuSelection(...) - request boom menu selection
 */
/**************************************************************************************************/

    private UserDeckAdapter mAdapter;

    /*
     * void initializeAdapter() - initialize adapter used by recycler
     */
    private void initializeAdapter(){
        //initialize adapter with layout
        mAdapter = new UserDeckAdapter(mActivity, R.layout.card_user_deck);

        //add boom menu values

        //add empty array list to adapter
        mAdapter.swapData(new ArrayList<DeckItem>());

        //set boom menu listener
        mAdapter.setBoomMenuListener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                //boom menu item selected
                boomMenuSelection(index);
            }
        });

        //set adapter item onClick listener
        mAdapter.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get deck selected from list
                mSelectedDeck = (DeckItem)v.getTag(R.string.tag_item);

                //get boom menu button from list
                BoomMenuButton menuButton = (BoomMenuButton)v.getTag(R.string.tag_boom);

                //open boom menu
                menuButton.boom();
            }
        });
    }

    /*
     * void initializeRecycler() - initialize recycler view
     */
    private void initializeRecycler(){
        //get empty recycler message
        String msg = mActivity.getString(R.string.emptyList_deck);

        //initialize recycler to linear layout
        initializeLinearRecycler();

        //set empty recycler message
        setRecyclerEmptyMessage(msg);

        //check if adapter for recycler is empty
        checkRecyclerEmpty(mAdapter.getItemCount());

        //set adapter for recycler
        setRecyclerAdapter(mAdapter);
    }

    /*
     * void updateAdapter(...) - update adapter with new list and check if recycler is empty
     */
    private void updateAdapter(ArrayList<DeckItem> deckList){
        //clear adapter
        mAdapter.clearData();
        mAdapter.swapData(deckList);

        //check if adapter for recycler is empty
        checkRecyclerEmpty(deckList.size());
    }

    /*
     * void boomMenuSelection(...) - request boom menu selection
     */
    private void boomMenuSelection(int index){
        //update deck selected item
        mBoss.setDeckSelected(mSelectedDeck);

        //create intent buffer
        Intent intent;

        //check which menu was selected
        switch(index){
            //
            case 0:
                //edit deck selection, start activity
                //intent = new Intent(mActivity, DeckDetailActivity.class);
                //mActivity.startActivity(intent);
                break;
            case 1:
                //leitner method
                break;
            case 2:
                //flashcard selection, start activity
                intent = new Intent(mActivity, FlashcardActivity.class);
                mActivity.startActivity(intent);
                break;
            case 3:
                //intent = new Intent(mActivity, DeckDetailActivity.class);
                //mActivity.startActivity(intent);
                break;
            default:
                //does nothing
        }
    }

/**************************************************************************************************/

/**************************************************************************************************/
/*
 * Butler Methods:
 *      void initializeButler() - initialize deck butler class
 *      void butlerDeckRequest() - request user decks from data storage
 */
/**************************************************************************************************/

    private DeckButler mDeckButler;

    /*
     * void initializeButler() - initialize deck butler class
     */
    private void initializeButler(){
        //initialize deck butler
        mDeckButler = new DeckButler(mActivity, mUserItem.userId);

        //set onLoaded listener
        mDeckButler.setOnLoadedListener(new MyButler.OnLoadedListener() {
            @Override
            public void onLoaded(ArrayList itemList) {
                //user deck data loaded
                userDeckLoaded(itemList);
            }
        });

    }

    /*
     * void butlerDeckRequest() - request user decks from data storage
     */
    private void butlerDeckRequest(){
        //load deck data by user id
        mDeckButler.loadByUserId(Boss.LOADER_DECK);
    }

    /*
     * void userDeckLoaded(...) - load deck data into adapter
     */
    private void userDeckLoaded(ArrayList<DeckItem> itemList){

        //update adapter
        updateAdapter(itemList);

        //set user deck buffer
        mBoss.setDeckList(itemList);

        if(itemList.size() == 0){
            //show floating action button
            mFAB.show();
        }


    }

/**************************************************************************************************/

}

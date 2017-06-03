package me.makeachoice.elephanttribe.controller.manager;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.base.MyBoss;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.FlashcardKeeper;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.SettingsKeeper;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.TutorialKeeper;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.deck.DeckDetailKeeper;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.deck.DeckKeeper;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.MarketKeeper;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;
import me.makeachoice.elephanttribe.model.item.user.UserItem;

/**
 * Created by tongson on 6/1/17.
 */

public class Boss extends MyBoss{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * LifeCycle Method:
 */
/**************************************************************************************************/

    @Override
    public void onCreate(){
        super.onCreate();

        mKeeperRegistry = new HashMap<>();

        initializeHouseKeeper();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * HouseKeeper method:
 */
/**************************************************************************************************/

    private void initializeHouseKeeper(){
        DeckKeeper deckKeeper = new DeckKeeper(R.layout.activity_deck);
        registerHouseKeeper(KEEPER_DECK, deckKeeper);

        DeckDetailKeeper deckDetailKeeper = new DeckDetailKeeper(R.layout.activity_deck_detail);
        registerHouseKeeper(KEEPER_DECK_DETAIL, deckDetailKeeper);

        FlashcardKeeper flashcardKeeper = new FlashcardKeeper(R.layout.activity_fragment);
        registerHouseKeeper(KEEPER_FLASHCARD, flashcardKeeper);

        TutorialKeeper tutorialKeeper = new TutorialKeeper(R.layout.activity_tutorial);
        registerHouseKeeper(KEEPER_TUTORIAL, tutorialKeeper);

        MarketKeeper marketKeeper = new MarketKeeper(R.layout.activity_market);
        registerHouseKeeper(KEEPER_MARKET, marketKeeper);

        SettingsKeeper settingsKeeper = new SettingsKeeper(R.layout.activity_settings);
        registerHouseKeeper(KEEPER_SETTINGS, settingsKeeper);

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * User Methods:
 */
/**************************************************************************************************/

    private UserItem mUserItem;

    public void setUser(UserItem userItem){
        mUserItem = userItem;
    }

    public UserItem getUser(){
        return mUserItem;
    }

/**************************************************************************************************/



/**************************************************************************************************/
/*
 * Deck Methods:
 */
/**************************************************************************************************/

    private DeckItem mDeckSelected;
    private ArrayList<DeckItem> mDeckList;

    public void setDeckSelected(DeckItem deck){
        mDeckSelected = deck;
    }

    public DeckItem getDeckSelected(){
        return mDeckSelected;
    }

    public void setDeckList(ArrayList<DeckItem> deckList){
        if(mDeckList == null){
            mDeckList = new ArrayList<>();
        }

        mDeckList.clear();
        mDeckList.addAll(deckList);
    }

    public ArrayList<DeckItem> getDeckList(){
        return mDeckList;
    }

/**************************************************************************************************/

}

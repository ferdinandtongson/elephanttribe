package me.makeachoice.elephanttribe.controller.manager;

import java.util.HashMap;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.manager.base.MyBoss;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.TutorialKeeper;
import me.makeachoice.elephanttribe.controller.viewside.housekeeper.deck.DeckKeeper;
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

        TutorialKeeper tutorialKeeper = new TutorialKeeper(R.layout.activity_tutorial);
        registerHouseKeeper(KEEPER_TUTORIAL, tutorialKeeper);


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

}

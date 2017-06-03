package me.makeachoice.elephanttribe.controller.manager;

import java.util.HashMap;

import me.makeachoice.elephanttribe.controller.viewside.maid.base.MyMaid;
import me.makeachoice.elephanttribe.controller.viewside.maid.deck.DeckFlashcardMaid;
import me.makeachoice.elephanttribe.controller.viewside.maid.deck.DeckInfoMaid;
import me.makeachoice.elephanttribe.controller.viewside.maid.flashcard.FlashcardMaid;

/**************************************************************************************************/
/*
 * MaidRegistry registers MyMaid classes into a HashMap registry
 */

/**************************************************************************************************/

public class MaidRegistry {

/**************************************************************************************************/
/*
 * Class Variables:
 *      mInstance - instance MaidRegistry class
 *      mMaidRegistry - maid registry
 */
/**************************************************************************************************/

    //mInstance - instance MaidRegistry class
    private static MaidRegistry mInstance;

    //mMaidRegistry - maid registry
    protected HashMap<String, MyMaid> mMaidRegistry = new HashMap<>();

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public static MaidRegistry getInstance(){
        if(mInstance == null){
            mInstance = new MaidRegistry();
        }

        return mInstance;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Maid Registry Methods:
 *      MyMaid requestMaid(...) - request maid from registry
 *      void registerMaid(...) - add maid to registry
 *      void removeMaid(...) - remove maid from registry
 *      void clearRegistry() - clear maid registry
 */
/**************************************************************************************************/
    /*
     * MyMaid requestMaid(...) - request maid from registry
     */
    public MyMaid requestMaid(String maidName){
        //check if registry has maid
        if(mMaidRegistry.containsKey(maidName)){
            return mMaidRegistry.get(maidName);
        }

        //invalid name
        return null;
    }

    /*
     * void registerMaid(...) - add maid to registry
     */
    public void registerMaid(String maidName, MyMaid maid){
        //add maid to registry
        mMaidRegistry.put(maidName, maid);
    }

    /*
     * void removeMaid(...) - remove maid from registry
     */
    public void removeMaid(String maidName){
        //remove maid from registry
        mMaidRegistry.remove(maidName);
    }

    /*
     * void clearMaidRegistry() - clear maid registry
     */
    public void clearMaidRegistry(){
        //clear maid registry
        mMaidRegistry.clear();
        mMaidRegistry = null;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Deck Detail Maids:
 */
/**************************************************************************************************/

    public DeckInfoMaid initializeDeckInfo(String maidName, int layoutId){
        DeckInfoMaid maid = new DeckInfoMaid(maidName, layoutId);
        registerMaid(maidName, maid);

        return maid;
    }

    public DeckFlashcardMaid initializeDeckFlashcard(String maidName, int layoutId){
        DeckFlashcardMaid maid = new DeckFlashcardMaid(maidName, layoutId);
        registerMaid(maidName, maid);

        return maid;
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Flashcard Maids:
 */
/**************************************************************************************************/

    public void initializeFlashcardMaid(String maidName, int layoutId, String userId){
        FlashcardMaid maid = new FlashcardMaid(maidName, layoutId, userId);
        registerMaid(maidName, maid);
    }


/**************************************************************************************************/

}

package me.makeachoice.elephanttribe.controller.modelside.firebase.deck;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.makeachoice.elephanttribe.controller.modelside.firebase.base.MyFirebase;
import me.makeachoice.elephanttribe.model.contract.deck.DeckContract;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;
import me.makeachoice.elephanttribe.model.item.flashcard.FlashcardItem;

/**
 * DeckFirebase gives access to deck data in firebase
 */

public class DeckFirebase extends MyFirebase{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //PARENT - parent director
    private final static String PARENT = DeckContract.PATH;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    private static DeckFirebase instance = null;

    public static DeckFirebase getInstance(){
        if(instance == null){
            instance = new DeckFirebase();
        }

        return instance;
    }

    private DeckFirebase(){
        mFirebase = FirebaseDatabase.getInstance();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Getter Methods:
 */
/**************************************************************************************************/

    private DatabaseReference getParentRef(){
        //deck -->
        return mFirebase.getReference().child(PARENT);
    }

    public DatabaseReference getUserIdRef(String userId){
        //deck --> userId
        return getParentRef().child(userId);
    }

    public DatabaseReference getDeckIdRef(String userId, String deckId){
        //deck --> userId --> deckId
        return getParentRef().child(userId).child(deckId);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Add Method:
 */
/**************************************************************************************************/

    public String addDeck(DeckItem item){
        DatabaseReference ref = getUserIdRef(item.userId);

        DatabaseReference pushedRef = ref.push();
        pushedRef.setValue(item.getFBItem());

        String key = pushedRef.getKey();

        return key;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Set Methods:
 */
/**************************************************************************************************/

    public void setFlashcard(FlashcardItem item){
        /*DatabaseReference ref = getCreatorDeckFBKeyRef(item.userId, item.creator, item.deck, item.fbKey);

        ref.child(CHILD_CARD).setValue(item.card);
        ref.child(CHILD_ANSWER).setValue(item.answer);
        ref.child(CHILD_PICTURE).setValue(item.picture);
        ref.child(CHILD_VOICE).setValue(item.voice);*/
    }

    public void setCardAnswer(FlashcardItem item){
        /*DatabaseReference ref = getCreatorDeckFBKeyRef(item.userId, item.creator, item.deck, item.fbKey);

        ref.child(CHILD_CARD).setValue(item.card);
        ref.child(CHILD_ANSWER).setValue(item.answer);*/
    }

    public void setPicture(FlashcardItem item){
        /*DatabaseReference ref = getCreatorDeckFBKeyRef(item.userId, item.creator, item.deck, item.fbKey);

        ref.child(CHILD_PICTURE).setValue(item.picture);*/
    }

    public void setVoice(FlashcardItem item){
        /*DatabaseReference ref = getCreatorDeckFBKeyRef(item.userId, item.creator, item.deck, item.fbKey);

        ref.child(CHILD_VOICE).setValue(item.voice);*/
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Request Method:
 */
/**************************************************************************************************/

    public void requestByDeckId(String userId, String deckId, OnDataLoadListener listener){
        //get reference
        DatabaseReference ref = getDeckIdRef(userId, deckId);

        mLoadListener = listener;

        //add listener to reference
        ref.addListenerForSingleValueEvent(mEventListener);
    }

    //todo - request decks with given status value
    /*public void requestByStatus(String userId, String deckId, OnDataLoadListener listener){
        DatabaseReference ref = getDeckIdRef(userId, deckId);

        mLoadListener = listener;

        ref.addListenerForSingleValueEvent(mEventListener);
    }*/

/**************************************************************************************************/


}

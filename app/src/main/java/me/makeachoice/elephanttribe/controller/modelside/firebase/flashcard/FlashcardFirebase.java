package me.makeachoice.elephanttribe.controller.modelside.firebase.flashcard;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.makeachoice.elephanttribe.controller.modelside.firebase.base.MyFirebase;
import me.makeachoice.elephanttribe.model.contract.flashcard.FlashcardContract;
import me.makeachoice.elephanttribe.model.item.flashcard.FlashcardItem;

/**
 * FlashcardFirebase gives access to flashcard data in firabase
 */

public class FlashcardFirebase extends MyFirebase{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //PARENT - parent director
    private final static String PARENT = FlashcardContract.PATH;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    private static FlashcardFirebase instance = null;

    public static FlashcardFirebase getInstance(){
        if(instance == null){
            instance = new FlashcardFirebase();
        }

        return instance;
    }

    private FlashcardFirebase(){
        mFirebase = FirebaseDatabase.getInstance();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Getter Methods:
 */
/**************************************************************************************************/

    private DatabaseReference getParentRef(){
        //flashcard -->
        return mFirebase.getReference().child(PARENT);
    }

    public DatabaseReference getUserIdRef(String userId){
        //flashcard --> userId
        return getParentRef().child(userId);
    }

    public DatabaseReference getDeckIdRef(String userId, String deckId){
        //flashcard --> userId --> deckId
        return getParentRef().child(userId).child(deckId);
    }

    public DatabaseReference getCardIdRef(String userId, String deckId, String cardId){
        //flashcard --> userId --> deckId --> cardId
        return getParentRef().child(userId).child(deckId).child(cardId);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Add Method:
 */
/**************************************************************************************************/

    public String addFlashcard(FlashcardItem item){
        DatabaseReference ref = getDeckIdRef(item.userId, item.deckId);

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

    public void requestByCardId(String userId, String deckId, String cardId, OnDataLoadListener listener){
        DatabaseReference ref = getCardIdRef(userId, deckId, cardId);

        mLoadListener = listener;

        ref.addListenerForSingleValueEvent(mEventListener);
    }

/**************************************************************************************************/


}

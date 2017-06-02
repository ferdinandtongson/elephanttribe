package me.makeachoice.elephanttribe.controller.modelside.firebase.user;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import me.makeachoice.elephanttribe.controller.modelside.firebase.base.MyFirebase;
import me.makeachoice.elephanttribe.model.contract.user.UserContract;
import me.makeachoice.elephanttribe.model.item.user.UserItem;

/**
 * UserFirebase access firebase general user data
 */

public class UserFirebase extends MyFirebase {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //PARENT - parent director
    private final static String PARENT = UserContract.PATH;


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    private static UserFirebase instance = null;

    public static UserFirebase getInstance(){
        if(instance == null){
            instance = new UserFirebase();
        }

        return instance;
    }

    private UserFirebase(){
        mFirebase = FirebaseDatabase.getInstance();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Reference Methods:
 */
/**************************************************************************************************/

    private DatabaseReference getParentRef(){
        //user -->
        return mFirebase.getReference().child(PARENT);
    }

    private DatabaseReference getUserIdRef(String userId){
        //user --> [userId]
        return getParentRef().child(userId);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Add Method:
 */
/**************************************************************************************************/

    public String addUser(UserItem item){
        DatabaseReference ref = getParentRef();

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

    public void setUser(UserItem item){
        DatabaseReference ref = getUserIdRef(item.userId);

        ref.child(UserContract.USERNAME).setValue(item.userName);

    }

    public void setEmail(UserItem item){
        DatabaseReference ref = getUserIdRef(item.userId);

        ref.child(UserContract.EMAIL).setValue(item.email);

    }

    public void setRegistration(UserItem item){
        DatabaseReference ref = getUserIdRef(item.userId);

        ref.child(UserContract.REGISTRATION).setValue(item.registration);

    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Request Method:
 */
/**************************************************************************************************/

    public void requestUser(OnDataLoadListener listener){
        //get reference
        Query query = getParentRef().orderByChild(UserContract.USERNAME);

        mLoadListener = listener;

        //add listener to reference
        query.addListenerForSingleValueEvent(mEventListener);
    }

    public void requestUserById(String userId, OnDataLoadListener listener){
        //get reference
        Query query = getUserIdRef(userId).orderByChild(UserContract.USERNAME);

        mLoadListener = listener;

        //add listener to reference
        query.addListenerForSingleValueEvent(mEventListener);
    }


/**************************************************************************************************/


}

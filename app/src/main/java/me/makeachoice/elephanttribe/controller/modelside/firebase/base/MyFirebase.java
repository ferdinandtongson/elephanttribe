package me.makeachoice.elephanttribe.controller.modelside.firebase.base;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * MyFirebase is the base class for firebase classes
 */

public class MyFirebase {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    //mFirebase - firebase instance
    protected FirebaseDatabase mFirebase;

    protected ValueEventListener mEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mLoadListener.onDataLoaded(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            mLoadListener.onCancelled();
        }
    };

    protected OnDataLoadListener mLoadListener;

    public interface OnDataLoadListener{
        void onDataLoaded(DataSnapshot dataSnapshot);
        void onCancelled();
    }

/**************************************************************************************************/

}

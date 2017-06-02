package me.makeachoice.elephanttribe.utilities;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import me.makeachoice.elephanttribe.controller.manager.Boss;
import me.makeachoice.elephanttribe.view.activity.base.MyActivity;

/**
 * Created by tongson on 4/29/17.
 */

public class SignInUtility {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Sign In and Out Methods:
 *      void signIn(...) - request user to sign in
 *      void signOut - sing out user
 */
/**************************************************************************************************/
    /*
     * void signIn(...) - request user to sign in
     */
    public static void signIn(MyActivity activity){
        //start activity with result (signed in or not)
        activity.startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setProviders(Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                .setIsSmartLockEnabled(false)
                .build(), Boss.RC_SIGN_IN
        );
    }

    /*
     * void signOut - sing out user
     */
    public static void signOut(){
        //get firebase authentication object
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();

        //sign out user
        fbAuth.signOut();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * SignIn:
 */
/**************************************************************************************************/


/**************************************************************************************************/

}

package me.makeachoice.elephanttribe.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import me.makeachoice.elephanttribe.R;


/**
 * NetworkUtility connection status info
 */

public class NetworkUtility {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private int STR_SETTINGS_ID = R.string.settings;
    private int STR_NO_NETWORK_ID = R.string.msg_no_network;

    private static NetworkUtility instance = null;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/


    public static NetworkUtility getInstance(){
        if(instance == null){
            instance = new NetworkUtility();
        }

        return instance;
    }

    private NetworkUtility(){}


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Connection Methods:
 *      boolean hasConnection(...) - check if there is network connectivity
 *      AlertDialog showNoNetworkDialog(...) - display "No network" dialog message to user
 */
/**************************************************************************************************/
    /*
     * boolean hasConnection(...) - check if there is network connectivity
     */
    public boolean hasConnection(Context ctx){
        //get connection manager
        ConnectivityManager connMgr = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        //get access to network information
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //check if we have connection
        if(networkInfo != null && networkInfo.isConnected()){
            //have connection
            return true;
        }

        //no connection
        return false;
    }

    /*
     * AlertDialog showNoNetworkDialog(...) - display "No network" dialog message to user
     */
    public AlertDialog showNoNetworkDialog(Context ctx, DialogInterface.OnClickListener refreshListener){
        //get "no network" message
        String noNetwork = ctx.getString(STR_NO_NETWORK_ID);
        String settings = ctx.getString(STR_SETTINGS_ID);

        //create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(noNetwork)
                .setCancelable(true)
                .setPositiveButton(settings, refreshListener);


        AlertDialog diaNoNetwork = builder.create();

        return diaNoNetwork;

    }

/**************************************************************************************************/


}

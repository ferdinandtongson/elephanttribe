package me.makeachoice.elephanttribe.utilities;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import me.makeachoice.elephanttribe.model.item.deck.DeckScoreItem;

/**
 * ScoreUtility used to calculate and format scores
 */

public class ScoreUtility {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private final static String FORMAT_DECIMAL = "#.##";

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * DeckScore Methods:
 */
/**************************************************************************************************/

    public static String getOverallScore(ArrayList<DeckScoreItem> scoreList){
        int count = scoreList.size();

        int total = 0;
        int correct = 0;
        for(int i = 0; i < count; i++){
            total = total + scoreList.get(i).total;
            correct = correct + scoreList.get(i).correct;
        }

        double score = (float)Math.round(correct * 100) / total;

        DecimalFormat twoDForm = new DecimalFormat(FORMAT_DECIMAL);

        return Double.valueOf(twoDForm.format(score)) + "%";
    }

    public static String getDeckScore(DeckScoreItem scoreItem){
        Log.d("Choice", "ScoreUtility.getDeckScore: ");
        Log.d("Choice", "     correct: " + scoreItem.correct);
        Log.d("Choice", "     total: " + scoreItem.total);
        double score = (float)Math.round(scoreItem.correct * 100) / scoreItem.total;

        DecimalFormat twoDForm = new DecimalFormat(FORMAT_DECIMAL);

        return Double.valueOf(twoDForm.format(score)) + "%";
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Methods:
 */
/**************************************************************************************************/



/**************************************************************************************************/

}

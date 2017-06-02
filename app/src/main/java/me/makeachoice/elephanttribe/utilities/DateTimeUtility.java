package me.makeachoice.elephanttribe.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tongson on 5/22/17.
 */

public class DateTimeUtility {

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private final static String FORMAT_TIMESTAMP = "yyyyMMddHHmmssSSS";
    private final static String FORMAT_DATESTAMP = "yyyyMMdd";

    private final static String FORMAT_DATE = "MMM dd yyyy";

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Today Methods:
 */
/**************************************************************************************************/

    public static String getToday(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);

        return dateFormat.format(cal.getTime());
    }

    public static long getTodayInMillis(){
        Calendar cal = Calendar.getInstance();

        return cal.getTimeInMillis();
    }

    public static String getTimestamp(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_TIMESTAMP);

        return dateFormat.format(cal.getTime());
    }


/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Methods:
 */
/**************************************************************************************************/



/**************************************************************************************************/

}

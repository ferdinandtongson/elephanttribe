package me.makeachoice.elephanttribe.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * DateTimeUtility is date and time helper for creating time stamps and date conversions
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
    private final static String FORMAT_DATE_TIME = "MMM dd yyyy HH:mm";

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

    public static String convertTimestampToDateTime(String timestamp){
        Long millis = Long.parseLong(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);

        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
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

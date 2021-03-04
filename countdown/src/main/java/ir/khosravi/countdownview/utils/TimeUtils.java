/*
 * Author Majid Khosravi on 05/12/2019.
 * majid.khosravi89@gmail.com
 */

package ir.khosravi.countdownview.utils;


import ir.khosravi.countdownview.model.TimeModel;

public class TimeUtils {

    //    In Milliseconds
    public static final int SECOND_IN_MILLI = 1000;
    public static final int MINUTE_IN_MILLI = 60 * SECOND_IN_MILLI;
    public static final int HOUR_IN_MILLI = 60 * MINUTE_IN_MILLI;
    public static final int DAY_IN_MILLIS = 24 * HOUR_IN_MILLI;

    public static TimeModel getRemainedTime(long milliSeconds) {
        long seconds = milliSeconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return new TimeModel(days, hours % 24, minutes % 60, seconds % 60);
    }

    public static int getMaxDay(long milliSeconds) {
        return (int) getRemainedTime(milliSeconds).getDays();
    }


}

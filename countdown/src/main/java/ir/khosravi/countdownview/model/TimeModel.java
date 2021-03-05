/*
 * Author Majid Khosravi on 05/12/2019.
 * majid.khosravi89@gmail.com
 */

package ir.khosravi.countdownview.model;

public class TimeModel {

    private long milliSeconds;
    private float days;
    private float hours;
    private float minutes;
    private float seconds;

    public TimeModel(long milliSeconds,float days, float hours, float minutes, float seconds) {
        this.milliSeconds = milliSeconds;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public float getDays() {
        return days;
    }

    public float getHours() {
        return hours;
    }

    public float getMinutes() {
        return minutes;
    }

    public float getSeconds() {
        return seconds;
    }

    public long getMilliSeconds() {
        return milliSeconds;
    }
}

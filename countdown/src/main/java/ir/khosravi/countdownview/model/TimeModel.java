/*
 * Author Majid Khosravi on 05/12/2019.
 * majid.khosravi89@gmail.com
 */

package ir.khosravi.countdownview.model;

public class TimeModel {

    private float days;
    private float hours;
    private float minutes;
    private float seconds;

    public TimeModel(float days, float hours, float minutes, float seconds) {
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
}

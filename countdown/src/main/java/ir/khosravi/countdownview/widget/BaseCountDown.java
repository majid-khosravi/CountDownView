package ir.khosravi.countdownview.widget;

public interface BaseCountDown {


    void setTimes(long currentTime, long startTime, TimerStat timerStatListener);

    void startTimer(long remainTime);

    void setTimerFinished();


    interface TimerStat {
        void onTimerFinishedListener();
    }

}

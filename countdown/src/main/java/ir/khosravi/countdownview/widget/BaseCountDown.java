package ir.khosravi.countdownview.widget;

public interface BaseCountDown {


    void setTimes(long currentTime, long startTime, TimerState timerStateListener);

    void startTimer(long remainTime);

    void setTimerFinished();


    interface TimerState {
        void onTimerFinishedListener();
        void onButtonClickedListener();
    }

    public class SimpleTimerState implements TimerState{

        @Override
        public void onTimerFinishedListener() {

        }

        @Override
        public void onButtonClickedListener() {

        }
    }

}

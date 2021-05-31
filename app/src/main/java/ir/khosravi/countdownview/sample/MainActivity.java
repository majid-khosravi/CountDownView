package ir.khosravi.countdownview.sample;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ir.khosravi.countdownview.utils.TimeUtils;
import ir.khosravi.countdownview.widget.BaseCountDown;
import ir.khosravi.countdownview.widget.CountDownByEmptyView;
import ir.khosravi.countdownview.widget.CountDownView;

public class MainActivity extends AppCompatActivity {

    private final long THREE_MINUTE_IN_MILLIS = TimeUtils.MINUTE_IN_MILLI + TimeUtils.SECOND_IN_MILLI * 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CountDownByEmptyView countDownView = findViewById(R.id.count_down);
        countDownView.setTimes(System.currentTimeMillis(), System.currentTimeMillis() + THREE_MINUTE_IN_MILLIS, new BaseCountDown.TimerState() {
            @Override
            public void onTimerFinishedListener() {

            }

            @Override
            public void onButtonClickedListener() {
                Log.e("Majid", "onButtonClickedListener");
            }
        });
        countDownView.setBackgroundImage(getResources().getDrawable(R.drawable.ic_launcher_background));
    }
}
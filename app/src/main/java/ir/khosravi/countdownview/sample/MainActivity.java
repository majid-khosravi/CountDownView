package ir.khosravi.countdownview.sample;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ir.khosravi.countdownview.utils.TimeUtils;
import ir.khosravi.countdownview.widget.CountDownView;

public class MainActivity extends AppCompatActivity {

    private final long THREE_MINUTE_IN_MILLIS = TimeUtils.MINUTE_IN_MILLI + TimeUtils.SECOND_IN_MILLI * 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CountDownView countDownView = findViewById(R.id.count_down);
        countDownView.setTimes(System.currentTimeMillis(), System.currentTimeMillis() + THREE_MINUTE_IN_MILLIS, new CountDownView.TimerStat() {
            @Override
            public void onTimerFinishedListener() {

            }
        });
        countDownView.setBackgroundImage(getResources().getDrawable(R.drawable.ic_launcher_background));
    }
}
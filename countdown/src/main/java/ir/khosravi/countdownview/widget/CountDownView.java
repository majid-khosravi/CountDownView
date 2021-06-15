package ir.khosravi.countdownview.widget;

/*
 * Author Majid Khosravi on 05/12/2019.
 * Farakav.com
 * majid.khosravi89@gmail.com
 */


import android.content.Context;
import android.content.res.TypedArray;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import ir.khosravi.countdownview.R;
import ir.khosravi.countdownview.databinding.RemainTimeViewBinding;
import ir.khosravi.countdownview.model.TimeModel;
import ir.khosravi.countdownview.utils.DimenUtils;
import ir.khosravi.countdownview.utils.TimeUtils;


public class CountDownView extends ConstraintLayout {

    private Context mContext;
    private String title;
    private int fontFamily;
    private float titleSize;
    private int backgroundImage = 0;
    private float progressStrokeWidth;

    private RemainTimeViewBinding mBinding;
    private CountDownTimer mCountDownTimer;
    private TimerStat mTimerStatListener;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        title = getResources().getString(R.string.text_title);
        fontFamily = R.font.iran_yekan_bold;
        titleSize = DimenUtils.sp2px(getResources(), 16);
        progressStrokeWidth = (int) DimenUtils.dp2px(getResources(), 5);

        final TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.CountDownView, defStyleAttr, 0);
        initByAttributes(typedArray);
        typedArray.recycle();

        initViews();
    }

    private void initByAttributes(TypedArray attributes) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.remain_time_view, this, true);
        if (attributes.getString(R.styleable.CountDownView_title) != null)
            title = attributes.getString(R.styleable.CountDownView_title);
        fontFamily = attributes.getResourceId(R.styleable.CountDownView_fontFace, fontFamily);
        titleSize = attributes.getDimension(R.styleable.CountDownView_titleSize, titleSize);
        backgroundImage = attributes.getResourceId(R.styleable.CountDownView_backgroundImage, backgroundImage);
        progressStrokeWidth = attributes.getDimension(R.styleable.CountDownView_progressStrokeWidth, progressStrokeWidth);

    }

    private void initViews() {
//        setBackgroundColor(Color.rgb(200, 200, 200));

        setProgressAttributes(mBinding.donutProgressDays);
        setProgressAttributes(mBinding.donutProgressHours);
        setProgressAttributes(mBinding.donutProgressMinutes);
        setProgressAttributes(mBinding.donutProgressSeconds);

        if (backgroundImage != 0)
            setBackgroundImage(getResources().getDrawable(backgroundImage));
        setTitleText(title);
        setTitleSize(titleSize);
        mBinding.textStart.setTypeface(ResourcesCompat.getFont(mContext, fontFamily));
    }

    private void setProgressAttributes(DonutProgress progress) {
        progress.setFinishedStrokeWidth(progressStrokeWidth);
        progress.setUnfinishedStrokeWidth(progressStrokeWidth);
        progress.setFontFace(fontFamily);
    }

    public void setTimes(long currentTime, long startTime, TimerStat timerStatListener) {
        mTimerStatListener = timerStatListener;
        long remainTime = startTime - currentTime;
        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        startTimer(Math.abs(remainTime));
    }

    private void startTimer(long remainTime) {
        mBinding.donutProgressDays.setMax(TimeUtils.getMaxDay(remainTime));
        if (remainTime > 0) {
            if (mCountDownTimer == null) {
                mCountDownTimer = new CountDownTimer(remainTime, TimeUtils.SECOND_IN_MILLI) {
                    @Override
                    public void onTick(long millisUntilFinished) {
//                        setTimerFinished(false);
                        setVisibleProgressBars(millisUntilFinished);

                        TimeModel timeModel = TimeUtils.getRemainedTime(Math.abs(millisUntilFinished));
                        mBinding.donutProgressDays.setProgress(timeModel.getDays());
                        mBinding.donutProgressHours.setProgress(timeModel.getHours());
                        mBinding.donutProgressMinutes.setProgress(timeModel.getMinutes());
                        mBinding.donutProgressSeconds.setProgress(timeModel.getSeconds());
                    }

                    @Override
                    public void onFinish() {
                        setTimerFinished();
                    }
                };
                mCountDownTimer.start();
            }
        } else {
            setTimerFinished();
        }
    }

    private void setTimerFinished() {
        if (mTimerStatListener != null)
            mTimerStatListener.onTimerFinishedListener();
    }

    private void setVisibleProgressBars(long millisUntilFinished) {
        mBinding.donutProgressDays.setVisibility(millisUntilFinished >= TimeUtils.DAY_IN_MILLIS ? View.VISIBLE : View.GONE);
        mBinding.donutProgressHours.setVisibility(millisUntilFinished >= TimeUtils.HOUR_IN_MILLI ? View.VISIBLE : View.GONE);
        mBinding.donutProgressMinutes.setVisibility(millisUntilFinished >= TimeUtils.MINUTE_IN_MILLI ? View.VISIBLE : View.GONE);
        mBinding.donutProgressSeconds.setVisibility(millisUntilFinished >= TimeUtils.SECOND_IN_MILLI ? View.VISIBLE : View.GONE);
    }

    public void setBackgroundImage(Drawable imageRes) {
        mBinding.imageBackground.setImageDrawable(imageRes);
    }

    public AppCompatImageView getBackgroundImageView() {
        return mBinding.imageBackground;
    }

    public void setTitleText(String text) {
        mBinding.textStart.setText(text);
    }

    public void setTitleSize(float size) {
        mBinding.textStart.setTextSize(size);
    }

    public interface TimerStat {
        void onTimerFinishedListener();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);

        setProgressWidthAndHeight(parentWidth, mBinding.donutProgressDays);
        setProgressWidthAndHeight(parentWidth, mBinding.donutProgressHours);
        setProgressWidthAndHeight(parentWidth, mBinding.donutProgressMinutes);
        setProgressWidthAndHeight(parentWidth, mBinding.donutProgressSeconds);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setProgressWidthAndHeight(int parentWidth, DonutProgress progressView) {
        LayoutParams params = (LayoutParams) progressView.getLayoutParams();

        params.width = parentWidth / 6;
        params.height = parentWidth / 6;
        params.leftMargin = params.width / 8;
        params.rightMargin = params.width / 8;

        progressView.setLayoutParams(params);

    }

}

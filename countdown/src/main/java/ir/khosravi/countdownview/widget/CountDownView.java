
/*
 * Author Majid Khosravi on 05/12/2019.
 * majid.khosravi89@gmail.com
 */


package ir.khosravi.countdownview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import ir.khosravi.countdownview.R;
import ir.khosravi.countdownview.databinding.RemainTimeViewBinding;
import ir.khosravi.countdownview.model.TimeModel;
import ir.khosravi.countdownview.utils.TimeUtils;

import static ir.khosravi.countdownview.utils.DimenUtils.INVALID_VALUE;

public class CountDownView extends ConstraintLayout implements BaseCountDown {

    @FontRes
    private int mFontResId;
    @StringRes
    private int mTitleResId;
    @DimenRes
    private int mTitleSizeResId;
    @DimenRes
    private int mProgressStrokeWidthResId;
    @DrawableRes
    private int mBackgroundImage;

    private RemainTimeViewBinding mBinding;
    private CountDownTimer mCountDownTimer;
    private TimerStat mTimerStatListener;
    private TimeModel mTimeModel;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CountDownView, defStyleAttr, 0);
        initByAttributes(typedArray);
        typedArray.recycle();
        initViews();
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

    @Override
    public void setTimes(long currentTime, long startTime, TimerStat timerStatListener) {
        mTimerStatListener = timerStatListener;
        long remainTime = startTime - currentTime;
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mTimeModel = TimeUtils.getRemainedTime(Math.abs(Math.abs(remainTime)));
        startTimer(Math.abs(remainTime));
    }

    @Override
    public void startTimer(long remainTime) {
        mBinding.donutProgressDays.setMax(TimeUtils.getMaxDay(remainTime));
        if (remainTime > 0) {
            if (mCountDownTimer == null) {
                mCountDownTimer = new CountDownTimer(remainTime, TimeUtils.SECOND_IN_MILLI) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTimeModel = TimeUtils.getRemainedTime(Math.abs(millisUntilFinished));
                        mBinding.setObj(mTimeModel);
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

    @Override
    public void setTimerFinished() {
        if (mTimerStatListener != null)
            mTimerStatListener.onTimerFinishedListener();
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

    public void setTitleSizeResId(@DimenRes int size) {
        mBinding.textStart.setTextSize(size);
    }

    private void setProgressWidthAndHeight(int parentWidth, DonutProgress progressView) {
        LayoutParams params = (LayoutParams) progressView.getLayoutParams();

        params.width = parentWidth / 6;
        params.height = parentWidth / 6;
        params.leftMargin = params.width / 8;
        params.rightMargin = params.width / 8;

        progressView.setLayoutParams(params);

    }

    private void initByAttributes(TypedArray attributes) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.remain_time_view, this, true);
        mTitleResId = attributes.getResourceId(R.styleable.CountDownView_title, R.string.to_start);
        mFontResId = attributes.getResourceId(R.styleable.CountDownView_fontFace, R.font.iran_yekan_bold);
        mTitleSizeResId = attributes.getResourceId(R.styleable.CountDownView_titleSize, R.dimen.text_title_size_large);
        mBackgroundImage = attributes.getResourceId(R.styleable.CountDownView_backgroundImage, INVALID_VALUE);
        mProgressStrokeWidthResId = attributes.getResourceId(R.styleable.CountDownView_progressStrokeWidth, R.dimen.donut_progress_stroke_width);
        mBinding.setObj(mTimeModel);
    }

    private void initViews() {
        setBackgroundColor(Color.rgb(200, 200, 200));

        setProgressAttributes();

        if (mBackgroundImage != INVALID_VALUE)
            setBackgroundImage(getResources().getDrawable(mBackgroundImage));
        setTitleText(getResources().getString(mTitleResId));
        setTitleSizeResId(mTitleSizeResId);
        setTextFontFace(mFontResId);
    }

    private void setProgressAttributes() {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                if (childView instanceof DonutProgress) {
                    DonutProgress progress = (DonutProgress) childView;
                    progress.setFinishedStrokeWidth(getResources().getDimension(mProgressStrokeWidthResId));
                    progress.setUnfinishedStrokeWidth(getResources().getDimension(mProgressStrokeWidthResId));
                    progress.setFontFace(mFontResId);
                }
            }
        }
    }

    private void setTextFontFace(@FontRes int fontResId) {
        mBinding.textStart.setTypeface(ResourcesCompat.getFont(getContext(), fontResId));
    }
}

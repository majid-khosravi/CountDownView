package ir.khosravi.countdownview.utils;

import android.view.View;

import androidx.databinding.BindingAdapter;

import ir.khosravi.countdownview.R;
import ir.khosravi.countdownview.model.TimeModel;
import ir.khosravi.countdownview.widget.DonutProgress;

public class CustomViewBindingAdapters {

    @BindingAdapter("progress")
    public static void setProgress(DonutProgress progress, TimeModel timeModel) {
        if (progress != null && timeModel != null) {
            int id = progress.getId();
            if (id == R.id.donutProgressSeconds) {
                progress.setProgress(timeModel.getSeconds());
                progress.setVisibility(timeModel.getSeconds() > 0 ? View.VISIBLE : View.GONE);
            } else if (id == R.id.donutProgressMinutes) {
                progress.setProgress(timeModel.getMinutes());
                progress.setVisibility(timeModel.getMinutes() > 0  ? View.VISIBLE : View.GONE);
            } else if (id == R.id.donutProgressHours) {
                progress.setProgress(timeModel.getHours());
                progress.setVisibility(timeModel.getHours() > 0  ? View.VISIBLE : View.GONE);
            } else if (id == R.id.donutProgressDays) {
                progress.setProgress(timeModel.getDays());
                progress.setVisibility(timeModel.getDays() > 0  ? View.VISIBLE : View.GONE);
            }
        }

    }
}

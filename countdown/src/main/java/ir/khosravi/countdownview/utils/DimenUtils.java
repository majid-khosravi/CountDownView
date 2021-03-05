/*
 * Author Majid Khosravi on 05/12/2019.
 * majid.khosravi89@gmail.com
 */

package ir.khosravi.countdownview.utils;

import android.content.res.Resources;

public class DimenUtils {

    public static int INVALID_VALUE = -1;

    private DimenUtils() {
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
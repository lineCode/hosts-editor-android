package com.nilhcem.hostseditor.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class Compatibility {

    private Compatibility() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if current SDK is compatible with the desired API level.
     *
     * @param apiLevel the required API level.
     * @return {@code true} if current OS is compatible.
     */
    private static boolean isCompatible(int apiLevel) {
        return android.os.Build.VERSION.SDK_INT >= apiLevel;
    }

    @SuppressWarnings("deprecation")
    public static Point getScreenDimensions(Context context) {
        boolean oldWay = true;
        Point size = new Point();

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Compatibility.isCompatible(Build.VERSION_CODES.HONEYCOMB_MR2)) {
            try {
                Method method = Display.class.getDeclaredMethod("getSize",
                        new Class[]{Point.class});
                method.invoke(display, size);
                oldWay = false;
            } catch (Exception e) {
                oldWay = true;
            }
        }
        if (oldWay) {
            size.set(display.getWidth(), display.getHeight());
        }
        return size;
    }

    /**
     * This method converts dp unit to equivalent device specific value in pixels.
     *
     * @param dp      A value in dp (Device independent pixels) unit. Which we need to convert into pixels.
     * @param context Context to get resources and device specific display metrics.
     * @return A float value to represent Pixels equivalent to dp according to device.
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }
}

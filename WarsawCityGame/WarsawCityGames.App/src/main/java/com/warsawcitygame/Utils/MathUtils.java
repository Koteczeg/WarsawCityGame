package com.warsawcitygame.Utils;

import android.graphics.RectF;


public final class MathUtils {

    public static float truncate(float f, int decimalPlaces)
    {
        float decimalShift = (float) Math.pow(10, decimalPlaces);
        return Math.round(f * decimalShift) / decimalShift;
    }

    public static boolean haveSameAspectRatio(RectF r1, RectF r2)
    {
        float srcRectRatio = MathUtils.truncate(MathUtils.getRectRatio(r1), 2);
        float dstRectRatio = MathUtils.truncate(MathUtils.getRectRatio(r2), 2);
        return (Math.abs(srcRectRatio - dstRectRatio) <= 0.01f);
    }

    public static float getRectRatio(RectF rect) {
        return rect.width() / rect.height();
    }
}
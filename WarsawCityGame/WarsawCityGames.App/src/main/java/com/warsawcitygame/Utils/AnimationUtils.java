package com.warsawcitygame.Utils;

import android.content.Context;
import android.view.animation.Animation;

import com.warsawcitygame.R;

public class AnimationUtils
{
    public static final int buttonFadeOutAnimationDelay = 250;

    public static Animation getFadeOutAnimation(Context context)
    {
        return android.view.animation.AnimationUtils.loadAnimation(context, R.anim.fade_out);
    }
}

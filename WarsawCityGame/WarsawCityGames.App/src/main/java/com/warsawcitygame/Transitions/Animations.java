package com.warsawcitygame.Transitions;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;


public class Animations
{
    public static void fadeOutAnimationNewFragment(final FrameLayout fl)
    {
        final int dur = 800;
        Animation fadeOut = new AlphaAnimation(0f, 1f);
        fadeOut.setDuration(dur);
        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                fl.setVisibility(View.VISIBLE);
            }
        });
        fl.startAnimation(fadeOut);
    }
}
